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

class ThriftHiveMetastore_add_partitions_args
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'new_parts',
            'isRequired' => false,
            'type' => TType::LST,
            'etype' => TType::STRUCT,
            'elem' => array(
                'type' => TType::STRUCT,
                'class' => '\metastore\Partition',
                ),
        ),
    );

    /**
     * @var \metastore\Partition[]
     */
    public $new_parts = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['new_parts'])) {
                $this->new_parts = $vals['new_parts'];
            }
        }
    }

    public function getName()
    {
        return 'ThriftHiveMetastore_add_partitions_args';
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
                        $this->new_parts = array();
                        $_size746 = 0;
                        $_etype749 = 0;
                        $xfer += $input->readListBegin($_etype749, $_size746);
                        for ($_i750 = 0; $_i750 < $_size746; ++$_i750) {
                            $elem751 = null;
                            $elem751 = new \metastore\Partition();
                            $xfer += $elem751->read($input);
                            $this->new_parts []= $elem751;
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
        $xfer += $output->writeStructBegin('ThriftHiveMetastore_add_partitions_args');
        if ($this->new_parts !== null) {
            if (!is_array($this->new_parts)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('new_parts', TType::LST, 1);
            $output->writeListBegin(TType::STRUCT, count($this->new_parts));
            foreach ($this->new_parts as $iter752) {
                $xfer += $iter752->write($output);
            }
            $output->writeListEnd();
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
