package org.apache.hadoop.hive.maprdb.json;

import org.apache.hadoop.hive.maprdb.json.input.HiveMapRDBJsonInputFormat;
import org.apache.hadoop.hive.maprdb.json.output.HiveMapRDBJsonOutputFormat;
import org.apache.hadoop.hive.maprdb.json.serde.MapRDBSerDe;
import org.apache.hadoop.hive.ql.metadata.HiveException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.hadoop.hive.maprdb.json.conf.MapRDBConstants.MAPRDB_COLUMN_ID;
import static org.apache.hadoop.hive.maprdb.json.conf.MapRDBConstants.MAPRDB_IS_IN_TEST_MODE;
import static org.apache.hadoop.hive.maprdb.json.conf.MapRDBConstants.MAPRDB_TABLE_NAME;
import static org.apache.hadoop.hive.metastore.api.hive_metastoreConstants.META_TABLE_STORAGE;

public class BaseFetchByIdOptimizerTest extends BaseExplainTest {
  private static final String TABLE_NAME = "t";

  @Override void createTables() throws HiveException {
    Map<String, String> commonParams = new HashMap<>();
    commonParams.put(MAPRDB_COLUMN_ID, "id");
    commonParams.put(MAPRDB_IS_IN_TEST_MODE, "true");
    commonParams.put(META_TABLE_STORAGE, MapRDBJsonStorageHandler.class.getName());
    Map<String, String> targetParams = new HashMap<>(commonParams);
    targetParams.put(MAPRDB_TABLE_NAME, "/" + TABLE_NAME);

    cleanupTables();
    List<String> columns = Arrays.asList("id", "first_name", "last_name", "age");
    db.createTable(TABLE_NAME, columns, null, HiveMapRDBJsonInputFormat.class, HiveMapRDBJsonOutputFormat.class, -1,
        null, targetParams, MapRDBSerDe.class);
  }

  void cleanupTables() throws HiveException {
    if (db != null) {
      db.dropTable(TABLE_NAME);
    }
  }
}
