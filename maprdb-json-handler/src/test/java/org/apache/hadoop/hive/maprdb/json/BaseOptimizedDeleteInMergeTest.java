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

class BaseOptimizedDeleteInMergeTest extends BaseExplainTest {

  private static final String TGT = "tgt";
  private static final String SRC = "src";
  private static final String SRC_SEQ = "src_seq";

  BaseOptimizedDeleteInMergeTest(String query) {
    this.query = query;
  }

  BaseOptimizedDeleteInMergeTest() {
    query = "";
  }

  @Override
  void createTables() throws HiveException {
    Map<String, String> commonParams = new HashMap<>();
    commonParams.put(MAPRDB_COLUMN_ID, "id");
    commonParams.put(MAPRDB_IS_IN_TEST_MODE, "true");
    commonParams.put(META_TABLE_STORAGE, MapRDBJsonStorageHandler.class.getName());

    List<String> columns = Arrays.asList("id", "value");
    Map<String, String> targetParams = new HashMap<>(commonParams);
    targetParams.put(MAPRDB_TABLE_NAME, "/" + TGT);

    Map<String, String> sourceParams = new HashMap<>(commonParams);
    sourceParams.put(MAPRDB_TABLE_NAME, "/" + SRC);

    cleanupTables();
    db.createTable(TGT, columns, null, HiveMapRDBJsonInputFormat.class, HiveMapRDBJsonOutputFormat.class, -1, null,
        targetParams, MapRDBSerDe.class);
    db.createTable(SRC, columns, null, HiveMapRDBJsonInputFormat.class, HiveMapRDBJsonOutputFormat.class, -1, null,
        sourceParams, MapRDBSerDe.class);
    db.createTable(SRC_SEQ, columns, null, HiveInputFormat.class, HiveBinaryOutputFormat.class);

  }

  void cleanupTables() throws HiveException {
    if (db != null) {
      db.dropTable(TGT);
      db.dropTable(SRC);
      db.dropTable(SRC_SEQ);
    }
  }
}
