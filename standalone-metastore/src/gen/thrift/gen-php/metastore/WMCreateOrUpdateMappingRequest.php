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

class WMCreateOrUpdateMappingRequest
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'mapping',
            'isRequired' => false,
            'type' => TType::STRUCT,
            'class' => '\metastore\WMMapping',
        ),
        2 => array(
            'var' => 'update',
            'isRequired' => false,
            'type' => TType::BOOL,
        ),
    );

    /**
     * @var \metastore\WMMapping
     */
    public $mapping = null;
    /**
     * @var bool
     */
    public $update = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['mapping'])) {
                $this->mapping = $vals['mapping'];
            }
            if (isset($vals['update'])) {
                $this->update = $vals['update'];
            }
        }
    }

    public function getName()
    {
        return 'WMCreateOrUpdateMappingRequest';
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
                        $this->mapping = new \metastore\WMMapping();
                        $xfer += $this->mapping->read($input);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 2:
                    if ($ftype == TType::BOOL) {
                        $xfer += $input->readBool($this->update);
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
        $xfer += $output->writeStructBegin('WMCreateOrUpdateMappingRequest');
        if ($this->mapping !== null) {
            if (!is_object($this->mapping)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('mapping', TType::STRUCT, 1);
            $xfer += $this->mapping->write($output);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->update !== null) {
            $xfer += $output->writeFieldBegin('update', TType::BOOL, 2);
            $xfer += $output->writeBool($this->update);
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
