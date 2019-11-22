 package com.github.sunjx.modbus.sender;

 import com.github.zengfr.easymodbus4j.ModbusConsts;
 import com.github.zengfr.easymodbus4j.func.request.ReadCoilsRequest;
 import com.github.zengfr.easymodbus4j.func.request.ReadDiscreteInputsRequest;
 import com.github.zengfr.easymodbus4j.func.request.ReadHoldingRegistersRequest;
 import com.github.zengfr.easymodbus4j.func.request.ReadInputRegistersRequest;
 import com.github.zengfr.easymodbus4j.func.request.WriteMultipleCoilsRequest;
 import com.github.zengfr.easymodbus4j.func.request.WriteMultipleRegistersRequest;
 import com.github.zengfr.easymodbus4j.func.request.WriteSingleCoilRequest;
 import com.github.zengfr.easymodbus4j.func.request.WriteSingleRegisterRequest;
 import com.github.zengfr.easymodbus4j.protocol.ModbusFunction;
 import io.netty.channel.Channel;
 import java.util.BitSet;





















 public class ChannelAsyncSender
   extends AbstractChannelSender
 {
/* 39 */   public ChannelAsyncSender(Channel channel) { this(channel, ModbusConsts.DEFAULT_UNIT_IDENTIFIER, (short)0); }



/* 43 */   public ChannelAsyncSender(Channel channel, short unitIdentifier) { this(channel, unitIdentifier, (short)0); }



/* 47 */   public ChannelAsyncSender(Channel channel, short unitId, short protocolIdentifier) { super(channel, unitId, protocolIdentifier); }



/* 51 */   public int writeSingleCoilAsync(int address, boolean state) throws Exception { return callModbusFunction((ModbusFunction)new WriteSingleCoilRequest(address, state)); }



/* 55 */   public int writeSingleRegisterAsync(int address, int value) throws Exception { return callModbusFunction((ModbusFunction)new WriteSingleRegisterRequest(address, value)); }



/* 59 */   public int readCoilsAsync(int startAddress, int quantityOfCoils) throws Exception { return callModbusFunction((ModbusFunction)new ReadCoilsRequest(startAddress, quantityOfCoils)); }



/* 63 */   public int readDiscreteInputsAsync(int startAddress, int quantityOfCoils) throws Exception { return callModbusFunction((ModbusFunction)new ReadDiscreteInputsRequest(startAddress, quantityOfCoils)); }



/* 67 */   public int readInputRegistersAsync(int startAddress, int quantityOfInputRegisters) throws Exception { return callModbusFunction((ModbusFunction)new ReadInputRegistersRequest(startAddress, quantityOfInputRegisters)); }



/* 71 */   public int readHoldingRegistersAsync(int startAddress, int quantityOfInputRegisters) throws Exception { return callModbusFunction((ModbusFunction)new ReadHoldingRegistersRequest(startAddress, quantityOfInputRegisters)); }


/* 74 */   public int writeMultipleCoilsAsync(int address, int quantityOfOutputs, boolean[] outputsValue) throws Exception { return callModbusFunction((ModbusFunction)new WriteMultipleCoilsRequest(address, quantityOfOutputs, outputsValue)); }


/* 77 */   public int writeMultipleCoilsAsync(int address, int quantityOfOutputs, BitSet outputsValue) throws Exception { return callModbusFunction((ModbusFunction)new WriteMultipleCoilsRequest(address, quantityOfOutputs, outputsValue)); }



/* 81 */   public int writeMultipleRegistersAsync(int address, int quantityOfRegisters, int[] registers) throws Exception { return callModbusFunction((ModbusFunction)new WriteMultipleRegistersRequest(address, quantityOfRegisters, registers)); }
 }


/* Location:              D:\logs\easymodbus4j-0.0.5.jar!\com\github\zengfr\easymodbus4j\com.github.sunjx.modbus.sender\ChannelAsyncSender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */