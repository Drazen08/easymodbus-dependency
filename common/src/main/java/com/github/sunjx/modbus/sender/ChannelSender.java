package com.github.sunjx.modbus.sender;

import com.github.sunjx.modbus.ModbusConsts;
import com.github.sunjx.modbus.func.request.*;
import com.github.sunjx.modbus.func.response.*;
import com.github.sunjx.modbus.protocol.ModbusFunction;
import io.netty.channel.Channel;

import java.util.BitSet;


public class ChannelSender
        extends AbstractChannelSender {
    public ChannelSender(Channel channel) {
        this(channel, ModbusConsts.DEFAULT_UNIT_IDENTIFIER, (short) 0);
    }


    public ChannelSender(Channel channel, short unitIdentifier) {
        this(channel, unitIdentifier, (short) 0);
    }


    public ChannelSender(Channel channel, short unitId, short protocolIdentifier) {
        super(channel, unitId, protocolIdentifier);
    }


    public int writeSingleCoilAsync(int address, boolean state) throws Exception {
        return callModbusFunction(new WriteSingleCoilRequest(address, state));
    }


    public int writeSingleRegisterAsync(int address, int value) throws Exception {
        return callModbusFunction(new WriteSingleRegisterRequest(address, value));
    }


    public int readCoilsAsync(int startAddress, int quantityOfCoils) throws Exception {
        return callModbusFunction(new ReadCoilsRequest(startAddress, quantityOfCoils));
    }


    public int readDiscreteInputsAsync(int startAddress, int quantityOfCoils) throws Exception {
        return callModbusFunction(new ReadDiscreteInputsRequest(startAddress, quantityOfCoils));
    }


    public int readInputRegistersAsync(int startAddress, int quantityOfInputRegisters) throws Exception {
        return callModbusFunction(new ReadInputRegistersRequest(startAddress, quantityOfInputRegisters));
    }

    /**
     * 使用
     *
     * @param startAddress
     * @param quantityOfInputRegisters
     * @return
     * @throws Exception
     */
    public int readHoldingRegistersAsync(int startAddress, int quantityOfInputRegisters) throws Exception {
        return callModbusFunction(new ReadHoldingRegistersRequest(startAddress, quantityOfInputRegisters));
    }


    public int writeMultipleCoilsAsync(int address, int quantityOfOutputs, boolean[] outputsValue) throws Exception {
        return callModbusFunction(new WriteMultipleCoilsRequest(address, quantityOfOutputs, outputsValue));
    }


    public int writeMultipleCoilsAsync(int address, int quantityOfOutputs, BitSet outputsValue) throws Exception {
        return callModbusFunction(new WriteMultipleCoilsRequest(address, quantityOfOutputs, outputsValue));
    }


    public int writeMultipleRegistersAsync(int address, int quantityOfRegisters, int[] registers) throws Exception {
        return callModbusFunction(new WriteMultipleRegistersRequest(address, quantityOfRegisters, registers));
    }


    public WriteSingleCoilResponse writeSingleCoil(int address, boolean state) throws Exception {
        return callModbusFunctionSync(new WriteSingleCoilRequest(address, state));
    }


    public WriteSingleRegisterResponse writeSingleRegister(int address, int value) throws Exception {
        return callModbusFunctionSync(new WriteSingleRegisterRequest(address, value));
    }


    public ReadCoilsResponse readCoils(int startAddress, int quantityOfCoils) throws Exception {
        return callModbusFunctionSync(new ReadCoilsRequest(startAddress, quantityOfCoils));
    }


    public ReadDiscreteInputsResponse readDiscreteInputs(int startAddress, int quantityOfCoils) throws Exception {
        return callModbusFunctionSync(new ReadDiscreteInputsRequest(startAddress, quantityOfCoils));
    }


    public ReadInputRegistersResponse readInputRegisters(int startAddress, int quantityOfInputRegisters) throws Exception {
        return callModbusFunctionSync(new ReadInputRegistersRequest(startAddress, quantityOfInputRegisters));
    }


    public ReadHoldingRegistersResponse readHoldingRegisters(int startAddress, int quantityOfInputRegisters) throws Exception {
        return callModbusFunctionSync(new ReadHoldingRegistersRequest(startAddress, quantityOfInputRegisters));
    }


    /* 122 */
    public WriteMultipleCoilsResponse writeMultipleCoils(int address, int quantityOfOutputs, boolean[] outputsValue) throws Exception {
        return callModbusFunctionSync(new WriteMultipleCoilsRequest(address, quantityOfOutputs, outputsValue));
    }


    /* 127 */
    public WriteMultipleCoilsResponse writeMultipleCoils(int address, int quantityOfOutputs, BitSet outputsValue) throws Exception {
        return callModbusFunctionSync(new WriteMultipleCoilsRequest(address, quantityOfOutputs, outputsValue));
    }


    /* 133 */
    public WriteMultipleRegistersResponse writeMultipleRegisters(int address, int quantityOfRegisters, int[] registers) throws Exception {
        return callModbusFunctionSync(new WriteMultipleRegistersRequest(address, quantityOfRegisters, registers));
    }
}

