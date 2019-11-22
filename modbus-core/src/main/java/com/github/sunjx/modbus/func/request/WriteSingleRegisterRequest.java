package com.github.sunjx.modbus.func.request;


import com.github.sunjx.modbus.func.AbstractRequest;

public class WriteSingleRegisterRequest
        extends AbstractRequest {
    /* 27 */
    public WriteSingleRegisterRequest() {
        super((short) 6);
    }


    /* 31 */
    public WriteSingleRegisterRequest(int outputAddress, int value) {
        super((short) 6, outputAddress, value);
    }


    /* 35 */
    public int getRegisterAddress() {
        return this.address;
    }


    /* 39 */
    public int getRegisterValue() {
        return this.value;
    }


    /* 44 */
    @Override
    public String toString() {
        return "WriteSingleRegisterRequest{registerAddress=" + this.address + ", registerValue=" + this.value + '}';
    }
}

