package com.github.sunjx.modbus.example.server;

import com.github.sunjx.modbus.example.channel.CustomModbusRtuChannelInitializer;
import com.github.sunjx.modbus.handler.ModbusInboundHandler;
import com.github.sunjx.modbus.handler.ModbusResponseHandler;
import com.github.sunjx.modbus.server.ModbusServer4RtuMaster;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class CustomableModbusServer4RtuMaster extends ModbusServer4RtuMaster {
    public CustomableModbusServer4RtuMaster(int port) {
        super(port);
    }


    @Override
    public void setup(ModbusResponseHandler handler) throws Exception {
        super.setup(handler);
    }

    @Override
    protected ChannelInitializer<SocketChannel> getChannelInitializer(ModbusInboundHandler handler) {
        return new CustomModbusRtuChannelInitializer(false, handler);
    }
}
