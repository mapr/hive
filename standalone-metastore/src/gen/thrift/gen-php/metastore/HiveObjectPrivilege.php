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

class HiveObjectPrivilege
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'hiveObject',
            'isRequired' => false,
            'type' => TType::STRUCT,
            'class' => '\metastore\HiveObjectRef',
        ),
        2 => array(
            'var' => 'principalName',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        3 => array(
            'var' => 'principalType',
            'isRequired' => false,
            'type' => TType::I32,
        ),
        4 => array(
            'var' => 'grantInfo',
            'isRequired' => false,
            'type' => TType::STRUCT,
            'class' => '\metastore\PrivilegeGrantInfo',
        ),
        5 => array(
            'var' => 'authorizer',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
    );

    /**
     * @var \metastore\HiveObjectRef
     */
    public $hiveObject = null;
    /**
     * @var string
     */
    public $principalName = null;
    /**
     * @var int
     */
    public $principalType = null;
    /**
     * @var \metastore\PrivilegeGrantInfo
     */
    public $grantInfo = null;
    /**
     * @var string
     */
    public $authorizer = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['hiveObject'])) {
                $this->hiveObject = $vals['hiveObject'];
            }
            if (isset($vals['principalName'])) {
                $this->principalName = $vals['principalName'];
            }
            if (isset($vals['principalType'])) {
                $this->principalType = $vals['principalType'];
            }
            if (isset($vals['grantInfo'])) {
                $this->grantInfo = $vals['grantInfo'];
            }
            if (isset($vals['authorizer'])) {
                $this->authorizer = $vals['authorizer'];
            }
        }
    }

    public function getName()
    {
        return 'HiveObjectPrivilege';
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
                        $this->hiveObject = new \metastore\HiveObjectRef();
                        $xfer += $this->hiveObject->read($input);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 2:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->principalName);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 3:
                    if ($ftype == TType::I32) {
                        $xfer += $input->readI32($this->principalType);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 4:
                    if ($ftype == TType::STRUCT) {
                        $this->grantInfo = new \metastore\PrivilegeGrantInfo();
                        $xfer += $this->grantInfo->read($input);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 5:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->authorizer);
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
        $xfer += $output->writeStructBegin('HiveObjectPrivilege');
        if ($this->hiveObject !== null) {
            if (!is_object($this->hiveObject)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('hiveObject', TType::STRUCT, 1);
            $xfer += $this->hiveObject->write($output);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->principalName !== null) {
            $xfer += $output->writeFieldBegin('principalName', TType::STRING, 2);
            $xfer += $output->writeString($this->principalName);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->principalType !== null) {
            $xfer += $output->writeFieldBegin('principalType', TType::I32, 3);
            $xfer += $output->writeI32($this->principalType);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->grantInfo !== null) {
            if (!is_object($this->grantInfo)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('grantInfo', TType::STRUCT, 4);
            $xfer += $this->grantInfo->write($output);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->authorizer !== null) {
            $xfer += $output->writeFieldBegin('authorizer', TType::STRING, 5);
            $xfer += $output->writeString($this->authorizer);
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
