package org.apache.hadoop.hive.ql.exec;

import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.ql.CompilationOpContext;
import org.apache.hadoop.hive.ql.DriverContext;
import org.apache.hadoop.hive.ql.QueryPlan;
import org.apache.hadoop.hive.ql.QueryState;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.plan.MapRDbJsonFetchByIdWork;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

public class TestMapRDbJsonFetchByIdTask {

  private MapRDbJsonFetchByIdTask mapRDbJsonFetchByIdTask;
  private FetchOperator fetch = Mockito.mock(FetchOperator.class);;
  private QueryState queryState = Mockito.mock(QueryState.class);
  private HiveConf conf = Mockito.spy(HiveConf.class);;
  private QueryPlan queryPlan = Mockito.mock(QueryPlan.class);;
  private DriverContext ctx = Mockito.mock(DriverContext.class);;
  private CompilationOpContext opContext = Mockito.mock(CompilationOpContext.class);;
  private MapRDbJsonFetchByIdWork work = Mockito.mock(MapRDbJsonFetchByIdWork.class);;
  private Operator source = Mockito.mock(Operator.class);;
  private ListSinkOperator sink = Mockito.mock(ListSinkOperator.class);;

  @Before
  public void setUp() throws Exception {
    mapRDbJsonFetchByIdTask = Mockito.spy(new MapRDbJsonFetchByIdTask());
    when(queryState.getConf()).thenReturn(conf);
    when(work.getSource()).thenReturn(source);
    when(work.getSink()).thenReturn(sink);
    doReturn(fetch).when(mapRDbJsonFetchByIdTask).createFetchOperator();
    mapRDbJsonFetchByIdTask.setWork(work);
    mapRDbJsonFetchByIdTask.initialize(queryState, queryPlan, ctx, opContext);
  }

  @Test
  public void testFetchPositive() throws Exception {
    when(fetch.pushRow()).thenReturn(true);
    boolean actual = mapRDbJsonFetchByIdTask.fetch(new ArrayList());
    Assert.assertTrue(actual);
  }

  @Test
  public void testFetchNegative() throws Exception {
    when(fetch.pushRow()).thenReturn(false);
    boolean actual = mapRDbJsonFetchByIdTask.fetch(new ArrayList());
    Assert.assertFalse(actual);
  }

  @Test(expected = IOException.class)
  public void testFetchExceptionCatch() throws Exception {
    when(fetch.pushRow()).thenThrow(new HiveException());
    mapRDbJsonFetchByIdTask.fetch(new ArrayList());
  }
}
