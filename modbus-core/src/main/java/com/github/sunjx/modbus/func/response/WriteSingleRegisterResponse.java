 package com.github.sunjx.modbus.func.response;

 import com.github.zengfr.easymodbus4j.func.AbstractWriteResponse;




















 public class WriteSingleRegisterResponse
   extends AbstractWriteResponse
 {
/* 27 */   public WriteSingleRegisterResponse() { super((short)6); }



/* 31 */   public WriteSingleRegisterResponse(int outputAddress, int value) { super((short)6, outputAddress, value); }



/* 35 */   public int getRegisterAddress() { return this.address; }



/* 39 */   public int getRegisterValue() { return this.value; }




/* 44 */   public String toString() { return "WriteSingleRegisterResponse{registerAddress=" + this.address + ", registerValue=" + this.value + '}'; }
 }


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4j\func\response\WriteSingleRegisterResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */