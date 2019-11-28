 package com.github.sunjx.modbus.relay;

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
     public RelayConsoleApp() {
     }

     public static void main(String[] params) {
     }

     public static void initClient2Client(String host, int clientPort, String host2, int clientPort2) {
         RelayClientChannelInboundHandler handle = new RelayClientChannelInboundHandler(host2, clientPort2);
         initClient(host, clientPort, handle);
     }

     public static void initClient2Server(String host, int clientPort, int serverPort) {
         RelayServerChannelInboundHandler handle = new RelayServerChannelInboundHandler(serverPort);
         initClient(host, clientPort, handle);
     }

     public static void initServer2Server(int serverPort1, int serverPort2) {
         RelayServerChannelInboundHandler handle = new RelayServerChannelInboundHandler(serverPort2);
         initServer(serverPort1, handle);
     }

     public static void initServer2Client(int serverPort, String host, int clientPort) {
         RelayClientChannelInboundHandler handle = new RelayClientChannelInboundHandler(host, clientPort);
         initServer(serverPort, handle);
     }

     protected static void initServer(final int serverPort, final RelayChannelInboundHandler relayHandler) {
         final ServerBootstrap bootstrap = new ServerBootstrap();
         ((ServerBootstrap)bootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup()).channel(NioServerSocketChannel.class)).childHandler(new ChannelInitializer<Channel>() {
             protected void initChannel(Channel ch) throws Exception {
                 ch.pipeline().addLast("logging", new LoggingHandler(LogLevel.INFO));
                 ch.pipeline().addLast(new ChannelHandler[]{new ByteArrayDecoder()});
                 ch.pipeline().addLast(new ChannelHandler[]{new ByteArrayEncoder()});
                 ch.pipeline().addLast(new ChannelHandler[]{relayHandler});
                 bootstrap.bind(new InetSocketAddress(serverPort)).sync().channel();
             }
         });
     }

     protected static void initClient(final String host, final int clientPort, final RelayChannelInboundHandler relayHandler) {
         final Bootstrap bootstrap = new Bootstrap();
         ((Bootstrap)((Bootstrap)bootstrap.group(new NioEventLoopGroup())).channel(NioSocketChannel.class)).handler(new ChannelInitializer<Channel>() {
             protected void initChannel(Channel ch) throws Exception {
                 ch.pipeline().addLast("logging", new LoggingHandler(LogLevel.INFO));
                 ch.pipeline().addLast(new ChannelHandler[]{new ByteArrayDecoder()});
                 ch.pipeline().addLast(new ChannelHandler[]{new ByteArrayEncoder()});
                 ch.pipeline().addLast(new ChannelHandler[]{relayHandler});
                 bootstrap.bind(new InetSocketAddress(host, clientPort)).sync().channel();
             }
         });
     }
 }


/* Location:              D:\logs\easymodbus4j-server-0.0.5.jar!\com\github\zengfr\easymodbus4j\relay\RelayConsoleApp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */