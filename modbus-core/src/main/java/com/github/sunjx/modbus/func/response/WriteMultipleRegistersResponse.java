package com.github.sunjx.modbus.func.response;


import com.github.sunjx.modbus.func.AbstractWriteResponse;

public class WriteMultipleRegistersResponse
        extends AbstractWriteResponse {
    public WriteMultipleRegistersResponse() {
        super((short) 16);
    }


    public WriteMultipleRegistersResponse(int startingAddress, int quantityOfRegisters) {
        super((short) 16, startingAddress, quantityOfRegisters);
    }


    public int getStartingAddress() {
        return this.address;
    }


    public int getQuantityOfRegisters() {
        return this.value;
    }


    @Override
    public String toString() {
        return "WriteMultipleRegistersResponse{startingAddress=" + this.address + ", quantityOfRegisters=" + this.value + '}';
    }
}
