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

class CompactionRequest
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'dbname',
            'isRequired' => true,
            'type' => TType::STRING,
        ),
        2 => array(
            'var' => 'tablename',
            'isRequired' => true,
            'type' => TType::STRING,
        ),
        3 => array(
            'var' => 'partitionname',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        4 => array(
            'var' => 'type',
            'isRequired' => true,
            'type' => TType::I32,
        ),
        5 => array(
            'var' => 'runas',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        6 => array(
            'var' => 'properties',
            'isRequired' => false,
            'type' => TType::MAP,
            'ktype' => TType::STRING,
            'vtype' => TType::STRING,
            'key' => array(
                'type' => TType::STRING,
            ),
            'val' => array(
                'type' => TType::STRING,
                ),
        ),
    );

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
    public $type = null;
    /**
     * @var string
     */
    public $runas = null;
    /**
     * @var array
     */
    public $properties = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['dbname'])) {
                $this->dbname = $vals['dbname'];
            }
            if (isset($vals['tablename'])) {
                $this->tablename = $vals['tablename'];
            }
            if (isset($vals['partitionname'])) {
                $this->partitionname = $vals['partitionname'];
            }
            if (isset($vals['type'])) {
                $this->type = $vals['type'];
            }
            if (isset($vals['runas'])) {
                $this->runas = $vals['runas'];
            }
            if (isset($vals['properties'])) {
                $this->properties = $vals['properties'];
            }
        }
    }

    public function getName()
    {
        return 'CompactionRequest';
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
                        $xfer += $input->readString($this->dbname);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 2:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->tablename);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 3:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->partitionname);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 4:
                    if ($ftype == TType::I32) {
                        $xfer += $input->readI32($this->type);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 5:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->runas);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 6:
                    if ($ftype == TType::MAP) {
                        $this->properties = array();
                        $_size493 = 0;
                        $_ktype494 = 0;
                        $_vtype495 = 0;
                        $xfer += $input->readMapBegin($_ktype494, $_vtype495, $_size493);
                        for ($_i497 = 0; $_i497 < $_size493; ++$_i497) {
                            $key498 = '';
                            $val499 = '';
                            $xfer += $input->readString($key498);
                            $xfer += $input->readString($val499);
                            $this->properties[$key498] = $val499;
                        }
                        $xfer += $input->readMapEnd();
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
        $xfer += $output->writeStructBegin('CompactionRequest');
        if ($this->dbname !== null) {
            $xfer += $output->writeFieldBegin('dbname', TType::STRING, 1);
            $xfer += $output->writeString($this->dbname);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->tablename !== null) {
            $xfer += $output->writeFieldBegin('tablename', TType::STRING, 2);
            $xfer += $output->writeString($this->tablename);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->partitionname !== null) {
            $xfer += $output->writeFieldBegin('partitionname', TType::STRING, 3);
            $xfer += $output->writeString($this->partitionname);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->type !== null) {
            $xfer += $output->writeFieldBegin('type', TType::I32, 4);
            $xfer += $output->writeI32($this->type);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->runas !== null) {
            $xfer += $output->writeFieldBegin('runas', TType::STRING, 5);
            $xfer += $output->writeString($this->runas);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->properties !== null) {
            if (!is_array($this->properties)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('properties', TType::MAP, 6);
            $output->writeMapBegin(TType::STRING, TType::STRING, count($this->properties));
            foreach ($this->properties as $kiter500 => $viter501) {
                $xfer += $output->writeString($kiter500);
                $xfer += $output->writeString($viter501);
            }
            $output->writeMapEnd();
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
