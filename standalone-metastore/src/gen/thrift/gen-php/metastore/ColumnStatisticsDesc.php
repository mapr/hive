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

class ColumnStatisticsDesc
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'isTblLevel',
            'isRequired' => true,
            'type' => TType::BOOL,
        ),
        2 => array(
            'var' => 'dbName',
            'isRequired' => true,
            'type' => TType::STRING,
        ),
        3 => array(
            'var' => 'tableName',
            'isRequired' => true,
            'type' => TType::STRING,
        ),
        4 => array(
            'var' => 'partName',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        5 => array(
            'var' => 'lastAnalyzed',
            'isRequired' => false,
            'type' => TType::I64,
        ),
        6 => array(
            'var' => 'catName',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
    );

    /**
     * @var bool
     */
    public $isTblLevel = null;
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
    public $partName = null;
    /**
     * @var int
     */
    public $lastAnalyzed = null;
    /**
     * @var string
     */
    public $catName = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['isTblLevel'])) {
                $this->isTblLevel = $vals['isTblLevel'];
            }
            if (isset($vals['dbName'])) {
                $this->dbName = $vals['dbName'];
            }
            if (isset($vals['tableName'])) {
                $this->tableName = $vals['tableName'];
            }
            if (isset($vals['partName'])) {
                $this->partName = $vals['partName'];
            }
            if (isset($vals['lastAnalyzed'])) {
                $this->lastAnalyzed = $vals['lastAnalyzed'];
            }
            if (isset($vals['catName'])) {
                $this->catName = $vals['catName'];
            }
        }
    }

    public function getName()
    {
        return 'ColumnStatisticsDesc';
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
                    if ($ftype == TType::BOOL) {
                        $xfer += $input->readBool($this->isTblLevel);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 2:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->dbName);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 3:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->tableName);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 4:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->partName);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 5:
                    if ($ftype == TType::I64) {
                        $xfer += $input->readI64($this->lastAnalyzed);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 6:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->catName);
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
        $xfer += $output->writeStructBegin('ColumnStatisticsDesc');
        if ($this->isTblLevel !== null) {
            $xfer += $output->writeFieldBegin('isTblLevel', TType::BOOL, 1);
            $xfer += $output->writeBool($this->isTblLevel);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->dbName !== null) {
            $xfer += $output->writeFieldBegin('dbName', TType::STRING, 2);
            $xfer += $output->writeString($this->dbName);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->tableName !== null) {
            $xfer += $output->writeFieldBegin('tableName', TType::STRING, 3);
            $xfer += $output->writeString($this->tableName);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->partName !== null) {
            $xfer += $output->writeFieldBegin('partName', TType::STRING, 4);
            $xfer += $output->writeString($this->partName);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->lastAnalyzed !== null) {
            $xfer += $output->writeFieldBegin('lastAnalyzed', TType::I64, 5);
            $xfer += $output->writeI64($this->lastAnalyzed);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->catName !== null) {
            $xfer += $output->writeFieldBegin('catName', TType::STRING, 6);
            $xfer += $output->writeString($this->catName);
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
