 package com.github.sunjx.modbus.common.util;

 import io.netty.buffer.ByteBuf;
















 public class ByteBufUtil
 {
   public static byte[] toBytes(ByteBuf msg) {
/* 23 */     ByteBuf buf = msg;
/* 24 */     byte[] req = new byte[buf.readableBytes()];
/* 25 */     buf.readBytes(req);
/* 26 */     return req;
   }
 }


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4j\commo\\util\ByteBufUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */