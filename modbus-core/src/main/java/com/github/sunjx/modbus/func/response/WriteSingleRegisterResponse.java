package com.github.sunjx.modbus.func.response;


import com.github.sunjx.modbus.func.AbstractWriteResponse;

public class WriteSingleRegisterResponse
        extends AbstractWriteResponse {
    public WriteSingleRegisterResponse() {
        super((short) 6);
    }


    public WriteSingleRegisterResponse(int outputAddress, int value) {
        super((short) 6, outputAddress, value);
    }


    public int getRegisterAddress() {
        return this.address;
    }


    public int getRegisterValue() {
        return this.value;
    }


    @Override
    public String toString() {
        return "WriteSingleRegisterResponse{registerAddress=" + this.address + ", registerValue=" + this.value + '}';
    }
}


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4j\func\response\WriteSingleRegisterResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */