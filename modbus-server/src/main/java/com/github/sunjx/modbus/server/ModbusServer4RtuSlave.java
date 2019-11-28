package com.github.sunjx.modbus.server;

import com.github.sunjx.modbus.channel.ModbusRtuChannelInitializer;
import com.github.sunjx.modbus.handler.ModbusInboundHandler;
import com.github.sunjx.modbus.handler.ModbusRequestHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;


public class ModbusServer4RtuSlave
        extends ModbusServer {
    /* 32 */
    public ModbusServer4RtuSlave(int port) {
        super(port);
    }


    /* 37 */
    @Override
    public void setup(ModbusRequestHandler handler) throws Exception {
        super.setup(handler);
    }


    /* 43 */
    @Override
    protected ChannelInitializer<SocketChannel> getChannelInitializer(ModbusInboundHandler handler) {
        return (ChannelInitializer<SocketChannel>) new ModbusRtuChannelInitializer(Boolean.valueOf(true), (SimpleChannelInboundHandler) handler);
    }
}


/* Location:              D:\logs\easymodbus4j-server-0.0.5.jar!\com\github\zengfr\easymodbus4j\server\ModbusServer4RtuSlave.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */