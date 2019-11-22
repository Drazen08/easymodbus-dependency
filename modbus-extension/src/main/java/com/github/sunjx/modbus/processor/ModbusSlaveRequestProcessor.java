package com.github.sunjx.modbus.processor;

import com.github.zengfr.easymodbus4j.func.request.ReadCoilsRequest;
import com.github.zengfr.easymodbus4j.func.request.ReadDiscreteInputsRequest;
import com.github.zengfr.easymodbus4j.func.request.ReadHoldingRegistersRequest;
import com.github.zengfr.easymodbus4j.func.request.ReadInputRegistersRequest;
import com.github.zengfr.easymodbus4j.func.request.WriteMultipleCoilsRequest;
import com.github.zengfr.easymodbus4j.func.request.WriteMultipleRegistersRequest;
import com.github.zengfr.easymodbus4j.func.request.WriteSingleCoilRequest;
import com.github.zengfr.easymodbus4j.func.request.WriteSingleRegisterRequest;
import com.github.zengfr.easymodbus4j.func.response.ReadCoilsResponse;
import com.github.zengfr.easymodbus4j.func.response.ReadDiscreteInputsResponse;
import com.github.zengfr.easymodbus4j.func.response.ReadHoldingRegistersResponse;
import com.github.zengfr.easymodbus4j.func.response.ReadInputRegistersResponse;
import com.github.zengfr.easymodbus4j.func.response.WriteMultipleCoilsResponse;
import com.github.zengfr.easymodbus4j.func.response.WriteMultipleRegistersResponse;
import com.github.zengfr.easymodbus4j.func.response.WriteSingleCoilResponse;
import com.github.zengfr.easymodbus4j.func.response.WriteSingleRegisterResponse;

public interface ModbusSlaveRequestProcessor extends ModbusProcessor {
  ReadCoilsResponse readCoils(short paramShort, ReadCoilsRequest paramReadCoilsRequest);
  
  ReadDiscreteInputsResponse readDiscreteInputs(short paramShort, ReadDiscreteInputsRequest paramReadDiscreteInputsRequest);
  
  ReadInputRegistersResponse readInputRegisters(short paramShort, ReadInputRegistersRequest paramReadInputRegistersRequest);
  
  ReadHoldingRegistersResponse readHoldingRegisters(short paramShort, ReadHoldingRegistersRequest paramReadHoldingRegistersRequest);
  
  WriteSingleCoilResponse writeSingleCoil(short paramShort, WriteSingleCoilRequest paramWriteSingleCoilRequest);
  
  WriteSingleRegisterResponse writeSingleRegister(short paramShort, WriteSingleRegisterRequest paramWriteSingleRegisterRequest);
  
  WriteMultipleCoilsResponse writeMultipleCoils(short paramShort, WriteMultipleCoilsRequest paramWriteMultipleCoilsRequest);
  
  WriteMultipleRegistersResponse writeMultipleRegisters(short paramShort, WriteMultipleRegistersRequest paramWriteMultipleRegistersRequest);
}


/* Location:              D:\logs\easymodbus4j-extension-0.0.5.jar!\com\github\zengfr\easymodbus4j\processor\ModbusSlaveRequestProcessor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.2
 */