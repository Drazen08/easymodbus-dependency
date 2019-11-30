package com.github.sunjx.modbus.handler;

import com.github.sunjx.modbus.logging.ChannelLogger;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.SocketAddress;


@Sharable
public class ModbusLoggingHandler
        extends LoggingHandler {
    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        super.connect(ctx, remoteAddress, localAddress, promise);
    }

    public ModbusLoggingHandler() {
    }

    public ModbusLoggingHandler(LogLevel logLevel) {
        super(logLevel);
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ChannelLogger.putMDC(ctx.channel());
        super.channelRead(ctx, msg);
    }
    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        ChannelLogger.putMDC(ctx.channel());
        super.read(ctx);
    }
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        ChannelLogger.putMDC(ctx.channel());
        super.write(ctx, msg, promise);
    }
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        ChannelLogger.putMDC(ctx.channel());
        super.channelRegistered(ctx);
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ChannelLogger.putMDC(ctx.channel());
        super.channelActive(ctx);
    }
}

