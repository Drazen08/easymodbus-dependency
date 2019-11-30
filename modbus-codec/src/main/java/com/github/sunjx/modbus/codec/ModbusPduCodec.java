package com.github.sunjx.modbus.codec;

import com.github.sunjx.modbus.protocol.ModbusFunction;
import io.netty.buffer.ByteBuf;


public abstract class ModbusPduCodec
        implements ModbusCodec<ModbusFunction> {
    @Override
    public ByteBuf encode(ModbusFunction obj) {
        return obj.encode();
    }
}
