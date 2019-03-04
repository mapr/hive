#
# Autogenerated by Thrift Compiler (0.12.0)
#
# DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
#
#  options string: py
#

from thrift.Thrift import TType, TMessageType, TFrozenDict, TException, TApplicationException
from thrift.protocol.TProtocol import TProtocolException
from thrift.TRecursive import fix_spec

import sys

from thrift.transport import TTransport
all_structs = []


class MyEnum(object):
    LLAMA = 1
    ALPACA = 2

    _VALUES_TO_NAMES = {
        1: "LLAMA",
        2: "ALPACA",
    }

    _NAMES_TO_VALUES = {
        "LLAMA": 1,
        "ALPACA": 2,
    }


class MiniStruct(object):
    """
    Attributes:
     - my_string
     - my_enum

    """


    def __init__(self, my_string=None, my_enum=None,):
        self.my_string = my_string
        self.my_enum = my_enum

    def read(self, iprot):
        if iprot._fast_decode is not None and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None:
            iprot._fast_decode(self, iprot, [self.__class__, self.thrift_spec])
            return
        iprot.readStructBegin()
        while True:
            (fname, ftype, fid) = iprot.readFieldBegin()
            if ftype == TType.STOP:
                break
            if fid == 1:
                if ftype == TType.STRING:
                    self.my_string = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            elif fid == 2:
                if ftype == TType.I32:
                    self.my_enum = iprot.readI32()
                else:
                    iprot.skip(ftype)
            else:
                iprot.skip(ftype)
            iprot.readFieldEnd()
        iprot.readStructEnd()

    def write(self, oprot):
        if oprot._fast_encode is not None and self.thrift_spec is not None:
            oprot.trans.write(oprot._fast_encode(self, [self.__class__, self.thrift_spec]))
            return
        oprot.writeStructBegin('MiniStruct')
        if self.my_string is not None:
            oprot.writeFieldBegin('my_string', TType.STRING, 1)
            oprot.writeString(self.my_string.encode('utf-8') if sys.version_info[0] == 2 else self.my_string)
            oprot.writeFieldEnd()
        if self.my_enum is not None:
            oprot.writeFieldBegin('my_enum', TType.I32, 2)
            oprot.writeI32(self.my_enum)
            oprot.writeFieldEnd()
        oprot.writeFieldStop()
        oprot.writeStructEnd()

    def validate(self):
        return

    def __repr__(self):
        L = ['%s=%r' % (key, value)
             for key, value in self.__dict__.items()]
        return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

    def __eq__(self, other):
        return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

    def __ne__(self, other):
        return not (self == other)


