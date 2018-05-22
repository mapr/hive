/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hive.jdbc;

import org.apache.hive.service.rpc.thrift.TCLIService;
import org.apache.hive.service.rpc.thrift.TCloseOperationReq;
import org.apache.hive.service.rpc.thrift.TCloseOperationResp;
import org.apache.hive.service.rpc.thrift.TExecuteStatementReq;
import org.apache.hive.service.rpc.thrift.TExecuteStatementResp;
import org.apache.hive.service.rpc.thrift.TGetOperationStatusReq;
import org.apache.hive.service.rpc.thrift.TGetOperationStatusResp;
import org.apache.hive.service.rpc.thrift.TOperationHandle;
import org.apache.hive.service.rpc.thrift.TOperationState;
import org.apache.hive.service.rpc.thrift.TSessionHandle;
import org.apache.hive.service.rpc.thrift.TStatus;
import org.apache.hive.service.rpc.thrift.TStatusCode;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestHivePreparedStatement {

  @Mock
  private HiveConnection connection;
  @Mock
  private TCLIService.Iface client;
  @Mock
  private TSessionHandle sessHandle;
  @Mock
  private TExecuteStatementResp tExecStatementResp;
  @Mock
  private TGetOperationStatusResp tGetOperationStatusResp;
  @Mock
  private TCloseOperationResp tCloseOperationResp;
  @Mock
  private TOperationHandle tOperationHandle;

  private TStatus tStatus_SUCCESS = new TStatus(TStatusCode.SUCCESS_STATUS);

  @Before
  public void before() throws Exception {
    MockitoAnnotations.initMocks(this);
    when(tExecStatementResp.getStatus()).thenReturn(tStatus_SUCCESS);
    when(tExecStatementResp.getOperationHandle()).thenReturn(tOperationHandle);

    when(tGetOperationStatusResp.getStatus()).thenReturn(tStatus_SUCCESS);
    when(tGetOperationStatusResp.getOperationState()).thenReturn(TOperationState.FINISHED_STATE);
    when(tGetOperationStatusResp.isSetOperationState()).thenReturn(true);
    when(tGetOperationStatusResp.isSetOperationCompleted()).thenReturn(true);

    when(tCloseOperationResp.getStatus()).thenReturn(tStatus_SUCCESS);

    when(client.GetOperationStatus(any(TGetOperationStatusReq.class))).thenReturn(tGetOperationStatusResp);
    when(client.CloseOperation(any(TCloseOperationReq.class))).thenReturn(tCloseOperationResp);
    when(client.ExecuteStatement(any(TExecuteStatementReq.class))).thenReturn(tExecStatementResp);
  }

  @Test
  public void testSingleQuoteSetString() throws Exception {
    String sql = "select * from table where value=?";
    ArgumentCaptor<TExecuteStatementReq> argument = ArgumentCaptor.forClass(TExecuteStatementReq.class);
    HivePreparedStatement ps = new HivePreparedStatement(connection, client, sessHandle, sql);

    ps.setString(1, "anyValue\\' or 1=1 --");
    ps.execute();
    verify(client).ExecuteStatement(argument.capture());
    assertEquals("select * from table where value='anyValue\\' or 1=1 --'", argument.getValue().getStatement());

    ps.setString(1, "anyValue\\\\' or 1=1 --");
    ps.execute();
    verify(client, times(2)).ExecuteStatement(argument.capture());
    assertEquals("select * from table where value='anyValue\\\\\\' or 1=1 --'", argument.getValue().getStatement());
  }


  @Test
  public void testSingleQuoteSetBinaryStream() throws Exception {
    String sql = "select * from table where value=?";
    ArgumentCaptor<TExecuteStatementReq> argument = ArgumentCaptor.forClass(TExecuteStatementReq.class);
    HivePreparedStatement ps = new HivePreparedStatement(connection, client, sessHandle, sql);

    ps.setBinaryStream(1, new ByteArrayInputStream("'anyValue' or 1=1".getBytes()));
    ps.execute();
    verify(client).ExecuteStatement(argument.capture());
    assertEquals("select * from table where value='\\'anyValue\\' or 1=1'", argument.getValue().getStatement());

    ps.setBinaryStream(1, new ByteArrayInputStream("\\'anyValue\\' or 1=1".getBytes()));
    ps.execute();
    verify(client, times(2)).ExecuteStatement(argument.capture());
    assertEquals("select * from table where value='\\'anyValue\\' or 1=1'", argument.getValue().getStatement());
  }

}
