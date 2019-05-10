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

class ColumnStatisticsObj
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'colName',
            'isRequired' => true,
            'type' => TType::STRING,
        ),
        2 => array(
            'var' => 'colType',
            'isRequired' => true,
            'type' => TType::STRING,
        ),
        3 => array(
            'var' => 'statsData',
            'isRequired' => true,
            'type' => TType::STRUCT,
            'class' => '\metastore\ColumnStatisticsData',
        ),
    );

    /**
     * @var string
     */
    public $colName = null;
    /**
     * @var string
     */
    public $colType = null;
    /**
     * @var \metastore\ColumnStatisticsData
     */
    public $statsData = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['colName'])) {
                $this->colName = $vals['colName'];
            }
            if (isset($vals['colType'])) {
                $this->colType = $vals['colType'];
            }
            if (isset($vals['statsData'])) {
                $this->statsData = $vals['statsData'];
            }
        }
    }

    public function getName()
    {
        return 'ColumnStatisticsObj';
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
                        $xfer += $input->readString($this->colName);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 2:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->colType);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 3:
                    if ($ftype == TType::STRUCT) {
                        $this->statsData = new \metastore\ColumnStatisticsData();
                        $xfer += $this->statsData->read($input);
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
        $xfer += $output->writeStructBegin('ColumnStatisticsObj');
        if ($this->colName !== null) {
            $xfer += $output->writeFieldBegin('colName', TType::STRING, 1);
            $xfer += $output->writeString($this->colName);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->colType !== null) {
            $xfer += $output->writeFieldBegin('colType', TType::STRING, 2);
            $xfer += $output->writeString($this->colType);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->statsData !== null) {
            if (!is_object($this->statsData)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('statsData', TType::STRUCT, 3);
            $xfer += $this->statsData->write($output);
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}