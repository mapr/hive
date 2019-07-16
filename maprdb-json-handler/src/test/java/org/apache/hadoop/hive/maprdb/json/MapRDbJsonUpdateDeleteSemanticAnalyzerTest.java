package org.apache.hadoop.hive.maprdb.json;

import com.google.common.io.Resources;
import org.apache.hadoop.hive.maprdb.json.input.HiveMapRDBJsonInputFormat;
import org.apache.hadoop.hive.maprdb.json.output.HiveMapRDBJsonOutputFormat;
import org.apache.hadoop.hive.maprdb.json.serde.MapRDBSerDe;
import org.apache.hadoop.hive.ql.io.HiveBinaryOutputFormat;
import org.apache.hadoop.hive.ql.io.HiveInputFormat;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.parse.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

import static org.apache.hadoop.hive.maprdb.json.conf.MapRDBConstants.MAPRDB_COLUMN_ID;
import static org.apache.hadoop.hive.maprdb.json.conf.MapRDBConstants.MAPRDB_IS_IN_TEST_MODE;
import static org.apache.hadoop.hive.maprdb.json.conf.MapRDBConstants.MAPRDB_TABLE_NAME;
import static org.apache.hadoop.hive.metastore.api.hive_metastoreConstants.META_TABLE_STORAGE;

@RunWith(Parameterized.class) public class MapRDbJsonUpdateDeleteSemanticAnalyzerTest extends BaseExplainTest {

  private static final Logger LOG = LoggerFactory.getLogger(MapRDbJsonUpdateDeleteSemanticAnalyzerTest.class.getName());
  private static final String CUSTOMER_DB_JSON_TARGET = "customer_db_json_target";
  private static final String CUSTOMER_DB_JSON_SOURCE = "customer_db_json_source";
  private static final String CUSTOMER_DB_JSON_ALL_TARGET = "customer_db_json_all_target";
  private static final String DEPARTMENT = "department";
  private static final String CUSTOMER_WEST = "customer_west";
  private static final String CUSTOMER_EAST = "customer_east";

  public MapRDbJsonUpdateDeleteSemanticAnalyzerTest(String query) {
    this.query = query;
  }

  @Parameterized.Parameters public static Collection queries() throws IOException, URISyntaxException {
    List<String> queries = new ArrayList<>();
    URL url = Resources.getResource("update-merge");
    File folder = new File(url.toURI());
    for (File file : folder.listFiles()) {
      queries.add(readFile(file.getPath()));
    }
    List<String[]> arguments = new ArrayList<>();
    for (String query : queries) {
      arguments.add(new String[] { query });
    }
    return arguments;
  }

  @Before public void setup() throws HiveException {
    super.setup();
  }

  @Override void createTables() throws HiveException {

    Map<String, String> commonParams = new HashMap<>();
    commonParams.put(MAPRDB_COLUMN_ID, "id");
    commonParams.put(MAPRDB_IS_IN_TEST_MODE, "true");
    commonParams.put(META_TABLE_STORAGE, MapRDBJsonStorageHandler.class.getName());

    Map<String, String> sourceParams = new HashMap<>(commonParams);
    sourceParams.put(MAPRDB_TABLE_NAME, "/" + CUSTOMER_DB_JSON_SOURCE);

    Map<String, String> targetParams = new HashMap<>(commonParams);
    targetParams.put(MAPRDB_TABLE_NAME, "/" + CUSTOMER_DB_JSON_TARGET);

    Map<String, String> targetAllParams = new HashMap<>(commonParams);
    targetAllParams.put(MAPRDB_TABLE_NAME, "/" + CUSTOMER_DB_JSON_ALL_TARGET);

    cleanupTables();
    List<String> columns = Arrays.asList("id", "first_name", "last_name", "age");
    List<String> columnsAll = Arrays.asList("id", "first_name", "last_name", "age", "department");
    List<String> columnsCustomer = Arrays.asList("id", "first_name", "last_name", "age", "department_id");
    List<String> columnsDepartment = Arrays.asList("id", "name");
    db.createTable(CUSTOMER_DB_JSON_TARGET, columns, null, HiveMapRDBJsonInputFormat.class,
        HiveMapRDBJsonOutputFormat.class, -1, null, targetParams, MapRDBSerDe.class);
    db.createTable(CUSTOMER_DB_JSON_SOURCE, columns, null, HiveMapRDBJsonInputFormat.class,
        HiveMapRDBJsonOutputFormat.class, -1, null, sourceParams, MapRDBSerDe.class);
    db.createTable(CUSTOMER_DB_JSON_ALL_TARGET, columnsAll, null, HiveMapRDBJsonInputFormat.class,
        HiveMapRDBJsonOutputFormat.class, -1, null, targetAllParams, MapRDBSerDe.class);
    db.createTable(DEPARTMENT, columnsDepartment, null, HiveInputFormat.class, HiveBinaryOutputFormat.class);
    db.createTable(CUSTOMER_WEST, columnsCustomer, null, HiveInputFormat.class, HiveBinaryOutputFormat.class);
    db.createTable(CUSTOMER_EAST, columnsCustomer, null, HiveInputFormat.class, HiveBinaryOutputFormat.class);
  }

  @Test public void testPositive() throws Exception {
    try {
      ReturnInfo rc = parseAndAnalyze(query);
      LOG.info(explain((SemanticAnalyzer) rc.sem, rc.plan));
    } finally {
      cleanupTables();
    }
  }

  private void cleanupTables() throws HiveException {
    if (db != null) {
      db.dropTable(CUSTOMER_DB_JSON_TARGET);
      db.dropTable(CUSTOMER_DB_JSON_SOURCE);
      db.dropTable(CUSTOMER_DB_JSON_ALL_TARGET);
      db.dropTable(DEPARTMENT);
      db.dropTable(CUSTOMER_WEST);
      db.dropTable(CUSTOMER_EAST);
    }
  }
}
