package com.github.sunjx.modbus.server;

import com.github.sunjx.modbus.channel.ModbusTcpChannelInitializer;
import com.github.sunjx.modbus.handler.ModbusInboundHandler;
import com.github.sunjx.modbus.handler.ModbusRequestHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;


public class ModbusServer4TcpSlave
        extends ModbusServer {
    /* 32 */
    public ModbusServer4TcpSlave(int port) {
        super(port);
    }


    /* 37 */
    @Override
    public void setup(ModbusRequestHandler handler) throws Exception {
        super.setup(handler);
    }


    @Override
    protected ChannelInitializer<SocketChannel> getChannelInitializer(ModbusInboundHandler handler) {
        return (ChannelInitializer<SocketChannel>) new ModbusTcpChannelInitializer(Boolean.valueOf(true), (SimpleChannelInboundHandler) handler);
    }
}


/* Location:              D:\logs\easymodbus4j-server-0.0.5.jar!\com\github\zengfr\easymodbus4j\server\ModbusServer4TcpSlave.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */