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

class ThriftTestObj
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'field1',
            'isRequired' => false,
            'type' => TType::I32,
        ),
        2 => array(
            'var' => 'field2',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        3 => array(
            'var' => 'field3',
            'isRequired' => false,
            'type' => TType::LST,
            'etype' => TType::STRUCT,
            'elem' => array(
                'type' => TType::STRUCT,
                'class' => '\InnerStruct',
                ),
        ),
    );

    /**
     * @var int
     */
    public $field1 = null;
    /**
     * @var string
     */
    public $field2 = null;
    /**
     * @var \InnerStruct[]
     */
    public $field3 = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['field1'])) {
                $this->field1 = $vals['field1'];
            }
            if (isset($vals['field2'])) {
                $this->field2 = $vals['field2'];
            }
            if (isset($vals['field3'])) {
                $this->field3 = $vals['field3'];
            }
        }
    }

    public function getName()
    {
        return 'ThriftTestObj';
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
                    if ($ftype == TType::I32) {
                        $xfer += $input->readI32($this->field1);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 2:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->field2);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 3:
                    if ($ftype == TType::LST) {
                        $this->field3 = array();
                        $_size0 = 0;
                        $_etype3 = 0;
                        $xfer += $input->readListBegin($_etype3, $_size0);
                        for ($_i4 = 0; $_i4 < $_size0; ++$_i4) {
                            $elem5 = null;
                            $elem5 = new \InnerStruct();
                            $xfer += $elem5->read($input);
                            $this->field3 []= $elem5;
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
        $xfer += $output->writeStructBegin('ThriftTestObj');
        if ($this->field1 !== null) {
            $xfer += $output->writeFieldBegin('field1', TType::I32, 1);
            $xfer += $output->writeI32($this->field1);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->field2 !== null) {
            $xfer += $output->writeFieldBegin('field2', TType::STRING, 2);
            $xfer += $output->writeString($this->field2);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->field3 !== null) {
            if (!is_array($this->field3)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('field3', TType::LST, 3);
            $output->writeListBegin(TType::STRUCT, count($this->field3));
            foreach ($this->field3 as $iter6) {
                $xfer += $iter6->write($output);
            }
            $output->writeListEnd();
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
