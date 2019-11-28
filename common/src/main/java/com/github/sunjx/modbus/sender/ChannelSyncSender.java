package com.github.sunjx.modbus.sender;


import com.github.sunjx.modbus.ModbusConsts;
import com.github.sunjx.modbus.func.request.*;
import com.github.sunjx.modbus.func.response.*;
import com.github.sunjx.modbus.protocol.ModbusFunction;
import io.netty.channel.Channel;

import java.util.BitSet;


public class ChannelSyncSender
        extends AbstractChannelSender {
    /* 48 */
    public ChannelSyncSender(Channel channel) {
        this(channel, ModbusConsts.DEFAULT_UNIT_IDENTIFIER, (short) 0);
    }


    /* 52 */
    public ChannelSyncSender(Channel channel, short unitIdentifier) {
        this(channel, unitIdentifier, (short) 0);
    }


    /* 56 */
    public ChannelSyncSender(Channel channel, short unitId, short protocolIdentifier) {
        super(channel, unitId, protocolIdentifier);
    }


    /* 59 */
    public WriteSingleCoilResponse writeSingleCoil(int address, boolean state) throws Exception {
        return callModbusFunctionSync((ModbusFunction) new WriteSingleCoilRequest(address, state));
    }


    /* 63 */
    public WriteSingleRegisterResponse writeSingleRegister(int address, int value) throws Exception {
        return callModbusFunctionSync((ModbusFunction) new WriteSingleRegisterRequest(address, value));
    }


    /* 67 */
    public ReadCoilsResponse readCoils(int startAddress, int quantityOfCoils) throws Exception {
        return callModbusFunctionSync((ModbusFunction) new ReadCoilsRequest(startAddress, quantityOfCoils));
    }


    /* 71 */
    public ReadDiscreteInputsResponse readDiscreteInputs(int startAddress, int quantityOfCoils) throws Exception {
        return callModbusFunctionSync((ModbusFunction) new ReadDiscreteInputsRequest(startAddress, quantityOfCoils));
    }


    /* 77 */
    public ReadInputRegistersResponse readInputRegisters(int startAddress, int quantityOfInputRegisters) throws Exception {
        return callModbusFunctionSync((ModbusFunction) new ReadInputRegistersRequest(startAddress, quantityOfInputRegisters));
    }


    /* 83 */
    public ReadHoldingRegistersResponse readHoldingRegisters(int startAddress, int quantityOfInputRegisters) throws Exception {
        return callModbusFunctionSync((ModbusFunction) new ReadHoldingRegistersRequest(startAddress, quantityOfInputRegisters));
    }


    /* 88 */
    public WriteMultipleCoilsResponse writeMultipleCoils(int address, int quantityOfOutputs, boolean[] outputsValue) throws Exception {
        return callModbusFunctionSync((ModbusFunction) new WriteMultipleCoilsRequest(address, quantityOfOutputs, outputsValue));
    }


    /* 93 */
    public WriteMultipleCoilsResponse writeMultipleCoils(int address, int quantityOfOutputs, BitSet outputsValue) throws Exception {
        return callModbusFunctionSync((ModbusFunction) new WriteMultipleCoilsRequest(address, quantityOfOutputs, outputsValue));
    }


    /* 99 */
    public WriteMultipleRegistersResponse writeMultipleRegisters(int address, int quantityOfRegisters, int[] registers) throws Exception {
        return callModbusFunctionSync((ModbusFunction) new WriteMultipleRegistersRequest(address, quantityOfRegisters, registers));
    }
}


/* Location:              D:\logs\easymodbus4j-0.0.5.jar!\com\github\zengfr\easymodbus4j\com.github.sunjx.modbus.sender\ChannelSyncSender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */