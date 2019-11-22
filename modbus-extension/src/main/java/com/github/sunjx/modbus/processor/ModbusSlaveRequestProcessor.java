package com.github.sunjx.modbus.processor;


import com.github.sunjx.modbus.func.request.*;
import com.github.sunjx.modbus.func.response.*;

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

