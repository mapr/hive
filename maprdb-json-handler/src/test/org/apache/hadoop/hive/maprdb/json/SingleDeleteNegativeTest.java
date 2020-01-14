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

public class SingleDeleteNegativeTest extends BaseSingleDeleteTest {
  private static final Logger LOG = LoggerFactory.getLogger(SingleDeleteNegativeTest.class.getName());

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  public SingleDeleteNegativeTest(){};

  @Before public void setup() throws HiveException {
    super.setup();
  }

  @Test public void testDeleteFromNonMapRDbJson() throws Exception {
    try {
      thrown.expect(SemanticException.class);
      thrown.expectMessage(containsString("Operation is not supported. Table is nor ACID neither MapRDbJSON"));
      query = IOUtils.toString(Resources.getResource("single-delete-negative/delete-from-non-mapr-db-json-table.sql"));
      ReturnInfo rc = parseAndAnalyze(query);
      String result = explain((SemanticAnalyzer) rc.sem, rc.plan);
      LOG.info(result);
      Assert.assertFalse(result.contains("Deletion from MapR DB Json Table"));
    } finally {
      cleanupTables();
    }
  }

  @Test public void testWithNonKeyColumn() throws Exception {
    try {
      thrown.expect(SemanticException.class);
      thrown.expectMessage(containsString("Deletion over column name is forbidden. Use only key column of MapR Db Json table: id"));
      query = IOUtils.toString(Resources.getResource("single-delete-negative/delete-with-non-key-column.sql"));
      ReturnInfo rc = parseAndAnalyze(query);
      String result = explain((SemanticAnalyzer) rc.sem, rc.plan);
      LOG.info(result);
      Assert.assertFalse(result.contains("Deletion from MapR DB Json Table"));
    } finally {
      cleanupTables();
    }
  }

  @Test public void testWithNonSupportedCondition() throws Exception {
    try {
      thrown.expect(SemanticException.class);
      thrown.expectMessage(containsString("This condition is not supported for MapR Db Json deletions."));
      query = IOUtils.toString(Resources.getResource("single-delete-negative/delete-with-unsupported-condition.sql"));
      ReturnInfo rc = parseAndAnalyze(query);
      String result = explain((SemanticAnalyzer) rc.sem, rc.plan);
      LOG.info(result);
      Assert.assertFalse(result.contains("Deletion from MapR DB Json Table"));
    } finally {
      cleanupTables();
    }
  }
}
