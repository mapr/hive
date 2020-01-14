package org.apache.hadoop.hive.maprdb.json;

import com.google.common.io.Resources;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.parse.SemanticAnalyzer;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.containsString;

public class OptimizedDeleteInMergeNegativeTest extends BaseOptimizedDeleteInMergeTest {
  private static final Logger LOG = LoggerFactory.getLogger(OptimizedDeleteInMergeNegativeTest.class.getName());

  @Rule public ExpectedException thrown = ExpectedException.none();

  public OptimizedDeleteInMergeNegativeTest() {
  }


  @Before
  public void setup() throws HiveException {
    super.setup();
  }

  @Test
  public void testNonSupportedCondition() throws Exception {
    try {
      thrown.expect(SemanticException.class);
      thrown.expectMessage(
          containsString("Delete operator is not supported with additional conditions after WHEN MATCHED."));
      query = IOUtils.toString(Resources.getResource("optimized-delete-in-merge-negative/not-supported-condition.sql"));
      ReturnInfo rc = parseAndAnalyze(query);
      String result = explain((SemanticAnalyzer) rc.sem, rc.plan);
      LOG.info(result);
      Assert.assertFalse(result.contains("Deletion from MapR DB Json Table"));
    } finally {
      cleanupTables();
    }
  }

  @Test
  public void testNonSupportedSource() throws Exception {
    try {
      thrown.expect(SemanticException.class);
      thrown.expectMessage(
          containsString("Source table should be MapR DB JSON table when deletion is used in MERGE operator."));
      query = IOUtils.toString(Resources.getResource("optimized-delete-in-merge-negative/not-supported-source.sql"));
      ReturnInfo rc = parseAndAnalyze(query);
      String result = explain((SemanticAnalyzer) rc.sem, rc.plan);
      LOG.info(result);
      Assert.assertFalse(result.contains("Deletion from MapR DB Json Table"));
    } finally {
      cleanupTables();
    }
  }

  @Test
  public void testNonSupportedSubQuery() throws Exception {
    try {
      thrown.expect(SemanticException.class);
      thrown.expectMessage(
          containsString("Sub queries are not supported as a source when DELETE is used."));
      query = IOUtils.toString(Resources.getResource("optimized-delete-in-merge-negative/not-supported-sub-query.sql"));
      ReturnInfo rc = parseAndAnalyze(query);
      String result = explain((SemanticAnalyzer) rc.sem, rc.plan);
      LOG.info(result);
      Assert.assertFalse(result.contains("Deletion from MapR DB Json Table"));
    } finally {
      cleanupTables();
    }
  }
}
