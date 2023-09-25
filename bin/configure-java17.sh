#!/usr/bin/env bash

# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

usage() {
    echo "Usage: $0 /path/to/hive/installation"
    echo
    echo "Example: $0 /opt/mapr/hive/hive-3.1.3"
    exit 1
}

export MAPR_COMMON_JAVA_OPTS="$MAPR_COMMON_JAVA_OPTS --add-opens java.base/java.lang=ALL-UNNAMED -XX:+UseParallelGC --add-opens java.base/java.net=ALL-UNNAMED --add-opens java.base/java.nio=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED --add-opens java.base/java.util.concurrent.atomic=ALL-UNNAMED --add-opens java.base/java.util.regex=ALL-UNNAMED --add-opens java.base/java.time=ALL-UNNAMED"

if [[ ! -d "$1" ]]; then
    echo -e "ERROR: Invalid hive installation path: '$1'\n"
    usage
    exit 2
else
    hive_home="$1"

    echo "Using '$hive_home' path as hive home folder"

    conf_tool_bin="$hive_home/bin/conftool"
    hive_site="$hive_home/conf/hive-site.xml"

    for file in "$conf_tool_bin" "$hive_site"; do
        if [[ ! -f "$file" ]]; then
            echo "ERROR: expected to find '$file', but it is not a regular file"
            exit 3
        else
            echo "Found '$file'"
        fi
    done
fi

# ------------ hive-site.xml configuration block

check_prop() {
    2>/dev/null "$conf_tool_bin" -path "$hive_site" -existProperty "$1"
}

add_prop() {
    2>/dev/null "$conf_tool_bin" -path "$hive_site" -addProperty "$1"
}

declare -A j17opts
j17opts["mapreduce.map.java.opts"]="-Xmx900m --add-opens java.base/java.lang=ALL-UNNAMED -XX:+UseParallelGC --add-opens java.base/java.net=ALL-UNNAMED --add-opens java.base/java.nio=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED --add-opens java.base/java.util.concurrent.atomic=ALL-UNNAMED --add-opens java.base/java.util.regex=ALL-UNNAMED --add-opens java.base/java.time=ALL-UNNAMED"
j17opts["mapreduce.reduce.java.opts"]="-Xmx2560m --add-opens java.base/java.lang=ALL-UNNAMED -XX:+UseParallelGC --add-opens java.base/java.net=ALL-UNNAMED --add-opens java.base/java.nio=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED --add-opens java.base/java.util.concurrent.atomic=ALL-UNNAMED --add-opens java.base/java.util.regex=ALL-UNNAMED --add-opens java.base/java.time=ALL-UNNAMED"
j17opts["yarn.app.mapreduce.am.command-opts"]="-Xmx2560m --add-opens java.base/java.lang=ALL-UNNAMED -XX:+UseParallelGC --add-opens java.base/java.net=ALL-UNNAMED --add-opens java.base/java.nio=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED --add-opens java.base/java.util.concurrent.atomic=ALL-UNNAMED --add-opens java.base/java.util.regex=ALL-UNNAMED --add-opens java.base/java.time=ALL-UNNAMED"

for opt_name in "${!j17opts[@]}"; do
    echo -n "Checking option existence: '$opt_name' . . . "
    res=$(check_prop "$opt_name")
    echo $res

    if [ "$res" == "true" ]; then
      echo "Option '$opt_name' is already present in '$hive_site', please modify manually. Suggested value is as follows:"
      echo "${j17opts[$opt_name]}"
      echo
      continue
    elif [ "$res" == "false" ]; then
      echo -n "Creating property '$opt_name' . . . "
      add_prop "$opt_name=${j17opts[$opt_name]}"
      echo -e "done\n"
      continue
    else
      echo "ERROR getting property status."
      echo "Check '$hive_home/logs/conftool.log' for details."
      echo "Unexpected system state, exiting."
      exit 1
    fi
done

# ------------ hive-env.sh configuration block

hive_env="$hive_home/conf/hive-env.sh"
grep="/bin/grep"
line="export HADOOP_OPTS=\"\$HADOOP_OPTS -XX:+IgnoreUnrecognizedVMOptions --add-opens java.base/java.time=ALL-UNNAMED\""

if [[ ! -f "$hive_env" ]]; then
    echo "$line" > "$hive_env"
    echo "Created '$hive_env' with configuration for Java 17"
else
    if ! "$grep" -qFx "$line" "$hive_env"; then
        echo "$line" >> "$hive_env"
        echo "Appended '$hive_env' with configuration for Java 17"
    else
        echo "Configuration for Java 17 already exists in '$hive_env'"
    fi
fi

echo "All done."
