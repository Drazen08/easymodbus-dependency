package com.github.sunjx.modbus.client;

import com.github.sunjx.modbus.channel.ModbusTcpChannelInitializer;
import com.github.sunjx.modbus.handler.ModbusInboundHandler;
import com.github.sunjx.modbus.handler.ModbusRequestHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;


public class ModbusClient4TcpSlave extends ModbusClient {
    public ModbusClient4TcpSlave(String host, int port) {
        super(host, port);
    }

    @Override
    public void setup(ModbusRequestHandler handler) throws Exception {
        super.setup(handler);
    }

    @Override
    protected ChannelInitializer<SocketChannel> getChannelInitializer(ModbusInboundHandler handler) {
        return new ModbusTcpChannelInitializer(Boolean.TRUE, handler);
    }
}
