<?php
namespace metastore;

/**
 * Autogenerated by Thrift Compiler (0.13.0)
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

class AddDynamicPartitions
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'txnid',
            'isRequired' => true,
            'type' => TType::I64,
        ),
        2 => array(
            'var' => 'writeid',
            'isRequired' => true,
            'type' => TType::I64,
        ),
        3 => array(
            'var' => 'dbname',
            'isRequired' => true,
            'type' => TType::STRING,
        ),
        4 => array(
            'var' => 'tablename',
            'isRequired' => true,
            'type' => TType::STRING,
        ),
        5 => array(
            'var' => 'partitionnames',
            'isRequired' => true,
            'type' => TType::LST,
            'etype' => TType::STRING,
            'elem' => array(
                'type' => TType::STRING,
                ),
        ),
        6 => array(
            'var' => 'operationType',
            'isRequired' => false,
            'type' => TType::I32,
        ),
    );

    /**
     * @var int
     */
    public $txnid = null;
    /**
     * @var int
     */
    public $writeid = null;
    /**
     * @var string
     */
    public $dbname = null;
    /**
     * @var string
     */
    public $tablename = null;
    /**
     * @var string[]
     */
    public $partitionnames = null;
    /**
     * @var int
     */
    public $operationType =     5;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['txnid'])) {
                $this->txnid = $vals['txnid'];
            }
            if (isset($vals['writeid'])) {
                $this->writeid = $vals['writeid'];
            }
            if (isset($vals['dbname'])) {
                $this->dbname = $vals['dbname'];
            }
            if (isset($vals['tablename'])) {
                $this->tablename = $vals['tablename'];
            }
            if (isset($vals['partitionnames'])) {
                $this->partitionnames = $vals['partitionnames'];
            }
            if (isset($vals['operationType'])) {
                $this->operationType = $vals['operationType'];
            }
        }
    }

    public function getName()
    {
        return 'AddDynamicPartitions';
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
                    if ($ftype == TType::I64) {
                        $xfer += $input->readI64($this->txnid);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 2:
                    if ($ftype == TType::I64) {
                        $xfer += $input->readI64($this->writeid);
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
                    if ($ftype == TType::LST) {
                        $this->partitionnames = array();
                        $_size640 = 0;
                        $_etype643 = 0;
                        $xfer += $input->readListBegin($_etype643, $_size640);
                        for ($_i644 = 0; $_i644 < $_size640; ++$_i644) {
                            $elem645 = null;
                            $xfer += $input->readString($elem645);
                            $this->partitionnames []= $elem645;
                        }
                        $xfer += $input->readListEnd();
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
        $xfer += $output->writeStructBegin('AddDynamicPartitions');
        if ($this->txnid !== null) {
            $xfer += $output->writeFieldBegin('txnid', TType::I64, 1);
            $xfer += $output->writeI64($this->txnid);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->writeid !== null) {
            $xfer += $output->writeFieldBegin('writeid', TType::I64, 2);
            $xfer += $output->writeI64($this->writeid);
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
        if ($this->partitionnames !== null) {
            if (!is_array($this->partitionnames)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('partitionnames', TType::LST, 5);
            $output->writeListBegin(TType::STRING, count($this->partitionnames));
            foreach ($this->partitionnames as $iter646) {
                $xfer += $output->writeString($iter646);
            }
            $output->writeListEnd();
            $xfer += $output->writeFieldEnd();
        }
        if ($this->operationType !== null) {
            $xfer += $output->writeFieldBegin('operationType', TType::I32, 6);
            $xfer += $output->writeI32($this->operationType);
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
