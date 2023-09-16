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

class Table
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'tableName',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        2 => array(
            'var' => 'dbName',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        3 => array(
            'var' => 'owner',
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
            'var' => 'retention',
            'isRequired' => false,
            'type' => TType::I32,
        ),
        7 => array(
            'var' => 'sd',
            'isRequired' => false,
            'type' => TType::STRUCT,
            'class' => '\metastore\StorageDescriptor',
        ),
        8 => array(
            'var' => 'partitionKeys',
            'isRequired' => false,
            'type' => TType::LST,
            'etype' => TType::STRUCT,
            'elem' => array(
                'type' => TType::STRUCT,
                'class' => '\metastore\FieldSchema',
                ),
        ),
        9 => array(
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
        10 => array(
            'var' => 'viewOriginalText',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        11 => array(
            'var' => 'viewExpandedText',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        12 => array(
            'var' => 'tableType',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        13 => array(
            'var' => 'privileges',
            'isRequired' => false,
            'type' => TType::STRUCT,
            'class' => '\metastore\PrincipalPrivilegeSet',
        ),
        14 => array(
            'var' => 'temporary',
            'isRequired' => false,
            'type' => TType::BOOL,
        ),
        15 => array(
            'var' => 'rewriteEnabled',
            'isRequired' => false,
            'type' => TType::BOOL,
        ),
        16 => array(
            'var' => 'creationMetadata',
            'isRequired' => false,
            'type' => TType::STRUCT,
            'class' => '\metastore\CreationMetadata',
        ),
        17 => array(
            'var' => 'catName',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        18 => array(
            'var' => 'ownerType',
            'isRequired' => false,
            'type' => TType::I32,
        ),
        19 => array(
            'var' => 'writeId',
            'isRequired' => false,
            'type' => TType::I64,
        ),
        20 => array(
            'var' => 'isStatsCompliant',
            'isRequired' => false,
            'type' => TType::BOOL,
        ),
    );

    /**
     * @var string
     */
    public $tableName = null;
    /**
     * @var string
     */
    public $dbName = null;
    /**
     * @var string
     */
    public $owner = null;
    /**
     * @var int
     */
    public $createTime = null;
    /**
     * @var int
     */
    public $lastAccessTime = null;
    /**
     * @var int
     */
    public $retention = null;
    /**
     * @var \metastore\StorageDescriptor
     */
    public $sd = null;
    /**
     * @var \metastore\FieldSchema[]
     */
    public $partitionKeys = null;
    /**
     * @var array
     */
    public $parameters = null;
    /**
     * @var string
     */
    public $viewOriginalText = null;
    /**
     * @var string
     */
    public $viewExpandedText = null;
    /**
     * @var string
     */
    public $tableType = null;
    /**
     * @var \metastore\PrincipalPrivilegeSet
     */
    public $privileges = null;
    /**
     * @var bool
     */
    public $temporary = false;
    /**
     * @var bool
     */
    public $rewriteEnabled = null;
    /**
     * @var \metastore\CreationMetadata
     */
    public $creationMetadata = null;
    /**
     * @var string
     */
    public $catName = null;
    /**
     * @var int
     */
    public $ownerType =     1;
    /**
     * @var int
     */
    public $writeId = -1;
    /**
     * @var bool
     */
    public $isStatsCompliant = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['tableName'])) {
                $this->tableName = $vals['tableName'];
            }
            if (isset($vals['dbName'])) {
                $this->dbName = $vals['dbName'];
            }
            if (isset($vals['owner'])) {
                $this->owner = $vals['owner'];
            }
            if (isset($vals['createTime'])) {
                $this->createTime = $vals['createTime'];
            }
            if (isset($vals['lastAccessTime'])) {
                $this->lastAccessTime = $vals['lastAccessTime'];
            }
            if (isset($vals['retention'])) {
                $this->retention = $vals['retention'];
            }
            if (isset($vals['sd'])) {
                $this->sd = $vals['sd'];
            }
            if (isset($vals['partitionKeys'])) {
                $this->partitionKeys = $vals['partitionKeys'];
            }
            if (isset($vals['parameters'])) {
                $this->parameters = $vals['parameters'];
            }
            if (isset($vals['viewOriginalText'])) {
                $this->viewOriginalText = $vals['viewOriginalText'];
            }
            if (isset($vals['viewExpandedText'])) {
                $this->viewExpandedText = $vals['viewExpandedText'];
            }
            if (isset($vals['tableType'])) {
                $this->tableType = $vals['tableType'];
            }
            if (isset($vals['privileges'])) {
                $this->privileges = $vals['privileges'];
            }
            if (isset($vals['temporary'])) {
                $this->temporary = $vals['temporary'];
            }
            if (isset($vals['rewriteEnabled'])) {
                $this->rewriteEnabled = $vals['rewriteEnabled'];
            }
            if (isset($vals['creationMetadata'])) {
                $this->creationMetadata = $vals['creationMetadata'];
            }
            if (isset($vals['catName'])) {
                $this->catName = $vals['catName'];
            }
            if (isset($vals['ownerType'])) {
                $this->ownerType = $vals['ownerType'];
            }
            if (isset($vals['writeId'])) {
                $this->writeId = $vals['writeId'];
            }
            if (isset($vals['isStatsCompliant'])) {
                $this->isStatsCompliant = $vals['isStatsCompliant'];
            }
        }
    }

    public function getName()
    {
        return 'Table';
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
                        $xfer += $input->readString($this->tableName);
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
                        $xfer += $input->readString($this->owner);
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
                    if ($ftype == TType::I32) {
                        $xfer += $input->readI32($this->retention);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 7:
                    if ($ftype == TType::STRUCT) {
                        $this->sd = new \metastore\StorageDescriptor();
                        $xfer += $this->sd->read($input);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 8:
                    if ($ftype == TType::LST) {
                        $this->partitionKeys = array();
                        $_size190 = 0;
                        $_etype193 = 0;
                        $xfer += $input->readListBegin($_etype193, $_size190);
                        for ($_i194 = 0; $_i194 < $_size190; ++$_i194) {
                            $elem195 = null;
                            $elem195 = new \metastore\FieldSchema();
                            $xfer += $elem195->read($input);
                            $this->partitionKeys []= $elem195;
                        }
                        $xfer += $input->readListEnd();
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 9:
                    if ($ftype == TType::MAP) {
                        $this->parameters = array();
                        $_size196 = 0;
                        $_ktype197 = 0;
                        $_vtype198 = 0;
                        $xfer += $input->readMapBegin($_ktype197, $_vtype198, $_size196);
                        for ($_i200 = 0; $_i200 < $_size196; ++$_i200) {
                            $key201 = '';
                            $val202 = '';
                            $xfer += $input->readString($key201);
                            $xfer += $input->readString($val202);
                            $this->parameters[$key201] = $val202;
                        }
                        $xfer += $input->readMapEnd();
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 10:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->viewOriginalText);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 11:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->viewExpandedText);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 12:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->tableType);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 13:
                    if ($ftype == TType::STRUCT) {
                        $this->privileges = new \metastore\PrincipalPrivilegeSet();
                        $xfer += $this->privileges->read($input);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 14:
                    if ($ftype == TType::BOOL) {
                        $xfer += $input->readBool($this->temporary);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 15:
                    if ($ftype == TType::BOOL) {
                        $xfer += $input->readBool($this->rewriteEnabled);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 16:
                    if ($ftype == TType::STRUCT) {
                        $this->creationMetadata = new \metastore\CreationMetadata();
                        $xfer += $this->creationMetadata->read($input);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 17:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->catName);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 18:
                    if ($ftype == TType::I32) {
                        $xfer += $input->readI32($this->ownerType);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 19:
                    if ($ftype == TType::I64) {
                        $xfer += $input->readI64($this->writeId);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 20:
                    if ($ftype == TType::BOOL) {
                        $xfer += $input->readBool($this->isStatsCompliant);
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
        $xfer += $output->writeStructBegin('Table');
        if ($this->tableName !== null) {
            $xfer += $output->writeFieldBegin('tableName', TType::STRING, 1);
            $xfer += $output->writeString($this->tableName);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->dbName !== null) {
            $xfer += $output->writeFieldBegin('dbName', TType::STRING, 2);
            $xfer += $output->writeString($this->dbName);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->owner !== null) {
            $xfer += $output->writeFieldBegin('owner', TType::STRING, 3);
            $xfer += $output->writeString($this->owner);
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
        if ($this->retention !== null) {
            $xfer += $output->writeFieldBegin('retention', TType::I32, 6);
            $xfer += $output->writeI32($this->retention);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->sd !== null) {
            if (!is_object($this->sd)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('sd', TType::STRUCT, 7);
            $xfer += $this->sd->write($output);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->partitionKeys !== null) {
            if (!is_array($this->partitionKeys)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('partitionKeys', TType::LST, 8);
            $output->writeListBegin(TType::STRUCT, count($this->partitionKeys));
            foreach ($this->partitionKeys as $iter203) {
                $xfer += $iter203->write($output);
            }
            $output->writeListEnd();
            $xfer += $output->writeFieldEnd();
        }
        if ($this->parameters !== null) {
            if (!is_array($this->parameters)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('parameters', TType::MAP, 9);
            $output->writeMapBegin(TType::STRING, TType::STRING, count($this->parameters));
            foreach ($this->parameters as $kiter204 => $viter205) {
                $xfer += $output->writeString($kiter204);
                $xfer += $output->writeString($viter205);
            }
            $output->writeMapEnd();
            $xfer += $output->writeFieldEnd();
        }
        if ($this->viewOriginalText !== null) {
            $xfer += $output->writeFieldBegin('viewOriginalText', TType::STRING, 10);
            $xfer += $output->writeString($this->viewOriginalText);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->viewExpandedText !== null) {
            $xfer += $output->writeFieldBegin('viewExpandedText', TType::STRING, 11);
            $xfer += $output->writeString($this->viewExpandedText);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->tableType !== null) {
            $xfer += $output->writeFieldBegin('tableType', TType::STRING, 12);
            $xfer += $output->writeString($this->tableType);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->privileges !== null) {
            if (!is_object($this->privileges)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('privileges', TType::STRUCT, 13);
            $xfer += $this->privileges->write($output);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->temporary !== null) {
            $xfer += $output->writeFieldBegin('temporary', TType::BOOL, 14);
            $xfer += $output->writeBool($this->temporary);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->rewriteEnabled !== null) {
            $xfer += $output->writeFieldBegin('rewriteEnabled', TType::BOOL, 15);
            $xfer += $output->writeBool($this->rewriteEnabled);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->creationMetadata !== null) {
            if (!is_object($this->creationMetadata)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('creationMetadata', TType::STRUCT, 16);
            $xfer += $this->creationMetadata->write($output);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->catName !== null) {
            $xfer += $output->writeFieldBegin('catName', TType::STRING, 17);
            $xfer += $output->writeString($this->catName);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->ownerType !== null) {
            $xfer += $output->writeFieldBegin('ownerType', TType::I32, 18);
            $xfer += $output->writeI32($this->ownerType);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->writeId !== null) {
            $xfer += $output->writeFieldBegin('writeId', TType::I64, 19);
            $xfer += $output->writeI64($this->writeId);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->isStatsCompliant !== null) {
            $xfer += $output->writeFieldBegin('isStatsCompliant', TType::BOOL, 20);
            $xfer += $output->writeBool($this->isStatsCompliant);
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
