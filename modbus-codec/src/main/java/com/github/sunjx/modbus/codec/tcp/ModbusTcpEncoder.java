 package com.github.sunjx.modbus.codec.tcp;

 import com.github.zengfr.easymodbus4j.protocol.tcp.ModbusFrame;
 import io.netty.channel.ChannelHandlerContext;
 import io.netty.channel.ChannelOutboundHandlerAdapter;
 import io.netty.channel.ChannelPromise;





















 public class ModbusTcpEncoder
   extends ChannelOutboundHandlerAdapter
 {
   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 32 */     if (msg instanceof ModbusFrame) {
/* 33 */       ModbusFrame frame = (ModbusFrame)msg;
/* 34 */       ctx.writeAndFlush(frame.encode());
     } else {
/* 36 */       ctx.write(msg);
     }
   }
 }


/* Location:              D:\logs\easymodbus4j-codec-0.0.5.jar!\com\github\zengfr\easymodbus4j\codec\tcp\ModbusTcpEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */