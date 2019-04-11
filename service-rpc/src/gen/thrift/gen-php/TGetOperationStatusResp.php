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

class TGetOperationStatusResp
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'status',
            'isRequired' => true,
            'type' => TType::STRUCT,
            'class' => '\TStatus',
        ),
        2 => array(
            'var' => 'operationState',
            'isRequired' => false,
            'type' => TType::I32,
        ),
        3 => array(
            'var' => 'sqlState',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        4 => array(
            'var' => 'errorCode',
            'isRequired' => false,
            'type' => TType::I32,
        ),
        5 => array(
            'var' => 'errorMessage',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        6 => array(
            'var' => 'taskStatus',
            'isRequired' => false,
            'type' => TType::STRING,
        ),
        7 => array(
            'var' => 'operationStarted',
            'isRequired' => false,
            'type' => TType::I64,
        ),
        8 => array(
            'var' => 'operationCompleted',
            'isRequired' => false,
            'type' => TType::I64,
        ),
        9 => array(
            'var' => 'hasResultSet',
            'isRequired' => false,
            'type' => TType::BOOL,
        ),
        10 => array(
            'var' => 'progressUpdateResponse',
            'isRequired' => false,
            'type' => TType::STRUCT,
            'class' => '\TProgressUpdateResp',
        ),
    );

    /**
     * @var \TStatus
     */
    public $status = null;
    /**
     * @var int
     */
    public $operationState = null;
    /**
     * @var string
     */
    public $sqlState = null;
    /**
     * @var int
     */
    public $errorCode = null;
    /**
     * @var string
     */
    public $errorMessage = null;
    /**
     * @var string
     */
    public $taskStatus = null;
    /**
     * @var int
     */
    public $operationStarted = null;
    /**
     * @var int
     */
    public $operationCompleted = null;
    /**
     * @var bool
     */
    public $hasResultSet = null;
    /**
     * @var \TProgressUpdateResp
     */
    public $progressUpdateResponse = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['status'])) {
                $this->status = $vals['status'];
            }
            if (isset($vals['operationState'])) {
                $this->operationState = $vals['operationState'];
            }
            if (isset($vals['sqlState'])) {
                $this->sqlState = $vals['sqlState'];
            }
            if (isset($vals['errorCode'])) {
                $this->errorCode = $vals['errorCode'];
            }
            if (isset($vals['errorMessage'])) {
                $this->errorMessage = $vals['errorMessage'];
            }
            if (isset($vals['taskStatus'])) {
                $this->taskStatus = $vals['taskStatus'];
            }
            if (isset($vals['operationStarted'])) {
                $this->operationStarted = $vals['operationStarted'];
            }
            if (isset($vals['operationCompleted'])) {
                $this->operationCompleted = $vals['operationCompleted'];
            }
            if (isset($vals['hasResultSet'])) {
                $this->hasResultSet = $vals['hasResultSet'];
            }
            if (isset($vals['progressUpdateResponse'])) {
                $this->progressUpdateResponse = $vals['progressUpdateResponse'];
            }
        }
    }

    public function getName()
    {
        return 'TGetOperationStatusResp';
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
                    if ($ftype == TType::STRUCT) {
                        $this->status = new \TStatus();
                        $xfer += $this->status->read($input);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 2:
                    if ($ftype == TType::I32) {
                        $xfer += $input->readI32($this->operationState);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 3:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->sqlState);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 4:
                    if ($ftype == TType::I32) {
                        $xfer += $input->readI32($this->errorCode);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 5:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->errorMessage);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 6:
                    if ($ftype == TType::STRING) {
                        $xfer += $input->readString($this->taskStatus);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 7:
                    if ($ftype == TType::I64) {
                        $xfer += $input->readI64($this->operationStarted);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 8:
                    if ($ftype == TType::I64) {
                        $xfer += $input->readI64($this->operationCompleted);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 9:
                    if ($ftype == TType::BOOL) {
                        $xfer += $input->readBool($this->hasResultSet);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 10:
                    if ($ftype == TType::STRUCT) {
                        $this->progressUpdateResponse = new \TProgressUpdateResp();
                        $xfer += $this->progressUpdateResponse->read($input);
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
        $xfer += $output->writeStructBegin('TGetOperationStatusResp');
        if ($this->status !== null) {
            if (!is_object($this->status)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('status', TType::STRUCT, 1);
            $xfer += $this->status->write($output);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->operationState !== null) {
            $xfer += $output->writeFieldBegin('operationState', TType::I32, 2);
            $xfer += $output->writeI32($this->operationState);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->sqlState !== null) {
            $xfer += $output->writeFieldBegin('sqlState', TType::STRING, 3);
            $xfer += $output->writeString($this->sqlState);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->errorCode !== null) {
            $xfer += $output->writeFieldBegin('errorCode', TType::I32, 4);
            $xfer += $output->writeI32($this->errorCode);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->errorMessage !== null) {
            $xfer += $output->writeFieldBegin('errorMessage', TType::STRING, 5);
            $xfer += $output->writeString($this->errorMessage);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->taskStatus !== null) {
            $xfer += $output->writeFieldBegin('taskStatus', TType::STRING, 6);
            $xfer += $output->writeString($this->taskStatus);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->operationStarted !== null) {
            $xfer += $output->writeFieldBegin('operationStarted', TType::I64, 7);
            $xfer += $output->writeI64($this->operationStarted);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->operationCompleted !== null) {
            $xfer += $output->writeFieldBegin('operationCompleted', TType::I64, 8);
            $xfer += $output->writeI64($this->operationCompleted);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->hasResultSet !== null) {
            $xfer += $output->writeFieldBegin('hasResultSet', TType::BOOL, 9);
            $xfer += $output->writeBool($this->hasResultSet);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->progressUpdateResponse !== null) {
            if (!is_object($this->progressUpdateResponse)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('progressUpdateResponse', TType::STRUCT, 10);
            $xfer += $this->progressUpdateResponse->write($output);
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
