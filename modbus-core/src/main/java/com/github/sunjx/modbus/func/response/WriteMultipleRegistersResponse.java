 package com.github.sunjx.modbus.func.response;

 import com.github.zengfr.easymodbus4j.func.AbstractWriteResponse;




















 public class WriteMultipleRegistersResponse
   extends AbstractWriteResponse
 {
/* 27 */   public WriteMultipleRegistersResponse() { super((short)16); }



/* 31 */   public WriteMultipleRegistersResponse(int startingAddress, int quantityOfRegisters) { super((short)16, startingAddress, quantityOfRegisters); }



/* 35 */   public int getStartingAddress() { return this.address; }



/* 39 */   public int getQuantityOfRegisters() { return this.value; }




/* 44 */   public String toString() { return "WriteMultipleRegistersResponse{startingAddress=" + this.address + ", quantityOfRegisters=" + this.value + '}'; }
 }


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4j\func\response\WriteMultipleRegistersResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */