package org.apache.hadoop.hive.ql.optimizer;

import org.apache.hadoop.hive.ql.QueryProperties;
import org.apache.hadoop.hive.ql.exec.TableScanOperator;
import org.apache.hadoop.hive.ql.parse.ParseContext;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestMapRDbJsonFetchByIdOptimizer {
  private ParseContext pctx = Mockito.mock(ParseContext.class);
  private QueryProperties queryProperties = Mockito.mock(QueryProperties.class);

  @Test
  public void testTransformPositive() throws Exception {
    MapRDbJsonFetchByIdOptimizer mapRDbJsonFetchByIdOptimizer = spy(new MapRDbJsonFetchByIdOptimizer());
    HashMap<String, TableScanOperator> topOps = new HashMap<>();
    topOps.put(new String(), Mockito.mock(TableScanOperator.class));
    when(pctx.getTopOps()).thenReturn(topOps);
    when(pctx.getQueryProperties()).thenReturn(queryProperties);
    when(queryProperties.isQuery()).thenReturn(true);
    when(queryProperties.isAnalyzeCommand()).thenReturn(false);
    doNothing().when(mapRDbJsonFetchByIdOptimizer).processTableScan(any(ParseContext.class),any(TableScanOperator.class));

    mapRDbJsonFetchByIdOptimizer.transform(pctx);

    verify(mapRDbJsonFetchByIdOptimizer, times(1)).processTableScan(any(ParseContext.class),
        any(TableScanOperator.class));
  }

  @Test
  public void testTransformNegative() throws Exception {
    MapRDbJsonFetchByIdOptimizer mapRDbJsonFetchByIdOptimizer = spy(new MapRDbJsonFetchByIdOptimizer());
    HashMap<String, TableScanOperator> topOps = new HashMap<>();
    when(pctx.getTopOps()).thenReturn(topOps);
    when(pctx.getQueryProperties()).thenReturn(queryProperties);
    when(queryProperties.isQuery()).thenReturn(false);
    when(queryProperties.isAnalyzeCommand()).thenReturn(true);
    doNothing().when(mapRDbJsonFetchByIdOptimizer).processTableScan(any(ParseContext.class),any(TableScanOperator.class));

    mapRDbJsonFetchByIdOptimizer.transform(pctx);

    verify(mapRDbJsonFetchByIdOptimizer, times(0)).processTableScan(any(ParseContext.class),
        any(TableScanOperator.class));
  }
}
