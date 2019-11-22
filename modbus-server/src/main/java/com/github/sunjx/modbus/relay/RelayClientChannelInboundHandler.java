 package com.github.zengfr.easymodbus4j.relay;

 import io.netty.bootstrap.Bootstrap;
 import io.netty.buffer.ByteBuf;
 import io.netty.channel.ChannelHandler;
 import io.netty.channel.ChannelHandlerContext;
 import io.netty.channel.EventLoopGroup;
 import io.netty.channel.SimpleChannelInboundHandler;
 import io.netty.channel.socket.nio.NioSocketChannel;
 import java.net.InetSocketAddress;














 public class RelayClientChannelInboundHandler
   extends RelayChannelInboundHandler
 {
   protected String host;

   public RelayClientChannelInboundHandler(String host, int port) {
/* 31 */     super(port);
/* 32 */     this.host = host;
   }


   public void channelActive(ChannelHandlerContext ctx) throws Exception {
/* 37 */     Bootstrap bootstrap = new Bootstrap();
/* 38 */     ((Bootstrap)bootstrap.channel(NioSocketChannel.class)).handler((ChannelHandler)new SimpleChannelInboundHandler<ByteBuf>()
         {
           protected void channelRead0(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
/* 41 */             RelayClientChannelInboundHandler.this.innerCtx = ctx;
           }
         });
/* 44 */     bootstrap.group((EventLoopGroup)ctx.channel().eventLoop());
/* 45 */     this.connectFuture = bootstrap.connect(new InetSocketAddress(this.host, this.port));
   }
 }


/* Location:              D:\logs\easymodbus4j-server-0.0.5.jar!\com\github\zengfr\easymodbus4j\relay\RelayClientChannelInboundHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */