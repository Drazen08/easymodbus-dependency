package com.github.sunjx.modbus.relay;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioServerSocketChannel;


public class RelayServerChannelInboundHandler
        extends RelayChannelInboundHandler {
    /* 28 */
    public RelayServerChannelInboundHandler(int port) {
        super(port);
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        /* 33 */
        ServerBootstrap bootstrap = new ServerBootstrap();
        /* 34 */
        ((ServerBootstrap) bootstrap.channel(NioServerSocketChannel.class)).handler((ChannelHandler) new SimpleChannelInboundHandler<ByteBuf>() {
            protected void channelRead0(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
                /* 37 */
                RelayServerChannelInboundHandler.this.innerCtx = ctx;
            }
        });
        /* 40 */
        bootstrap.group((EventLoopGroup) ctx.channel().eventLoop());
        /* 41 */
        this.connectFuture = bootstrap.bind(this.port);
    }
}


/* Location:              D:\logs\easymodbus4j-server-0.0.5.jar!\com\github\zengfr\easymodbus4j\relay\RelayServerChannelInboundHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */