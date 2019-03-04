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

class CheckLockRequest
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'lockid',
            'isRequired' => true,
            'type' => TType::I64,
        ),
        2 => array(
            'var' => 'txnid',
            'isRequired' => false,
            'type' => TType::I64,
        ),
        3 => array(
            'var' => 'elapsed_ms',
            'isRequired' => false,
            'type' => TType::I64,
        ),
    );

    /**
     * @var int
     */
    public $lockid = null;
    /**
     * @var int
     */
    public $txnid = null;
    /**
     * @var int
     */
    public $elapsed_ms = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['lockid'])) {
                $this->lockid = $vals['lockid'];
            }
            if (isset($vals['txnid'])) {
                $this->txnid = $vals['txnid'];
            }
            if (isset($vals['elapsed_ms'])) {
                $this->elapsed_ms = $vals['elapsed_ms'];
            }
        }
    }

    public function getName()
    {
        return 'CheckLockRequest';
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
                        $xfer += $input->readI64($this->lockid);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 2:
                    if ($ftype == TType::I64) {
                        $xfer += $input->readI64($this->txnid);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 3:
                    if ($ftype == TType::I64) {
                        $xfer += $input->readI64($this->elapsed_ms);
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
        $xfer += $output->writeStructBegin('CheckLockRequest');
        if ($this->lockid !== null) {
            $xfer += $output->writeFieldBegin('lockid', TType::I64, 1);
            $xfer += $output->writeI64($this->lockid);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->txnid !== null) {
            $xfer += $output->writeFieldBegin('txnid', TType::I64, 2);
            $xfer += $output->writeI64($this->txnid);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->elapsed_ms !== null) {
            $xfer += $output->writeFieldBegin('elapsed_ms', TType::I64, 3);
            $xfer += $output->writeI64($this->elapsed_ms);
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
