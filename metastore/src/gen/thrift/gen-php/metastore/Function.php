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

class Function
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'functionName',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        2 => array(
            'var' => 'dbName',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        3 => array(
            'var' => 'className',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        4 => array(
            'var' => 'ownerName',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        5 => array(
            'var' => 'ownerType',
            'isRequired' => false,
            'type' => TType::I32,
        ),
        6 => array(
            'var' => 'createTime',
            'isRequired' => false,
            'type' => TType::I32,
        ),
        7 => array(
            'var' => 'functionType',
            'isRequired' => false,
            'type' => TType::I32,
        ),
        8 => array(
            'var' => 'resourceUris',
            'isRequired' => false,
            'type' => TType::LST,
            'etype' => TType::STRUCT,
            'elem' => array(
                'type' => TType::STRUCT,
                'class' => '\metastore\ResourceUri',
                ),
        ),
    );

    /**
     * @var string
     */
    public $functionName = null;
    /**
     * @var string
     */
    public $dbName = null;
    /**
     * @var string
     */
    public $className = null;
    /**
     * @var string
     */
    public $ownerName = null;
    /**
     * @var int
     */
    public $ownerType = null;
    /**
     * @var int
     */
    public $createTime = null;
    /**
     * @var int
     */
    public $functionType = null;
    /**
     * @var \metastore\ResourceUri[]
     */
    public $resourceUris = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['functionName'])) {
                $this->functionName = $vals['functionName'];
            }
            if (isset($vals['dbName'])) {
                $this->dbName = $vals['dbName'];
            }
            if (isset($vals['className'])) {
                $this->className = $vals['className'];
            }
            if (isset($vals['ownerName'])) {
                $this->ownerName = $vals['ownerName'];
            }
            if (isset($vals['ownerType'])) {
                $this->ownerType = $vals['ownerType'];
            }
            if (isset($vals['createTime'])) {
                $this->createTime = $vals['createTime'];
            }
            if (isset($vals['functionType'])) {
                $this->functionType = $vals['functionType'];
            }
            if (isset($vals['resourceUris'])) {
                $this->resourceUris = $vals['resourceUris'];
            }
        }
    }

    public function getName()
    {
        return 'Function';
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
                        $xfer += $input->readString($this->functionName);
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
                        $xfer += $input->readString($this->className);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 4:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->ownerName);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 5:
                    if ($ftype == TType::I32) {
                        $xfer += $input->readI32($this->ownerType);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 6:
                    if ($ftype == TType::I32) {
                        $xfer += $input->readI32($this->createTime);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 7:
                    if ($ftype == TType::I32) {
                        $xfer += $input->readI32($this->functionType);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 8:
                    if ($ftype == TType::LST) {
                        $this->resourceUris = array();
                        $_size427 = 0;
                        $_etype430 = 0;
                        $xfer += $input->readListBegin($_etype430, $_size427);
                        for ($_i431 = 0; $_i431 < $_size427; ++$_i431) {
                            $elem432 = null;
                            $elem432 = new \metastore\ResourceUri();
                            $xfer += $elem432->read($input);
                            $this->resourceUris []= $elem432;
                        }
                        $xfer += $input->readListEnd();
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
        $xfer += $output->writeStructBegin('Function');
        if ($this->functionName !== null) {
            $xfer += $output->writeFieldBegin('functionName', TType::STRING, 1);
            $xfer += $output->writeString($this->functionName);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->dbName !== null) {
            $xfer += $output->writeFieldBegin('dbName', TType::STRING, 2);
            $xfer += $output->writeString($this->dbName);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->className !== null) {
            $xfer += $output->writeFieldBegin('className', TType::STRING, 3);
            $xfer += $output->writeString($this->className);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->ownerName !== null) {
            $xfer += $output->writeFieldBegin('ownerName', TType::STRING, 4);
            $xfer += $output->writeString($this->ownerName);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->ownerType !== null) {
            $xfer += $output->writeFieldBegin('ownerType', TType::I32, 5);
            $xfer += $output->writeI32($this->ownerType);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->createTime !== null) {
            $xfer += $output->writeFieldBegin('createTime', TType::I32, 6);
            $xfer += $output->writeI32($this->createTime);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->functionType !== null) {
            $xfer += $output->writeFieldBegin('functionType', TType::I32, 7);
            $xfer += $output->writeI32($this->functionType);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->resourceUris !== null) {
            if (!is_array($this->resourceUris)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('resourceUris', TType::LST, 8);
            $output->writeListBegin(TType::STRUCT, count($this->resourceUris));
            foreach ($this->resourceUris as $iter433) {
                $xfer += $iter433->write($output);
            }
            $output->writeListEnd();
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
