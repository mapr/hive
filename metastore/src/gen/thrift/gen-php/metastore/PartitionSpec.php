<?php
namespace metastore;

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

class PartitionSpec
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'dbName',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        2 => array(
            'var' => 'tableName',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        3 => array(
            'var' => 'rootPath',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        4 => array(
            'var' => 'sharedSDPartitionSpec',
            'isRequired' => false,
            'type' => TType::STRUCT,
            'class' => '\metastore\PartitionSpecWithSharedSD',
        ),
        5 => array(
            'var' => 'partitionList',
            'isRequired' => false,
            'type' => TType::STRUCT,
            'class' => '\metastore\PartitionListComposingSpec',
        ),
    );

    /**
     * @var string
     */
    public $dbName = null;
    /**
     * @var string
     */
    public $tableName = null;
    /**
     * @var string
     */
    public $rootPath = null;
    /**
     * @var \metastore\PartitionSpecWithSharedSD
     */
    public $sharedSDPartitionSpec = null;
    /**
     * @var \metastore\PartitionListComposingSpec
     */
    public $partitionList = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['dbName'])) {
                $this->dbName = $vals['dbName'];
            }
            if (isset($vals['tableName'])) {
                $this->tableName = $vals['tableName'];
            }
            if (isset($vals['rootPath'])) {
                $this->rootPath = $vals['rootPath'];
            }
            if (isset($vals['sharedSDPartitionSpec'])) {
                $this->sharedSDPartitionSpec = $vals['sharedSDPartitionSpec'];
            }
            if (isset($vals['partitionList'])) {
                $this->partitionList = $vals['partitionList'];
            }
        }
    }

    public function getName()
    {
        return 'PartitionSpec';
    }


    public function read($input)
    {
        $xfer = 0;
        $fname = null;
        $ftype = 0;
        $fid = 0;
        $xfer += $input->readStructBegin($fname);
        while (true) {
            $xfer += $input->readFieldBegin($fname, $ftype, $fid);
            if ($ftype == TType::STOP) {
                break;
            }
            switch ($fid) {
                case 1:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->dbName);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 2:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->tableName);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 3:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->rootPath);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 4:
                    if ($ftype == TType::STRUCT) {
                        $this->sharedSDPartitionSpec = new \metastore\PartitionSpecWithSharedSD();
                        $xfer += $this->sharedSDPartitionSpec->read($input);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 5:
                    if ($ftype == TType::STRUCT) {
                        $this->partitionList = new \metastore\PartitionListComposingSpec();
                        $xfer += $this->partitionList->read($input);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                default:
                    $xfer += $input->skip($ftype);
                    break;
            }
            $xfer += $input->readFieldEnd();
        }
        $xfer += $input->readStructEnd();
        return $xfer;
    }

    public function write($output)
    {
        $xfer = 0;
        $xfer += $output->writeStructBegin('PartitionSpec');
        if ($this->dbName !== null) {
            $xfer += $output->writeFieldBegin('dbName', TType::STRING, 1);
            $xfer += $output->writeString($this->dbName);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->tableName !== null) {
            $xfer += $output->writeFieldBegin('tableName', TType::STRING, 2);
            $xfer += $output->writeString($this->tableName);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->rootPath !== null) {
            $xfer += $output->writeFieldBegin('rootPath', TType::STRING, 3);
            $xfer += $output->writeString($this->rootPath);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->sharedSDPartitionSpec !== null) {
            if (!is_object($this->sharedSDPartitionSpec)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('sharedSDPartitionSpec', TType::STRUCT, 4);
            $xfer += $this->sharedSDPartitionSpec->write($output);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->partitionList !== null) {
            if (!is_object($this->partitionList)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('partitionList', TType::STRUCT, 5);
            $xfer += $this->partitionList->write($output);
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
