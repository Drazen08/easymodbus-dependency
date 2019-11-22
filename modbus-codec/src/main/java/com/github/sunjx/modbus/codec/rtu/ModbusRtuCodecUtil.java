 package com.github.sunjx.modbus.codec.rtu;

 import com.github.zengfr.easymodbus4j.util.ModbusFunctionUtil;
 import io.netty.buffer.ByteBuf;
























 public class ModbusRtuCodecUtil
 {
   private static final int MessageMinSize = 5;
   private static final int FunctionFieldIndex = 1;
   private static final int LengthFieldIndex = 2;

/* 35 */   public static int getMessageMinSize() { return 5; }


/* 38 */   private static int getLength(ByteBuf in, int startIndex) { return in.getUnsignedByte(startIndex + 2); }



/* 42 */   private static short getFunctionCode(ByteBuf in, int startIndex) { return in.getUnsignedByte(startIndex + 1); }


   public static int getMessageLength(ByteBuf in, int startIndex) {
     int length;
/* 47 */     short functionCode = getFunctionCode(in, startIndex);

/* 49 */     if (ModbusFunctionUtil.isError(functionCode))
     {
/* 51 */       return 5;
     }
/* 53 */     switch (functionCode) {
       case 1:
       case 2:
       case 3:
       case 4:
/* 58 */         length = getLength(in, startIndex);

/* 60 */         return 3 + length + 2;

       case 5:
       case 6:
       case 15:
       case 16:
/* 66 */         return 8;
     }




/* 72 */     return 0;
   }
 }


/* Location:              D:\logs\easymodbus4j-codec-0.0.5.jar!\com\github\zengfr\easymodbus4j\codec\rtu\ModbusRtuCodecUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */