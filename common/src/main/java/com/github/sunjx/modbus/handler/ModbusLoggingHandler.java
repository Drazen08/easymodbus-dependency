 package com.github.sunjx.modbus.handler;

 import com.github.zengfr.easymodbus4j.logging.ChannelLogger;
 import io.netty.channel.ChannelHandler.Sharable;
 import io.netty.channel.ChannelHandlerContext;
 import io.netty.channel.ChannelPromise;
 import io.netty.handler.logging.LogLevel;
 import io.netty.handler.logging.LoggingHandler;
 import java.net.SocketAddress;






 @Sharable
 public class ModbusLoggingHandler
   extends LoggingHandler
 {
/* 20 */   public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception { super.connect(ctx, remoteAddress, localAddress, promise); }



   public ModbusLoggingHandler() {}



/* 28 */   public ModbusLoggingHandler(LogLevel logLevel) { super(logLevel); }



   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/* 33 */     ChannelLogger.putMDC(ctx.channel());
/* 34 */     super.channelRead(ctx, msg);
   }


   public void read(ChannelHandlerContext ctx) throws Exception {
/* 39 */     ChannelLogger.putMDC(ctx.channel());
/* 40 */     super.read(ctx);
   }

   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 44 */     ChannelLogger.putMDC(ctx.channel());
/* 45 */     super.write(ctx, msg, promise);
   }

   public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
/* 49 */     ChannelLogger.putMDC(ctx.channel());
/* 50 */     super.channelRegistered(ctx);
   }


   public void channelActive(ChannelHandlerContext ctx) throws Exception {
/* 55 */     ChannelLogger.putMDC(ctx.channel());
/* 56 */     super.channelActive(ctx);
   }
 }


/* Location:              D:\logs\easymodbus4j-0.0.5.jar!\com\github\zengfr\easymodbus4j\com.github.sunjx.modbus.handler\ModbusLoggingHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */