package com.github.sunjx.modbus.codec;

import io.netty.buffer.ByteBuf;

public interface ModbusCodec<T> {
  T decode(ByteBuf paramByteBuf);
  
  ByteBuf encode(T paramT);
}


/* Location:              D:\logs\easymodbus4j-codec-0.0.5.jar!\com\github\zengfr\easymodbus4j\codec\ModbusCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */