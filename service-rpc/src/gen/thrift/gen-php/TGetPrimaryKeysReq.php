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

class TGetPrimaryKeysReq
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'sessionHandle',
            'isRequired' => true,
            'type' => TType::STRUCT,
            'class' => '\TSessionHandle',
        ),
        2 => array(
            'var' => 'catalogName',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        3 => array(
            'var' => 'schemaName',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        4 => array(
            'var' => 'tableName',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
    );

    /**
     * @var \TSessionHandle
     */
    public $sessionHandle = null;
    /**
     * @var string
     */
    public $catalogName = null;
    /**
     * @var string
     */
    public $schemaName = null;
    /**
     * @var string
     */
    public $tableName = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['sessionHandle'])) {
                $this->sessionHandle = $vals['sessionHandle'];
            }
            if (isset($vals['catalogName'])) {
                $this->catalogName = $vals['catalogName'];
            }
            if (isset($vals['schemaName'])) {
                $this->schemaName = $vals['schemaName'];
            }
            if (isset($vals['tableName'])) {
                $this->tableName = $vals['tableName'];
            }
        }
    }

    public function getName()
    {
        return 'TGetPrimaryKeysReq';
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
                    if ($ftype == TType::STRUCT) {
                        $this->sessionHandle = new \TSessionHandle();
                        $xfer += $this->sessionHandle->read($input);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 2:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->catalogName);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 3:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->schemaName);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 4:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->tableName);
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
        $xfer += $output->writeStructBegin('TGetPrimaryKeysReq');
        if ($this->sessionHandle !== null) {
            if (!is_object($this->sessionHandle)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('sessionHandle', TType::STRUCT, 1);
            $xfer += $this->sessionHandle->write($output);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->catalogName !== null) {
            $xfer += $output->writeFieldBegin('catalogName', TType::STRING, 2);
            $xfer += $output->writeString($this->catalogName);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->schemaName !== null) {
            $xfer += $output->writeFieldBegin('schemaName', TType::STRING, 3);
            $xfer += $output->writeString($this->schemaName);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->tableName !== null) {
            $xfer += $output->writeFieldBegin('tableName', TType::STRING, 4);
            $xfer += $output->writeString($this->tableName);
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
