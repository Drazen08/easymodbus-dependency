package com.github.sunjx.modbus.func.request;


import com.github.sunjx.modbus.func.AbstractRequest;
import lombok.Getter;
import lombok.Setter;

public class ReadHoldingRegistersRequest
        extends AbstractRequest {
    public ReadHoldingRegistersRequest() {
        super((short) 3);
    }

    public ReadHoldingRegistersRequest(int startingAddress, int quantityOfInputRegisters) {
        super((short) 3, startingAddress, quantityOfInputRegisters);
    }



    public int getStartingAddress() {
        return this.address;
    }

    public int getQuantityOfInputRegisters() {
        return this.value;
    }

    @Getter
    private String deviceSign;

    @Override
    public String toString() {
        return "ReadHoldingRegistersRequest{startingAddress=" + this.address + ", quantityOfInputRegisters=" + this.value + '}';
    }
}

