<?php
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

final class StageType
{
    const CONDITIONAL = 0;

    const COPY = 1;

    const DDL = 2;

    const MAPRED = 3;

    const EXPLAIN = 4;

    const FETCH = 5;

    const FUNC = 6;

    const MAPREDLOCAL = 7;

    const MOVE = 8;

    const STATS = 9;

    const DEPENDENCY_COLLECTION = 10;

    const COLUMNSTATS = 11;

    const REPL_DUMP = 12;

    const REPL_BOOTSTRAP_LOAD = 13;

    const REPL_STATE_LOG = 14;

    const REPL_TXN = 15;

    static public $__names = array(
        0 => 'CONDITIONAL',
        1 => 'COPY',
        2 => 'DDL',
        3 => 'MAPRED',
        4 => 'EXPLAIN',
        5 => 'FETCH',
        6 => 'FUNC',
        7 => 'MAPREDLOCAL',
        8 => 'MOVE',
        9 => 'STATS',
        10 => 'DEPENDENCY_COLLECTION',
        11 => 'COLUMNSTATS',
        12 => 'REPL_DUMP',
        13 => 'REPL_BOOTSTRAP_LOAD',
        14 => 'REPL_STATE_LOG',
        15 => 'REPL_TXN',
    );
}

