<?php
namespace metastore;

/**
 * Autogenerated by Thrift Compiler (0.16.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
use Thrift\Base\TBase;
use Thrift\Type\TType;
use Thrift\Type\TMessageType;
use Thrift\Exception\TException;
use Thrift\Exception\TProtocolException;
use Thrift\Protocol\TProtocol;
use Thrift\Protocol\TBinaryProtocolAccelerated;
use Thrift\Exception\TApplicationException;

final class Constant extends \Thrift\Type\TConstant
{
    static protected $DDL_TIME;
    static protected $HIVE_FILTER_FIELD_OWNER;
    static protected $HIVE_FILTER_FIELD_PARAMS;
    static protected $HIVE_FILTER_FIELD_LAST_ACCESS;
    static protected $IS_ARCHIVED;
    static protected $ORIGINAL_LOCATION;
    static protected $IS_IMMUTABLE;
    static protected $META_TABLE_COLUMNS;
    static protected $META_TABLE_COLUMN_TYPES;
    static protected $BUCKET_FIELD_NAME;
    static protected $BUCKET_COUNT;
    static protected $FIELD_TO_DIMENSION;
    static protected $META_TABLE_NAME;
    static protected $META_TABLE_DB;
    static protected $META_TABLE_LOCATION;
    static protected $META_TABLE_SERDE;
    static protected $META_TABLE_PARTITION_COLUMNS;
    static protected $META_TABLE_PARTITION_COLUMN_TYPES;
    static protected $FILE_INPUT_FORMAT;
    static protected $FILE_OUTPUT_FORMAT;
    static protected $META_TABLE_STORAGE;
    static protected $TABLE_IS_TRANSACTIONAL;
    static protected $TABLE_NO_AUTO_COMPACT;
    static protected $TABLE_TRANSACTIONAL_PROPERTIES;
    static protected $TABLE_BUCKETING_VERSION;

    protected static function init_DDL_TIME()
    {
        return "transient_lastDdlTime";
    }

    protected static function init_HIVE_FILTER_FIELD_OWNER()
    {
        return "hive_filter_field_owner__";
    }

    protected static function init_HIVE_FILTER_FIELD_PARAMS()
    {
        return "hive_filter_field_params__";
    }

    protected static function init_HIVE_FILTER_FIELD_LAST_ACCESS()
    {
        return "hive_filter_field_last_access__";
    }

    protected static function init_IS_ARCHIVED()
    {
        return "is_archived";
    }

    protected static function init_ORIGINAL_LOCATION()
    {
        return "original_location";
    }

    protected static function init_IS_IMMUTABLE()
    {
        return "immutable";
    }

    protected static function init_META_TABLE_COLUMNS()
    {
        return "columns";
    }

    protected static function init_META_TABLE_COLUMN_TYPES()
    {
        return "columns.types";
    }

    protected static function init_BUCKET_FIELD_NAME()
    {
        return "bucket_field_name";
    }

    protected static function init_BUCKET_COUNT()
    {
        return "bucket_count";
    }

    protected static function init_FIELD_TO_DIMENSION()
    {
        return "field_to_dimension";
    }

    protected static function init_META_TABLE_NAME()
    {
        return "name";
    }

    protected static function init_META_TABLE_DB()
    {
        return "db";
    }

    protected static function init_META_TABLE_LOCATION()
    {
        return "location";
    }

    protected static function init_META_TABLE_SERDE()
    {
        return "serde";
    }

    protected static function init_META_TABLE_PARTITION_COLUMNS()
    {
        return "partition_columns";
    }

    protected static function init_META_TABLE_PARTITION_COLUMN_TYPES()
    {
        return "partition_columns.types";
    }

    protected static function init_FILE_INPUT_FORMAT()
    {
        return "file.inputformat";
    }

    protected static function init_FILE_OUTPUT_FORMAT()
    {
        return "file.outputformat";
    }

    protected static function init_META_TABLE_STORAGE()
    {
        return "storage_handler";
    }

    protected static function init_TABLE_IS_TRANSACTIONAL()
    {
        return "transactional";
    }

    protected static function init_TABLE_NO_AUTO_COMPACT()
    {
        return "no_auto_compaction";
    }

    protected static function init_TABLE_TRANSACTIONAL_PROPERTIES()
    {
        return "transactional_properties";
    }

    protected static function init_TABLE_BUCKETING_VERSION()
    {
        return "bucketing_version";
    }
}
