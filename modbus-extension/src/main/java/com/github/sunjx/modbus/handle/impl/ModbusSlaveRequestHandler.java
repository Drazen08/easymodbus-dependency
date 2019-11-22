/*     */ package com.github.sunjx.modbus.handle.impl;
/*     */ 
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
/*     */ import com.github.zengfr.easymodbus4j.handler.ModbusRequestHandler;
/*     */ import com.github.zengfr.easymodbus4j.logging.ChannelLogger;
/*     */ import com.github.zengfr.easymodbus4j.processor.ModbusSlaveRequestProcessor;
/*     */ import com.github.zengfr.easymodbus4j.protocol.ModbusFunction;
/*     */ import com.github.zengfr.easymodbus4j.protocol.tcp.ModbusFrame;
/*     */ import com.github.zengfr.easymodbus4j.util.ModbusFrameUtil;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelHandler.Sharable;
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
/*     */ @Sharable
/*     */ public class ModbusSlaveRequestHandler
/*     */   extends ModbusRequestHandler
/*     */ {
/*  52 */   private static final ChannelLogger log = ChannelLogger.getLogger(ModbusSlaveRequestHandler.class);
/*     */   
/*     */   private ModbusSlaveRequestProcessor processor;
/*     */ 
/*     */   
/*  57 */   public short getTransactionIdentifierOffset() { return this.processor.getTransactionIdentifierOffset(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   public ModbusSlaveRequestHandler(ModbusSlaveRequestProcessor processor) { this.processor = processor; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   protected int getRespTransactionIdByReqTransactionId(int reqTransactionIdentifier) { return reqTransactionIdentifier + getTransactionIdentifierOffset(); }
/*     */ 
/*     */ 
/*     */   
/*     */   protected ModbusFunction processRequestFrame(Channel channel, ModbusFrame frame) {
/*  72 */     if (this.processor.isShowFrameDetail()) {
/*  73 */       ModbusFrameUtil.showFrameLog(log, channel, frame, true);
/*     */     }
/*  75 */     return super.processRequestFrame(channel, frame);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   protected WriteSingleCoilResponse writeSingleCoil(short unitIdentifier, WriteSingleCoilRequest request) { return this.processor.writeSingleCoil(unitIdentifier, request); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   protected WriteSingleRegisterResponse writeSingleRegister(short unitIdentifier, WriteSingleRegisterRequest request) { return this.processor.writeSingleRegister(unitIdentifier, request); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   protected ReadCoilsResponse readCoils(short unitIdentifier, ReadCoilsRequest request) { return this.processor.readCoils(unitIdentifier, request); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  98 */   protected ReadDiscreteInputsResponse readDiscreteInputs(short unitIdentifier, ReadDiscreteInputsRequest request) { return this.processor.readDiscreteInputs(unitIdentifier, request); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 104 */   protected ReadInputRegistersResponse readInputRegisters(short unitIdentifier, ReadInputRegistersRequest request) { return this.processor.readInputRegisters(unitIdentifier, request); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 110 */   protected ReadHoldingRegistersResponse readHoldingRegisters(short unitIdentifier, ReadHoldingRegistersRequest request) { return this.processor.readHoldingRegisters(unitIdentifier, request); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 116 */   protected WriteMultipleCoilsResponse writeMultipleCoils(short unitIdentifier, WriteMultipleCoilsRequest request) { return this.processor.writeMultipleCoils(unitIdentifier, request); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   protected WriteMultipleRegistersResponse writeMultipleRegisters(short unitIdentifier, WriteMultipleRegistersRequest request) { return this.processor.writeMultipleRegisters(unitIdentifier, request); }
/*     */ }


/* Location:              D:\logs\easymodbus4j-extension-0.0.5.jar!\com\github\zengfr\easymodbus4j\handle\impl\ModbusSlaveRequestHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.2
 */