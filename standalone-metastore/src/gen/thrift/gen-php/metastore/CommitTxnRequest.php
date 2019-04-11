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
    );

    /**
     * @var int
     */
    public $txnid = null;
    /**
     * @var string
     */
    public $replPolicy = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['txnid'])) {
                $this->txnid = $vals['txnid'];
            }
            if (isset($vals['replPolicy'])) {
                $this->replPolicy = $vals['replPolicy'];
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
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
