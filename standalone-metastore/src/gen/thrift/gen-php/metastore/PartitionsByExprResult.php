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

class PartitionsByExprResult
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'partitions',
            'isRequired' => true,
            'type' => TType::LST,
            'etype' => TType::STRUCT,
            'elem' => array(
                'type' => TType::STRUCT,
                'class' => '\metastore\Partition',
                ),
        ),
        2 => array(
            'var' => 'hasUnknownPartitions',
            'isRequired' => true,
            'type' => TType::BOOL,
        ),
    );

    /**
     * @var \metastore\Partition[]
     */
    public $partitions = null;
    /**
     * @var bool
     */
    public $hasUnknownPartitions = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['partitions'])) {
                $this->partitions = $vals['partitions'];
            }
            if (isset($vals['hasUnknownPartitions'])) {
                $this->hasUnknownPartitions = $vals['hasUnknownPartitions'];
            }
        }
    }

    public function getName()
    {
        return 'PartitionsByExprResult';
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
                        $this->partitions = array();
                        $_size367 = 0;
                        $_etype370 = 0;
                        $xfer += $input->readListBegin($_etype370, $_size367);
                        for ($_i371 = 0; $_i371 < $_size367; ++$_i371) {
                            $elem372 = null;
                            $elem372 = new \metastore\Partition();
                            $xfer += $elem372->read($input);
                            $this->partitions []= $elem372;
                        }
                        $xfer += $input->readListEnd();
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 2:
                    if ($ftype == TType::BOOL) {
                        $xfer += $input->readBool($this->hasUnknownPartitions);
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
        $xfer += $output->writeStructBegin('PartitionsByExprResult');
        if ($this->partitions !== null) {
            if (!is_array($this->partitions)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('partitions', TType::LST, 1);
            $output->writeListBegin(TType::STRUCT, count($this->partitions));
            foreach ($this->partitions as $iter373) {
                $xfer += $iter373->write($output);
            }
            $output->writeListEnd();
            $xfer += $output->writeFieldEnd();
        }
        if ($this->hasUnknownPartitions !== null) {
            $xfer += $output->writeFieldBegin('hasUnknownPartitions', TType::BOOL, 2);
            $xfer += $output->writeBool($this->hasUnknownPartitions);
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
