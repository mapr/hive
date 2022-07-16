<?php
namespace metastore;

/**
 * Autogenerated by Thrift Compiler (0.16.0)
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

class WMCreateOrDropTriggerToPoolMappingRequest
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'resourcePlanName',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        2 => array(
            'var' => 'triggerName',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        3 => array(
            'var' => 'poolPath',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        4 => array(
            'var' => 'drop',
            'isRequired' => false,
            'type' => TType::BOOL,
        ),
    );

    /**
     * @var string
     */
    public $resourcePlanName = null;
    /**
     * @var string
     */
    public $triggerName = null;
    /**
     * @var string
     */
    public $poolPath = null;
    /**
     * @var bool
     */
    public $drop = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['resourcePlanName'])) {
                $this->resourcePlanName = $vals['resourcePlanName'];
            }
            if (isset($vals['triggerName'])) {
                $this->triggerName = $vals['triggerName'];
            }
            if (isset($vals['poolPath'])) {
                $this->poolPath = $vals['poolPath'];
            }
            if (isset($vals['drop'])) {
                $this->drop = $vals['drop'];
            }
        }
    }

    public function getName()
    {
        return 'WMCreateOrDropTriggerToPoolMappingRequest';
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
                        $xfer += $input->readString($this->resourcePlanName);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 2:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->triggerName);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 3:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->poolPath);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 4:
                    if ($ftype == TType::BOOL) {
                        $xfer += $input->readBool($this->drop);
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
        $xfer += $output->writeStructBegin('WMCreateOrDropTriggerToPoolMappingRequest');
        if ($this->resourcePlanName !== null) {
            $xfer += $output->writeFieldBegin('resourcePlanName', TType::STRING, 1);
            $xfer += $output->writeString($this->resourcePlanName);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->triggerName !== null) {
            $xfer += $output->writeFieldBegin('triggerName', TType::STRING, 2);
            $xfer += $output->writeString($this->triggerName);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->poolPath !== null) {
            $xfer += $output->writeFieldBegin('poolPath', TType::STRING, 3);
            $xfer += $output->writeString($this->poolPath);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->drop !== null) {
            $xfer += $output->writeFieldBegin('drop', TType::BOOL, 4);
            $xfer += $output->writeBool($this->drop);
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
