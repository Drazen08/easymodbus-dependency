package com.github.sunjx.modbus.func;

import com.github.sunjx.modbus.protocol.ModbusFunction;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;


public abstract class AbstractFunction
        extends ModbusFunction {
    protected int address;
    protected int value;

    /* 33 */
    public AbstractFunction(short functionCode) {
        super(functionCode);
    }


    public AbstractFunction(short functionCode, int address, int quantity) {
        /* 37 */
        super(functionCode);

        /* 39 */
        this.address = address;
        /* 40 */
        this.value = quantity;
    }


    /* 44 */
    public int getAddress() {
        return this.address;
    }


    /* 48 */
    public int getValue() {
        return this.value;
    }


    /* 54 */
    @Override
    public int calculateLength() {
        return 5;
    }


    @Override
    public ByteBuf encode() {
        /* 59 */
        ByteBuf buf = Unpooled.buffer(calculateLength());
        /* 60 */
        buf.writeByte(getFunctionCode());
        /* 61 */
        buf.writeShort(this.address);
        /* 62 */
        buf.writeShort(this.value);

        /* 64 */
        return buf;
    }


    @Override
    public void decode(ByteBuf data) {
        /* 69 */
        this.address = data.readUnsignedShort();
        /* 70 */
        this.value = data.readUnsignedShort();
    }
}
