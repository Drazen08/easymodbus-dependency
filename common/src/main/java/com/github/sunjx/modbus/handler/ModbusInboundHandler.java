package com.github.sunjx.modbus.handler;

import com.github.sunjx.modbus.channel.ChannelManager;
import com.github.sunjx.modbus.channel.ChannelReconnectable;
import com.github.sunjx.modbus.protocol.tcp.ModbusFrame;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.util.concurrent.TimeUnit;


public abstract class ModbusInboundHandler extends SimpleChannelInboundHandler<ModbusFrame> {

    private static final InternalLogger log = InternalLoggerFactory.getInstance(ModbusInboundHandler.class);
    private ChannelManager channelManager;

    static {
        log.info("https://github.com/zengfr/easymodbus4j");
    }


    /* 44 */
    public void setChannelManager(ChannelManager channelManager) {
        this.channelManager = channelManager;
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        /* 49 */
        if (!ctx.channel().isActive()) {
            /* 50 */
            ctx.channel().close();
        } else {
            /* 52 */
            super.exceptionCaught(ctx, cause);
        }
    }

    /**
     * 连接断开
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (this.channelManager != null) {
            this.channelManager.removeChannel(ctx.channel());
            if (this.channelManager instanceof ChannelReconnectable) {
                final ChannelReconnectable rc = (ChannelReconnectable) this.channelManager;
                EventLoop eventLoop = ctx.channel().eventLoop();
                eventLoop.schedule(new Runnable() {
                    public void run() {
                        rc.reConnect();
                    }
                }, 5L, TimeUnit.SECONDS);
            }
        }
        /* 70 */
        super.channelInactive(ctx);
    }


    /**
     * 创建连接
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        if (this.channelManager != null) {
            this.channelManager.addChannel(ctx.channel());
        }
    }
}
