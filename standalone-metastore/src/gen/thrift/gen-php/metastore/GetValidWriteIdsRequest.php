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

class GetValidWriteIdsRequest
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'fullTableNames',
            'isRequired' => true,
            'type' => TType::LST,
            'etype' => TType::STRING,
            'elem' => array(
                'type' => TType::STRING,
                ),
        ),
        2 => array(
            'var' => 'validTxnList',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        3 => array(
            'var' => 'writeId',
            'isRequired' => false,
            'type' => TType::I64,
        ),
    );

    /**
     * @var string[]
     */
    public $fullTableNames = null;
    /**
     * @var string
     */
    public $validTxnList = null;
    /**
     * @var int
     */
    public $writeId = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['fullTableNames'])) {
                $this->fullTableNames = $vals['fullTableNames'];
            }
            if (isset($vals['validTxnList'])) {
                $this->validTxnList = $vals['validTxnList'];
            }
            if (isset($vals['writeId'])) {
                $this->writeId = $vals['writeId'];
            }
        }
    }

    public function getName()
    {
        return 'GetValidWriteIdsRequest';
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
                        $this->fullTableNames = array();
                        $_size552 = 0;
                        $_etype555 = 0;
                        $xfer += $input->readListBegin($_etype555, $_size552);
                        for ($_i556 = 0; $_i556 < $_size552; ++$_i556) {
                            $elem557 = null;
                            $xfer += $input->readString($elem557);
                            $this->fullTableNames []= $elem557;
                        }
                        $xfer += $input->readListEnd();
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 2:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->validTxnList);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 3:
                    if ($ftype == TType::I64) {
                        $xfer += $input->readI64($this->writeId);
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
        $xfer += $output->writeStructBegin('GetValidWriteIdsRequest');
        if ($this->fullTableNames !== null) {
            if (!is_array($this->fullTableNames)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('fullTableNames', TType::LST, 1);
            $output->writeListBegin(TType::STRING, count($this->fullTableNames));
            foreach ($this->fullTableNames as $iter558) {
                $xfer += $output->writeString($iter558);
            }
            $output->writeListEnd();
            $xfer += $output->writeFieldEnd();
        }
        if ($this->validTxnList !== null) {
            $xfer += $output->writeFieldBegin('validTxnList', TType::STRING, 2);
            $xfer += $output->writeString($this->validTxnList);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->writeId !== null) {
            $xfer += $output->writeFieldBegin('writeId', TType::I64, 3);
            $xfer += $output->writeI64($this->writeId);
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
