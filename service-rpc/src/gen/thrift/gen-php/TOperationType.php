<?php
/**
 * Autogenerated by Thrift Compiler (0.12.0)
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

final class TOperationType
{
    const EXECUTE_STATEMENT = 0;

    const GET_TYPE_INFO = 1;

    const GET_CATALOGS = 2;

    const GET_SCHEMAS = 3;

    const GET_TABLES = 4;

    const GET_TABLE_TYPES = 5;

    const GET_COLUMNS = 6;

    const GET_FUNCTIONS = 7;

    const UNKNOWN = 8;

    static public $__names = array(
        0 => 'EXECUTE_STATEMENT',
        1 => 'GET_TYPE_INFO',
        2 => 'GET_CATALOGS',
        3 => 'GET_SCHEMAS',
        4 => 'GET_TABLES',
        5 => 'GET_TABLE_TYPES',
        6 => 'GET_COLUMNS',
        7 => 'GET_FUNCTIONS',
        8 => 'UNKNOWN',
    );
}

