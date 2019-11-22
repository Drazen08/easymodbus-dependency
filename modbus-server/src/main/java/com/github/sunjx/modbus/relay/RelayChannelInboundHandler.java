 package com.github.zengfr.easymodbus4j.relay;

 import io.netty.buffer.ByteBuf;
 import io.netty.channel.ChannelFuture;
 import io.netty.channel.ChannelHandlerContext;
 import io.netty.channel.SimpleChannelInboundHandler;















 public abstract class RelayChannelInboundHandler
   extends SimpleChannelInboundHandler<ByteBuf>
 {
   protected ChannelHandlerContext innerCtx;
   protected ChannelFuture connectFuture;
/* 27 */   protected int port = 9090;

/* 29 */   public RelayChannelInboundHandler(int port) { this.port = port; }


   protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
/* 33 */     if (this.connectFuture.isDone() && 
/* 34 */       this.innerCtx != null && this.innerCtx.channel().isActive())
/* 35 */       this.innerCtx.writeAndFlush(msg); 
   }
 }


/* Location:              D:\logs\easymodbus4j-server-0.0.5.jar!\com\github\zengfr\easymodbus4j\relay\RelayChannelInboundHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */