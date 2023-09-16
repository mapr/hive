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

class CommitTxnRequest
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'txnid',
            'isRequired' => true,
            'type' => TType::I64,
        ),
        2 => array(
            'var' => 'replPolicy',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        3 => array(
            'var' => 'writeEventInfos',
            'isRequired' => false,
            'type' => TType::LST,
            'etype' => TType::STRUCT,
            'elem' => array(
                'type' => TType::STRUCT,
                'class' => '\metastore\WriteEventInfo',
                ),
        ),
    );

    /**
     * @var int
     */
    public $txnid = null;
    /**
     * @var string
     */
    public $replPolicy = null;
    /**
     * @var \metastore\WriteEventInfo[]
     */
    public $writeEventInfos = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['txnid'])) {
                $this->txnid = $vals['txnid'];
            }
            if (isset($vals['replPolicy'])) {
                $this->replPolicy = $vals['replPolicy'];
            }
            if (isset($vals['writeEventInfos'])) {
                $this->writeEventInfos = $vals['writeEventInfos'];
            }
        }
    }

    public function getName()
    {
        return 'CommitTxnRequest';
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
                    if ($ftype == TType::I64) {
                        $xfer += $input->readI64($this->txnid);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 2:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->replPolicy);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 3:
                    if ($ftype == TType::LST) {
                        $this->writeEventInfos = array();
                        $_size538 = 0;
                        $_etype541 = 0;
                        $xfer += $input->readListBegin($_etype541, $_size538);
                        for ($_i542 = 0; $_i542 < $_size538; ++$_i542) {
                            $elem543 = null;
                            $elem543 = new \metastore\WriteEventInfo();
                            $xfer += $elem543->read($input);
                            $this->writeEventInfos []= $elem543;
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
        $xfer += $output->writeStructBegin('CommitTxnRequest');
        if ($this->txnid !== null) {
            $xfer += $output->writeFieldBegin('txnid', TType::I64, 1);
            $xfer += $output->writeI64($this->txnid);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->replPolicy !== null) {
            $xfer += $output->writeFieldBegin('replPolicy', TType::STRING, 2);
            $xfer += $output->writeString($this->replPolicy);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->writeEventInfos !== null) {
            if (!is_array($this->writeEventInfos)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('writeEventInfos', TType::LST, 3);
            $output->writeListBegin(TType::STRUCT, count($this->writeEventInfos));
            foreach ($this->writeEventInfos as $iter544) {
                $xfer += $iter544->write($output);
            }
            $output->writeListEnd();
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