class MegaStruct(object):
    """
    Attributes:
     - my_bool
     - my_byte
     - my_16bit_int
     - my_32bit_int
     - my_64bit_int
     - my_double
     - my_string
     - my_binary
     - my_string_string_map
     - my_string_enum_map
     - my_enum_string_map
     - my_enum_struct_map
     - my_enum_stringlist_map
     - my_enum_structlist_map
     - my_stringlist
     - my_structlist
     - my_enumlist
     - my_stringset
     - my_enumset
     - my_structset

    """


    def __init__(self, my_bool=None, my_byte=None, my_16bit_int=None, my_32bit_int=None, my_64bit_int=None, my_double=None, my_string=None, my_binary=None, my_string_string_map=None, my_string_enum_map=None, my_enum_string_map=None, my_enum_struct_map=None, my_enum_stringlist_map=None, my_enum_structlist_map=None, my_stringlist=None, my_structlist=None, my_enumlist=None, my_stringset=None, my_enumset=None, my_structset=None,):
        self.my_bool = my_bool
        self.my_byte = my_byte
        self.my_16bit_int = my_16bit_int
        self.my_32bit_int = my_32bit_int
        self.my_64bit_int = my_64bit_int
        self.my_double = my_double
        self.my_string = my_string
        self.my_binary = my_binary
        self.my_string_string_map = my_string_string_map
        self.my_string_enum_map = my_string_enum_map
        self.my_enum_string_map = my_enum_string_map
        self.my_enum_struct_map = my_enum_struct_map
        self.my_enum_stringlist_map = my_enum_stringlist_map
        self.my_enum_structlist_map = my_enum_structlist_map
        self.my_stringlist = my_stringlist
        self.my_structlist = my_structlist
        self.my_enumlist = my_enumlist
        self.my_stringset = my_stringset
        self.my_enumset = my_enumset
        self.my_structset = my_structset

    def read(self, iprot):
        if iprot._fast_decode is not None and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None:
            iprot._fast_decode(self, iprot, [self.__class__, self.thrift_spec])
            return
        iprot.readStructBegin()
        while True:
            (fname, ftype, fid) = iprot.readFieldBegin()
            if ftype == TType.STOP:
                break
            if fid == 1:
                if ftype == TType.BOOL:
                    self.my_bool = iprot.readBool()
                else:
                    iprot.skip(ftype)
            elif fid == 2:
                if ftype == TType.BYTE:
                    self.my_byte = iprot.readByte()
                else:
                    iprot.skip(ftype)
            elif fid == 3:
                if ftype == TType.I16:
                    self.my_16bit_int = iprot.readI16()
                else:
                    iprot.skip(ftype)
            elif fid == 4:
                if ftype == TType.I32:
                    self.my_32bit_int = iprot.readI32()
                else:
                    iprot.skip(ftype)
            elif fid == 5:
                if ftype == TType.I64:
                    self.my_64bit_int = iprot.readI64()
                else:
                    iprot.skip(ftype)
            elif fid == 6:
                if ftype == TType.DOUBLE:
                    self.my_double = iprot.readDouble()
                else:
                    iprot.skip(ftype)
            elif fid == 7:
                if ftype == TType.STRING:
                    self.my_string = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                else:
                    iprot.skip(ftype)
            elif fid == 8:
                if ftype == TType.STRING:
                    self.my_binary = iprot.readBinary()
                else:
                    iprot.skip(ftype)
            elif fid == 9:
                if ftype == TType.MAP:
                    self.my_string_string_map = {}
                    (_ktype1, _vtype2, _size0) = iprot.readMapBegin()
                    for _i4 in range(_size0):
                        _key5 = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                        _val6 = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                        self.my_string_string_map[_key5] = _val6
                    iprot.readMapEnd()
                else:
                    iprot.skip(ftype)
            elif fid == 10:
                if ftype == TType.MAP:
                    self.my_string_enum_map = {}
                    (_ktype8, _vtype9, _size7) = iprot.readMapBegin()
                    for _i11 in range(_size7):
                        _key12 = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                        _val13 = iprot.readI32()
                        self.my_string_enum_map[_key12] = _val13
                    iprot.readMapEnd()
                else:
                    iprot.skip(ftype)
            elif fid == 11:
                if ftype == TType.MAP:
                    self.my_enum_string_map = {}
                    (_ktype15, _vtype16, _size14) = iprot.readMapBegin()
                    for _i18 in range(_size14):
                        _key19 = iprot.readI32()
                        _val20 = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                        self.my_enum_string_map[_key19] = _val20
                    iprot.readMapEnd()
                else:
                    iprot.skip(ftype)
            elif fid == 12:
                if ftype == TType.MAP:
                    self.my_enum_struct_map = {}
                    (_ktype22, _vtype23, _size21) = iprot.readMapBegin()
                    for _i25 in range(_size21):
                        _key26 = iprot.readI32()
                        _val27 = MiniStruct()
                        _val27.read(iprot)
                        self.my_enum_struct_map[_key26] = _val27
                    iprot.readMapEnd()
                else:
                    iprot.skip(ftype)
            elif fid == 13:
                if ftype == TType.MAP:
                    self.my_enum_stringlist_map = {}
                    (_ktype29, _vtype30, _size28) = iprot.readMapBegin()
                    for _i32 in range(_size28):
                        _key33 = iprot.readI32()
                        _val34 = []
                        (_etype38, _size35) = iprot.readListBegin()
                        for _i39 in range(_size35):
                            _elem40 = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                            _val34.append(_elem40)
                        iprot.readListEnd()
                        self.my_enum_stringlist_map[_key33] = _val34
                    iprot.readMapEnd()
                else:
                    iprot.skip(ftype)
            elif fid == 14:
                if ftype == TType.MAP:
                    self.my_enum_structlist_map = {}
                    (_ktype42, _vtype43, _size41) = iprot.readMapBegin()
                    for _i45 in range(_size41):
                        _key46 = iprot.readI32()
                        _val47 = []
                        (_etype51, _size48) = iprot.readListBegin()
                        for _i52 in range(_size48):
                            _elem53 = MiniStruct()
                            _elem53.read(iprot)
                            _val47.append(_elem53)
                        iprot.readListEnd()
                        self.my_enum_structlist_map[_key46] = _val47
                    iprot.readMapEnd()
                else:
                    iprot.skip(ftype)
            elif fid == 15:
                if ftype == TType.LIST:
                    self.my_stringlist = []
                    (_etype57, _size54) = iprot.readListBegin()
                    for _i58 in range(_size54):
                        _elem59 = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                        self.my_stringlist.append(_elem59)
                    iprot.readListEnd()
                else:
                    iprot.skip(ftype)
            elif fid == 16:
                if ftype == TType.LIST:
                    self.my_structlist = []
                    (_etype63, _size60) = iprot.readListBegin()
                    for _i64 in range(_size60):
                        _elem65 = MiniStruct()
                        _elem65.read(iprot)
                        self.my_structlist.append(_elem65)
                    iprot.readListEnd()
                else:
                    iprot.skip(ftype)
            elif fid == 17:
                if ftype == TType.LIST:
                    self.my_enumlist = []
                    (_etype69, _size66) = iprot.readListBegin()
                    for _i70 in range(_size66):
                        _elem71 = iprot.readI32()
                        self.my_enumlist.append(_elem71)
                    iprot.readListEnd()
                else:
                    iprot.skip(ftype)
            elif fid == 18:
                if ftype == TType.SET:
                    self.my_stringset = set()
                    (_etype75, _size72) = iprot.readSetBegin()
                    for _i76 in range(_size72):
                        _elem77 = iprot.readString().decode('utf-8') if sys.version_info[0] == 2 else iprot.readString()
                        self.my_stringset.add(_elem77)
                    iprot.readSetEnd()
                else:
                    iprot.skip(ftype)
            elif fid == 19:
                if ftype == TType.SET:
                    self.my_enumset = set()
                    (_etype81, _size78) = iprot.readSetBegin()
                    for _i82 in range(_size78):
                        _elem83 = iprot.readI32()
                        self.my_enumset.add(_elem83)
                    iprot.readSetEnd()
                else:
                    iprot.skip(ftype)
            elif fid == 20:
                if ftype == TType.SET:
                    self.my_structset = set()
                    (_etype87, _size84) = iprot.readSetBegin()
                    for _i88 in range(_size84):
                        _elem89 = MiniStruct()
                        _elem89.read(iprot)
                        self.my_structset.add(_elem89)
                    iprot.readSetEnd()
                else:
                    iprot.skip(ftype)
            else:
                iprot.skip(ftype)
            iprot.readFieldEnd()
        iprot.readStructEnd()

    def write(self, oprot):
        if oprot._fast_encode is not None and self.thrift_spec is not None:
            oprot.trans.write(oprot._fast_encode(self, [self.__class__, self.thrift_spec]))
            return
        oprot.writeStructBegin('MegaStruct')
        if self.my_bool is not None:
            oprot.writeFieldBegin('my_bool', TType.BOOL, 1)
            oprot.writeBool(self.my_bool)
            oprot.writeFieldEnd()
        if self.my_byte is not None:
            oprot.writeFieldBegin('my_byte', TType.BYTE, 2)
            oprot.writeByte(self.my_byte)
            oprot.writeFieldEnd()
        if self.my_16bit_int is not None:
            oprot.writeFieldBegin('my_16bit_int', TType.I16, 3)
            oprot.writeI16(self.my_16bit_int)
            oprot.writeFieldEnd()
        if self.my_32bit_int is not None:
            oprot.writeFieldBegin('my_32bit_int', TType.I32, 4)
            oprot.writeI32(self.my_32bit_int)
            oprot.writeFieldEnd()
        if self.my_64bit_int is not None:
            oprot.writeFieldBegin('my_64bit_int', TType.I64, 5)
            oprot.writeI64(self.my_64bit_int)
            oprot.writeFieldEnd()
        if self.my_double is not None:
            oprot.writeFieldBegin('my_double', TType.DOUBLE, 6)
            oprot.writeDouble(self.my_double)
            oprot.writeFieldEnd()
        if self.my_string is not None:
            oprot.writeFieldBegin('my_string', TType.STRING, 7)
            oprot.writeString(self.my_string.encode('utf-8') if sys.version_info[0] == 2 else self.my_string)
            oprot.writeFieldEnd()
        if self.my_binary is not None:
            oprot.writeFieldBegin('my_binary', TType.STRING, 8)
            oprot.writeBinary(self.my_binary)
            oprot.writeFieldEnd()
        if self.my_string_string_map is not None:
            oprot.writeFieldBegin('my_string_string_map', TType.MAP, 9)
            oprot.writeMapBegin(TType.STRING, TType.STRING, len(self.my_string_string_map))
            for kiter90, viter91 in self.my_string_string_map.items():
                oprot.writeString(kiter90.encode('utf-8') if sys.version_info[0] == 2 else kiter90)
                oprot.writeString(viter91.encode('utf-8') if sys.version_info[0] == 2 else viter91)
            oprot.writeMapEnd()
            oprot.writeFieldEnd()
        if self.my_string_enum_map is not None:
            oprot.writeFieldBegin('my_string_enum_map', TType.MAP, 10)
            oprot.writeMapBegin(TType.STRING, TType.I32, len(self.my_string_enum_map))
            for kiter92, viter93 in self.my_string_enum_map.items():
                oprot.writeString(kiter92.encode('utf-8') if sys.version_info[0] == 2 else kiter92)
                oprot.writeI32(viter93)
            oprot.writeMapEnd()
            oprot.writeFieldEnd()
        if self.my_enum_string_map is not None:
            oprot.writeFieldBegin('my_enum_string_map', TType.MAP, 11)
            oprot.writeMapBegin(TType.I32, TType.STRING, len(self.my_enum_string_map))
            for kiter94, viter95 in self.my_enum_string_map.items():
                oprot.writeI32(kiter94)
                oprot.writeString(viter95.encode('utf-8') if sys.version_info[0] == 2 else viter95)
            oprot.writeMapEnd()
            oprot.writeFieldEnd()
        if self.my_enum_struct_map is not None:
            oprot.writeFieldBegin('my_enum_struct_map', TType.MAP, 12)
            oprot.writeMapBegin(TType.I32, TType.STRUCT, len(self.my_enum_struct_map))
            for kiter96, viter97 in self.my_enum_struct_map.items():
                oprot.writeI32(kiter96)
                viter97.write(oprot)
            oprot.writeMapEnd()
            oprot.writeFieldEnd()
        if self.my_enum_stringlist_map is not None:
            oprot.writeFieldBegin('my_enum_stringlist_map', TType.MAP, 13)
            oprot.writeMapBegin(TType.I32, TType.LIST, len(self.my_enum_stringlist_map))
            for kiter98, viter99 in self.my_enum_stringlist_map.items():
                oprot.writeI32(kiter98)
                oprot.writeListBegin(TType.STRING, len(viter99))
                for iter100 in viter99:
                    oprot.writeString(iter100.encode('utf-8') if sys.version_info[0] == 2 else iter100)
                oprot.writeListEnd()
            oprot.writeMapEnd()
            oprot.writeFieldEnd()
        if self.my_enum_structlist_map is not None:
            oprot.writeFieldBegin('my_enum_structlist_map', TType.MAP, 14)
            oprot.writeMapBegin(TType.I32, TType.LIST, len(self.my_enum_structlist_map))
            for kiter101, viter102 in self.my_enum_structlist_map.items():
                oprot.writeI32(kiter101)
                oprot.writeListBegin(TType.STRUCT, len(viter102))
                for iter103 in viter102:
                    iter103.write(oprot)
                oprot.writeListEnd()
            oprot.writeMapEnd()
            oprot.writeFieldEnd()
        if self.my_stringlist is not None:
            oprot.writeFieldBegin('my_stringlist', TType.LIST, 15)
            oprot.writeListBegin(TType.STRING, len(self.my_stringlist))
            for iter104 in self.my_stringlist:
                oprot.writeString(iter104.encode('utf-8') if sys.version_info[0] == 2 else iter104)
            oprot.writeListEnd()
            oprot.writeFieldEnd()
        if self.my_structlist is not None:
            oprot.writeFieldBegin('my_structlist', TType.LIST, 16)
            oprot.writeListBegin(TType.STRUCT, len(self.my_structlist))
            for iter105 in self.my_structlist:
                iter105.write(oprot)
            oprot.writeListEnd()
            oprot.writeFieldEnd()
        if self.my_enumlist is not None:
            oprot.writeFieldBegin('my_enumlist', TType.LIST, 17)
            oprot.writeListBegin(TType.I32, len(self.my_enumlist))
            for iter106 in self.my_enumlist:
                oprot.writeI32(iter106)
            oprot.writeListEnd()
            oprot.writeFieldEnd()
        if self.my_stringset is not None:
            oprot.writeFieldBegin('my_stringset', TType.SET, 18)
            oprot.writeSetBegin(TType.STRING, len(self.my_stringset))
            for iter107 in self.my_stringset:
                oprot.writeString(iter107.encode('utf-8') if sys.version_info[0] == 2 else iter107)
            oprot.writeSetEnd()
            oprot.writeFieldEnd()
        if self.my_enumset is not None:
            oprot.writeFieldBegin('my_enumset', TType.SET, 19)
            oprot.writeSetBegin(TType.I32, len(self.my_enumset))
            for iter108 in self.my_enumset:
                oprot.writeI32(iter108)
            oprot.writeSetEnd()
            oprot.writeFieldEnd()
        if self.my_structset is not None:
            oprot.writeFieldBegin('my_structset', TType.SET, 20)
            oprot.writeSetBegin(TType.STRUCT, len(self.my_structset))
            for iter109 in self.my_structset:
                iter109.write(oprot)
            oprot.writeSetEnd()
            oprot.writeFieldEnd()
        oprot.writeFieldStop()
        oprot.writeStructEnd()

    def validate(self):
        return

    def __repr__(self):
        L = ['%s=%r' % (key, value)
             for key, value in self.__dict__.items()]
        return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

    def __eq__(self, other):
        return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

    def __ne__(self, other):
        return not (self == other)
all_structs.append(MiniStruct)
MiniStruct.thrift_spec = (
    None,  # 0
    (1, TType.STRING, 'my_string', 'UTF8', None, ),  # 1
    (2, TType.I32, 'my_enum', None, None, ),  # 2
)
all_structs.append(MegaStruct)
MegaStruct.thrift_spec = (
    None,  # 0
    (1, TType.BOOL, 'my_bool', None, None, ),  # 1
    (2, TType.BYTE, 'my_byte', None, None, ),  # 2
    (3, TType.I16, 'my_16bit_int', None, None, ),  # 3
    (4, TType.I32, 'my_32bit_int', None, None, ),  # 4
    (5, TType.I64, 'my_64bit_int', None, None, ),  # 5
    (6, TType.DOUBLE, 'my_double', None, None, ),  # 6
    (7, TType.STRING, 'my_string', 'UTF8', None, ),  # 7
    (8, TType.STRING, 'my_binary', 'BINARY', None, ),  # 8
    (9, TType.MAP, 'my_string_string_map', (TType.STRING, 'UTF8', TType.STRING, 'UTF8', False), None, ),  # 9
    (10, TType.MAP, 'my_string_enum_map', (TType.STRING, 'UTF8', TType.I32, None, False), None, ),  # 10
    (11, TType.MAP, 'my_enum_string_map', (TType.I32, None, TType.STRING, 'UTF8', False), None, ),  # 11
    (12, TType.MAP, 'my_enum_struct_map', (TType.I32, None, TType.STRUCT, [MiniStruct, None], False), None, ),  # 12
    (13, TType.MAP, 'my_enum_stringlist_map', (TType.I32, None, TType.LIST, (TType.STRING, 'UTF8', False), False), None, ),  # 13
    (14, TType.MAP, 'my_enum_structlist_map', (TType.I32, None, TType.LIST, (TType.STRUCT, [MiniStruct, None], False), False), None, ),  # 14
    (15, TType.LIST, 'my_stringlist', (TType.STRING, 'UTF8', False), None, ),  # 15
    (16, TType.LIST, 'my_structlist', (TType.STRUCT, [MiniStruct, None], False), None, ),  # 16
    (17, TType.LIST, 'my_enumlist', (TType.I32, None, False), None, ),  # 17
    (18, TType.SET, 'my_stringset', (TType.STRING, 'UTF8', False), None, ),  # 18
    (19, TType.SET, 'my_enumset', (TType.I32, None, False), None, ),  # 19
    (20, TType.SET, 'my_structset', (TType.STRUCT, [MiniStruct, None], False), None, ),  # 20
)
fix_spec(all_structs)
del all_structs
