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

class InsertEventRequestData
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'filesAdded',
            'isRequired' => true,
            'type' => TType::LST,
            'etype' => TType::STRING,
            'elem' => array(
                'type' => TType::STRING,
                ),
        ),
        2 => array(
            'var' => 'filesAddedChecksum',
            'isRequired' => false,
            'type' => TType::LST,
            'etype' => TType::STRING,
            'elem' => array(
                'type' => TType::STRING,
                ),
        ),
    );

    /**
     * @var string[]
     */
    public $filesAdded = null;
    /**
     * @var string[]
     */
    public $filesAddedChecksum = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['filesAdded'])) {
                $this->filesAdded = $vals['filesAdded'];
            }
            if (isset($vals['filesAddedChecksum'])) {
                $this->filesAddedChecksum = $vals['filesAddedChecksum'];
            }
        }
    }

    public function getName()
    {
        return 'InsertEventRequestData';
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
                        $this->filesAdded = array();
                        $_size523 = 0;
                        $_etype526 = 0;
                        $xfer += $input->readListBegin($_etype526, $_size523);
                        for ($_i527 = 0; $_i527 < $_size523; ++$_i527) {
                            $elem528 = null;
                            $xfer += $input->readString($elem528);
                            $this->filesAdded []= $elem528;
                        }
                        $xfer += $input->readListEnd();
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 2:
                    if ($ftype == TType::LST) {
                        $this->filesAddedChecksum = array();
                        $_size529 = 0;
                        $_etype532 = 0;
                        $xfer += $input->readListBegin($_etype532, $_size529);
                        for ($_i533 = 0; $_i533 < $_size529; ++$_i533) {
                            $elem534 = null;
                            $xfer += $input->readString($elem534);
                            $this->filesAddedChecksum []= $elem534;
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
        $xfer += $output->writeStructBegin('InsertEventRequestData');
        if ($this->filesAdded !== null) {
            if (!is_array($this->filesAdded)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('filesAdded', TType::LST, 1);
            $output->writeListBegin(TType::STRING, count($this->filesAdded));
            foreach ($this->filesAdded as $iter535) {
                $xfer += $output->writeString($iter535);
            }
            $output->writeListEnd();
            $xfer += $output->writeFieldEnd();
        }
        if ($this->filesAddedChecksum !== null) {
            if (!is_array($this->filesAddedChecksum)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('filesAddedChecksum', TType::LST, 2);
            $output->writeListBegin(TType::STRING, count($this->filesAddedChecksum));
            foreach ($this->filesAddedChecksum as $iter536) {
                $xfer += $output->writeString($iter536);
            }
            $output->writeListEnd();
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
