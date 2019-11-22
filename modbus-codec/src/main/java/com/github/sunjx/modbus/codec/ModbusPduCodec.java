 package com.github.sunjx.modbus.codec;

 import com.github.sunjx.modbus.protocol.ModbusFunction;
 import io.netty.buffer.ByteBuf;





















 public abstract class ModbusPduCodec
   implements ModbusCodec<ModbusFunction>
 {
/* 29 */   public ByteBuf encode(ModbusFunction obj) { return obj.encode(); }
 }


/* Location:              D:\logs\easymodbus4j-codec-0.0.5.jar!\com\github\zengfr\easymodbus4j\codec\ModbusPduCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */