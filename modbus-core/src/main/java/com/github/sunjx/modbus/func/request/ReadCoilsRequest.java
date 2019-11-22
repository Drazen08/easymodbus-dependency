 package com.github.sunjx.modbus.func.request;

 import com.github.zengfr.easymodbus4j.func.AbstractRequest;





















 public class ReadCoilsRequest
   extends AbstractRequest
 {
/* 28 */   public ReadCoilsRequest() { super((short)1); }



/* 32 */   public ReadCoilsRequest(int startingAddress, int quantityOfCoils) { super((short)1, startingAddress, quantityOfCoils); }



/* 36 */   public int getStartingAddress() { return this.address; }



/* 40 */   public int getQuantityOfCoils() { return this.value; }




/* 45 */   public String toString() { return "ReadCoilsRequest{startingAddress=" + this.address + ", quantityOfCoils=" + this.value + '}'; }
 }


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4j\func\request\ReadCoilsRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */