#!/bin/sh
new_version=$1

if [ -z "$new_version" ]; then
    echo "New version can't be empty" >&2
    exit 1
fi

old_version=$(mvn -B -f pom.xml help:evaluate -Dexpression=project.version -q -DforceStdout)
echo "replacing hive version                '${old_version}' --> '${new_version}'"

find . -type f -name pom.xml -exec sed -i "s|<version>${old_version}</version>|<version>${new_version}</version>|" {} \;

# storage-api version chage
storage_api_pom=storage-api/pom.xml
ts=$(echo ${new_version} | cut -d v -f 2)
old_storage_api=$(mvn -B -f ${storage_api_pom} help:evaluate -Dexpression=project.version -q -DforceStdout)
new_storage_api=${old_storage_api}-v${ts}
echo "replacing hive storage-api version    '${old_storage_api}' --> '${new_storage_api}'"
sed -i "s|<version>${old_storage_api}</version>|<version>${new_storage_api}</version>|" ${storage_api_pom}
sed -i "s|<storage-api.version>${old_storage_api}</storage-api.version>|<storage-api.version>${new_storage_api}</storage-api.version>|" pom.xml standalone-metastore/pom.xml
