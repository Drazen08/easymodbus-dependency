 package com.github.sunjx.modbus.func.request;

 import com.github.zengfr.easymodbus4j.func.AbstractRequest;




















 public class ReadInputRegistersRequest
   extends AbstractRequest
 {
/* 27 */   public ReadInputRegistersRequest() { super((short)4); }



/* 31 */   public ReadInputRegistersRequest(int startingAddress, int quantityOfInputRegisters) { super((short)4, startingAddress, quantityOfInputRegisters); }



/* 35 */   public int getStartingAddress() { return this.address; }



/* 39 */   public int getQuantityOfInputRegisters() { return this.value; }




/* 44 */   public String toString() { return "ReadInputRegistersRequest{startingAddress=" + this.address + ", quantityOfInputRegisters=" + this.value + '}'; }
 }


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4j\func\request\ReadInputRegistersRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */