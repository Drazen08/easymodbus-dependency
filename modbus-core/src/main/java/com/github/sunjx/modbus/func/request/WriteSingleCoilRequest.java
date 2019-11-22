package com.github.sunjx.modbus.func.request;

import com.github.sunjx.modbus.func.AbstractRequest;
import io.netty.buffer.ByteBuf;


public class WriteSingleCoilRequest
        extends AbstractRequest {
    private boolean state;

    /* 29 */
    public WriteSingleCoilRequest() {
        super((short) 5);
    }


    public WriteSingleCoilRequest(int outputAddress, boolean state) {
        /* 33 */
        super((short) 5, outputAddress, state ? 65280 : 0);

        /* 35 */
        this.state = state;
    }


    /* 39 */
    public int getOutputAddress() {
        return this.address;
    }


    @Override
    public void decode(ByteBuf data) {
        /* 44 */
        super.decode(data);

        /* 46 */
        this.state = (this.value == 65280);
    }


    /* 50 */
    public boolean isState() {
        return this.state;
    }


    /* 55 */
    @Override
    public String toString() {
        return "WriteSingleCoilRequest{outputAddress=" + this.address + ", state=" + this.state + '}';
    }
}


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4j\func\request\WriteSingleCoilRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */