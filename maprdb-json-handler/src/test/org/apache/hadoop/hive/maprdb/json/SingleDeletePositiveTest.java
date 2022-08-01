package org.apache.hadoop.hive.maprdb.json;

import com.google.common.io.Resources;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.parse.SemanticAnalyzer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
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

@Ignore//FIXME
@RunWith(Parameterized.class) public class SingleDeletePositiveTest extends BaseSingleDeleteTest {
  private static final Logger LOG = LoggerFactory.getLogger(SingleDeletePositiveTest.class.getName());

  public SingleDeletePositiveTest(String query) {
    super(query);
  }

  @Before public void setup() throws HiveException {
    super.setup();
  }

  @Parameterized.Parameters public static Collection queries() throws IOException, URISyntaxException {
    List<String> queries = new ArrayList<>();
    URL url = Resources.getResource("single-delete-positive");
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

  @Test public void testPositive() throws Exception {
    try {
      ReturnInfo rc = parseAndAnalyze(query);
      String result = explain((SemanticAnalyzer) rc.sem, rc.plan);
      LOG.info(result);
      Assert.assertTrue(result.contains("Deletion from MapR DB Json Table"));
    } finally {
      cleanupTables();
    }
  }
}
