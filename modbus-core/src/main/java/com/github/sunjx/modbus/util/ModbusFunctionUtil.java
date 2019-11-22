 package com.github.sunjx.modbus.util;

 import com.github.zengfr.easymodbus4j.common.util.ByteUtil;
 import com.github.zengfr.easymodbus4j.protocol.ModbusFunction;
 import io.netty.buffer.ByteBuf;





















 public class ModbusFunctionUtil
 {
/* 29 */   public static boolean isError(short functionCode) { return (functionCode - 128 >= 0); }


   public static byte[] getFunctionValues(ModbusFunction func) {
/* 33 */     if (func.toString().contains("Read")) {
/* 34 */       return getReadReponseFunctionValues(func);
     }
/* 36 */     return getWriteReponseFunctionValues(func);
   }

   protected static byte[] getReadReponseFunctionValues(ModbusFunction func) {
/* 40 */     ByteBuf data = func.encode();
/* 41 */     short funcCode = data.readUnsignedByte();
/* 42 */     short byteCount = data.readUnsignedByte();
/* 43 */     byte[] inputs = new byte[byteCount];
/* 44 */     if (data.readableBytes() > 0)
/* 45 */       data.readBytes(inputs); 
/* 46 */     return inputs;
   }

   protected static byte[] getWriteReponseFunctionValues(ModbusFunction func) {
/* 50 */     ByteBuf data = func.encode();
/* 51 */     short funcCode = data.readUnsignedByte();
/* 52 */     int address = data.readUnsignedShort();
/* 53 */     int value = data.readUnsignedShort();
/* 54 */     return ByteUtil.toByteArrayInt(new int[] { value });
   }
 }


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4\\util\ModbusFunctionUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */