package com.github.sunjx.modbus.handler;

import com.github.sunjx.modbus.ModbusConsts;
import com.github.sunjx.modbus.logging.ChannelLogger;
import com.google.common.collect.Maps;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.Map;


@Sharable
public class ModbusHeartbeatHandler
        extends ChannelInboundHandlerAdapter {
    /*  40 */   private static final ChannelLogger log = ChannelLogger.getLogger(ModbusHeartbeatHandler.class);
    /*  41 */   private static final ByteBuf HEARTBEAT = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer(ModbusConsts.HEARTBEAT, CharsetUtil.UTF_8));
    /*  42 */   private Map<String, Integer> heartbeatIdleCounter = Maps.newConcurrentMap();

    private boolean isSlave = false;

    /*  46 */
    public ModbusHeartbeatHandler(boolean isSlave) {
        this.isSlave = isSlave;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /*  51 */
        String key = "" + ctx.channel().remoteAddress();
        /*  52 */
        this.heartbeatIdleCounter.put(key, Integer.valueOf(0));
        /*  53 */
        log.debug(ctx.channel(), String.format("channelRead heartbeatIdle:%s", new Object[]{this.heartbeatIdleCounter.get(key)}), new Object[0]);
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        /*  59 */
        if (evt instanceof IdleStateEvent) {
            /*  60 */
            IdleStateEvent event = (IdleStateEvent) evt;
            /*  61 */
            if (!this.isSlave) {
                /*  62 */
                handleIdleStateEvent4Master(ctx, event);
            } else {
                /*  64 */
                handleIdleStateEvent4Salve(ctx, event);
            }
        } else {
            /*  67 */
            super.userEventTriggered(ctx, evt);
        }
    }

    protected void handleIdleStateEvent4Master(ChannelHandlerContext ctx, IdleStateEvent event) {
        /*  72 */
        IdleState state = event.state();
        /*  73 */
        switch (state) {
            case READER_IDLE:
                /*  75 */
                handleIdle4Master(ctx, state);
                break;
            case WRITER_IDLE:
                /*  78 */
                handleIdle4Master(ctx, state);
                break;
            case ALL_IDLE:
                /*  81 */
                handleIdle4Master(ctx, state);
                break;
        }
    }


    protected void handleIdleStateEvent4Salve(ChannelHandlerContext ctx, IdleStateEvent event) {
        /*  89 */
        String key = "" + ctx.channel().remoteAddress();
        /*  90 */
        if (!this.heartbeatIdleCounter.containsKey(key)) {
            /*  91 */
            this.heartbeatIdleCounter.put(key, Integer.valueOf(0));
        } else {
            /*  93 */
            this.heartbeatIdleCounter.put(key, Integer.valueOf(((Integer) this.heartbeatIdleCounter.get(key)).intValue() + 1));
        }
        /*  95 */
        if (((Integer) this.heartbeatIdleCounter.get(key)).intValue() >= 3) {
            /*  96 */
            IdleState state = event.state();
            /*  97 */
            switch (state) {
                case READER_IDLE:
                    /*  99 */
                    handleIdle4Slave(ctx, state);
                    break;
                case WRITER_IDLE:
                    /* 102 */
                    handleIdle4Slave(ctx, state);
                    break;
                case ALL_IDLE:
                    /* 105 */
                    handleIdle4Slave(ctx, state);
                    break;
            }
        }
    }


    protected void handleIdle4Master(ChannelHandlerContext ctx, IdleState state) {
        /* 113 */
        log.debug(ctx.channel(), String.format("idle4Master:%s", new Object[]{state}), new Object[0]);
        /* 114 */
        ctx.writeAndFlush(HEARTBEAT.duplicate()).addListener((GenericFutureListener) ChannelFutureListener.CLOSE_ON_FAILURE);
    }

    protected void handleIdle4Slave(ChannelHandlerContext ctx, IdleState state) {
        /* 118 */
        String key = "" + ctx.channel().remoteAddress();
        /* 119 */
        log.debug(ctx.channel(), String.format("idle4Slave:%s,%s", new Object[]{state, this.heartbeatIdleCounter.get(key)}), new Object[0]);
        /* 120 */
        ctx.channel().close();
    }
}

