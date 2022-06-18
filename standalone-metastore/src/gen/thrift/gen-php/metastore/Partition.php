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

class Partition
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'values',
            'isRequired' => false,
            'type' => TType::LST,
            'etype' => TType::STRING,
            'elem' => array(
                'type' => TType::STRING,
                ),
        ),
        2 => array(
            'var' => 'dbName',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        3 => array(
            'var' => 'tableName',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        4 => array(
            'var' => 'createTime',
            'isRequired' => false,
            'type' => TType::I32,
        ),
        5 => array(
            'var' => 'lastAccessTime',
            'isRequired' => false,
            'type' => TType::I32,
        ),
        6 => array(
            'var' => 'sd',
            'isRequired' => false,
            'type' => TType::STRUCT,
            'class' => '\metastore\StorageDescriptor',
        ),
        7 => array(
            'var' => 'parameters',
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
        8 => array(
            'var' => 'privileges',
            'isRequired' => false,
            'type' => TType::STRUCT,
            'class' => '\metastore\PrincipalPrivilegeSet',
        ),
        9 => array(
            'var' => 'catName',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
    );

    /**
     * @var string[]
     */
    public $values = null;
    /**
     * @var string
     */
    public $dbName = null;
    /**
     * @var string
     */
    public $tableName = null;
    /**
     * @var int
     */
    public $createTime = null;
    /**
     * @var int
     */
    public $lastAccessTime = null;
    /**
     * @var \metastore\StorageDescriptor
     */
    public $sd = null;
    /**
     * @var array
     */
    public $parameters = null;
    /**
     * @var \metastore\PrincipalPrivilegeSet
     */
    public $privileges = null;
    /**
     * @var string
     */
    public $catName = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['values'])) {
                $this->values = $vals['values'];
            }
            if (isset($vals['dbName'])) {
                $this->dbName = $vals['dbName'];
            }
            if (isset($vals['tableName'])) {
                $this->tableName = $vals['tableName'];
            }
            if (isset($vals['createTime'])) {
                $this->createTime = $vals['createTime'];
            }
            if (isset($vals['lastAccessTime'])) {
                $this->lastAccessTime = $vals['lastAccessTime'];
            }
            if (isset($vals['sd'])) {
                $this->sd = $vals['sd'];
            }
            if (isset($vals['parameters'])) {
                $this->parameters = $vals['parameters'];
            }
            if (isset($vals['privileges'])) {
                $this->privileges = $vals['privileges'];
            }
            if (isset($vals['catName'])) {
                $this->catName = $vals['catName'];
            }
        }
    }

    public function getName()
    {
        return 'Partition';
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
                    if ($ftype == TType::LST) {
                        $this->values = array();
                        $_size191 = 0;
                        $_etype194 = 0;
                        $xfer += $input->readListBegin($_etype194, $_size191);
                        for ($_i195 = 0; $_i195 < $_size191; ++$_i195) {
                            $elem196 = null;
                            $xfer += $input->readString($elem196);
                            $this->values []= $elem196;
                        }
                        $xfer += $input->readListEnd();
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
                    if ($ftype == TType::I32) {
                        $xfer += $input->readI32($this->createTime);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 5:
                    if ($ftype == TType::I32) {
                        $xfer += $input->readI32($this->lastAccessTime);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 6:
                    if ($ftype == TType::STRUCT) {
                        $this->sd = new \metastore\StorageDescriptor();
                        $xfer += $this->sd->read($input);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 7:
                    if ($ftype == TType::MAP) {
                        $this->parameters = array();
                        $_size197 = 0;
                        $_ktype198 = 0;
                        $_vtype199 = 0;
                        $xfer += $input->readMapBegin($_ktype198, $_vtype199, $_size197);
                        for ($_i201 = 0; $_i201 < $_size197; ++$_i201) {
                            $key202 = '';
                            $val203 = '';
                            $xfer += $input->readString($key202);
                            $xfer += $input->readString($val203);
                            $this->parameters[$key202] = $val203;
                        }
                        $xfer += $input->readMapEnd();
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 8:
                    if ($ftype == TType::STRUCT) {
                        $this->privileges = new \metastore\PrincipalPrivilegeSet();
                        $xfer += $this->privileges->read($input);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 9:
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
        $xfer += $output->writeStructBegin('Partition');
        if ($this->values !== null) {
            if (!is_array($this->values)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('values', TType::LST, 1);
            $output->writeListBegin(TType::STRING, count($this->values));
            foreach ($this->values as $iter204) {
                $xfer += $output->writeString($iter204);
            }
            $output->writeListEnd();
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
        if ($this->createTime !== null) {
            $xfer += $output->writeFieldBegin('createTime', TType::I32, 4);
            $xfer += $output->writeI32($this->createTime);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->lastAccessTime !== null) {
            $xfer += $output->writeFieldBegin('lastAccessTime', TType::I32, 5);
            $xfer += $output->writeI32($this->lastAccessTime);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->sd !== null) {
            if (!is_object($this->sd)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('sd', TType::STRUCT, 6);
            $xfer += $this->sd->write($output);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->parameters !== null) {
            if (!is_array($this->parameters)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('parameters', TType::MAP, 7);
            $output->writeMapBegin(TType::STRING, TType::STRING, count($this->parameters));
            foreach ($this->parameters as $kiter205 => $viter206) {
                $xfer += $output->writeString($kiter205);
                $xfer += $output->writeString($viter206);
            }
            $output->writeMapEnd();
            $xfer += $output->writeFieldEnd();
        }
        if ($this->privileges !== null) {
            if (!is_object($this->privileges)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('privileges', TType::STRUCT, 8);
            $xfer += $this->privileges->write($output);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->catName !== null) {
            $xfer += $output->writeFieldBegin('catName', TType::STRING, 9);
            $xfer += $output->writeString($this->catName);
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
