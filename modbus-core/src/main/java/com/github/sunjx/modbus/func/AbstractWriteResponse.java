 package com.github.sunjx.modbus.func;




















 public abstract class AbstractWriteResponse
   extends AbstractFunction
 {
/* 25 */   public AbstractWriteResponse(short functionCode) { super(functionCode); }


/* 28 */   public AbstractWriteResponse(short functionCode, int address, int quantity) { super(functionCode, address, quantity); }
 }


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4j\func\AbstractWriteResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */