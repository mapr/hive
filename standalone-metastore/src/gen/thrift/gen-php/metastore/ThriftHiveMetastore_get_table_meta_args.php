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

class ThriftHiveMetastore_get_table_meta_args
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'db_patterns',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        2 => array(
            'var' => 'tbl_patterns',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        3 => array(
            'var' => 'tbl_types',
            'isRequired' => false,
            'type' => TType::LST,
            'etype' => TType::STRING,
            'elem' => array(
                'type' => TType::STRING,
                ),
        ),
    );

    /**
     * @var string
     */
    public $db_patterns = null;
    /**
     * @var string
     */
    public $tbl_patterns = null;
    /**
     * @var string[]
     */
    public $tbl_types = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['db_patterns'])) {
                $this->db_patterns = $vals['db_patterns'];
            }
            if (isset($vals['tbl_patterns'])) {
                $this->tbl_patterns = $vals['tbl_patterns'];
            }
            if (isset($vals['tbl_types'])) {
                $this->tbl_types = $vals['tbl_types'];
            }
        }
    }

    public function getName()
    {
        return 'ThriftHiveMetastore_get_table_meta_args';
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
                        $xfer += $input->readString($this->db_patterns);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 2:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->tbl_patterns);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 3:
                    if ($ftype == TType::LST) {
                        $this->tbl_types = array();
                        $_size996 = 0;
                        $_etype999 = 0;
                        $xfer += $input->readListBegin($_etype999, $_size996);
                        for ($_i1000 = 0; $_i1000 < $_size996; ++$_i1000) {
                            $elem1001 = null;
                            $xfer += $input->readString($elem1001);
                            $this->tbl_types []= $elem1001;
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
        $xfer += $output->writeStructBegin('ThriftHiveMetastore_get_table_meta_args');
        if ($this->db_patterns !== null) {
            $xfer += $output->writeFieldBegin('db_patterns', TType::STRING, 1);
            $xfer += $output->writeString($this->db_patterns);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->tbl_patterns !== null) {
            $xfer += $output->writeFieldBegin('tbl_patterns', TType::STRING, 2);
            $xfer += $output->writeString($this->tbl_patterns);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->tbl_types !== null) {
            if (!is_array($this->tbl_types)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('tbl_types', TType::LST, 3);
            $output->writeListBegin(TType::STRING, count($this->tbl_types));
            foreach ($this->tbl_types as $iter1002) {
                $xfer += $output->writeString($iter1002);
            }
            $output->writeListEnd();
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
