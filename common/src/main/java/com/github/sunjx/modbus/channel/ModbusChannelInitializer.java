
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


/**
 * @author my
 */
@Sharable
public abstract class ModbusChannelInitializer extends ChannelInitializer<SocketChannel> {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(ModbusChannelInitializer.class);
    private final SimpleChannelInboundHandler<ModbusFrame> handler;
    protected final Boolean isSlave;


    public ModbusChannelInitializer(Boolean isSlave, SimpleChannelInboundHandler<ModbusFrame> handler) {
        this.isSlave = isSlave;
        this.handler = handler;
    }


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        log.info(String.format("initChannel->isSlave:%s", new Object[]{this.isSlave}));
        if (ch.localAddress() != null || ch.remoteAddress() != null) {
            log.info(String.format("initChannel->%s,%s", new Object[]{ch.localAddress(), ch.remoteAddress()}));
        }
        ChannelPipeline pipeline = ch.pipeline();
        initPipeline4Logging(pipeline);
        initPipeline4Ping(pipeline);
        initPipelinePreProcessHandler(pipeline);
        initPipeline4ProcessHandler(pipeline);
        initPipelinePostProcessHandler(pipeline);
        initPipeline4HeartbeatIdle(pipeline);

    }


    protected void initPipeline4Logging(ChannelPipeline pipeline) {
        if (isShowDebugLog(this.isSlave)) {
            pipeline.addLast("logging", new ModbusLoggingHandler(LogLevel.DEBUG));
        }
    }


    protected void initPipeline4Ping(ChannelPipeline pipeline) {
        Charset charset = Charset.forName("UTF-8");
        pipeline.addLast("pingDecoder",  new ModbusPingDecoder(ModbusConsts.HEARTBEAT, charset));
        pipeline.addLast("pingEncoder",  new ModbusPingEncoder(charset));

    }


    protected void initPipeline4ProcessHandler(ChannelPipeline pipeline) {
        if (this.handler != null) {
            if (this.isSlave) {
                pipeline.addLast("requestHandler",  this.handler);
            } else {
                pipeline.addLast("responseHandler",  this.handler);
            }
        }
    }


    protected void initPipeline4HeartbeatIdle(ChannelPipeline pipeline) {
        int timeOut = getIdleTimeout();
        if (timeOut > 1) {
            pipeline.addLast("idleStateHandler",  new IdleStateHandler(timeOut, 0, 0));
            pipeline.addLast("heartbeatHandler",  new ModbusHeartbeatHandler(this.isSlave));
        }

    }

    protected static int getIdleTimeout() {
        return ModbusConfs.IDLE_TIMEOUT_SECOND;
    }

    protected static boolean isShowDebugLog(boolean isSlave) {
        return ((isSlave && ModbusConfs.SLAVE_SHOW_DEBUG_LOG) || (ModbusConfs.MASTER_SHOW_DEBUG_LOG && !isSlave));
    }

    protected abstract void initPipelinePreProcessHandler(ChannelPipeline paramChannelPipeline);

    protected abstract void initPipelinePostProcessHandler(ChannelPipeline paramChannelPipeline);

}
