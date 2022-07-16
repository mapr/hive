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

class GetTablesResult
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'tables',
            'isRequired' => true,
            'type' => TType::LST,
            'etype' => TType::STRUCT,
            'elem' => array(
                'type' => TType::STRUCT,
                'class' => '\metastore\Table',
                ),
        ),
    );

    /**
     * @var \metastore\Table[]
     */
    public $tables = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['tables'])) {
                $this->tables = $vals['tables'];
            }
        }
    }

    public function getName()
    {
        return 'GetTablesResult';
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
                        $this->tables = array();
                        $_size735 = 0;
                        $_etype738 = 0;
                        $xfer += $input->readListBegin($_etype738, $_size735);
                        for ($_i739 = 0; $_i739 < $_size735; ++$_i739) {
                            $elem740 = null;
                            $elem740 = new \metastore\Table();
                            $xfer += $elem740->read($input);
                            $this->tables []= $elem740;
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
        $xfer += $output->writeStructBegin('GetTablesResult');
        if ($this->tables !== null) {
            if (!is_array($this->tables)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('tables', TType::LST, 1);
            $output->writeListBegin(TType::STRUCT, count($this->tables));
            foreach ($this->tables as $iter741) {
                $xfer += $iter741->write($output);
            }
            $output->writeListEnd();
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
