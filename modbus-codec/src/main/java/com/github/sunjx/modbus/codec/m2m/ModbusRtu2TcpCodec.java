 package com.github.sunjx.modbus.codec.m2m;

 import com.github.zengfr.easymodbus4j.protocol.rtu.ModbusRtuFrame;
 import com.github.zengfr.easymodbus4j.protocol.tcp.ModbusFrame;
 import io.netty.channel.ChannelHandlerContext;
 import io.netty.handler.codec.MessageToMessageCodec;
 import java.util.List;























 public class ModbusRtu2TcpCodec
   extends MessageToMessageCodec<ModbusRtuFrame, ModbusFrame>
 {
   protected void encode(ChannelHandlerContext ctx, ModbusFrame msg, List<Object> out) throws Exception {
/* 35 */     ModbusRtuFrame frame = ModbusFrameConvertor.tcp2Rtu(msg);
/* 36 */     out.add(frame);
   }


   protected void decode(ChannelHandlerContext ctx, ModbusRtuFrame msg, List<Object> out) throws Exception {
/* 41 */     int transactionId = -1;
/* 42 */     int protocolIdentifier = 0;
/* 43 */     ModbusFrame frame = ModbusFrameConvertor.rtu2Tcp(msg, transactionId, protocolIdentifier);
/* 44 */     out.add(frame);
   }
 }


/* Location:              D:\logs\easymodbus4j-codec-0.0.5.jar!\com\github\zengfr\easymodbus4j\codec\m2m\ModbusRtu2TcpCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */