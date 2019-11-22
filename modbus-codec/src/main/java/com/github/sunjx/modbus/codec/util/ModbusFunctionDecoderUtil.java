/*     */ package com.github.sunjx.modbus.codec.util;
/*     */ 
/*     */ import com.github.zengfr.easymodbus4j.func.request.ReadCoilsRequest;
/*     */ import com.github.zengfr.easymodbus4j.func.request.ReadDiscreteInputsRequest;
/*     */ import com.github.zengfr.easymodbus4j.func.request.ReadHoldingRegistersRequest;
/*     */ import com.github.zengfr.easymodbus4j.func.request.ReadInputRegistersRequest;
/*     */ import com.github.zengfr.easymodbus4j.func.request.WriteMultipleCoilsRequest;
/*     */ import com.github.zengfr.easymodbus4j.func.request.WriteMultipleRegistersRequest;
/*     */ import com.github.zengfr.easymodbus4j.func.request.WriteSingleCoilRequest;
/*     */ import com.github.zengfr.easymodbus4j.func.request.WriteSingleRegisterRequest;
/*     */ import com.github.zengfr.easymodbus4j.func.response.ErrorFunctionResponse;
/*     */ import com.github.zengfr.easymodbus4j.func.response.ReadCoilsResponse;
/*     */ import com.github.zengfr.easymodbus4j.func.response.ReadDiscreteInputsResponse;
/*     */ import com.github.zengfr.easymodbus4j.func.response.ReadHoldingRegistersResponse;
/*     */ import com.github.zengfr.easymodbus4j.func.response.ReadInputRegistersResponse;
/*     */ import com.github.zengfr.easymodbus4j.func.response.WriteMultipleCoilsResponse;
/*     */ import com.github.zengfr.easymodbus4j.func.response.WriteMultipleRegistersResponse;
/*     */ import com.github.zengfr.easymodbus4j.func.response.WriteSingleCoilResponse;
/*     */ import com.github.zengfr.easymodbus4j.func.response.WriteSingleRegisterResponse;
/*     */ import com.github.zengfr.easymodbus4j.protocol.ModbusFunction;
/*     */ import com.github.zengfr.easymodbus4j.util.ModbusFunctionUtil;
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
/*     */ public class ModbusFunctionDecoderUtil
/*     */ {
/*     */   public static ModbusFunction decodeReqFunction(short functionCode) {
/*     */     ReadHoldingRegistersRequest readHoldingRegistersRequest;
/*     */     WriteMultipleRegistersRequest writeMultipleRegistersRequest;
/*     */     ReadInputRegistersRequest readInputRegistersRequest;
/*     */     WriteSingleRegisterRequest writeSingleRegisterRequest;
/*     */     ReadCoilsRequest readCoilsRequest;
/*     */     WriteSingleCoilRequest writeSingleCoilRequest;
/*     */     WriteMultipleCoilsRequest writeMultipleCoilsRequest;
/*     */     ReadDiscreteInputsRequest readDiscreteInputsRequest;
/*  45 */     ModbusFunction function = null;
/*  46 */     switch (functionCode) {
/*     */       case 1:
/*  48 */         readCoilsRequest = new ReadCoilsRequest();
/*     */         break;
/*     */       case 2:
/*  51 */         readDiscreteInputsRequest = new ReadDiscreteInputsRequest();
/*     */         break;
/*     */       case 4:
/*  54 */         readInputRegistersRequest = new ReadInputRegistersRequest();
/*     */         break;
/*     */       case 3:
/*  57 */         readHoldingRegistersRequest = new ReadHoldingRegistersRequest();
/*     */         break;
/*     */       case 5:
/*  60 */         writeSingleCoilRequest = new WriteSingleCoilRequest();
/*     */         break;
/*     */       case 6:
/*  63 */         writeSingleRegisterRequest = new WriteSingleRegisterRequest();
/*     */         break;
/*     */       case 15:
/*  66 */         writeMultipleCoilsRequest = new WriteMultipleCoilsRequest();
/*     */         break;
/*     */       case 16:
/*  69 */         writeMultipleRegistersRequest = new WriteMultipleRegistersRequest();
/*     */         break;
/*     */     } 
/*     */     
/*  73 */     return (ModbusFunction)writeMultipleRegistersRequest; } public static ModbusFunction decodeRespFunction(short functionCode) { WriteSingleCoilResponse writeSingleCoilResponse; ReadDiscreteInputsResponse readDiscreteInputsResponse; ReadCoilsResponse readCoilsResponse; WriteSingleRegisterResponse writeSingleRegisterResponse; ReadInputRegistersResponse readInputRegistersResponse; ReadHoldingRegistersResponse readHoldingRegistersResponse;
/*     */     WriteMultipleCoilsResponse writeMultipleCoilsResponse;
/*     */     Object object;
/*  76 */     ModbusFunction function = null;
/*  77 */     switch (functionCode) {
/*     */       case 1:
/*  79 */         readCoilsResponse = new ReadCoilsResponse();
/*     */         break;
/*     */       case 2:
/*  82 */         readDiscreteInputsResponse = new ReadDiscreteInputsResponse();
/*     */         break;
/*     */       case 4:
/*  85 */         readInputRegistersResponse = new ReadInputRegistersResponse();
/*     */         break;
/*     */       case 3:
/*  88 */         readHoldingRegistersResponse = new ReadHoldingRegistersResponse();
/*     */         break;
/*     */       case 5:
/*  91 */         writeSingleCoilResponse = new WriteSingleCoilResponse();
/*     */         break;
/*     */       case 6:
/*  94 */         writeSingleRegisterResponse = new WriteSingleRegisterResponse();
/*     */         break;
/*     */       case 15:
/*  97 */         writeMultipleCoilsResponse = new WriteMultipleCoilsResponse();
/*     */         break;
/*     */       case 16:
/* 100 */         object = new WriteMultipleRegistersResponse();
/*     */         break;
/*     */     } 
/* 103 */     if (ModbusFunctionUtil.isError(functionCode)) {
/* 104 */       object = new ErrorFunctionResponse(functionCode);
/* 105 */     } else if (object == null) {
/* 106 */       object = new ErrorFunctionResponse(functionCode, (short)1);
/*     */     } 
/* 108 */     return (ModbusFunction)object; }
/*     */ 
/*     */ }


/* Location:              D:\logs\easymodbus4j-codec-0.0.5.jar!\com\github\zengfr\easymodbus4j\code\\util\ModbusFunctionDecoderUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */