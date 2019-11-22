package com.github.sunjx.modbus.func.request;


import com.github.sunjx.modbus.func.AbstractRequest;

public class ReadDiscreteInputsRequest
        extends AbstractRequest {
    /* 27 */
    public ReadDiscreteInputsRequest() {
        super((short) 2);
    }


    /* 31 */
    public ReadDiscreteInputsRequest(int startingAddress, int quantityOfCoils) {
        super((short) 2, startingAddress, quantityOfCoils);
    }


    /* 35 */
    public int getStartingAddress() {
        return this.address;
    }


    /* 39 */
    public int getQuantityOfCoils() {
        return this.value;
    }


    /* 44 */
    @Override
    public String toString() {
        return "ReadDiscreteInputsRequest{startingAddress=" + this.address + ", quantityOfCoils=" + this.value + '}';
    }
}

