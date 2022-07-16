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

class WMMapping
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'resourcePlanName',
            'isRequired' => true,
            'type' => TType::STRING,
        ),
        2 => array(
            'var' => 'entityType',
            'isRequired' => true,
            'type' => TType::STRING,
        ),
        3 => array(
            'var' => 'entityName',
            'isRequired' => true,
            'type' => TType::STRING,
        ),
        4 => array(
            'var' => 'poolPath',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        5 => array(
            'var' => 'ordering',
            'isRequired' => false,
            'type' => TType::I32,
        ),
    );

    /**
     * @var string
     */
    public $resourcePlanName = null;
    /**
     * @var string
     */
    public $entityType = null;
    /**
     * @var string
     */
    public $entityName = null;
    /**
     * @var string
     */
    public $poolPath = null;
    /**
     * @var int
     */
    public $ordering = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['resourcePlanName'])) {
                $this->resourcePlanName = $vals['resourcePlanName'];
            }
            if (isset($vals['entityType'])) {
                $this->entityType = $vals['entityType'];
            }
            if (isset($vals['entityName'])) {
                $this->entityName = $vals['entityName'];
            }
            if (isset($vals['poolPath'])) {
                $this->poolPath = $vals['poolPath'];
            }
            if (isset($vals['ordering'])) {
                $this->ordering = $vals['ordering'];
            }
        }
    }

    public function getName()
    {
        return 'WMMapping';
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
                        $xfer += $input->readString($this->entityType);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 3:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->entityName);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 4:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->poolPath);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 5:
                    if ($ftype == TType::I32) {
                        $xfer += $input->readI32($this->ordering);
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
        $xfer += $output->writeStructBegin('WMMapping');
        if ($this->resourcePlanName !== null) {
            $xfer += $output->writeFieldBegin('resourcePlanName', TType::STRING, 1);
            $xfer += $output->writeString($this->resourcePlanName);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->entityType !== null) {
            $xfer += $output->writeFieldBegin('entityType', TType::STRING, 2);
            $xfer += $output->writeString($this->entityType);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->entityName !== null) {
            $xfer += $output->writeFieldBegin('entityName', TType::STRING, 3);
            $xfer += $output->writeString($this->entityName);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->poolPath !== null) {
            $xfer += $output->writeFieldBegin('poolPath', TType::STRING, 4);
            $xfer += $output->writeString($this->poolPath);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->ordering !== null) {
            $xfer += $output->writeFieldBegin('ordering', TType::I32, 5);
            $xfer += $output->writeI32($this->ordering);
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
