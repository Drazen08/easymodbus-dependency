package com.github.sunjx.modbus.codec;

import com.github.sunjx.modbus.codec.util.ModbusFunctionDecoderUtil;
import com.github.sunjx.modbus.protocol.ModbusFunction;
import io.netty.buffer.ByteBuf;


public class ModbusPduReqCodec extends ModbusPduCodec {
    @Override
    public ModbusFunction decode(ByteBuf buffer) {
        return decodeFunction(buffer);
    }


    public static ModbusFunction decodeFunction(ByteBuf buffer) {
        short functionCode = buffer.readUnsignedByte();
        ModbusFunction function = ModbusFunctionDecoderUtil.decodeReqFunction(functionCode);
        function.decode(buffer);
        return function;
    }
}
