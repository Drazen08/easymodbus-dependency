 package com.github.sunjx.modbus.func.request;

 import com.github.zengfr.easymodbus4j.func.AbstractRequest;




















 public class WriteSingleRegisterRequest
   extends AbstractRequest
 {
/* 27 */   public WriteSingleRegisterRequest() { super((short)6); }



/* 31 */   public WriteSingleRegisterRequest(int outputAddress, int value) { super((short)6, outputAddress, value); }



/* 35 */   public int getRegisterAddress() { return this.address; }



/* 39 */   public int getRegisterValue() { return this.value; }




/* 44 */   public String toString() { return "WriteSingleRegisterRequest{registerAddress=" + this.address + ", registerValue=" + this.value + '}'; }
 }


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4j\func\request\WriteSingleRegisterRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */