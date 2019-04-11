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

class Schema
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'fieldSchemas',
            'isRequired' => false,
            'type' => TType::LST,
            'etype' => TType::STRUCT,
            'elem' => array(
                'type' => TType::STRUCT,
                'class' => '\metastore\FieldSchema',
                ),
        ),
        2 => array(
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
     * @var \metastore\FieldSchema[]
     */
    public $fieldSchemas = null;
    /**
     * @var array
     */
    public $properties = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['fieldSchemas'])) {
                $this->fieldSchemas = $vals['fieldSchemas'];
            }
            if (isset($vals['properties'])) {
                $this->properties = $vals['properties'];
            }
        }
    }

    public function getName()
    {
        return 'Schema';
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
                        $this->fieldSchemas = array();
                        $_size258 = 0;
                        $_etype261 = 0;
                        $xfer += $input->readListBegin($_etype261, $_size258);
                        for ($_i262 = 0; $_i262 < $_size258; ++$_i262) {
                            $elem263 = null;
                            $elem263 = new \metastore\FieldSchema();
                            $xfer += $elem263->read($input);
                            $this->fieldSchemas []= $elem263;
                        }
                        $xfer += $input->readListEnd();
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 2:
                    if ($ftype == TType::MAP) {
                        $this->properties = array();
                        $_size264 = 0;
                        $_ktype265 = 0;
                        $_vtype266 = 0;
                        $xfer += $input->readMapBegin($_ktype265, $_vtype266, $_size264);
                        for ($_i268 = 0; $_i268 < $_size264; ++$_i268) {
                            $key269 = '';
                            $val270 = '';
                            $xfer += $input->readString($key269);
                            $xfer += $input->readString($val270);
                            $this->properties[$key269] = $val270;
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
        $xfer += $output->writeStructBegin('Schema');
        if ($this->fieldSchemas !== null) {
            if (!is_array($this->fieldSchemas)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('fieldSchemas', TType::LST, 1);
            $output->writeListBegin(TType::STRUCT, count($this->fieldSchemas));
            foreach ($this->fieldSchemas as $iter271) {
                $xfer += $iter271->write($output);
            }
            $output->writeListEnd();
            $xfer += $output->writeFieldEnd();
        }
        if ($this->properties !== null) {
            if (!is_array($this->properties)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('properties', TType::MAP, 2);
            $output->writeMapBegin(TType::STRING, TType::STRING, count($this->properties));
            foreach ($this->properties as $kiter272 => $viter273) {
                $xfer += $output->writeString($kiter272);
                $xfer += $output->writeString($viter273);
            }
            $output->writeMapEnd();
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
