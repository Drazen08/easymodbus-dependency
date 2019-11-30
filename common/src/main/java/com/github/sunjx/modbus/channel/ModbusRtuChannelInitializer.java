package com.github.sunjx.modbus.channel;

import com.github.sunjx.modbus.ModbusConfs;
import com.github.sunjx.modbus.codec.ModbusPduReqCodec;
import com.github.sunjx.modbus.codec.ModbusPduRespCodec;
import com.github.sunjx.modbus.codec.rtu.ModbusRtuCodec2;
import com.github.sunjx.modbus.handler.ModbusChannelSyncHandler;
import com.github.sunjx.modbus.protocol.tcp.ModbusFrame;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;


@Sharable
public class ModbusRtuChannelInitializer extends ModbusChannelInitializer {
    public ModbusRtuChannelInitializer(Boolean isSlave, SimpleChannelInboundHandler<ModbusFrame> handler) {
        super(isSlave, handler);
    }


    @Override
    protected void initPipelinePreProcessHandler(ChannelPipeline pipeline) {
        if (this.isSlave) {
            pipeline.addLast("codec", new ModbusRtuCodec2(new ModbusPduReqCodec()));
        } else {
            pipeline.addLast("codec", new ModbusRtuCodec2(new ModbusPduRespCodec()));
        }
    }


    @Override
    protected void initPipelinePostProcessHandler(ChannelPipeline pipeline) {
        if (!this.isSlave) {
            pipeline.addLast("sync", new ModbusChannelSyncHandler(ModbusConfs.SYNC_TRYACQUIRE_TIMEOUT_Millis));
        }
    }
}

