#
# Autogenerated by Thrift Compiler (0.12.0)
#
# DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
#

require 'thrift'

class PropValueUnion < ::Thrift::Union; end

class IntString; end

class Complex; end

class SetIntString; end

class PropValueUnion < ::Thrift::Union
  include ::Thrift::Struct_Union
  class << self
    def intValue(val)
      PropValueUnion.new(:intValue, val)
    end

    def longValue(val)
      PropValueUnion.new(:longValue, val)
    end

    def stringValue(val)
      PropValueUnion.new(:stringValue, val)
    end

    def doubleValue(val)
      PropValueUnion.new(:doubleValue, val)
    end

    def flag(val)
      PropValueUnion.new(:flag, val)
    end

    def lString(val)
      PropValueUnion.new(:lString, val)
    end

    def unionMStringString(val)
      PropValueUnion.new(:unionMStringString, val)
    end
  end

  INTVALUE = 1
  LONGVALUE = 2
  STRINGVALUE = 3
  DOUBLEVALUE = 4
  FLAG = 5
  LSTRING = 6
  UNIONMSTRINGSTRING = 7

  FIELDS = {
    INTVALUE => {:type => ::Thrift::Types::I32, :name => 'intValue', :optional => true},
    LONGVALUE => {:type => ::Thrift::Types::I64, :name => 'longValue', :optional => true},
    STRINGVALUE => {:type => ::Thrift::Types::STRING, :name => 'stringValue', :optional => true},
    DOUBLEVALUE => {:type => ::Thrift::Types::DOUBLE, :name => 'doubleValue', :optional => true},
    FLAG => {:type => ::Thrift::Types::BOOL, :name => 'flag', :optional => true},
    LSTRING => {:type => ::Thrift::Types::LIST, :name => 'lString', :element => {:type => ::Thrift::Types::STRING}, :optional => true},
    UNIONMSTRINGSTRING => {:type => ::Thrift::Types::MAP, :name => 'unionMStringString', :key => {:type => ::Thrift::Types::STRING}, :value => {:type => ::Thrift::Types::STRING}, :optional => true}
  }

  def struct_fields; FIELDS; end

  def validate
    raise(StandardError, 'Union fields are not set.') if get_set_field.nil? || get_value.nil?
  end

  ::Thrift::Union.generate_accessors self
end

class IntString
  include ::Thrift::Struct, ::Thrift::Struct_Union
  MYINT = 1
  MYSTRING = 2
  UNDERSCORE_INT = 3

  FIELDS = {
    MYINT => {:type => ::Thrift::Types::I32, :name => 'myint'},
    MYSTRING => {:type => ::Thrift::Types::STRING, :name => 'myString'},
    UNDERSCORE_INT => {:type => ::Thrift::Types::I32, :name => 'underscore_int'}
  }

  def struct_fields; FIELDS; end

  def validate
  end

  ::Thrift::Struct.generate_accessors self
end

class Complex
  include ::Thrift::Struct, ::Thrift::Struct_Union
  AINT = 1
  ASTRING = 2
  LINT = 3
  LSTRING = 4
  LINTSTRING = 5
  MSTRINGSTRING = 6
  ATTRIBUTES = 7
  UNIONFIELD1 = 8
  UNIONFIELD2 = 9
  UNIONFIELD3 = 10

  FIELDS = {
    AINT => {:type => ::Thrift::Types::I32, :name => 'aint'},
    ASTRING => {:type => ::Thrift::Types::STRING, :name => 'aString'},
    LINT => {:type => ::Thrift::Types::LIST, :name => 'lint', :element => {:type => ::Thrift::Types::I32}},
    LSTRING => {:type => ::Thrift::Types::LIST, :name => 'lString', :element => {:type => ::Thrift::Types::STRING}},
    LINTSTRING => {:type => ::Thrift::Types::LIST, :name => 'lintString', :element => {:type => ::Thrift::Types::STRUCT, :class => ::IntString}},
    MSTRINGSTRING => {:type => ::Thrift::Types::MAP, :name => 'mStringString', :key => {:type => ::Thrift::Types::STRING}, :value => {:type => ::Thrift::Types::STRING}},
    ATTRIBUTES => {:type => ::Thrift::Types::MAP, :name => 'attributes', :key => {:type => ::Thrift::Types::STRING}, :value => {:type => ::Thrift::Types::MAP, :key => {:type => ::Thrift::Types::STRING}, :value => {:type => ::Thrift::Types::MAP, :key => {:type => ::Thrift::Types::STRING}, :value => {:type => ::Thrift::Types::STRUCT, :class => ::PropValueUnion}}}},
    UNIONFIELD1 => {:type => ::Thrift::Types::STRUCT, :name => 'unionField1', :class => ::PropValueUnion},
    UNIONFIELD2 => {:type => ::Thrift::Types::STRUCT, :name => 'unionField2', :class => ::PropValueUnion},
    UNIONFIELD3 => {:type => ::Thrift::Types::STRUCT, :name => 'unionField3', :class => ::PropValueUnion}
  }

  def struct_fields; FIELDS; end

  def validate
  end

  ::Thrift::Struct.generate_accessors self
end

class SetIntString
  include ::Thrift::Struct, ::Thrift::Struct_Union
  SINTSTRING = 1
  ASTRING = 2

  FIELDS = {
    SINTSTRING => {:type => ::Thrift::Types::SET, :name => 'sIntString', :element => {:type => ::Thrift::Types::STRUCT, :class => ::IntString}},
    ASTRING => {:type => ::Thrift::Types::STRING, :name => 'aString'}
  }

  def struct_fields; FIELDS; end

  def validate
  end

  ::Thrift::Struct.generate_accessors self
end

