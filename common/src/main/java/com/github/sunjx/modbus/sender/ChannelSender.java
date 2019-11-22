/*     */ package com.github.sunjx.modbus.sender;
/*     */ 
/*     */ import com.github.zengfr.easymodbus4j.ModbusConsts;
/*     */ import com.github.zengfr.easymodbus4j.func.request.ReadCoilsRequest;
/*     */ import com.github.zengfr.easymodbus4j.func.request.ReadDiscreteInputsRequest;
/*     */ import com.github.zengfr.easymodbus4j.func.request.ReadHoldingRegistersRequest;
/*     */ import com.github.zengfr.easymodbus4j.func.request.ReadInputRegistersRequest;
/*     */ import com.github.zengfr.easymodbus4j.func.request.WriteMultipleCoilsRequest;
/*     */ import com.github.zengfr.easymodbus4j.func.request.WriteMultipleRegistersRequest;
/*     */ import com.github.zengfr.easymodbus4j.func.request.WriteSingleCoilRequest;
/*     */ import com.github.zengfr.easymodbus4j.func.request.WriteSingleRegisterRequest;
/*     */ import com.github.zengfr.easymodbus4j.func.response.ReadCoilsResponse;
/*     */ import com.github.zengfr.easymodbus4j.func.response.ReadDiscreteInputsResponse;
/*     */ import com.github.zengfr.easymodbus4j.func.response.ReadHoldingRegistersResponse;
/*     */ import com.github.zengfr.easymodbus4j.func.response.ReadInputRegistersResponse;
/*     */ import com.github.zengfr.easymodbus4j.func.response.WriteMultipleCoilsResponse;
/*     */ import com.github.zengfr.easymodbus4j.func.response.WriteMultipleRegistersResponse;
/*     */ import com.github.zengfr.easymodbus4j.func.response.WriteSingleCoilResponse;
/*     */ import com.github.zengfr.easymodbus4j.func.response.WriteSingleRegisterResponse;
/*     */ import com.github.zengfr.easymodbus4j.protocol.ModbusFunction;
/*     */ import io.netty.channel.Channel;
/*     */ import java.util.BitSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChannelSender
/*     */   extends AbstractChannelSender
/*     */ {
/*  47 */   public ChannelSender(Channel channel) { this(channel, ModbusConsts.DEFAULT_UNIT_IDENTIFIER, (short)0); }
/*     */ 
/*     */ 
/*     */   
/*  51 */   public ChannelSender(Channel channel, short unitIdentifier) { this(channel, unitIdentifier, (short)0); }
/*     */ 
/*     */ 
/*     */   
/*  55 */   public ChannelSender(Channel channel, short unitId, short protocolIdentifier) { super(channel, unitId, protocolIdentifier); }
/*     */ 
/*     */ 
/*     */   
/*  59 */   public int writeSingleCoilAsync(int address, boolean state) throws Exception { return callModbusFunction((ModbusFunction)new WriteSingleCoilRequest(address, state)); }
/*     */ 
/*     */ 
/*     */   
/*  63 */   public int writeSingleRegisterAsync(int address, int value) throws Exception { return callModbusFunction((ModbusFunction)new WriteSingleRegisterRequest(address, value)); }
/*     */ 
/*     */ 
/*     */   
/*  67 */   public int readCoilsAsync(int startAddress, int quantityOfCoils) throws Exception { return callModbusFunction((ModbusFunction)new ReadCoilsRequest(startAddress, quantityOfCoils)); }
/*     */ 
/*     */ 
/*     */   
/*  71 */   public int readDiscreteInputsAsync(int startAddress, int quantityOfCoils) throws Exception { return callModbusFunction((ModbusFunction)new ReadDiscreteInputsRequest(startAddress, quantityOfCoils)); }
/*     */ 
/*     */ 
/*     */   
/*  75 */   public int readInputRegistersAsync(int startAddress, int quantityOfInputRegisters) throws Exception { return callModbusFunction((ModbusFunction)new ReadInputRegistersRequest(startAddress, quantityOfInputRegisters)); }
/*     */ 
/*     */ 
/*     */   
/*  79 */   public int readHoldingRegistersAsync(int startAddress, int quantityOfInputRegisters) throws Exception { return callModbusFunction((ModbusFunction)new ReadHoldingRegistersRequest(startAddress, quantityOfInputRegisters)); }
/*     */ 
/*     */   
/*  82 */   public int writeMultipleCoilsAsync(int address, int quantityOfOutputs, boolean[] outputsValue) throws Exception { return callModbusFunction((ModbusFunction)new WriteMultipleCoilsRequest(address, quantityOfOutputs, outputsValue)); }
/*     */ 
/*     */   
/*  85 */   public int writeMultipleCoilsAsync(int address, int quantityOfOutputs, BitSet outputsValue) throws Exception { return callModbusFunction((ModbusFunction)new WriteMultipleCoilsRequest(address, quantityOfOutputs, outputsValue)); }
/*     */ 
/*     */ 
/*     */   
/*  89 */   public int writeMultipleRegistersAsync(int address, int quantityOfRegisters, int[] registers) throws Exception { return callModbusFunction((ModbusFunction)new WriteMultipleRegistersRequest(address, quantityOfRegisters, registers)); }
/*     */ 
/*     */ 
/*     */   
/*  93 */   public WriteSingleCoilResponse writeSingleCoil(int address, boolean state) throws Exception { return callModbusFunctionSync((ModbusFunction)new WriteSingleCoilRequest(address, state)); }
/*     */ 
/*     */ 
/*     */   
/*  97 */   public WriteSingleRegisterResponse writeSingleRegister(int address, int value) throws Exception { return callModbusFunctionSync((ModbusFunction)new WriteSingleRegisterRequest(address, value)); }
/*     */ 
/*     */ 
/*     */   
/* 101 */   public ReadCoilsResponse readCoils(int startAddress, int quantityOfCoils) throws Exception { return callModbusFunctionSync((ModbusFunction)new ReadCoilsRequest(startAddress, quantityOfCoils)); }
/*     */ 
/*     */ 
/*     */   
/* 105 */   public ReadDiscreteInputsResponse readDiscreteInputs(int startAddress, int quantityOfCoils) throws Exception { return callModbusFunctionSync((ModbusFunction)new ReadDiscreteInputsRequest(startAddress, quantityOfCoils)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   public ReadInputRegistersResponse readInputRegisters(int startAddress, int quantityOfInputRegisters) throws Exception { return callModbusFunctionSync((ModbusFunction)new ReadInputRegistersRequest(startAddress, quantityOfInputRegisters)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   public ReadHoldingRegistersResponse readHoldingRegisters(int startAddress, int quantityOfInputRegisters) throws Exception { return callModbusFunctionSync((ModbusFunction)new ReadHoldingRegistersRequest(startAddress, quantityOfInputRegisters)); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   public WriteMultipleCoilsResponse writeMultipleCoils(int address, int quantityOfOutputs, boolean[] outputsValue) throws Exception { return callModbusFunctionSync((ModbusFunction)new WriteMultipleCoilsRequest(address, quantityOfOutputs, outputsValue)); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 127 */   public WriteMultipleCoilsResponse writeMultipleCoils(int address, int quantityOfOutputs, BitSet outputsValue) throws Exception { return callModbusFunctionSync((ModbusFunction)new WriteMultipleCoilsRequest(address, quantityOfOutputs, outputsValue)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 133 */   public WriteMultipleRegistersResponse writeMultipleRegisters(int address, int quantityOfRegisters, int[] registers) throws Exception { return callModbusFunctionSync((ModbusFunction)new WriteMultipleRegistersRequest(address, quantityOfRegisters, registers)); }
/*     */ }


/* Location:              D:\logs\easymodbus4j-0.0.5.jar!\com\github\zengfr\easymodbus4j\com.github.sunjx.modbus.sender\ChannelSender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */