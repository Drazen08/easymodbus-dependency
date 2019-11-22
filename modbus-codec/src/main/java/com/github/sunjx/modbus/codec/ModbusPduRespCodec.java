 package com.github.sunjx.modbus.codec;

 import com.github.zengfr.easymodbus4j.codec.util.ModbusFunctionDecoderUtil;
 import com.github.zengfr.easymodbus4j.protocol.ModbusFunction;
 import io.netty.buffer.ByteBuf;






















 public class ModbusPduRespCodec
   extends ModbusPduCodec
 {
/* 31 */   public ModbusFunction decode(ByteBuf buffer) { return decodeFunction(buffer); }



   public static ModbusFunction decodeFunction(ByteBuf buffer) {
/* 36 */     short functionCode = buffer.readUnsignedByte();
/* 37 */     ModbusFunction function = ModbusFunctionDecoderUtil.decodeRespFunction(functionCode);
/* 38 */     function.decode(buffer);
/* 39 */     return function;
   }
 }


/* Location:              D:\logs\easymodbus4j-codec-0.0.5.jar!\com\github\zengfr\easymodbus4j\codec\ModbusPduRespCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */