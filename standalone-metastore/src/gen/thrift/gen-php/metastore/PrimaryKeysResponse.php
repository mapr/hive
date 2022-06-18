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

class PrimaryKeysResponse
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'primaryKeys',
            'isRequired' => true,
            'type' => TType::LST,
            'etype' => TType::STRUCT,
            'elem' => array(
                'type' => TType::STRUCT,
                'class' => '\metastore\SQLPrimaryKey',
                ),
        ),
    );

    /**
     * @var \metastore\SQLPrimaryKey[]
     */
    public $primaryKeys = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['primaryKeys'])) {
                $this->primaryKeys = $vals['primaryKeys'];
            }
        }
    }

    public function getName()
    {
        return 'PrimaryKeysResponse';
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
                        $this->primaryKeys = array();
                        $_size283 = 0;
                        $_etype286 = 0;
                        $xfer += $input->readListBegin($_etype286, $_size283);
                        for ($_i287 = 0; $_i287 < $_size283; ++$_i287) {
                            $elem288 = null;
                            $elem288 = new \metastore\SQLPrimaryKey();
                            $xfer += $elem288->read($input);
                            $this->primaryKeys []= $elem288;
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
        $xfer += $output->writeStructBegin('PrimaryKeysResponse');
        if ($this->primaryKeys !== null) {
            if (!is_array($this->primaryKeys)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('primaryKeys', TType::LST, 1);
            $output->writeListBegin(TType::STRUCT, count($this->primaryKeys));
            foreach ($this->primaryKeys as $iter289) {
                $xfer += $iter289->write($output);
            }
            $output->writeListEnd();
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
