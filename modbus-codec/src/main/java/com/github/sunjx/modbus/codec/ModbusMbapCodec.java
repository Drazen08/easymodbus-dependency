 package com.github.sunjx.modbus.codec;

 import com.github.zengfr.easymodbus4j.protocol.tcp.ModbusHeader;
 import io.netty.buffer.ByteBuf;





















 public class ModbusMbapCodec
   implements ModbusCodec<ModbusHeader>
 {
/* 29 */   public ModbusHeader decode(ByteBuf buffer) { return ModbusHeader.decode(buffer); }




/* 34 */   public ByteBuf encode(ModbusHeader obj) { return obj.encode(); }
 }


/* Location:              D:\logs\easymodbus4j-codec-0.0.5.jar!\com\github\zengfr\easymodbus4j\codec\ModbusMbapCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */