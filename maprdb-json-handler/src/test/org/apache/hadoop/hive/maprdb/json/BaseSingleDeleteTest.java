package org.apache.hadoop.hive.maprdb.json;

import org.apache.hadoop.hive.maprdb.json.input.HiveMapRDBJsonInputFormat;
import org.apache.hadoop.hive.maprdb.json.output.HiveMapRDBJsonOutputFormat;
import org.apache.hadoop.hive.maprdb.json.serde.MapRDBSerDe;
import org.apache.hadoop.hive.ql.io.HiveBinaryOutputFormat;
import org.apache.hadoop.hive.ql.io.HiveInputFormat;
import org.apache.hadoop.hive.ql.metadata.HiveException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.hadoop.hive.maprdb.json.conf.MapRDBConstants.MAPRDB_COLUMN_ID;
import static org.apache.hadoop.hive.maprdb.json.conf.MapRDBConstants.MAPRDB_IS_IN_TEST_MODE;
import static org.apache.hadoop.hive.maprdb.json.conf.MapRDBConstants.MAPRDB_TABLE_NAME;
import static org.apache.hadoop.hive.metastore.api.hive_metastoreConstants.META_TABLE_STORAGE;

class BaseSingleDeleteTest extends BaseExplainTest {

  private static final String CUSTOMER_DB_JSON = "customer_db_json";
  private static final String DEPARTMENT = "department";
  BaseSingleDeleteTest(String query) {
    this.query = query;
  }

  BaseSingleDeleteTest(){query = "";}

  @Override void createTables() throws HiveException {
    Map<String, String> commonParams = new HashMap<>();
    commonParams.put(MAPRDB_COLUMN_ID, "id");
    commonParams.put(MAPRDB_IS_IN_TEST_MODE, "true");
    commonParams.put(META_TABLE_STORAGE, MapRDBJsonStorageHandler.class.getName());

    List<String> columnsDepartment = Arrays.asList("id", "name");
    Map<String, String> targetParams = new HashMap<>(commonParams);
    targetParams.put(MAPRDB_TABLE_NAME, "/" + CUSTOMER_DB_JSON);

    cleanupTables();
    List<String> columns = Arrays.asList("id", "first_name", "last_name", "age");
    db.createTable(CUSTOMER_DB_JSON, columns, null, HiveMapRDBJsonInputFormat.class,
        HiveMapRDBJsonOutputFormat.class, -1, null, targetParams, MapRDBSerDe.class);
    db.createTable(DEPARTMENT, columnsDepartment, null, HiveInputFormat.class, HiveBinaryOutputFormat.class);

  }

  void cleanupTables() throws HiveException {
    if (db != null) {
      db.dropTable(CUSTOMER_DB_JSON);
      db.dropTable(DEPARTMENT);
    }
  }
}
