package com.github.sunjx.modbus.example.channel;


import com.github.sunjx.modbus.ModbusConfs;
import com.github.sunjx.modbus.channel.ModbusRtuChannelInitializer;
import com.github.sunjx.modbus.handler.ModbusChannelSyncHandler;
import com.github.sunjx.modbus.protocol.tcp.ModbusFrame;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class CustomModbusRtuChannelInitializer extends ModbusRtuChannelInitializer {
    public CustomModbusRtuChannelInitializer(Boolean isSlave, SimpleChannelInboundHandler<ModbusFrame> handler) {
        super(isSlave, handler);
    }

    @Override
    protected void initPipelinePreProcessHandler(ChannelPipeline pipeline) {
        pipeline.addFirst("dtu-reg", new DtuRegDecoder("DTU:"));
        super.initPipelinePreProcessHandler(pipeline);
    }

    @Override
    protected void initPipelinePostProcessHandler(ChannelPipeline pipeline) {
        if (!this.isSlave) {
            pipeline.addLast("sync", new ModbusChannelSyncHandler((long) ModbusConfs.SYNC_TRYACQUIRE_TIMEOUT_Millis));
        }
    }
}

