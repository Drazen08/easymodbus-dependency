package com.github.sunjx.modbus.protocol;

import io.netty.buffer.ByteBuf;


public abstract class ModbusFunction {
    private final short functionCode;

    /* 30 */
    public ModbusFunction(short functionCode) {
        this.functionCode = functionCode;
    }


    /* 34 */
    public short getFunctionCode() {
        return this.functionCode;
    }

    public abstract int calculateLength();

    public abstract ByteBuf encode();

    public abstract void decode(ByteBuf paramByteBuf);
}


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4j\protocol\ModbusFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */