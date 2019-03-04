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

class ThriftHiveMetastore_update_table_column_statistics_args
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'stats_obj',
            'isRequired' => false,
            'type' => TType::STRUCT,
            'class' => '\metastore\ColumnStatistics',
        ),
    );

    /**
     * @var \metastore\ColumnStatistics
     */
    public $stats_obj = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['stats_obj'])) {
                $this->stats_obj = $vals['stats_obj'];
            }
        }
    }

    public function getName()
    {
        return 'ThriftHiveMetastore_update_table_column_statistics_args';
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
                        $this->stats_obj = new \metastore\ColumnStatistics();
                        $xfer += $this->stats_obj->read($input);
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
        $xfer += $output->writeStructBegin('ThriftHiveMetastore_update_table_column_statistics_args');
        if ($this->stats_obj !== null) {
            if (!is_object($this->stats_obj)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('stats_obj', TType::STRUCT, 1);
            $xfer += $this->stats_obj->write($output);
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
