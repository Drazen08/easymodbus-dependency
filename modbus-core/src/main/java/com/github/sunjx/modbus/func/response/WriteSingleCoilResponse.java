package com.github.sunjx.modbus.func.response;

import com.github.sunjx.modbus.func.AbstractWriteResponse;
import io.netty.buffer.ByteBuf;


public class WriteSingleCoilResponse
        extends AbstractWriteResponse {
    private boolean state;

    public WriteSingleCoilResponse() {
        super((short) 5);
    }


    public WriteSingleCoilResponse(int outputAddress, boolean state) {
        super((short) 5, outputAddress, state ? 65280 : 0);

        this.state = state;
    }


    public int getOutputAddress() {
        return this.address;
    }


    @Override
    public void decode(ByteBuf data) {
        super.decode(data);

        this.state = (this.value == 65280);
    }


    public boolean isState() {
        return this.state;
    }


    @Override
    public String toString() {
        return "WriteSingleCoilResponse{outputAddress=" + this.address + ", state=" + this.state + '}';
    }
}

