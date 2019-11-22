/*     */ package com.github.sunjx.modbus.func.response;
/*     */ 
/*     */ import com.github.zengfr.easymodbus4j.func.AbstractReadResponse;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import java.util.HashMap;
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
/*     */ public class ErrorFunctionResponse
/*     */   extends AbstractReadResponse
/*     */ {
/* 105 */   private static final HashMap<Short, String> ERRORS = new HashMap<>();
/*     */   
/*     */   static  {
/* 108 */     ERRORS.put(Short.valueOf((short)1), "ILLEGAL FUNCTION");
/* 109 */     ERRORS.put(Short.valueOf((short)2), "ILLEGAL DATA ADDRESS");
/* 110 */     ERRORS.put(Short.valueOf((short)3), "ILLEGAL DATA VALUE");
/* 111 */     ERRORS.put(Short.valueOf((short)4), "SLAVE DEVICE FAILURE");
/* 112 */     ERRORS.put(Short.valueOf((short)5), "ACKNOWLEDGE");
/* 113 */     ERRORS.put(Short.valueOf((short)6), "SLAVE DEVICE BUSY");
/* 114 */     ERRORS.put(Short.valueOf((short)8), "MEMORY PARITY ERROR");
/* 115 */     ERRORS.put(Short.valueOf((short)10), "GATEWAY PATH UNAVAILABLE");
/* 116 */     ERRORS.put(Short.valueOf((short)11), "GATEWAY TARGET DEVICE FAILED TO RESPOND");
/*     */   }
/*     */ 
/*     */   
/*     */   private short exceptionCode;
/*     */   private String exceptionMessage;
/*     */   
/* 123 */   public ErrorFunctionResponse(short functionCode) { super(functionCode); }
/*     */ 
/*     */   
/*     */   public ErrorFunctionResponse(short functionCode, short exceptionCode) {
/* 127 */     super(functionCode);
/* 128 */     this.exceptionCode = exceptionCode;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 133 */   private void setExceptionMessage(short exceptionCode) { this.exceptionMessage = (ERRORS.get(Short.valueOf(exceptionCode)) != null) ? ERRORS.get(Short.valueOf(exceptionCode)) : "UNDEFINED ERROR"; }
/*     */ 
/*     */ 
/*     */   
/* 137 */   public short getExceptionCode() { return this.exceptionCode; }
/*     */ 
/*     */ 
/*     */   
/* 141 */   public String getExceptionMessage() { return this.exceptionMessage; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 146 */   public int calculateLength() { return 2; }
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuf encode() {
/* 151 */     ByteBuf buf = Unpooled.buffer(calculateLength());
/* 152 */     buf.writeByte(getFunctionCode());
/* 153 */     buf.writeByte(this.exceptionCode);
/* 154 */     return buf;
/*     */   }
/*     */ 
/*     */   
/*     */   public void decode(ByteBuf data) {
/* 159 */     this.exceptionCode = data.readUnsignedByte();
/* 160 */     setExceptionMessage(this.exceptionCode);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 165 */   public String toString() { return "ModbusError{exceptionCode=" + this.exceptionCode + ", exceptionMessage=" + this.exceptionMessage + '}'; }
/*     */ }


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4j\func\response\ErrorFunctionResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */