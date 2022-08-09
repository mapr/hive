SELECT 'Upgrading MetaStore schema from 3.0.0 to 3.1.0' AS Status from dual;

-- HIVE-19440
ALTER TABLE GLOBAL_PRIVS ADD AUTHORIZER VARCHAR2(128) NULL;
DROP INDEX GLOBALPRIVILEGEINDEX;
CREATE UNIQUE INDEX GLOBALPRIVILEGEINDEX ON GLOBAL_PRIVS (AUTHORIZER,PRINCIPAL_NAME,PRINCIPAL_TYPE,USER_PRIV,GRANTOR,GRANTOR_TYPE);

ALTER TABLE DB_PRIVS ADD AUTHORIZER VARCHAR2(128) NULL;
DROP INDEX DBPRIVILEGEINDEX;
CREATE UNIQUE INDEX DBPRIVILEGEINDEX ON DB_PRIVS (AUTHORIZER,DB_ID,PRINCIPAL_NAME,PRINCIPAL_TYPE,DB_PRIV,GRANTOR,GRANTOR_TYPE);

ALTER TABLE TBL_PRIVS ADD AUTHORIZER VARCHAR2(128) NULL;
DROP INDEX TABLEPRIVILEGEINDEX;
CREATE INDEX TABLEPRIVILEGEINDEX ON TBL_PRIVS (AUTHORIZER,TBL_ID,PRINCIPAL_NAME,PRINCIPAL_TYPE,TBL_PRIV,GRANTOR,GRANTOR_TYPE);

ALTER TABLE PART_PRIVS ADD AUTHORIZER VARCHAR2(128) NULL;
DROP INDEX PARTPRIVILEGEINDEX;
CREATE INDEX PARTPRIVILEGEINDEX ON PART_PRIVS (AUTHORIZER,PART_ID,PRINCIPAL_NAME,PRINCIPAL_TYPE,PART_PRIV,GRANTOR,GRANTOR_TYPE);

ALTER TABLE TBL_COL_PRIVS ADD AUTHORIZER VARCHAR2(128) NULL;
DROP INDEX TABLECOLUMNPRIVILEGEINDEX;
CREATE INDEX TABLECOLUMNPRIVILEGEINDEX ON TBL_COL_PRIVS (AUTHORIZER,TBL_ID,"COLUMN_NAME",PRINCIPAL_NAME,PRINCIPAL_TYPE,TBL_COL_PRIV,GRANTOR,GRANTOR_TYPE);

ALTER TABLE PART_COL_PRIVS ADD AUTHORIZER VARCHAR2(128) NULL;
DROP INDEX PARTITIONCOLUMNPRIVILEGEINDEX;
CREATE INDEX PARTITIONCOLUMNPRIVILEGEINDEX ON PART_COL_PRIVS (AUTHORIZER,PART_ID,"COLUMN_NAME",PRINCIPAL_NAME,PRINCIPAL_TYPE,PART_COL_PRIV,GRANTOR,GRANTOR_TYPE);

CREATE INDEX TAB_COL_STATS_IDX ON TAB_COL_STATS (CAT_NAME, DB_NAME, TABLE_NAME, COLUMN_NAME);

-- HIVE-19340
ALTER TABLE TXNS ADD TXN_TYPE number(10) NULL;

-- HIVE-19027
-- add column MATERIALIZATION_TIME (bigint) to MV_CREATION_METADATA table
ALTER TABLE MV_CREATION_METADATA ADD MATERIALIZATION_TIME NUMBER NULL;
UPDATE MV_CREATION_METADATA SET MATERIALIZATION_TIME = 0;
ALTER TABLE MV_CREATION_METADATA MODIFY(MATERIALIZATION_TIME NOT NULL);

-- add column CTC_UPDATE_DELETE (char) to COMPLETED_TXN_COMPONENTS table
ALTER TABLE COMPLETED_TXN_COMPONENTS ADD CTC_UPDATE_DELETE char(1) NULL;
UPDATE COMPLETED_TXN_COMPONENTS SET CTC_UPDATE_DELETE = 'N';
ALTER TABLE COMPLETED_TXN_COMPONENTS MODIFY(CTC_UPDATE_DELETE NOT NULL);

CREATE TABLE MATERIALIZATION_REBUILD_LOCKS (
  MRL_TXN_ID NUMBER NOT NULL,
  MRL_DB_NAME VARCHAR(128) NOT NULL,
  MRL_TBL_NAME VARCHAR(256) NOT NULL,
  MRL_LAST_HEARTBEAT NUMBER NOT NULL,
  PRIMARY KEY(MRL_TXN_ID)
);

-- HIVE-22729
ALTER TABLE COMPACTION_QUEUE ADD CQ_ERROR_MESSAGE CLOB;
ALTER TABLE COMPLETED_COMPACTIONS ADD CC_ERROR_MESSAGE CLOB;

-- These lines need to be last.  Insert any changes above.
UPDATE VERSION SET SCHEMA_VERSION='3.1.0', VERSION_COMMENT='Hive release version 3.1.0' where VER_ID=1;
SELECT 'Finished upgrading MetaStore schema from 3.0.0 to 3.1.0' AS Status from dual;
