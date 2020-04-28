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

class Database
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'name',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        2 => array(
            'var' => 'description',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        3 => array(
            'var' => 'locationUri',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        4 => array(
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
        5 => array(
            'var' => 'privileges',
            'isRequired' => false,
            'type' => TType::STRUCT,
            'class' => '\metastore\PrincipalPrivilegeSet',
        ),
        6 => array(
            'var' => 'ownerName',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        7 => array(
            'var' => 'ownerType',
            'isRequired' => false,
            'type' => TType::I32,
        ),
    );

    /**
     * @var string
     */
    public $name = null;
    /**
     * @var string
     */
    public $description = null;
    /**
     * @var string
     */
    public $locationUri = null;
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
    public $ownerName = null;
    /**
     * @var int
     */
    public $ownerType = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['name'])) {
                $this->name = $vals['name'];
            }
            if (isset($vals['description'])) {
                $this->description = $vals['description'];
            }
            if (isset($vals['locationUri'])) {
                $this->locationUri = $vals['locationUri'];
            }
            if (isset($vals['parameters'])) {
                $this->parameters = $vals['parameters'];
            }
            if (isset($vals['privileges'])) {
                $this->privileges = $vals['privileges'];
            }
            if (isset($vals['ownerName'])) {
                $this->ownerName = $vals['ownerName'];
            }
            if (isset($vals['ownerType'])) {
                $this->ownerType = $vals['ownerType'];
            }
        }
    }

    public function getName()
    {
        return 'Database';
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
                        $xfer += $input->readString($this->name);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 2:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->description);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 3:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->locationUri);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 4:
                    if ($ftype == TType::MAP) {
                        $this->parameters = array();
                        $_size83 = 0;
                        $_ktype84 = 0;
                        $_vtype85 = 0;
                        $xfer += $input->readMapBegin($_ktype84, $_vtype85, $_size83);
                        for ($_i87 = 0; $_i87 < $_size83; ++$_i87) {
                            $key88 = '';
                            $val89 = '';
                            $xfer += $input->readString($key88);
                            $xfer += $input->readString($val89);
                            $this->parameters[$key88] = $val89;
                        }
                        $xfer += $input->readMapEnd();
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 5:
                    if ($ftype == TType::STRUCT) {
                        $this->privileges = new \metastore\PrincipalPrivilegeSet();
                        $xfer += $this->privileges->read($input);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 6:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->ownerName);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 7:
                    if ($ftype == TType::I32) {
                        $xfer += $input->readI32($this->ownerType);
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
        $xfer += $output->writeStructBegin('Database');
        if ($this->name !== null) {
            $xfer += $output->writeFieldBegin('name', TType::STRING, 1);
            $xfer += $output->writeString($this->name);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->description !== null) {
            $xfer += $output->writeFieldBegin('description', TType::STRING, 2);
            $xfer += $output->writeString($this->description);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->locationUri !== null) {
            $xfer += $output->writeFieldBegin('locationUri', TType::STRING, 3);
            $xfer += $output->writeString($this->locationUri);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->parameters !== null) {
            if (!is_array($this->parameters)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('parameters', TType::MAP, 4);
            $output->writeMapBegin(TType::STRING, TType::STRING, count($this->parameters));
            foreach ($this->parameters as $kiter90 => $viter91) {
                $xfer += $output->writeString($kiter90);
                $xfer += $output->writeString($viter91);
            }
            $output->writeMapEnd();
            $xfer += $output->writeFieldEnd();
        }
        if ($this->privileges !== null) {
            if (!is_object($this->privileges)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('privileges', TType::STRUCT, 5);
            $xfer += $this->privileges->write($output);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->ownerName !== null) {
            $xfer += $output->writeFieldBegin('ownerName', TType::STRING, 6);
            $xfer += $output->writeString($this->ownerName);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->ownerType !== null) {
            $xfer += $output->writeFieldBegin('ownerType', TType::I32, 7);
            $xfer += $output->writeI32($this->ownerType);
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
