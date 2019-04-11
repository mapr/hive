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

class TGetDelegationTokenResp
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'status',
            'isRequired' => true,
            'type' => TType::STRUCT,
            'class' => '\TStatus',
        ),
        2 => array(
            'var' => 'delegationToken',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
    );

    /**
     * @var \TStatus
     */
    public $status = null;
    /**
     * @var string
     */
    public $delegationToken = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['status'])) {
                $this->status = $vals['status'];
            }
            if (isset($vals['delegationToken'])) {
                $this->delegationToken = $vals['delegationToken'];
            }
        }
    }

    public function getName()
    {
        return 'TGetDelegationTokenResp';
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
                    if ($ftype == TType::STRUCT) {
                        $this->status = new \TStatus();
                        $xfer += $this->status->read($input);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 2:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->delegationToken);
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
        $xfer += $output->writeStructBegin('TGetDelegationTokenResp');
        if ($this->status !== null) {
            if (!is_object($this->status)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('status', TType::STRUCT, 1);
            $xfer += $this->status->write($output);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->delegationToken !== null) {
            $xfer += $output->writeFieldBegin('delegationToken', TType::STRING, 2);
            $xfer += $output->writeString($this->delegationToken);
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
