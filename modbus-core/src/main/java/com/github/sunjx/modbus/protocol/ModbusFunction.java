package com.github.sunjx.modbus.protocol;

import io.netty.buffer.ByteBuf;


public abstract class ModbusFunction {
    private final short functionCode;
    public ModbusFunction(short functionCode) {
        this.functionCode = functionCode;
    }


    public short getFunctionCode() {
        return this.functionCode;
    }

    public abstract int calculateLength();

    public abstract ByteBuf encode();

    public abstract void decode(ByteBuf paramByteBuf);
}

