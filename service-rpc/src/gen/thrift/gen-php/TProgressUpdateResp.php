<?php
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

class TProgressUpdateResp
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'headerNames',
            'isRequired' => true,
            'type' => TType::LST,
            'etype' => TType::STRING,
            'elem' => array(
                'type' => TType::STRING,
                ),
        ),
        2 => array(
            'var' => 'rows',
            'isRequired' => true,
            'type' => TType::LST,
            'etype' => TType::LST,
            'elem' => array(
                'type' => TType::LST,
                'etype' => TType::STRING,
                'elem' => array(
                    'type' => TType::STRING,
                    ),
                ),
        ),
        3 => array(
            'var' => 'progressedPercentage',
            'isRequired' => true,
            'type' => TType::DOUBLE,
        ),
        4 => array(
            'var' => 'status',
            'isRequired' => true,
            'type' => TType::I32,
        ),
        5 => array(
            'var' => 'footerSummary',
            'isRequired' => true,
            'type' => TType::STRING,
        ),
        6 => array(
            'var' => 'startTime',
            'isRequired' => true,
            'type' => TType::I64,
        ),
    );

    /**
     * @var string[]
     */
    public $headerNames = null;
    /**
     * @var (string[])[]
     */
    public $rows = null;
    /**
     * @var double
     */
    public $progressedPercentage = null;
    /**
     * @var int
     */
    public $status = null;
    /**
     * @var string
     */
    public $footerSummary = null;
    /**
     * @var int
     */
    public $startTime = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['headerNames'])) {
                $this->headerNames = $vals['headerNames'];
            }
            if (isset($vals['rows'])) {
                $this->rows = $vals['rows'];
            }
            if (isset($vals['progressedPercentage'])) {
                $this->progressedPercentage = $vals['progressedPercentage'];
            }
            if (isset($vals['status'])) {
                $this->status = $vals['status'];
            }
            if (isset($vals['footerSummary'])) {
                $this->footerSummary = $vals['footerSummary'];
            }
            if (isset($vals['startTime'])) {
                $this->startTime = $vals['startTime'];
            }
        }
    }

    public function getName()
    {
        return 'TProgressUpdateResp';
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
                        $this->headerNames = array();
                        $_size168 = 0;
                        $_etype171 = 0;
                        $xfer += $input->readListBegin($_etype171, $_size168);
                        for ($_i172 = 0; $_i172 < $_size168; ++$_i172) {
                            $elem173 = null;
                            $xfer += $input->readString($elem173);
                            $this->headerNames []= $elem173;
                        }
                        $xfer += $input->readListEnd();
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 2:
                    if ($ftype == TType::LST) {
                        $this->rows = array();
                        $_size174 = 0;
                        $_etype177 = 0;
                        $xfer += $input->readListBegin($_etype177, $_size174);
                        for ($_i178 = 0; $_i178 < $_size174; ++$_i178) {
                            $elem179 = null;
                            $elem179 = array();
                            $_size180 = 0;
                            $_etype183 = 0;
                            $xfer += $input->readListBegin($_etype183, $_size180);
                            for ($_i184 = 0; $_i184 < $_size180; ++$_i184) {
                                $elem185 = null;
                                $xfer += $input->readString($elem185);
                                $elem179 []= $elem185;
                            }
                            $xfer += $input->readListEnd();
                            $this->rows []= $elem179;
                        }
                        $xfer += $input->readListEnd();
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 3:
                    if ($ftype == TType::DOUBLE) {
                        $xfer += $input->readDouble($this->progressedPercentage);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 4:
                    if ($ftype == TType::I32) {
                        $xfer += $input->readI32($this->status);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 5:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->footerSummary);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 6:
                    if ($ftype == TType::I64) {
                        $xfer += $input->readI64($this->startTime);
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
        $xfer += $output->writeStructBegin('TProgressUpdateResp');
        if ($this->headerNames !== null) {
            if (!is_array($this->headerNames)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('headerNames', TType::LST, 1);
            $output->writeListBegin(TType::STRING, count($this->headerNames));
            foreach ($this->headerNames as $iter186) {
                $xfer += $output->writeString($iter186);
            }
            $output->writeListEnd();
            $xfer += $output->writeFieldEnd();
        }
        if ($this->rows !== null) {
            if (!is_array($this->rows)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('rows', TType::LST, 2);
            $output->writeListBegin(TType::LST, count($this->rows));
            foreach ($this->rows as $iter187) {
                $output->writeListBegin(TType::STRING, count($iter187));
                foreach ($iter187 as $iter188) {
                    $xfer += $output->writeString($iter188);
                }
                $output->writeListEnd();
            }
            $output->writeListEnd();
            $xfer += $output->writeFieldEnd();
        }
        if ($this->progressedPercentage !== null) {
            $xfer += $output->writeFieldBegin('progressedPercentage', TType::DOUBLE, 3);
            $xfer += $output->writeDouble($this->progressedPercentage);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->status !== null) {
            $xfer += $output->writeFieldBegin('status', TType::I32, 4);
            $xfer += $output->writeI32($this->status);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->footerSummary !== null) {
            $xfer += $output->writeFieldBegin('footerSummary', TType::STRING, 5);
            $xfer += $output->writeString($this->footerSummary);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->startTime !== null) {
            $xfer += $output->writeFieldBegin('startTime', TType::I64, 6);
            $xfer += $output->writeI64($this->startTime);
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
