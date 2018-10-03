package org.apache.hadoop.hive.maprdb.json;

import com.google.common.io.Resources;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.maprdb.json.input.HiveMapRDBJsonInputFormat;
import org.apache.hadoop.hive.maprdb.json.output.HiveMapRDBJsonOutputFormat;
import org.apache.hadoop.hive.maprdb.json.serde.MapRDBSerDe;
import org.apache.hadoop.hive.ql.Context;
import org.apache.hadoop.hive.ql.QueryPlan;
import org.apache.hadoop.hive.ql.QueryState;
import org.apache.hadoop.hive.ql.exec.ExplainTask;
import org.apache.hadoop.hive.ql.io.HiveBinaryOutputFormat;
import org.apache.hadoop.hive.ql.io.HiveInputFormat;
import org.apache.hadoop.hive.ql.metadata.Hive;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.parse.*;
import org.apache.hadoop.hive.ql.plan.ExplainWork;
import org.apache.hadoop.hive.ql.security.authorization.plugin.sqlstd.SQLStdHiveAuthorizerFactory;
import org.apache.hadoop.hive.ql.session.SessionState;
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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.hadoop.hive.maprdb.json.conf.MapRDBConstants.MAPRDB_COLUMN_ID;
import static org.apache.hadoop.hive.maprdb.json.conf.MapRDBConstants.MAPRDB_IS_IN_TEST_MODE;
import static org.apache.hadoop.hive.maprdb.json.conf.MapRDBConstants.MAPRDB_TABLE_NAME;
import static org.apache.hadoop.hive.metastore.api.hive_metastoreConstants.META_TABLE_STORAGE;

@RunWith(Parameterized.class) public class TestMapRDbJsonUpdateSemanticAnalyzer {

  private static final Logger LOG = LoggerFactory.getLogger(TestMapRDbJsonUpdateSemanticAnalyzer.class.getName());
  private static final String CUSTOMER_DB_JSON_TARGET = "customer_db_json_target";
  private static final String CUSTOMER_DB_JSON_SOURCE = "customer_db_json_source";
  private static final String CUSTOMER_DB_JSON_ALL_TARGET = "customer_db_json_all_target";
  private static final String DEPARTMENT = "department";
  private static final String CUSTOMER_WEST = "customer_west";
  private static final String CUSTOMER_EAST = "customer_east";

  private QueryState queryState;
  private HiveConf conf;
  private Hive db;
  private String query;

  public TestMapRDbJsonUpdateSemanticAnalyzer(String query) {
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

  private static String readFile(String path) throws IOException {
    byte[] encoded = Files.readAllBytes(Paths.get(path));
    return new String(encoded, UTF_8);
  }

  @Before public void setup() throws HiveException {
    conf = new HiveConf();
    queryState = new QueryState.Builder().withHiveConf(conf).build();
    conf.setVar(HiveConf.ConfVars.HIVE_AUTHORIZATION_MANAGER, SQLStdHiveAuthorizerFactory.class.getName());

    SessionState.start(conf);
    db = Hive.get(conf);
    createTables();
  }

  private void createTables() throws HiveException {

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

  private ReturnInfo parseAndAnalyze(String query) throws IOException, ParseException, HiveException {
    Context ctx = new Context(conf);
    ctx.setCmd(query);
    ctx.setHDFSCleanup(true);
    SessionState.get().initTxnMgr(conf);
    ASTNode tree = ParseUtils.parse(query, ctx);
    BaseSemanticAnalyzer sem = SemanticAnalyzerFactory.get(queryState, tree);
    sem.analyze(tree, ctx);
    // validate the plan
    sem.validate();
    QueryPlan plan = new QueryPlan(query, sem, 0L, null, null, null);
    return new ReturnInfo(sem, plan);

  }

  private class ReturnInfo {
    BaseSemanticAnalyzer sem;
    QueryPlan plan;

    ReturnInfo(BaseSemanticAnalyzer s, QueryPlan p) {
      sem = s;
      plan = p;
    }
  }

  private String explain(SemanticAnalyzer sem, QueryPlan plan) throws IOException {
    FileSystem fs = FileSystem.get(conf);
    File f = File.createTempFile("TestSemanticAnalyzer", "explain");
    Path tmp = new Path(f.getPath());
    fs.create(tmp);
    fs.deleteOnExit(tmp);
    ExplainConfiguration config = new ExplainConfiguration();
    config.setExtended(true);
    ExplainWork work =
        new ExplainWork(tmp, sem.getParseContext(), sem.getRootTasks(), sem.getFetchTask(), null, sem, config, null);
    ExplainTask task = new ExplainTask();
    task.setWork(work);
    task.initialize(queryState, plan, null, null);
    task.execute(null);
    FSDataInputStream in = fs.open(tmp);
    StringBuilder builder = new StringBuilder();
    final int bufSz = 4096;
    byte[] buf = new byte[bufSz];
    long pos = 0L;
    while (true) {
      int bytesRead = in.read(pos, buf, 0, bufSz);
      if (bytesRead > 0) {
        pos += bytesRead;
        builder.append(new String(buf, 0, bytesRead));
      } else {
        // Reached end of file
        in.close();
        break;
      }
    }
    return builder.toString().replaceAll("pfile:/.*\n", "pfile:MASKED-OUT\n")
        .replaceAll("location file:/.*\n", "location file:MASKED-OUT\n").replaceAll("file:/.*\n", "file:MASKED-OUT\n")
        .replaceAll("transient_lastDdlTime.*\n", "transient_lastDdlTime MASKED-OUT\n");
  }
}
