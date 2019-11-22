package com.github.sunjx.modbus.func.request;


import com.github.sunjx.modbus.func.AbstractRequest;

public class ReadHoldingRegistersRequest
        extends AbstractRequest {
    /* 27 */
    public ReadHoldingRegistersRequest() {
        super((short) 3);
    }


    /* 31 */
    public ReadHoldingRegistersRequest(int startingAddress, int quantityOfInputRegisters) {
        super((short) 3, startingAddress, quantityOfInputRegisters);
    }


    /* 35 */
    public int getStartingAddress() {
        return this.address;
    }


    /* 39 */
    public int getQuantityOfInputRegisters() {
        return this.value;
    }


    /* 44 */
    public String toString() {
        return "ReadHoldingRegistersRequest{startingAddress=" + this.address + ", quantityOfInputRegisters=" + this.value + '}';
    }
}


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4j\func\request\ReadHoldingRegistersRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */