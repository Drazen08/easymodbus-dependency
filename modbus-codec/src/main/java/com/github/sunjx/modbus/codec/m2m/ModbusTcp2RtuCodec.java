 package com.github.sunjx.modbus.codec.m2m;

 import com.github.zengfr.easymodbus4j.protocol.rtu.ModbusRtuFrame;
 import com.github.zengfr.easymodbus4j.protocol.tcp.ModbusFrame;
 import io.netty.channel.ChannelHandlerContext;
 import io.netty.handler.codec.MessageToMessageCodec;
 import java.util.List;























 public class ModbusTcp2RtuCodec
   extends MessageToMessageCodec<ModbusFrame, ModbusRtuFrame>
 {
   protected void encode(ChannelHandlerContext ctx, ModbusRtuFrame msg, List<Object> out) throws Exception {
/* 35 */     int transactionId = -1;
/* 36 */     int protocolIdentifier = 0;
/* 37 */     ModbusFrame frame = ModbusFrameConvertor.rtu2Tcp(msg, transactionId, protocolIdentifier);
/* 38 */     out.add(frame);
   }


   protected void decode(ChannelHandlerContext ctx, ModbusFrame msg, List<Object> out) throws Exception {
/* 43 */     ModbusRtuFrame frame = ModbusFrameConvertor.tcp2Rtu(msg);
/* 44 */     out.add(frame);
   }
 }


/* Location:              D:\logs\easymodbus4j-codec-0.0.5.jar!\com\github\zengfr\easymodbus4j\codec\m2m\ModbusTcp2RtuCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */