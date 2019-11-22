
package com.github.sunjx.modbus.channel;


import com.github.sunjx.modbus.ModbusConfs;
import com.github.sunjx.modbus.ModbusConsts;
import com.github.sunjx.modbus.codec.ping.ModbusPingDecoder;
import com.github.sunjx.modbus.codec.ping.ModbusPingEncoder;
import com.github.sunjx.modbus.handler.ModbusHeartbeatHandler;
import com.github.sunjx.modbus.handler.ModbusLoggingHandler;
import com.github.sunjx.modbus.protocol.tcp.ModbusFrame;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.nio.charset.Charset;


@Sharable
public abstract class ModbusChannelInitializer
        extends ChannelInitializer<SocketChannel> {
    /*  44 */   private static final InternalLogger log = InternalLoggerFactory.getInstance(ModbusChannelInitializer.class);
    private final SimpleChannelInboundHandler<ModbusFrame> handler;
    protected final Boolean isSlave;


    public ModbusChannelInitializer(Boolean isSlave, SimpleChannelInboundHandler<ModbusFrame> handler) {
        /*  49 */
        this.isSlave = isSlave;
        /*  50 */
        this.handler = handler;

    }


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        /*  55 */
        log.info(String.format("initChannel->isSlave:%s", new Object[]{this.isSlave}));
        /*  56 */
        if (ch.localAddress() != null || ch.remoteAddress() != null) {
            /*  57 */
            log.info(String.format("initChannel->%s,%s", new Object[]{ch.localAddress(), ch.remoteAddress()}));

        }
        /*  59 */
        ChannelPipeline pipeline = ch.pipeline();
        /*  60 */
        initPipeline4Logging(pipeline);
        /*  61 */
        initPipeline4Ping(pipeline);

        /*  63 */
        initPipelinePreProcessHandler(pipeline);
        /*  64 */
        initPipeline4ProcessHandler(pipeline);
        /*  65 */
        initPipelinePostProcessHandler(pipeline);

        /*  67 */
        initPipeline4HeartbeatIdle(pipeline);

    }


    protected void initPipeline4Logging(ChannelPipeline pipeline) {
        /*  73 */
        if (isShowDebugLog(this.isSlave.booleanValue())) {
            /*  74 */
            pipeline.addLast("logging", (ChannelHandler) new ModbusLoggingHandler(LogLevel.DEBUG));

        }

    }


    protected void initPipeline4Ping(ChannelPipeline pipeline) {
        /*  79 */
        Charset charset = Charset.forName("UTF-8");
        /*  80 */
        pipeline.addLast("pingDecoder", (ChannelHandler) new ModbusPingDecoder(ModbusConsts.HEARTBEAT, charset));
        /*  81 */
        pipeline.addLast("pingEncoder", (ChannelHandler) new ModbusPingEncoder(charset));

    }


    protected void initPipeline4ProcessHandler(ChannelPipeline pipeline) {
        /*  85 */
        if (this.handler != null) {
            /*  86 */
            if (this.isSlave.booleanValue()) {
                /*  87 */
                pipeline.addLast("requestHandler", (ChannelHandler) this.handler);

            } else {
                /*  89 */
                pipeline.addLast("responseHandler", (ChannelHandler) this.handler);

            }

        }

    }


    protected void initPipeline4HeartbeatIdle(ChannelPipeline pipeline) {
        /*  95 */
        int timeOut = getIdleTimeout();
        /*  96 */
        if (timeOut > 1) {



            /* 100 */
            pipeline.addLast("idleStateHandler", (ChannelHandler) new IdleStateHandler(timeOut, 0, 0));
            /* 101 */
            pipeline.addLast("heartbeatHandler", (ChannelHandler) new ModbusHeartbeatHandler(this.isSlave.booleanValue()));

        }

    }


    /* 106 */
    protected static int getIdleTimeout() {
        return ModbusConfs.IDLE_TIMEOUT_SECOND;
    }


    /* 110 */
    protected static boolean isShowDebugLog(boolean isSlave) {
        return ((isSlave && ModbusConfs.SLAVE_SHOW_DEBUG_LOG) || (ModbusConfs.MASTER_SHOW_DEBUG_LOG && !isSlave));
    }


    protected abstract void initPipelinePreProcessHandler(ChannelPipeline paramChannelPipeline);


    protected abstract void initPipelinePostProcessHandler(ChannelPipeline paramChannelPipeline);

}
