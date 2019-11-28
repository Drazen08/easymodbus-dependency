package com.github.sunjx.modbus.codec;

import com.github.sunjx.modbus.codec.util.ModbusFunctionDecoderUtil;
import com.github.sunjx.modbus.protocol.ModbusFunction;
import io.netty.buffer.ByteBuf;


public class ModbusPduRespCodec
        extends ModbusPduCodec {
    /* 31 */
    @Override
    public ModbusFunction decode(ByteBuf buffer) {
        return decodeFunction(buffer);
    }


    public static ModbusFunction decodeFunction(ByteBuf buffer) {
        /* 36 */
        short functionCode = buffer.readUnsignedByte();
        /* 37 */
        ModbusFunction function = ModbusFunctionDecoderUtil.decodeRespFunction(functionCode);
        /* 38 */
        function.decode(buffer);
        /* 39 */
        return function;
    }
}

