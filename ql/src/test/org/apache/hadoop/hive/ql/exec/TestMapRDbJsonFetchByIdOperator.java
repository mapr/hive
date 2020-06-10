package org.apache.hadoop.hive.ql.exec;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.maprdb.json.serde.MapRDBSerDe;
import org.apache.hadoop.hive.maprdb.json.shims.DocumentWritable;
import org.apache.hadoop.hive.maprdb.json.util.MapRDbJsonTableUtil;
import org.apache.hadoop.hive.ql.plan.MapRDbJsonFetchByIdWork;
import org.apache.hadoop.hive.ql.plan.TableDesc;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.mapred.JobConf;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ojai.Document;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MapRDbJsonTableUtil.class)
public class TestMapRDbJsonFetchByIdOperator {

  private MapRDbJsonFetchByIdWork fetchWork = mock(MapRDbJsonFetchByIdWork.class);
  private JobConf jobConf = mock(JobConf.class);
  private Operator source = mock(Operator.class);
  private Document document =  mock(Document.class);
  private TableDesc tableDesc = mock(TableDesc.class);
  private MapRDBSerDe mapRDBSerDe = mock(MapRDBSerDe.class);
  private StructObjectInspector objectInspector = mock(StructObjectInspector.class);

  @Before
  public void setUp() throws Exception {
    when(fetchWork.getTblDesc()).thenReturn(tableDesc);
    when(tableDesc.getDeserializer(any(Configuration.class), anyBoolean()))
        .thenReturn(mapRDBSerDe);
    when(mapRDBSerDe.getObjectInspector()).thenReturn(objectInspector);
    PowerMockito.mockStatic(MapRDbJsonTableUtil.class);
    PowerMockito.when(MapRDbJsonTableUtil.findById(anyString(), anyString()))
        .thenReturn(document);
  }

  @Test
  public void testGetNextRowWorkIsEmpty() throws Exception {
    when(fetchWork.isEmpty()).thenReturn(true);

    MapRDbJsonFetchByIdOperator mapRDbJsonFetchByIdOperator =
        new MapRDbJsonFetchByIdOperator(fetchWork, jobConf, source);
    Assert.assertNull(mapRDbJsonFetchByIdOperator.getNextRow());
  }

  @Test
  public void testGetNextRowDocumentEqualsNull() throws Exception {
    PowerMockito.when(MapRDbJsonTableUtil.findById(anyString(), anyString()))
        .thenReturn(null);

    MapRDbJsonFetchByIdOperator mapRDbJsonFetchByIdOperator =
        new MapRDbJsonFetchByIdOperator(fetchWork, jobConf, source);

    Assert.assertNull(mapRDbJsonFetchByIdOperator.getNextRow());
  }

  @Test
  public void testGetNextRow() throws Exception {
    MapRDbJsonFetchByIdOperator mapRDbJsonFetchByIdOperator =
        new MapRDbJsonFetchByIdOperator(fetchWork, jobConf, source);

    mapRDbJsonFetchByIdOperator.getNextRow();

    verify(mapRDBSerDe,times(2)).getObjectInspector();
    verify(mapRDBSerDe, times(1)).deserialize(any(DocumentWritable.class));
  }
}
