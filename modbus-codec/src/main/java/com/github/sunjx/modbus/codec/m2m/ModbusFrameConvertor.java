 package com.github.sunjx.modbus.codec.m2m;

 import com.github.zengfr.easymodbus4j.protocol.rtu.ModbusRtuFrame;
 import com.github.zengfr.easymodbus4j.protocol.tcp.ModbusFrame;
 import com.github.zengfr.easymodbus4j.protocol.tcp.ModbusHeader;



















 public class ModbusFrameConvertor
 {
   public static ModbusFrame rtu2Tcp(ModbusRtuFrame frame, int transactionId, int protocolIdentifier) {
/* 28 */     int pduLength = frame.getFunction().calculateLength();
/* 29 */     short unitId = frame.getUnitId();
/* 30 */     ModbusHeader header = new ModbusHeader(transactionId, protocolIdentifier, pduLength, unitId);
/* 31 */     ModbusFrame f = new ModbusFrame(header, frame.getFunction());
/* 32 */     return f;
   }

   public static ModbusRtuFrame tcp2Rtu(ModbusFrame frame) {
/* 36 */     short unitId = frame.getHeader().getUnitIdentifier();
/* 37 */     ModbusRtuFrame f = new ModbusRtuFrame(unitId, frame.getFunction());
/* 38 */     return f;
   }
 }


/* Location:              D:\logs\easymodbus4j-codec-0.0.5.jar!\com\github\zengfr\easymodbus4j\codec\m2m\ModbusFrameConvertor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */