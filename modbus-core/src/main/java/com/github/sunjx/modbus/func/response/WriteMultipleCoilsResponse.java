package com.github.sunjx.modbus.func.response;


import com.github.sunjx.modbus.func.AbstractWriteResponse;

public class WriteMultipleCoilsResponse
        extends AbstractWriteResponse {
    public WriteMultipleCoilsResponse() {
        super((short) 15);
    }


    public WriteMultipleCoilsResponse(int startingAddress, int quantityOfOutputs) {
        super((short) 15, startingAddress, quantityOfOutputs);
    }


    public int getStartingAddress() {
        return this.address;
    }


    public int getQuantityOfOutputs() {
        return this.value;
    }


    @Override
    public String toString() {
        return "WriteMultipleCoilsResponse{startingAddress=" + this.address + ", quantityOfOutputs=" + this.value + '}';
    }
}


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4j\func\response\WriteMultipleCoilsResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */