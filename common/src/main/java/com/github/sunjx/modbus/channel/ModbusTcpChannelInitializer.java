package com.github.sunjx.modbus.channel;

import com.github.sunjx.modbus.codec.ModbusPduCodec;
import com.github.sunjx.modbus.codec.ModbusPduReqCodec;
import com.github.sunjx.modbus.codec.ModbusPduRespCodec;
import com.github.sunjx.modbus.codec.tcp.ModbusTcpCodec;
import com.github.sunjx.modbus.codec.tcp.ModbusTcpLFBFrameDecoder;
import com.github.sunjx.modbus.protocol.tcp.ModbusFrame;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;


@Sharable
public class ModbusTcpChannelInitializer
        extends ModbusChannelInitializer {

    public ModbusTcpChannelInitializer(Boolean isSlave, SimpleChannelInboundHandler<ModbusFrame> handler) {
        super(isSlave, handler);
    }


    @Override
    protected void initPipelinePreProcessHandler(ChannelPipeline pipeline) {
        pipeline.addLast("framer", new ModbusTcpLFBFrameDecoder(true));
        if (this.isSlave) {
            pipeline.addLast("codec", new ModbusTcpCodec(new ModbusPduReqCodec()));
        } else {
            pipeline.addLast("codec", new ModbusTcpCodec(new ModbusPduRespCodec()));
        }
    }

    @Override
    protected void initPipelinePostProcessHandler(ChannelPipeline pipeline) {
    }
}

