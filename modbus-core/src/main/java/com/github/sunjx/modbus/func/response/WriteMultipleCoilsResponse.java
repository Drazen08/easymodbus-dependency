 package com.github.sunjx.modbus.func.response;

 import com.github.zengfr.easymodbus4j.func.AbstractWriteResponse;





















 public class WriteMultipleCoilsResponse
   extends AbstractWriteResponse
 {
/* 28 */   public WriteMultipleCoilsResponse() { super((short)15); }



/* 32 */   public WriteMultipleCoilsResponse(int startingAddress, int quantityOfOutputs) { super((short)15, startingAddress, quantityOfOutputs); }



/* 36 */   public int getStartingAddress() { return this.address; }



/* 40 */   public int getQuantityOfOutputs() { return this.value; }




/* 45 */   public String toString() { return "WriteMultipleCoilsResponse{startingAddress=" + this.address + ", quantityOfOutputs=" + this.value + '}'; }
 }


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4j\func\response\WriteMultipleCoilsResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */