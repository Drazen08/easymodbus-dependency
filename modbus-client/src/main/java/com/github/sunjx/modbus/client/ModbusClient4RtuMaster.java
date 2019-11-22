package com.github.sunjx.modbus.client;

import com.github.sunjx.modbus.channel.ModbusRtuChannelInitializer;
import com.github.sunjx.modbus.handler.ModbusInboundHandler;
import com.github.sunjx.modbus.handler.ModbusResponseHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;


public class ModbusClient4RtuMaster
        extends ModbusClient {
    public ModbusClient4RtuMaster(String host, int port) {
        super(host, port);
    }


    @Override
    public void setup(ModbusResponseHandler handler) throws Exception {
        super.setup(handler);
    }


    @Override
    protected ChannelInitializer<SocketChannel> getChannelInitializer(ModbusInboundHandler handler) {
        return (ChannelInitializer<SocketChannel>) new ModbusRtuChannelInitializer(Boolean.valueOf(false), (SimpleChannelInboundHandler) handler);
    }
}

