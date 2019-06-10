package org.apache.hadoop.hive.maprdb.json;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.ql.Context;
import org.apache.hadoop.hive.ql.QueryPlan;
import org.apache.hadoop.hive.ql.QueryState;
import org.apache.hadoop.hive.ql.exec.ExplainTask;
import org.apache.hadoop.hive.ql.metadata.Hive;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.parse.ASTNode;
import org.apache.hadoop.hive.ql.parse.BaseSemanticAnalyzer;
import org.apache.hadoop.hive.ql.parse.ExplainConfiguration;
import org.apache.hadoop.hive.ql.parse.ParseException;
import org.apache.hadoop.hive.ql.parse.ParseUtils;
import org.apache.hadoop.hive.ql.parse.SemanticAnalyzer;
import org.apache.hadoop.hive.ql.parse.SemanticAnalyzerFactory;
import org.apache.hadoop.hive.ql.plan.ExplainWork;
import org.apache.hadoop.hive.ql.security.authorization.plugin.sqlstd.SQLStdHiveAuthorizerFactory;
import org.apache.hadoop.hive.ql.session.SessionState;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.nio.charset.StandardCharsets.UTF_8;

abstract class BaseExplainTest {

  private QueryState queryState;
  private HiveConf conf;
  Hive db;
  String query;

  void setup() throws HiveException {
    conf = new HiveConf();
    conf.set("fs.default.name", "file:///");
    queryState = new QueryState(conf);
    conf.setVar(HiveConf.ConfVars.HIVE_AUTHORIZATION_MANAGER, SQLStdHiveAuthorizerFactory.class.getName());

    SessionState.start(conf);
    db = Hive.get(conf);
    createTables();
  }

  abstract void createTables() throws HiveException;

  String explain(SemanticAnalyzer sem, QueryPlan plan) throws IOException {
    FileSystem fs = FileSystem.get(conf);
    File f = File.createTempFile("result", "explain");
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

  ReturnInfo parseAndAnalyze(String query) throws IOException, ParseException, HiveException {
    Context ctx = new Context(conf);
    ctx.setCmd(query);
    ctx.setHDFSCleanup(true);
    SessionState.get().initTxnMgr(conf);
    ASTNode tree = ParseUtils.parse(query, ctx, conf);
    BaseSemanticAnalyzer sem = SemanticAnalyzerFactory.get(queryState, tree);
    sem.analyze(tree, ctx);
    // validate the plan
    sem.validate();
    QueryPlan plan = new QueryPlan(query, sem, 0L, null, null, null);
    return new ReturnInfo(sem, plan);
  }

  class ReturnInfo {
    BaseSemanticAnalyzer sem;
    QueryPlan plan;

    ReturnInfo(BaseSemanticAnalyzer s, QueryPlan p) {
      sem = s;
      plan = p;
    }
  }

  static String readFile(String path) throws IOException {
    byte[] encoded = Files.readAllBytes(Paths.get(path));
    return new String(encoded, UTF_8);
  }
}
