SELECT 'Upgrading MetaStore schema from 3.0.0 to 3.1.0' AS MESSAGE;

-- HIVE-19440
ALTER TABLE GLOBAL_PRIVS ADD AUTHORIZER nvarchar(128) NULL;
-- Earlier versions have unique constraint instead of unique index.
ALTER TABLE GLOBAL_PRIVS DROP CONSTRAINT IF EXISTS GLOBALPRIVILEGEINDEX;
DROP INDEX IF EXISTS GLOBAL_PRIVS.GLOBALPRIVILEGEINDEX;
CREATE UNIQUE INDEX GLOBALPRIVILEGEINDEX ON GLOBAL_PRIVS (AUTHORIZER,PRINCIPAL_NAME,PRINCIPAL_TYPE,USER_PRIV,GRANTOR,GRANTOR_TYPE);

ALTER TABLE DB_PRIVS ADD AUTHORIZER nvarchar(128) NULL;
-- Earlier versions have unique constraint instead of unique index.
ALTER TABLE DB_PRIVS DROP CONSTRAINT IF EXISTS DBPRIVILEGEINDEX;
DROP INDEX IF EXISTS DB_PRIVS.DBPRIVILEGEINDEX;
CREATE UNIQUE INDEX DBPRIVILEGEINDEX ON DB_PRIVS (AUTHORIZER,DB_ID,PRINCIPAL_NAME,PRINCIPAL_TYPE,DB_PRIV,GRANTOR,GRANTOR_TYPE);

ALTER TABLE TBL_PRIVS ADD AUTHORIZER nvarchar(128) NULL;
-- Earlier versions have unique constraint instead of unique index.
ALTER TABLE TBL_PRIVS DROP CONSTRAINT IF EXISTS TABLEPRIVILEGEINDEX;
DROP INDEX IF EXISTS TBL_PRIVS.TABLEPRIVILEGEINDEX;
CREATE INDEX TABLEPRIVILEGEINDEX ON TBL_PRIVS (AUTHORIZER,TBL_ID,PRINCIPAL_NAME,PRINCIPAL_TYPE,TBL_PRIV,GRANTOR,GRANTOR_TYPE);

ALTER TABLE PART_PRIVS ADD AUTHORIZER nvarchar(128) NULL;
-- Earlier versions have unique constraint instead of unique index.
ALTER TABLE PART_PRIVS DROP CONSTRAINT IF EXISTS PARTPRIVILEGEINDEX;
DROP INDEX IF EXISTS PART_PRIVS.PARTPRIVILEGEINDEX;
CREATE INDEX PARTPRIVILEGEINDEX ON PART_PRIVS (AUTHORIZER,PART_ID,PRINCIPAL_NAME,PRINCIPAL_TYPE,PART_PRIV,GRANTOR,GRANTOR_TYPE);

ALTER TABLE TBL_COL_PRIVS ADD AUTHORIZER nvarchar(128) NULL;
-- Earlier versions have unique constraint instead of unique index.
ALTER TABLE TBL_COL_PRIVS DROP CONSTRAINT IF EXISTS TABLECOLUMNPRIVILEGEINDEX;
DROP INDEX IF EXISTS TBL_COL_PRIVS.TABLECOLUMNPRIVILEGEINDEX;
CREATE INDEX TABLECOLUMNPRIVILEGEINDEX ON TBL_COL_PRIVS (AUTHORIZER,TBL_ID,"COLUMN_NAME",PRINCIPAL_NAME,PRINCIPAL_TYPE,TBL_COL_PRIV,GRANTOR,GRANTOR_TYPE);

ALTER TABLE PART_COL_PRIVS ADD AUTHORIZER nvarchar(128) NULL;
-- Earlier versions have unique constraint instead of unique index.
ALTER TABLE PART_COL_PRIVS DROP CONSTRAINT IF EXISTS PARTITIONCOLUMNPRIVILEGEINDEX;
DROP INDEX IF EXISTS PART_COL_PRIVS.PARTITIONCOLUMNPRIVILEGEINDEX;
CREATE INDEX PARTITIONCOLUMNPRIVILEGEINDEX ON PART_COL_PRIVS (AUTHORIZER,PART_ID,"COLUMN_NAME",PRINCIPAL_NAME,PRINCIPAL_TYPE,PART_COL_PRIV,GRANTOR,GRANTOR_TYPE);

CREATE INDEX TAB_COL_STATS_IDX ON TAB_COL_STATS (CAT_NAME, DB_NAME, TABLE_NAME, COLUMN_NAME);

-- HIVE-19340
ALTER TABLE TXNS ADD TXN_TYPE int NULL;

-- HIVE-19027
-- add column MATERIALIZATION_TIME (bigint) to MV_CREATION_METADATA table
ALTER TABLE MV_CREATION_METADATA ADD MATERIALIZATION_TIME bigint NOT NULL CONSTRAINT DEF_MATERIALIZATION_TIME DEFAULT(0);

-- add column CTC_UPDATE_DELETE (char) to COMPLETED_TXN_COMPONENTS table
ALTER TABLE COMPLETED_TXN_COMPONENTS ADD CTC_UPDATE_DELETE char(1) NOT NULL CONSTRAINT DEF_CTC_UPDATE_DELETE DEFAULT('N');

CREATE TABLE MATERIALIZATION_REBUILD_LOCKS (
  MRL_TXN_ID bigint NOT NULL,
  MRL_DB_NAME nvarchar(128) NOT NULL,
  MRL_TBL_NAME nvarchar(256) NOT NULL,
  MRL_LAST_HEARTBEAT bigint NOT NULL,
CONSTRAINT PK_MATERIALIZATION_REBUILD_LOCKS PRIMARY KEY CLUSTERED
(
    MRL_TXN_ID ASC
)
);

-- HIVE-22729
ALTER TABLE COMPACTION_QUEUE ADD CQ_ERROR_MESSAGE varchar(max) NULL;
ALTER TABLE COMPLETED_COMPACTIONS ADD CC_ERROR_MESSAGE varchar(max) NULL;

-- These lines need to be last.  Insert any changes above.
UPDATE VERSION SET SCHEMA_VERSION='3.1.0', VERSION_COMMENT='Hive release version 3.1.0' where VER_ID=1;
SELECT 'Finished upgrading MetaStore schema from 3.0.0 to 3.1.0' AS MESSAGE;
