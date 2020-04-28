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

class TableStatsResult
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'tableStats',
            'isRequired' => true,
            'type' => TType::LST,
            'etype' => TType::STRUCT,
            'elem' => array(
                'type' => TType::STRUCT,
                'class' => '\metastore\ColumnStatisticsObj',
                ),
        ),
    );

    /**
     * @var \metastore\ColumnStatisticsObj[]
     */
    public $tableStats = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['tableStats'])) {
                $this->tableStats = $vals['tableStats'];
            }
        }
    }

    public function getName()
    {
        return 'TableStatsResult';
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
                        $this->tableStats = array();
                        $_size320 = 0;
                        $_etype323 = 0;
                        $xfer += $input->readListBegin($_etype323, $_size320);
                        for ($_i324 = 0; $_i324 < $_size320; ++$_i324) {
                            $elem325 = null;
                            $elem325 = new \metastore\ColumnStatisticsObj();
                            $xfer += $elem325->read($input);
                            $this->tableStats []= $elem325;
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
        $xfer += $output->writeStructBegin('TableStatsResult');
        if ($this->tableStats !== null) {
            if (!is_array($this->tableStats)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('tableStats', TType::LST, 1);
            $output->writeListBegin(TType::STRUCT, count($this->tableStats));
            foreach ($this->tableStats as $iter326) {
                $xfer += $iter326->write($output);
            }
            $output->writeListEnd();
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
