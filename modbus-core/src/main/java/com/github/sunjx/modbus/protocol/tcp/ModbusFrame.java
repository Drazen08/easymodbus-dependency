package com.github.sunjx.modbus.protocol.tcp;

import com.github.sunjx.modbus.protocol.ModbusFunction;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;


public class ModbusFrame {
    private final ModbusHeader header;
    private final ModbusFunction function;

    public ModbusFrame(ModbusHeader header, ModbusFunction function) {
        this.header = header;
        this.function = function;
    }
    public ModbusHeader getHeader() {
        return this.header;
    }

    public ModbusFunction getFunction() {
        return this.function;
    }

    public ByteBuf encode() {
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(this.header.encode());
        buf.writeBytes(this.function.encode());
        return buf;
    }

    @Override
    public String toString() {
        return this.header + " " + this.function;
    }
}

