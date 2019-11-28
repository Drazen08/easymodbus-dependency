package com.github.sunjx.modbus.codec;

import com.github.sunjx.modbus.codec.util.ModbusFunctionDecoderUtil;
import com.github.sunjx.modbus.protocol.ModbusFunction;
import io.netty.buffer.ByteBuf;


public class ModbusPduReqCodec
        extends ModbusPduCodec {
    /* 31 */
    @Override
    public ModbusFunction decode(ByteBuf buffer) {
        return decodeFunction(buffer);
    }


    public static ModbusFunction decodeFunction(ByteBuf buffer) {
        /* 35 */
        short functionCode = buffer.readUnsignedByte();
        /* 36 */
        ModbusFunction function = ModbusFunctionDecoderUtil.decodeReqFunction(functionCode);
        /* 37 */
        function.decode(buffer);
        /* 38 */
        return function;
    }
}


/* Location:              D:\logs\easymodbus4j-codec-0.0.5.jar!\com\github\zengfr\easymodbus4j\codec\ModbusPduReqCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */