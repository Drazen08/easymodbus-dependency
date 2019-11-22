package com.github.sunjx.modbus.channel;

import com.github.sunjx.modbus.ModbusConfs;
import com.github.sunjx.modbus.codec.ModbusPduCodec;
import com.github.sunjx.modbus.codec.ModbusPduReqCodec;
import com.github.sunjx.modbus.codec.ModbusPduRespCodec;
import com.github.sunjx.modbus.codec.rtu.ModbusRtuCodec2;
import com.github.sunjx.modbus.handler.ModbusChannelSyncHandler;
import com.github.sunjx.modbus.protocol.tcp.ModbusFrame;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;


@Sharable
public class ModbusRtuChannelInitializer
        extends ModbusChannelInitializer {
    /* 38 */
    public ModbusRtuChannelInitializer(Boolean isSlave, SimpleChannelInboundHandler<ModbusFrame> handler) {
        super(isSlave, handler);
    }


    @Override
    protected void initPipelinePreProcessHandler(ChannelPipeline pipeline) {
        /* 43 */
        if (this.isSlave.booleanValue()) {
            /* 44 */
            pipeline.addLast("codec", (ChannelHandler) new ModbusRtuCodec2((ModbusPduCodec) new ModbusPduReqCodec()));
        } else {
            /* 46 */
            pipeline.addLast("codec", (ChannelHandler) new ModbusRtuCodec2((ModbusPduCodec) new ModbusPduRespCodec()));
        }
    }


    @Override
    protected void initPipelinePostProcessHandler(ChannelPipeline pipeline) {
        /* 53 */
        if (!this.isSlave.booleanValue()) {

            /* 56 */
            pipeline.addLast("sync", (ChannelHandler) new ModbusChannelSyncHandler(ModbusConfs.SYNC_TRYACQUIRE_TIMEOUT_Millis));
        }
    }
}

