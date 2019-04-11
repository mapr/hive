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

class UniqueConstraintsResponse
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'uniqueConstraints',
            'isRequired' => true,
            'type' => TType::LST,
            'etype' => TType::STRUCT,
            'elem' => array(
                'type' => TType::STRUCT,
                'class' => '\metastore\SQLUniqueConstraint',
                ),
        ),
    );

    /**
     * @var \metastore\SQLUniqueConstraint[]
     */
    public $uniqueConstraints = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['uniqueConstraints'])) {
                $this->uniqueConstraints = $vals['uniqueConstraints'];
            }
        }
    }

    public function getName()
    {
        return 'UniqueConstraintsResponse';
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
                        $this->uniqueConstraints = array();
                        $_size297 = 0;
                        $_etype300 = 0;
                        $xfer += $input->readListBegin($_etype300, $_size297);
                        for ($_i301 = 0; $_i301 < $_size297; ++$_i301) {
                            $elem302 = null;
                            $elem302 = new \metastore\SQLUniqueConstraint();
                            $xfer += $elem302->read($input);
                            $this->uniqueConstraints []= $elem302;
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
        $xfer += $output->writeStructBegin('UniqueConstraintsResponse');
        if ($this->uniqueConstraints !== null) {
            if (!is_array($this->uniqueConstraints)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('uniqueConstraints', TType::LST, 1);
            $output->writeListBegin(TType::STRUCT, count($this->uniqueConstraints));
            foreach ($this->uniqueConstraints as $iter303) {
                $xfer += $iter303->write($output);
            }
            $output->writeListEnd();
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
