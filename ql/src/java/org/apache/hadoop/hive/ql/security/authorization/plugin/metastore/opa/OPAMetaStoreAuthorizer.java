/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.hadoop.hive.ql.security.authorization.plugin.metastore.opa;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.metastore.HiveMetaStore;
import org.apache.hadoop.hive.metastore.MetaStorePreEventListener;
import org.apache.hadoop.hive.metastore.api.MetaException;
import org.apache.hadoop.hive.metastore.conf.MetastoreConf;
import org.apache.hadoop.hive.metastore.events.PreEventContext;
import org.apache.hadoop.hive.metastore.utils.MetaStoreUtils;
import org.apache.hadoop.hive.ql.security.authorization.plugin.HivePrivilegeObject;
import org.apache.hadoop.hive.ql.security.authorization.plugin.metastore.HiveMetaStoreAuthorizableEvent;
import org.apache.hadoop.hive.ql.security.authorization.plugin.metastore.HiveMetaStoreAuthzInfo;
import org.apache.hadoop.hive.ql.security.authorization.plugin.metastore.events.AddPartitionEvent;
import org.apache.hadoop.hive.ql.security.authorization.plugin.metastore.events.AlterDatabaseEvent;
import org.apache.hadoop.hive.ql.security.authorization.plugin.metastore.events.AlterPartitionEvent;
import org.apache.hadoop.hive.ql.security.authorization.plugin.metastore.events.AlterTableEvent;
import org.apache.hadoop.hive.ql.security.authorization.plugin.metastore.events.CreateDatabaseEvent;
import org.apache.hadoop.hive.ql.security.authorization.plugin.metastore.events.CreateFunctionEvent;
import org.apache.hadoop.hive.ql.security.authorization.plugin.metastore.events.CreateTableEvent;
import org.apache.hadoop.hive.ql.security.authorization.plugin.metastore.events.DropDatabaseEvent;
import org.apache.hadoop.hive.ql.security.authorization.plugin.metastore.events.DropFunctionEvent;
import org.apache.hadoop.hive.ql.security.authorization.plugin.metastore.events.DropPartitionEvent;
import org.apache.hadoop.hive.ql.security.authorization.plugin.metastore.events.DropTableEvent;
import org.apache.hadoop.hive.ql.security.authorization.plugin.metastore.events.LoadPartitionDoneEvent;
import org.apache.hadoop.hive.ql.security.authorization.plugin.metastore.events.ReadDatabaseEvent;
import org.apache.hadoop.hive.ql.security.authorization.plugin.metastore.events.ReadTableEvent;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Flow:
 *      1. Receive the pre event for HMS operations
 *      2. Build the HiveMetaStoreAuthzInfo object which sets the Hive's event object,
 *         as well as OPA event type (create, alter, delete, read)
 *      3. Check the privileges for the event by querying OPA
 * Notes:
 *      - The OPA URL is set in the Hive configuration
 *      - Keeping everything private for now except for the constructor and onEvent method
 *      - Catalog is set to "datalakehouse" for now but can be changed (we can make it configurable when needed)
 *      - eventType is only property that is set in the class and is used
 * Add the following properties to the hive-site.xml to enable the OPA authorizer:
*        <property>
*            <name>hive.metastore.pre.event.listeners</name>
*            <value>org.apache.hadoop.hive.ql.security.authorization.plugin.metastore.opa.OPAMetaStoreAuthorizer</value>
*            <description>Comma separated list of listeners to be invoked before metastore events</description>
*        </property>
*        <property>
*            <name>hive.metastore.opa.url</name>
*            <value>OPA_endpoint</value>
*            <description>URL for OPA endpoint</description>
*        </property>
 */
public class OPAMetaStoreAuthorizer extends MetaStorePreEventListener {
    private static final Log LOG = LogFactory.getLog(OPAMetaStoreAuthorizer.class);
    private final String OPA_URL;
    private final String CATALOG = "datalakehouse";
    private String eventType;

    public OPAMetaStoreAuthorizer(Configuration config) {
        super(config);
        this.OPA_URL = config.get(MetastoreConf.ConfVars.HIVE_METASTORE_OPA_URL.getHiveName());
    }

    @Override
    public final void onEvent(PreEventContext preEventContext) throws MetaException{
        if (LOG.isDebugEnabled()) {
            LOG.debug("==> OPAMetaStoreAuthorizer.onEvent(): preEventContext event type = " + preEventContext.getEventType());
        }

        if (!isSuperUser(getCurrentUser())) {
            HiveMetaStoreAuthzInfo authzContext = buildAuthzContext(preEventContext);
            checkPrivileges(authzContext, preEventContext);
        }
    }

