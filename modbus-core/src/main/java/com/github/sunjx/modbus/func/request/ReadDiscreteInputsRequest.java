 package com.github.sunjx.modbus.func.request;

 import com.github.zengfr.easymodbus4j.func.AbstractRequest;




















 public class ReadDiscreteInputsRequest
   extends AbstractRequest
 {
/* 27 */   public ReadDiscreteInputsRequest() { super((short)2); }



/* 31 */   public ReadDiscreteInputsRequest(int startingAddress, int quantityOfCoils) { super((short)2, startingAddress, quantityOfCoils); }



/* 35 */   public int getStartingAddress() { return this.address; }



/* 39 */   public int getQuantityOfCoils() { return this.value; }




/* 44 */   public String toString() { return "ReadDiscreteInputsRequest{startingAddress=" + this.address + ", quantityOfCoils=" + this.value + '}'; }
 }


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4j\func\request\ReadDiscreteInputsRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */