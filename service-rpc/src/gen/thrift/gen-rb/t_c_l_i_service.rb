#
# Autogenerated by Thrift Compiler (0.16.0)
#
# DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
#

require 'thrift'
require 't_c_l_i_service_types'

module TCLIService
  class Client
    include ::Thrift::Client

    def OpenSession(req)
      send_OpenSession(req)
      return recv_OpenSession()
    end

    def send_OpenSession(req)
      send_message('OpenSession', OpenSession_args, :req => req)
    end

    def recv_OpenSession()
      result = receive_message(OpenSession_result)
      return result.success unless result.success.nil?
      raise ::Thrift::ApplicationException.new(::Thrift::ApplicationException::MISSING_RESULT, 'OpenSession failed: unknown result')
    end

    def CloseSession(req)
      send_CloseSession(req)
      return recv_CloseSession()
    end

    def send_CloseSession(req)
      send_message('CloseSession', CloseSession_args, :req => req)
    end

    def recv_CloseSession()
      result = receive_message(CloseSession_result)
      return result.success unless result.success.nil?
      raise ::Thrift::ApplicationException.new(::Thrift::ApplicationException::MISSING_RESULT, 'CloseSession failed: unknown result')
    end

    def GetInfo(req)
      send_GetInfo(req)
      return recv_GetInfo()
    end

    def send_GetInfo(req)
      send_message('GetInfo', GetInfo_args, :req => req)
    end

    def recv_GetInfo()
      result = receive_message(GetInfo_result)
      return result.success unless result.success.nil?
      raise ::Thrift::ApplicationException.new(::Thrift::ApplicationException::MISSING_RESULT, 'GetInfo failed: unknown result')
    end

    def ExecuteStatement(req)
      send_ExecuteStatement(req)
      return recv_ExecuteStatement()
    end

    def send_ExecuteStatement(req)
      send_message('ExecuteStatement', ExecuteStatement_args, :req => req)
    end

    def recv_ExecuteStatement()
      result = receive_message(ExecuteStatement_result)
      return result.success unless result.success.nil?
      raise ::Thrift::ApplicationException.new(::Thrift::ApplicationException::MISSING_RESULT, 'ExecuteStatement failed: unknown result')
    end

    def GetTypeInfo(req)
      send_GetTypeInfo(req)
      return recv_GetTypeInfo()
    end

    def send_GetTypeInfo(req)
      send_message('GetTypeInfo', GetTypeInfo_args, :req => req)
    end

    def recv_GetTypeInfo()
      result = receive_message(GetTypeInfo_result)
      return result.success unless result.success.nil?
      raise ::Thrift::ApplicationException.new(::Thrift::ApplicationException::MISSING_RESULT, 'GetTypeInfo failed: unknown result')
    end

    def GetCatalogs(req)
      send_GetCatalogs(req)
      return recv_GetCatalogs()
    end

    def send_GetCatalogs(req)
      send_message('GetCatalogs', GetCatalogs_args, :req => req)
    end

    def recv_GetCatalogs()
      result = receive_message(GetCatalogs_result)
      return result.success unless result.success.nil?
      raise ::Thrift::ApplicationException.new(::Thrift::ApplicationException::MISSING_RESULT, 'GetCatalogs failed: unknown result')
    end

    def GetSchemas(req)
      send_GetSchemas(req)
      return recv_GetSchemas()
    end

    def send_GetSchemas(req)
      send_message('GetSchemas', GetSchemas_args, :req => req)
    end

    def recv_GetSchemas()
      result = receive_message(GetSchemas_result)
      return result.success unless result.success.nil?
      raise ::Thrift::ApplicationException.new(::Thrift::ApplicationException::MISSING_RESULT, 'GetSchemas failed: unknown result')
    end

    def GetTables(req)
      send_GetTables(req)
      return recv_GetTables()
    end

    def send_GetTables(req)
      send_message('GetTables', GetTables_args, :req => req)
    end

    def recv_GetTables()
      result = receive_message(GetTables_result)
      return result.success unless result.success.nil?
      raise ::Thrift::ApplicationException.new(::Thrift::ApplicationException::MISSING_RESULT, 'GetTables failed: unknown result')
    end

    def GetTableTypes(req)
      send_GetTableTypes(req)
      return recv_GetTableTypes()
    end

    def send_GetTableTypes(req)
      send_message('GetTableTypes', GetTableTypes_args, :req => req)
    end

    def recv_GetTableTypes()
      result = receive_message(GetTableTypes_result)
      return result.success unless result.success.nil?
      raise ::Thrift::ApplicationException.new(::Thrift::ApplicationException::MISSING_RESULT, 'GetTableTypes failed: unknown result')
    end

    def GetColumns(req)
      send_GetColumns(req)
      return recv_GetColumns()
    end

    def send_GetColumns(req)
      send_message('GetColumns', GetColumns_args, :req => req)
    end

    def recv_GetColumns()
      result = receive_message(GetColumns_result)
      return result.success unless result.success.nil?
      raise ::Thrift::ApplicationException.new(::Thrift::ApplicationException::MISSING_RESULT, 'GetColumns failed: unknown result')
    end

    def GetFunctions(req)
      send_GetFunctions(req)
      return recv_GetFunctions()
    end

    def send_GetFunctions(req)
      send_message('GetFunctions', GetFunctions_args, :req => req)
    end

    def recv_GetFunctions()
      result = receive_message(GetFunctions_result)
      return result.success unless result.success.nil?
      raise ::Thrift::ApplicationException.new(::Thrift::ApplicationException::MISSING_RESULT, 'GetFunctions failed: unknown result')
    end

    def GetPrimaryKeys(req)
      send_GetPrimaryKeys(req)
      return recv_GetPrimaryKeys()
    end

    def send_GetPrimaryKeys(req)
      send_message('GetPrimaryKeys', GetPrimaryKeys_args, :req => req)
    end

    def recv_GetPrimaryKeys()
      result = receive_message(GetPrimaryKeys_result)
      return result.success unless result.success.nil?
      raise ::Thrift::ApplicationException.new(::Thrift::ApplicationException::MISSING_RESULT, 'GetPrimaryKeys failed: unknown result')
    end

    def GetCrossReference(req)
      send_GetCrossReference(req)
      return recv_GetCrossReference()
    end

    def send_GetCrossReference(req)
      send_message('GetCrossReference', GetCrossReference_args, :req => req)
    end

    def recv_GetCrossReference()
      result = receive_message(GetCrossReference_result)
      return result.success unless result.success.nil?
      raise ::Thrift::ApplicationException.new(::Thrift::ApplicationException::MISSING_RESULT, 'GetCrossReference failed: unknown result')
    end

    def GetOperationStatus(req)
      send_GetOperationStatus(req)
      return recv_GetOperationStatus()
    end

    def send_GetOperationStatus(req)
      send_message('GetOperationStatus', GetOperationStatus_args, :req => req)
    end

    def recv_GetOperationStatus()
      result = receive_message(GetOperationStatus_result)
      return result.success unless result.success.nil?
      raise ::Thrift::ApplicationException.new(::Thrift::ApplicationException::MISSING_RESULT, 'GetOperationStatus failed: unknown result')
    end

    def CancelOperation(req)
      send_CancelOperation(req)
      return recv_CancelOperation()
    end

    def send_CancelOperation(req)
      send_message('CancelOperation', CancelOperation_args, :req => req)
    end

    def recv_CancelOperation()
      result = receive_message(CancelOperation_result)
      return result.success unless result.success.nil?
      raise ::Thrift::ApplicationException.new(::Thrift::ApplicationException::MISSING_RESULT, 'CancelOperation failed: unknown result')
    end

    def CloseOperation(req)
      send_CloseOperation(req)
      return recv_CloseOperation()
    end

    def send_CloseOperation(req)
      send_message('CloseOperation', CloseOperation_args, :req => req)
    end

    def recv_CloseOperation()
      result = receive_message(CloseOperation_result)
      return result.success unless result.success.nil?
      raise ::Thrift::ApplicationException.new(::Thrift::ApplicationException::MISSING_RESULT, 'CloseOperation failed: unknown result')
    end

    def GetResultSetMetadata(req)
      send_GetResultSetMetadata(req)
      return recv_GetResultSetMetadata()
    end

    def send_GetResultSetMetadata(req)
      send_message('GetResultSetMetadata', GetResultSetMetadata_args, :req => req)
    end

    def recv_GetResultSetMetadata()
      result = receive_message(GetResultSetMetadata_result)
      return result.success unless result.success.nil?
      raise ::Thrift::ApplicationException.new(::Thrift::ApplicationException::MISSING_RESULT, 'GetResultSetMetadata failed: unknown result')
    end

    def FetchResults(req)
      send_FetchResults(req)
      return recv_FetchResults()
    end

    def send_FetchResults(req)
      send_message('FetchResults', FetchResults_args, :req => req)
    end

    def recv_FetchResults()
      result = receive_message(FetchResults_result)
      return result.success unless result.success.nil?
      raise ::Thrift::ApplicationException.new(::Thrift::ApplicationException::MISSING_RESULT, 'FetchResults failed: unknown result')
    end

    def GetDelegationToken(req)
      send_GetDelegationToken(req)
      return recv_GetDelegationToken()
    end

    def send_GetDelegationToken(req)
      send_message('GetDelegationToken', GetDelegationToken_args, :req => req)
    end

    def recv_GetDelegationToken()
      result = receive_message(GetDelegationToken_result)
      return result.success unless result.success.nil?
      raise ::Thrift::ApplicationException.new(::Thrift::ApplicationException::MISSING_RESULT, 'GetDelegationToken failed: unknown result')
    end

    def CancelDelegationToken(req)
      send_CancelDelegationToken(req)
      return recv_CancelDelegationToken()
    end

    def send_CancelDelegationToken(req)
      send_message('CancelDelegationToken', CancelDelegationToken_args, :req => req)
    end

    def recv_CancelDelegationToken()
      result = receive_message(CancelDelegationToken_result)
      return result.success unless result.success.nil?
      raise ::Thrift::ApplicationException.new(::Thrift::ApplicationException::MISSING_RESULT, 'CancelDelegationToken failed: unknown result')
    end

    def RenewDelegationToken(req)
      send_RenewDelegationToken(req)
      return recv_RenewDelegationToken()
    end

    def send_RenewDelegationToken(req)
      send_message('RenewDelegationToken', RenewDelegationToken_args, :req => req)
    end

    def recv_RenewDelegationToken()
      result = receive_message(RenewDelegationToken_result)
      return result.success unless result.success.nil?
      raise ::Thrift::ApplicationException.new(::Thrift::ApplicationException::MISSING_RESULT, 'RenewDelegationToken failed: unknown result')
    end

    def GetQueryId(req)
      send_GetQueryId(req)
      return recv_GetQueryId()
    end

    def send_GetQueryId(req)
      send_message('GetQueryId', GetQueryId_args, :req => req)
    end

    def recv_GetQueryId()
      result = receive_message(GetQueryId_result)
      return result.success unless result.success.nil?
      raise ::Thrift::ApplicationException.new(::Thrift::ApplicationException::MISSING_RESULT, 'GetQueryId failed: unknown result')
    end

    def SetClientInfo(req)
      send_SetClientInfo(req)
      return recv_SetClientInfo()
    end

    def send_SetClientInfo(req)
      send_message('SetClientInfo', SetClientInfo_args, :req => req)
    end

    def recv_SetClientInfo()
      result = receive_message(SetClientInfo_result)
      return result.success unless result.success.nil?
      raise ::Thrift::ApplicationException.new(::Thrift::ApplicationException::MISSING_RESULT, 'SetClientInfo failed: unknown result')
    end

  end

  class Processor
    include ::Thrift::Processor

    def process_OpenSession(seqid, iprot, oprot)
      args = read_args(iprot, OpenSession_args)
      result = OpenSession_result.new()
      result.success = @handler.OpenSession(args.req)
      write_result(result, oprot, 'OpenSession', seqid)
    end

    def process_CloseSession(seqid, iprot, oprot)
      args = read_args(iprot, CloseSession_args)
      result = CloseSession_result.new()
      result.success = @handler.CloseSession(args.req)
      write_result(result, oprot, 'CloseSession', seqid)
    end

    def process_GetInfo(seqid, iprot, oprot)
      args = read_args(iprot, GetInfo_args)
      result = GetInfo_result.new()
      result.success = @handler.GetInfo(args.req)
      write_result(result, oprot, 'GetInfo', seqid)
    end

    def process_ExecuteStatement(seqid, iprot, oprot)
      args = read_args(iprot, ExecuteStatement_args)
      result = ExecuteStatement_result.new()
      result.success = @handler.ExecuteStatement(args.req)
      write_result(result, oprot, 'ExecuteStatement', seqid)
    end

    def process_GetTypeInfo(seqid, iprot, oprot)
      args = read_args(iprot, GetTypeInfo_args)
      result = GetTypeInfo_result.new()
      result.success = @handler.GetTypeInfo(args.req)
      write_result(result, oprot, 'GetTypeInfo', seqid)
    end

    def process_GetCatalogs(seqid, iprot, oprot)
      args = read_args(iprot, GetCatalogs_args)
      result = GetCatalogs_result.new()
      result.success = @handler.GetCatalogs(args.req)
      write_result(result, oprot, 'GetCatalogs', seqid)
    end

    def process_GetSchemas(seqid, iprot, oprot)
      args = read_args(iprot, GetSchemas_args)
      result = GetSchemas_result.new()
      result.success = @handler.GetSchemas(args.req)
      write_result(result, oprot, 'GetSchemas', seqid)
    end

    def process_GetTables(seqid, iprot, oprot)
      args = read_args(iprot, GetTables_args)
      result = GetTables_result.new()
      result.success = @handler.GetTables(args.req)
      write_result(result, oprot, 'GetTables', seqid)
    end

    def process_GetTableTypes(seqid, iprot, oprot)
      args = read_args(iprot, GetTableTypes_args)
      result = GetTableTypes_result.new()
      result.success = @handler.GetTableTypes(args.req)
      write_result(result, oprot, 'GetTableTypes', seqid)
    end

    def process_GetColumns(seqid, iprot, oprot)
      args = read_args(iprot, GetColumns_args)
      result = GetColumns_result.new()
      result.success = @handler.GetColumns(args.req)
      write_result(result, oprot, 'GetColumns', seqid)
    end

    def process_GetFunctions(seqid, iprot, oprot)
      args = read_args(iprot, GetFunctions_args)
      result = GetFunctions_result.new()
      result.success = @handler.GetFunctions(args.req)
      write_result(result, oprot, 'GetFunctions', seqid)
    end

    def process_GetPrimaryKeys(seqid, iprot, oprot)
      args = read_args(iprot, GetPrimaryKeys_args)
      result = GetPrimaryKeys_result.new()
      result.success = @handler.GetPrimaryKeys(args.req)
      write_result(result, oprot, 'GetPrimaryKeys', seqid)
    end

    def process_GetCrossReference(seqid, iprot, oprot)
      args = read_args(iprot, GetCrossReference_args)
      result = GetCrossReference_result.new()
      result.success = @handler.GetCrossReference(args.req)
      write_result(result, oprot, 'GetCrossReference', seqid)
    end

    def process_GetOperationStatus(seqid, iprot, oprot)
      args = read_args(iprot, GetOperationStatus_args)
      result = GetOperationStatus_result.new()
      result.success = @handler.GetOperationStatus(args.req)
      write_result(result, oprot, 'GetOperationStatus', seqid)
    end

    def process_CancelOperation(seqid, iprot, oprot)
      args = read_args(iprot, CancelOperation_args)
      result = CancelOperation_result.new()
      result.success = @handler.CancelOperation(args.req)
      write_result(result, oprot, 'CancelOperation', seqid)
    end

    def process_CloseOperation(seqid, iprot, oprot)
      args = read_args(iprot, CloseOperation_args)
      result = CloseOperation_result.new()
      result.success = @handler.CloseOperation(args.req)
      write_result(result, oprot, 'CloseOperation', seqid)
    end

    def process_GetResultSetMetadata(seqid, iprot, oprot)
      args = read_args(iprot, GetResultSetMetadata_args)
      result = GetResultSetMetadata_result.new()
      result.success = @handler.GetResultSetMetadata(args.req)
      write_result(result, oprot, 'GetResultSetMetadata', seqid)
    end

    def process_FetchResults(seqid, iprot, oprot)
      args = read_args(iprot, FetchResults_args)
      result = FetchResults_result.new()
      result.success = @handler.FetchResults(args.req)
      write_result(result, oprot, 'FetchResults', seqid)
    end

    def process_GetDelegationToken(seqid, iprot, oprot)
      args = read_args(iprot, GetDelegationToken_args)
      result = GetDelegationToken_result.new()
      result.success = @handler.GetDelegationToken(args.req)
      write_result(result, oprot, 'GetDelegationToken', seqid)
    end

    def process_CancelDelegationToken(seqid, iprot, oprot)
      args = read_args(iprot, CancelDelegationToken_args)
      result = CancelDelegationToken_result.new()
      result.success = @handler.CancelDelegationToken(args.req)
      write_result(result, oprot, 'CancelDelegationToken', seqid)
    end

    def process_RenewDelegationToken(seqid, iprot, oprot)
      args = read_args(iprot, RenewDelegationToken_args)
      result = RenewDelegationToken_result.new()
      result.success = @handler.RenewDelegationToken(args.req)
      write_result(result, oprot, 'RenewDelegationToken', seqid)
    end

    def process_GetQueryId(seqid, iprot, oprot)
      args = read_args(iprot, GetQueryId_args)
      result = GetQueryId_result.new()
      result.success = @handler.GetQueryId(args.req)
      write_result(result, oprot, 'GetQueryId', seqid)
    end

    def process_SetClientInfo(seqid, iprot, oprot)
      args = read_args(iprot, SetClientInfo_args)
      result = SetClientInfo_result.new()
      result.success = @handler.SetClientInfo(args.req)
      write_result(result, oprot, 'SetClientInfo', seqid)
    end

  end

  # HELPER FUNCTIONS AND STRUCTURES

  class OpenSession_args
    include ::Thrift::Struct, ::Thrift::Struct_Union
    REQ = 1

    FIELDS = {
      REQ => {:type => ::Thrift::Types::STRUCT, :name => 'req', :class => ::TOpenSessionReq}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class OpenSession_result
    include ::Thrift::Struct, ::Thrift::Struct_Union
    SUCCESS = 0

    FIELDS = {
      SUCCESS => {:type => ::Thrift::Types::STRUCT, :name => 'success', :class => ::TOpenSessionResp}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class CloseSession_args
    include ::Thrift::Struct, ::Thrift::Struct_Union
    REQ = 1

    FIELDS = {
      REQ => {:type => ::Thrift::Types::STRUCT, :name => 'req', :class => ::TCloseSessionReq}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class CloseSession_result
    include ::Thrift::Struct, ::Thrift::Struct_Union
    SUCCESS = 0

    FIELDS = {
      SUCCESS => {:type => ::Thrift::Types::STRUCT, :name => 'success', :class => ::TCloseSessionResp}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class GetInfo_args
    include ::Thrift::Struct, ::Thrift::Struct_Union
    REQ = 1

    FIELDS = {
      REQ => {:type => ::Thrift::Types::STRUCT, :name => 'req', :class => ::TGetInfoReq}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class GetInfo_result
    include ::Thrift::Struct, ::Thrift::Struct_Union
    SUCCESS = 0

    FIELDS = {
      SUCCESS => {:type => ::Thrift::Types::STRUCT, :name => 'success', :class => ::TGetInfoResp}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class ExecuteStatement_args
    include ::Thrift::Struct, ::Thrift::Struct_Union
    REQ = 1

    FIELDS = {
      REQ => {:type => ::Thrift::Types::STRUCT, :name => 'req', :class => ::TExecuteStatementReq}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class ExecuteStatement_result
    include ::Thrift::Struct, ::Thrift::Struct_Union
    SUCCESS = 0

    FIELDS = {
      SUCCESS => {:type => ::Thrift::Types::STRUCT, :name => 'success', :class => ::TExecuteStatementResp}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class GetTypeInfo_args
    include ::Thrift::Struct, ::Thrift::Struct_Union
    REQ = 1

    FIELDS = {
      REQ => {:type => ::Thrift::Types::STRUCT, :name => 'req', :class => ::TGetTypeInfoReq}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class GetTypeInfo_result
    include ::Thrift::Struct, ::Thrift::Struct_Union
    SUCCESS = 0

    FIELDS = {
      SUCCESS => {:type => ::Thrift::Types::STRUCT, :name => 'success', :class => ::TGetTypeInfoResp}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class GetCatalogs_args
    include ::Thrift::Struct, ::Thrift::Struct_Union
    REQ = 1

    FIELDS = {
      REQ => {:type => ::Thrift::Types::STRUCT, :name => 'req', :class => ::TGetCatalogsReq}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class GetCatalogs_result
    include ::Thrift::Struct, ::Thrift::Struct_Union
    SUCCESS = 0

    FIELDS = {
      SUCCESS => {:type => ::Thrift::Types::STRUCT, :name => 'success', :class => ::TGetCatalogsResp}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class GetSchemas_args
    include ::Thrift::Struct, ::Thrift::Struct_Union
    REQ = 1

    FIELDS = {
      REQ => {:type => ::Thrift::Types::STRUCT, :name => 'req', :class => ::TGetSchemasReq}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class GetSchemas_result
    include ::Thrift::Struct, ::Thrift::Struct_Union
    SUCCESS = 0

    FIELDS = {
      SUCCESS => {:type => ::Thrift::Types::STRUCT, :name => 'success', :class => ::TGetSchemasResp}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class GetTables_args
    include ::Thrift::Struct, ::Thrift::Struct_Union
    REQ = 1

    FIELDS = {
      REQ => {:type => ::Thrift::Types::STRUCT, :name => 'req', :class => ::TGetTablesReq}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class GetTables_result
    include ::Thrift::Struct, ::Thrift::Struct_Union
    SUCCESS = 0

    FIELDS = {
      SUCCESS => {:type => ::Thrift::Types::STRUCT, :name => 'success', :class => ::TGetTablesResp}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class GetTableTypes_args
    include ::Thrift::Struct, ::Thrift::Struct_Union
    REQ = 1

    FIELDS = {
      REQ => {:type => ::Thrift::Types::STRUCT, :name => 'req', :class => ::TGetTableTypesReq}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class GetTableTypes_result
    include ::Thrift::Struct, ::Thrift::Struct_Union
    SUCCESS = 0

    FIELDS = {
      SUCCESS => {:type => ::Thrift::Types::STRUCT, :name => 'success', :class => ::TGetTableTypesResp}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class GetColumns_args
    include ::Thrift::Struct, ::Thrift::Struct_Union
    REQ = 1

    FIELDS = {
      REQ => {:type => ::Thrift::Types::STRUCT, :name => 'req', :class => ::TGetColumnsReq}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class GetColumns_result
    include ::Thrift::Struct, ::Thrift::Struct_Union
    SUCCESS = 0

    FIELDS = {
      SUCCESS => {:type => ::Thrift::Types::STRUCT, :name => 'success', :class => ::TGetColumnsResp}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class GetFunctions_args
    include ::Thrift::Struct, ::Thrift::Struct_Union
    REQ = 1

    FIELDS = {
      REQ => {:type => ::Thrift::Types::STRUCT, :name => 'req', :class => ::TGetFunctionsReq}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class GetFunctions_result
    include ::Thrift::Struct, ::Thrift::Struct_Union
    SUCCESS = 0

    FIELDS = {
      SUCCESS => {:type => ::Thrift::Types::STRUCT, :name => 'success', :class => ::TGetFunctionsResp}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class GetPrimaryKeys_args
    include ::Thrift::Struct, ::Thrift::Struct_Union
    REQ = 1

    FIELDS = {
      REQ => {:type => ::Thrift::Types::STRUCT, :name => 'req', :class => ::TGetPrimaryKeysReq}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class GetPrimaryKeys_result
    include ::Thrift::Struct, ::Thrift::Struct_Union
    SUCCESS = 0

    FIELDS = {
      SUCCESS => {:type => ::Thrift::Types::STRUCT, :name => 'success', :class => ::TGetPrimaryKeysResp}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class GetCrossReference_args
    include ::Thrift::Struct, ::Thrift::Struct_Union
    REQ = 1

    FIELDS = {
      REQ => {:type => ::Thrift::Types::STRUCT, :name => 'req', :class => ::TGetCrossReferenceReq}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class GetCrossReference_result
    include ::Thrift::Struct, ::Thrift::Struct_Union
    SUCCESS = 0

    FIELDS = {
      SUCCESS => {:type => ::Thrift::Types::STRUCT, :name => 'success', :class => ::TGetCrossReferenceResp}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class GetOperationStatus_args
    include ::Thrift::Struct, ::Thrift::Struct_Union
    REQ = 1

    FIELDS = {
      REQ => {:type => ::Thrift::Types::STRUCT, :name => 'req', :class => ::TGetOperationStatusReq}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class GetOperationStatus_result
    include ::Thrift::Struct, ::Thrift::Struct_Union
    SUCCESS = 0

    FIELDS = {
      SUCCESS => {:type => ::Thrift::Types::STRUCT, :name => 'success', :class => ::TGetOperationStatusResp}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class CancelOperation_args
    include ::Thrift::Struct, ::Thrift::Struct_Union
    REQ = 1

    FIELDS = {
      REQ => {:type => ::Thrift::Types::STRUCT, :name => 'req', :class => ::TCancelOperationReq}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class CancelOperation_result
    include ::Thrift::Struct, ::Thrift::Struct_Union
    SUCCESS = 0

    FIELDS = {
      SUCCESS => {:type => ::Thrift::Types::STRUCT, :name => 'success', :class => ::TCancelOperationResp}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class CloseOperation_args
    include ::Thrift::Struct, ::Thrift::Struct_Union
    REQ = 1

    FIELDS = {
      REQ => {:type => ::Thrift::Types::STRUCT, :name => 'req', :class => ::TCloseOperationReq}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class CloseOperation_result
    include ::Thrift::Struct, ::Thrift::Struct_Union
    SUCCESS = 0

    FIELDS = {
      SUCCESS => {:type => ::Thrift::Types::STRUCT, :name => 'success', :class => ::TCloseOperationResp}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class GetResultSetMetadata_args
    include ::Thrift::Struct, ::Thrift::Struct_Union
    REQ = 1

    FIELDS = {
      REQ => {:type => ::Thrift::Types::STRUCT, :name => 'req', :class => ::TGetResultSetMetadataReq}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class GetResultSetMetadata_result
    include ::Thrift::Struct, ::Thrift::Struct_Union
    SUCCESS = 0

    FIELDS = {
      SUCCESS => {:type => ::Thrift::Types::STRUCT, :name => 'success', :class => ::TGetResultSetMetadataResp}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class FetchResults_args
    include ::Thrift::Struct, ::Thrift::Struct_Union
    REQ = 1

    FIELDS = {
      REQ => {:type => ::Thrift::Types::STRUCT, :name => 'req', :class => ::TFetchResultsReq}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class FetchResults_result
    include ::Thrift::Struct, ::Thrift::Struct_Union
    SUCCESS = 0

    FIELDS = {
      SUCCESS => {:type => ::Thrift::Types::STRUCT, :name => 'success', :class => ::TFetchResultsResp}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class GetDelegationToken_args
    include ::Thrift::Struct, ::Thrift::Struct_Union
    REQ = 1

    FIELDS = {
      REQ => {:type => ::Thrift::Types::STRUCT, :name => 'req', :class => ::TGetDelegationTokenReq}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class GetDelegationToken_result
    include ::Thrift::Struct, ::Thrift::Struct_Union
    SUCCESS = 0

    FIELDS = {
      SUCCESS => {:type => ::Thrift::Types::STRUCT, :name => 'success', :class => ::TGetDelegationTokenResp}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class CancelDelegationToken_args
    include ::Thrift::Struct, ::Thrift::Struct_Union
    REQ = 1

    FIELDS = {
      REQ => {:type => ::Thrift::Types::STRUCT, :name => 'req', :class => ::TCancelDelegationTokenReq}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class CancelDelegationToken_result
    include ::Thrift::Struct, ::Thrift::Struct_Union
    SUCCESS = 0

    FIELDS = {
      SUCCESS => {:type => ::Thrift::Types::STRUCT, :name => 'success', :class => ::TCancelDelegationTokenResp}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class RenewDelegationToken_args
    include ::Thrift::Struct, ::Thrift::Struct_Union
    REQ = 1

    FIELDS = {
      REQ => {:type => ::Thrift::Types::STRUCT, :name => 'req', :class => ::TRenewDelegationTokenReq}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class RenewDelegationToken_result
    include ::Thrift::Struct, ::Thrift::Struct_Union
    SUCCESS = 0

    FIELDS = {
      SUCCESS => {:type => ::Thrift::Types::STRUCT, :name => 'success', :class => ::TRenewDelegationTokenResp}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class GetQueryId_args
    include ::Thrift::Struct, ::Thrift::Struct_Union
    REQ = 1

    FIELDS = {
      REQ => {:type => ::Thrift::Types::STRUCT, :name => 'req', :class => ::TGetQueryIdReq}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class GetQueryId_result
    include ::Thrift::Struct, ::Thrift::Struct_Union
    SUCCESS = 0

    FIELDS = {
      SUCCESS => {:type => ::Thrift::Types::STRUCT, :name => 'success', :class => ::TGetQueryIdResp}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class SetClientInfo_args
    include ::Thrift::Struct, ::Thrift::Struct_Union
    REQ = 1

    FIELDS = {
      REQ => {:type => ::Thrift::Types::STRUCT, :name => 'req', :class => ::TSetClientInfoReq}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

  class SetClientInfo_result
    include ::Thrift::Struct, ::Thrift::Struct_Union
    SUCCESS = 0

    FIELDS = {
      SUCCESS => {:type => ::Thrift::Types::STRUCT, :name => 'success', :class => ::TSetClientInfoResp}
    }

    def struct_fields; FIELDS; end

    def validate
    end

    ::Thrift::Struct.generate_accessors self
  end

end

