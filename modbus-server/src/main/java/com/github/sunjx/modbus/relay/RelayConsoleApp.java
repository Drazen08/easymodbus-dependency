 package com.github.zengfr.easymodbus4j.relay;

 import io.netty.bootstrap.Bootstrap;
 import io.netty.bootstrap.ServerBootstrap;
 import io.netty.channel.Channel;
 import io.netty.channel.ChannelHandler;
 import io.netty.channel.ChannelInitializer;
 import io.netty.channel.EventLoopGroup;
 import io.netty.channel.nio.NioEventLoopGroup;
 import io.netty.channel.socket.nio.NioServerSocketChannel;
 import io.netty.channel.socket.nio.NioSocketChannel;
 import io.netty.handler.codec.bytes.ByteArrayDecoder;
 import io.netty.handler.codec.bytes.ByteArrayEncoder;
 import io.netty.handler.logging.LogLevel;
 import io.netty.handler.logging.LoggingHandler;
 import java.net.InetSocketAddress;

















 public class RelayConsoleApp
 {
   public static void main(String[] params) {}

   public static void initClient2Client(String host, int clientPort, String host2, int clientPort2) {
/* 39 */     RelayClientChannelInboundHandler handle = new RelayClientChannelInboundHandler(host2, clientPort2);
/* 40 */     initClient(host, clientPort, handle);
   }

   public static void initClient2Server(String host, int clientPort, int serverPort) {
/* 44 */     RelayServerChannelInboundHandler handle = new RelayServerChannelInboundHandler(serverPort);
/* 45 */     initClient(host, clientPort, handle);
   }

   public static void initServer2Server(int serverPort1, int serverPort2) {
/* 49 */     RelayServerChannelInboundHandler handle = new RelayServerChannelInboundHandler(serverPort2);
/* 50 */     initServer(serverPort1, handle);
   }

   public static void initServer2Client(int serverPort, String host, int clientPort) {
/* 54 */     RelayClientChannelInboundHandler handle = new RelayClientChannelInboundHandler(host, clientPort);
/* 55 */     initServer(serverPort, handle);
   }

   protected static void initServer(final int serverPort, final RelayChannelInboundHandler relayHandler) {
/* 59 */     final ServerBootstrap bootstrap = new ServerBootstrap();
/* 60 */     ((ServerBootstrap)bootstrap.group((EventLoopGroup)new NioEventLoopGroup(), (EventLoopGroup)new NioEventLoopGroup()).channel(NioServerSocketChannel.class))
/* 61 */       .childHandler((ChannelHandler)new ChannelInitializer<Channel>()
         {
           protected void initChannel(Channel ch) throws Exception {
/* 64 */             ch.pipeline().addLast("logging", (ChannelHandler)new LoggingHandler(LogLevel.INFO));
/* 65 */             ch.pipeline().addLast(new ChannelHandler[] { (ChannelHandler)new ByteArrayDecoder() });
/* 66 */             ch.pipeline().addLast(new ChannelHandler[] { (ChannelHandler)new ByteArrayEncoder() });
/* 67 */             ch.pipeline().addLast(new ChannelHandler[] { (ChannelHandler)this.val$relayHandler });
/* 68 */             bootstrap.bind(new InetSocketAddress(serverPort)).sync().channel();
           }
         });
   }

   protected static void initClient(final String host, final int clientPort, final RelayChannelInboundHandler relayHandler) {
/* 74 */     final Bootstrap bootstrap = new Bootstrap();
/* 75 */     ((Bootstrap)((Bootstrap)bootstrap.group((EventLoopGroup)new NioEventLoopGroup())).channel(NioSocketChannel.class))
/* 76 */       .handler((ChannelHandler)new ChannelInitializer<Channel>()
         {
           protected void initChannel(Channel ch) throws Exception {
/* 79 */             ch.pipeline().addLast("logging", (ChannelHandler)new LoggingHandler(LogLevel.INFO));
/* 80 */             ch.pipeline().addLast(new ChannelHandler[] { (ChannelHandler)new ByteArrayDecoder() });
/* 81 */             ch.pipeline().addLast(new ChannelHandler[] { (ChannelHandler)new ByteArrayEncoder() });
/* 82 */             ch.pipeline().addLast(new ChannelHandler[] { (ChannelHandler)this.val$relayHandler });
/* 83 */             bootstrap.bind(new InetSocketAddress(host, clientPort)).sync().channel();
           }
         });
   }
 }


/* Location:              D:\logs\easymodbus4j-server-0.0.5.jar!\com\github\zengfr\easymodbus4j\relay\RelayConsoleApp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */