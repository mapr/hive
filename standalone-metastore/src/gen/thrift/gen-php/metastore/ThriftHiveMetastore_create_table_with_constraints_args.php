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

class ThriftHiveMetastore_create_table_with_constraints_args
{
    static public $isValidate = false;

    static public $_TSPEC = array(
        1 => array(
            'var' => 'tbl',
            'isRequired' => false,
            'type' => TType::STRUCT,
            'class' => '\metastore\Table',
        ),
        2 => array(
            'var' => 'primaryKeys',
            'isRequired' => false,
            'type' => TType::LST,
            'etype' => TType::STRUCT,
            'elem' => array(
                'type' => TType::STRUCT,
                'class' => '\metastore\SQLPrimaryKey',
                ),
        ),
        3 => array(
            'var' => 'foreignKeys',
            'isRequired' => false,
            'type' => TType::LST,
            'etype' => TType::STRUCT,
            'elem' => array(
                'type' => TType::STRUCT,
                'class' => '\metastore\SQLForeignKey',
                ),
        ),
        4 => array(
            'var' => 'uniqueConstraints',
            'isRequired' => false,
            'type' => TType::LST,
            'etype' => TType::STRUCT,
            'elem' => array(
                'type' => TType::STRUCT,
                'class' => '\metastore\SQLUniqueConstraint',
                ),
        ),
        5 => array(
            'var' => 'notNullConstraints',
            'isRequired' => false,
            'type' => TType::LST,
            'etype' => TType::STRUCT,
            'elem' => array(
                'type' => TType::STRUCT,
                'class' => '\metastore\SQLNotNullConstraint',
                ),
        ),
        6 => array(
            'var' => 'defaultConstraints',
            'isRequired' => false,
            'type' => TType::LST,
            'etype' => TType::STRUCT,
            'elem' => array(
                'type' => TType::STRUCT,
                'class' => '\metastore\SQLDefaultConstraint',
                ),
        ),
        7 => array(
            'var' => 'checkConstraints',
            'isRequired' => false,
            'type' => TType::LST,
            'etype' => TType::STRUCT,
            'elem' => array(
                'type' => TType::STRUCT,
                'class' => '\metastore\SQLCheckConstraint',
                ),
        ),
    );

    /**
     * @var \metastore\Table
     */
    public $tbl = null;
    /**
     * @var \metastore\SQLPrimaryKey[]
     */
    public $primaryKeys = null;
    /**
     * @var \metastore\SQLForeignKey[]
     */
    public $foreignKeys = null;
    /**
     * @var \metastore\SQLUniqueConstraint[]
     */
    public $uniqueConstraints = null;
    /**
     * @var \metastore\SQLNotNullConstraint[]
     */
    public $notNullConstraints = null;
    /**
     * @var \metastore\SQLDefaultConstraint[]
     */
    public $defaultConstraints = null;
    /**
     * @var \metastore\SQLCheckConstraint[]
     */
    public $checkConstraints = null;

    public function __construct($vals = null)
    {
        if (is_array($vals)) {
            if (isset($vals['tbl'])) {
                $this->tbl = $vals['tbl'];
            }
            if (isset($vals['primaryKeys'])) {
                $this->primaryKeys = $vals['primaryKeys'];
            }
            if (isset($vals['foreignKeys'])) {
                $this->foreignKeys = $vals['foreignKeys'];
            }
            if (isset($vals['uniqueConstraints'])) {
                $this->uniqueConstraints = $vals['uniqueConstraints'];
            }
            if (isset($vals['notNullConstraints'])) {
                $this->notNullConstraints = $vals['notNullConstraints'];
            }
            if (isset($vals['defaultConstraints'])) {
                $this->defaultConstraints = $vals['defaultConstraints'];
            }
            if (isset($vals['checkConstraints'])) {
                $this->checkConstraints = $vals['checkConstraints'];
            }
        }
    }

    public function getName()
    {
        return 'ThriftHiveMetastore_create_table_with_constraints_args';
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
                        $this->tbl = new \metastore\Table();
                        $xfer += $this->tbl->read($input);
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 2:
                    if ($ftype == TType::LST) {
                        $this->primaryKeys = array();
                        $_size926 = 0;
                        $_etype929 = 0;
                        $xfer += $input->readListBegin($_etype929, $_size926);
                        for ($_i930 = 0; $_i930 < $_size926; ++$_i930) {
                            $elem931 = null;
                            $elem931 = new \metastore\SQLPrimaryKey();
                            $xfer += $elem931->read($input);
                            $this->primaryKeys []= $elem931;
                        }
                        $xfer += $input->readListEnd();
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 3:
                    if ($ftype == TType::LST) {
                        $this->foreignKeys = array();
                        $_size932 = 0;
                        $_etype935 = 0;
                        $xfer += $input->readListBegin($_etype935, $_size932);
                        for ($_i936 = 0; $_i936 < $_size932; ++$_i936) {
                            $elem937 = null;
                            $elem937 = new \metastore\SQLForeignKey();
                            $xfer += $elem937->read($input);
                            $this->foreignKeys []= $elem937;
                        }
                        $xfer += $input->readListEnd();
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 4:
                    if ($ftype == TType::LST) {
                        $this->uniqueConstraints = array();
                        $_size938 = 0;
                        $_etype941 = 0;
                        $xfer += $input->readListBegin($_etype941, $_size938);
                        for ($_i942 = 0; $_i942 < $_size938; ++$_i942) {
                            $elem943 = null;
                            $elem943 = new \metastore\SQLUniqueConstraint();
                            $xfer += $elem943->read($input);
                            $this->uniqueConstraints []= $elem943;
                        }
                        $xfer += $input->readListEnd();
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 5:
                    if ($ftype == TType::LST) {
                        $this->notNullConstraints = array();
                        $_size944 = 0;
                        $_etype947 = 0;
                        $xfer += $input->readListBegin($_etype947, $_size944);
                        for ($_i948 = 0; $_i948 < $_size944; ++$_i948) {
                            $elem949 = null;
                            $elem949 = new \metastore\SQLNotNullConstraint();
                            $xfer += $elem949->read($input);
                            $this->notNullConstraints []= $elem949;
                        }
                        $xfer += $input->readListEnd();
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 6:
                    if ($ftype == TType::LST) {
                        $this->defaultConstraints = array();
                        $_size950 = 0;
                        $_etype953 = 0;
                        $xfer += $input->readListBegin($_etype953, $_size950);
                        for ($_i954 = 0; $_i954 < $_size950; ++$_i954) {
                            $elem955 = null;
                            $elem955 = new \metastore\SQLDefaultConstraint();
                            $xfer += $elem955->read($input);
                            $this->defaultConstraints []= $elem955;
                        }
                        $xfer += $input->readListEnd();
                    } else {
                        $xfer += $input->skip($ftype);
                    }
                    break;
                case 7:
                    if ($ftype == TType::LST) {
                        $this->checkConstraints = array();
                        $_size956 = 0;
                        $_etype959 = 0;
                        $xfer += $input->readListBegin($_etype959, $_size956);
                        for ($_i960 = 0; $_i960 < $_size956; ++$_i960) {
                            $elem961 = null;
                            $elem961 = new \metastore\SQLCheckConstraint();
                            $xfer += $elem961->read($input);
                            $this->checkConstraints []= $elem961;
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
        $xfer += $output->writeStructBegin('ThriftHiveMetastore_create_table_with_constraints_args');
        if ($this->tbl !== null) {
            if (!is_object($this->tbl)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('tbl', TType::STRUCT, 1);
            $xfer += $this->tbl->write($output);
            $xfer += $output->writeFieldEnd();
        }
        if ($this->primaryKeys !== null) {
            if (!is_array($this->primaryKeys)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('primaryKeys', TType::LST, 2);
            $output->writeListBegin(TType::STRUCT, count($this->primaryKeys));
            foreach ($this->primaryKeys as $iter962) {
                $xfer += $iter962->write($output);
            }
            $output->writeListEnd();
            $xfer += $output->writeFieldEnd();
        }
        if ($this->foreignKeys !== null) {
            if (!is_array($this->foreignKeys)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('foreignKeys', TType::LST, 3);
            $output->writeListBegin(TType::STRUCT, count($this->foreignKeys));
            foreach ($this->foreignKeys as $iter963) {
                $xfer += $iter963->write($output);
            }
            $output->writeListEnd();
            $xfer += $output->writeFieldEnd();
        }
        if ($this->uniqueConstraints !== null) {
            if (!is_array($this->uniqueConstraints)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('uniqueConstraints', TType::LST, 4);
            $output->writeListBegin(TType::STRUCT, count($this->uniqueConstraints));
            foreach ($this->uniqueConstraints as $iter964) {
                $xfer += $iter964->write($output);
            }
            $output->writeListEnd();
            $xfer += $output->writeFieldEnd();
        }
        if ($this->notNullConstraints !== null) {
            if (!is_array($this->notNullConstraints)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('notNullConstraints', TType::LST, 5);
            $output->writeListBegin(TType::STRUCT, count($this->notNullConstraints));
            foreach ($this->notNullConstraints as $iter965) {
                $xfer += $iter965->write($output);
            }
            $output->writeListEnd();
            $xfer += $output->writeFieldEnd();
        }
        if ($this->defaultConstraints !== null) {
            if (!is_array($this->defaultConstraints)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('defaultConstraints', TType::LST, 6);
            $output->writeListBegin(TType::STRUCT, count($this->defaultConstraints));
            foreach ($this->defaultConstraints as $iter966) {
                $xfer += $iter966->write($output);
            }
            $output->writeListEnd();
            $xfer += $output->writeFieldEnd();
        }
        if ($this->checkConstraints !== null) {
            if (!is_array($this->checkConstraints)) {
                throw new TProtocolException('Bad type in structure.', TProtocolException::INVALID_DATA);
            }
            $xfer += $output->writeFieldBegin('checkConstraints', TType::LST, 7);
            $output->writeListBegin(TType::STRUCT, count($this->checkConstraints));
            foreach ($this->checkConstraints as $iter967) {
                $xfer += $iter967->write($output);
            }
            $output->writeListEnd();
            $xfer += $output->writeFieldEnd();
        }
        $xfer += $output->writeFieldStop();
        $xfer += $output->writeStructEnd();
        return $xfer;
    }
}