    private HiveMetaStoreAuthzInfo buildAuthzContext(PreEventContext preEventContext) throws MetaException {
        HiveMetaStoreAuthorizableEvent authzEvent = null;
        String create = "CREATE";
        String alter = "ALTER";
        String drop = "DELETE";
        String read = "READ";

        switch (preEventContext.getEventType()) {
            case CREATE_DATABASE:
                authzEvent = new CreateDatabaseEvent(preEventContext);
                this.eventType = create;
                break;
            case ALTER_DATABASE:
                authzEvent = new AlterDatabaseEvent(preEventContext);
                this.eventType = alter;
                break;
            case DROP_DATABASE:
                authzEvent = new DropDatabaseEvent(preEventContext);
                this.eventType = drop;
                break;
            case CREATE_TABLE:
                authzEvent = new CreateTableEvent(preEventContext);
                this.eventType = create;
                break;
            case ALTER_TABLE:
                authzEvent = new AlterTableEvent(preEventContext);
                this.eventType = alter;
                break;
            case DROP_TABLE:
                authzEvent = new DropTableEvent(preEventContext);
                this.eventType = drop;
                break;
            case ADD_PARTITION:
                authzEvent = new AddPartitionEvent(preEventContext);
                this.eventType = alter;
                break;
            case ALTER_PARTITION:
                authzEvent = new AlterPartitionEvent(preEventContext);
                this.eventType = alter;
                break;
            case LOAD_PARTITION_DONE:
                authzEvent = new LoadPartitionDoneEvent(preEventContext);
                this.eventType = alter;
                break;
            case DROP_PARTITION:
                authzEvent = new DropPartitionEvent(preEventContext);
                this.eventType = alter;
                break;
            case READ_TABLE:
                authzEvent = new ReadTableEvent(preEventContext);
                this.eventType = read;
                break;
            case READ_DATABASE:
                authzEvent = new ReadDatabaseEvent(preEventContext);
                this.eventType = read;
                break;
            case CREATE_FUNCTION:
                authzEvent = new CreateFunctionEvent(preEventContext);
                this.eventType = create;
                break;
            case DROP_FUNCTION:
                authzEvent = new DropFunctionEvent(preEventContext);
                this.eventType = drop;
                break;
            case AUTHORIZATION_API_CALL:
            case READ_ISCHEMA:
            case CREATE_ISCHEMA:
            case DROP_ISCHEMA:
            case ALTER_ISCHEMA:
            case ADD_SCHEMA_VERSION:
            case ALTER_SCHEMA_VERSION:
            case DROP_SCHEMA_VERSION:
            case READ_SCHEMA_VERSION:
            case CREATE_CATALOG:
            case ALTER_CATALOG:
            case DROP_CATALOG:
                if (!isSuperUser(getCurrentUser())) {
                    throw new MetaException(" Operation type " + preEventContext.getEventType().name()
                            + " not allowed for user: " + getCurrentUser());
                }
                break;
            default:
                break;
        }
        return authzEvent != null ? authzEvent.getAuthzContext() : null;
    }

    private void checkPrivileges(HiveMetaStoreAuthzInfo authzContext, PreEventContext preEventContext) throws MetaException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("==> OPAMetaStoreAuthorizer.checkPrivileges(): event type = " + preEventContext.getEventType());
        }

        // Check if input objects are provided
        List<HivePrivilegeObject> inputHObjs = authzContext.getInputHObjs();
        if (inputHObjs.isEmpty()) {
            String message = "No input objects provided for authorization even though authorization is enabled.";
            LOG.error(message);
            throw new MetaException(message);
        }

        // Check privileges for each input object
        for (HivePrivilegeObject obj : inputHObjs) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("==> OPAMetaStoreAuthorizer.checkPrivileges(): object = " + obj);
            }

            // Variables for OPA input
            String databaseName = obj.getDbname();
            String tableName = obj.getObjectName();
            String user = getCurrentUser();

            // Construct OPA inputs
            Map<String, Object> input = new HashMap<>();
            input.put("input_grant", null);
            input.put("input_resource", Map.of("groups", List.of(),
                    "resource", constructInputResource(this.CATALOG, null, null, databaseName, tableName),
                    "user", user));
            input.put("operation", this.eventType);
            input.put("target_resource", null);

            // Query OPA
            boolean isAllowed = queryOPA(input);
            if (!isAllowed) {
                throw new MetaException(" Operation is not allowed by OPA policy. Operation: "
                        + preEventContext.getEventType() + ", User: " + user);
            }
        }
    }

    private Map<String, Object> constructInputResource(String catalog, String columns, String propertyName, String schemaName, String tableName) {
        Map<String, Object> inputResource = new HashMap<>();
        inputResource.put("catalog", catalog);
        inputResource.put("columns", columns);
        inputResource.put("propertyName", propertyName);
        inputResource.put("schemaName", schemaName);
        inputResource.put("tableName", tableName);
        return inputResource;
    }

    private boolean queryOPA(Map<String, Object> input) throws MetaException {
        try {
            Map<String, Object> requestBody = Map.of("input", input);
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBodyJson = objectMapper.writeValueAsString(requestBody);
            if (LOG.isDebugEnabled()) {
                LOG.debug("==> OPAMetaStoreAuthorizer.queryOPA(): input = " + requestBodyJson);
            }

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(OPA_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBodyJson))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (LOG.isDebugEnabled()) {
                LOG.debug("==> OPAMetaStoreAuthorizer.queryOPA(): response = " + response.body());
            }

            Map<String, Object> responseBody = objectMapper.readValue(response.body(), Map.class);
            return (boolean) responseBody.getOrDefault("result", false);
        } catch (Exception e) {
            LOG.error("Error querying OPA at " + OPA_URL, e);
            throw new MetaException("Authorization query to OPA failed: " + e.getMessage());
        }
    }

    private boolean isSuperUser(String userName) {
        Configuration conf = getConf();
        String ipAddress = HiveMetaStore.HMSHandler.getIPAddress();
        return (MetaStoreUtils.checkUserHasHostProxyPrivileges(userName, conf, ipAddress));
    }

    private String getCurrentUser() {
        try {
            return UserGroupInformation.getCurrentUser().getShortUserName();
        } catch (IOException ioException) {
            LOG.error("Error getting current user: ", ioException);
        }
        return null;
    }
}
