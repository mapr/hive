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

final class TJobExecutionStatus
{
    const IN_PROGRESS = 0;

    const COMPLETE = 1;

    const NOT_AVAILABLE = 2;

    static public $__names = array(
        0 => 'IN_PROGRESS',
        1 => 'COMPLETE',
        2 => 'NOT_AVAILABLE',
    );
}

