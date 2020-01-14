package org.apache.hadoop.hive.maprdb.json;

import com.google.common.io.Resources;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.parse.SemanticAnalyzer;
import org.junit.Assert;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class) public class MapRDbJsonFetchByIdOptimizerPositiveTest extends BaseFetchByIdOptimizerTest {

  private static final Logger LOG = LoggerFactory.getLogger(MapRDbJsonFetchByIdOptimizerPositiveTest.class.getName());

  public MapRDbJsonFetchByIdOptimizerPositiveTest(String query) {
    this.query = query;
  }

  @Parameterized.Parameters public static Collection queries() throws IOException, URISyntaxException {
    List<String> queries = new ArrayList<>();
    URL url = Resources.getResource("fetch-by-id-optimizer-positive");
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

  @Test public void testPositive() throws Exception {
    try {
      ReturnInfo rc = parseAndAnalyze(query);
      String output = explain((SemanticAnalyzer) rc.sem, rc.plan);
      LOG.info(output);
      Assert.assertTrue(output.contains("MapR DB JSON Fetch By Id Operator"));
    } finally {
      cleanupTables();
    }
  }
}
