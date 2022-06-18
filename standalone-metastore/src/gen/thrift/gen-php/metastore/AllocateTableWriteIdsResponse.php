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

class AllocateTableWriteIdsResponse
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'txnToWriteIds',
            'isRequired' => true,
            'type' => TType::LST,
            'etype' => TType::STRUCT,
            'elem' => array(
                'type' => TType::STRUCT,
                'class' => '\metastore\TxnToWriteId',
                ),
        ),
    );

    /**
     * @var \metastore\TxnToWriteId[]
     */
    public $txnToWriteIds = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['txnToWriteIds'])) {
                $this->txnToWriteIds = $vals['txnToWriteIds'];
            }
        }
    }

    public function getName()
    {
        return 'AllocateTableWriteIdsResponse';
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
                        $this->txnToWriteIds = array();
                        $_size565 = 0;
                        $_etype568 = 0;
                        $xfer += $input->readListBegin($_etype568, $_size565);
                        for ($_i569 = 0; $_i569 < $_size565; ++$_i569) {
                            $elem570 = null;
                            $elem570 = new \metastore\TxnToWriteId();
                            $xfer += $elem570->read($input);
                            $this->txnToWriteIds []= $elem570;
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
        $xfer += $output->writeStructBegin('AllocateTableWriteIdsResponse');
        if ($this->txnToWriteIds !== null) {
            if (!is_array($this->txnToWriteIds)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('txnToWriteIds', TType::LST, 1);
            $output->writeListBegin(TType::STRUCT, count($this->txnToWriteIds));
            foreach ($this->txnToWriteIds as $iter571) {
                $xfer += $iter571->write($output);
            }
            $output->writeListEnd();
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
