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

class LockComponent
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'type',
            'isRequired' => true,
            'type' => TType::I32,
        ),
        2 => array(
            'var' => 'level',
            'isRequired' => true,
            'type' => TType::I32,
        ),
        3 => array(
            'var' => 'dbname',
            'isRequired' => true,
            'type' => TType::STRING,
        ),
        4 => array(
            'var' => 'tablename',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        5 => array(
            'var' => 'partitionname',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        6 => array(
            'var' => 'operationType',
            'isRequired' => false,
            'type' => TType::I32,
        ),
        7 => array(
            'var' => 'isTransactional',
            'isRequired' => false,
            'type' => TType::BOOL,
        ),
        8 => array(
            'var' => 'isDynamicPartitionWrite',
            'isRequired' => false,
            'type' => TType::BOOL,
        ),
    );

    /**
     * @var int
     */
    public $type = null;
    /**
     * @var int
     */
    public $level = null;
    /**
     * @var string
     */
    public $dbname = null;
    /**
     * @var string
     */
    public $tablename = null;
    /**
     * @var string
     */
    public $partitionname = null;
    /**
     * @var int
     */
    public $operationType =     5;
    /**
     * @var bool
     */
    public $isTransactional = false;
    /**
     * @var bool
     */
    public $isDynamicPartitionWrite = false;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['type'])) {
                $this->type = $vals['type'];
            }
            if (isset($vals['level'])) {
                $this->level = $vals['level'];
            }
            if (isset($vals['dbname'])) {
                $this->dbname = $vals['dbname'];
            }
            if (isset($vals['tablename'])) {
                $this->tablename = $vals['tablename'];
            }
            if (isset($vals['partitionname'])) {
                $this->partitionname = $vals['partitionname'];
            }
            if (isset($vals['operationType'])) {
                $this->operationType = $vals['operationType'];
            }
            if (isset($vals['isTransactional'])) {
                $this->isTransactional = $vals['isTransactional'];
            }
            if (isset($vals['isDynamicPartitionWrite'])) {
                $this->isDynamicPartitionWrite = $vals['isDynamicPartitionWrite'];
            }
        }
    }

    public function getName()
    {
        return 'LockComponent';
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
                    if ($ftype == TType::I32) {
                        $xfer += $input->readI32($this->type);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 2:
                    if ($ftype == TType::I32) {
                        $xfer += $input->readI32($this->level);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 3:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->dbname);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 4:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->tablename);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 5:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->partitionname);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 6:
                    if ($ftype == TType::I32) {
                        $xfer += $input->readI32($this->operationType);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 7:
                    if ($ftype == TType::BOOL) {
                        $xfer += $input->readBool($this->isTransactional);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 8:
                    if ($ftype == TType::BOOL) {
                        $xfer += $input->readBool($this->isDynamicPartitionWrite);
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
        $xfer += $output->writeStructBegin('LockComponent');
        if ($this->type !== null) {
            $xfer += $output->writeFieldBegin('type', TType::I32, 1);
            $xfer += $output->writeI32($this->type);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->level !== null) {
            $xfer += $output->writeFieldBegin('level', TType::I32, 2);
            $xfer += $output->writeI32($this->level);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->dbname !== null) {
            $xfer += $output->writeFieldBegin('dbname', TType::STRING, 3);
            $xfer += $output->writeString($this->dbname);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->tablename !== null) {
            $xfer += $output->writeFieldBegin('tablename', TType::STRING, 4);
            $xfer += $output->writeString($this->tablename);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->partitionname !== null) {
            $xfer += $output->writeFieldBegin('partitionname', TType::STRING, 5);
            $xfer += $output->writeString($this->partitionname);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->operationType !== null) {
            $xfer += $output->writeFieldBegin('operationType', TType::I32, 6);
            $xfer += $output->writeI32($this->operationType);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->isTransactional !== null) {
            $xfer += $output->writeFieldBegin('isTransactional', TType::BOOL, 7);
            $xfer += $output->writeBool($this->isTransactional);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->isDynamicPartitionWrite !== null) {
            $xfer += $output->writeFieldBegin('isDynamicPartitionWrite', TType::BOOL, 8);
            $xfer += $output->writeBool($this->isDynamicPartitionWrite);
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
