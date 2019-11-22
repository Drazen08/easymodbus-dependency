package com.github.sunjx.modbus.server;

import com.github.zengfr.easymodbus4j.channel.ModbusTcpChannelInitializer;
import com.github.zengfr.easymodbus4j.handler.ModbusInboundHandler;
import com.github.zengfr.easymodbus4j.handler.ModbusResponseHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;


public class ModbusServer4TcpMaster
        extends ModbusServer {
    /* 33 */
    public ModbusServer4TcpMaster(int port) {
        super(port);
    }


    /* 38 */
    @Override
    public void setup(ModbusResponseHandler handler) throws Exception {
        super.setup(handler);
    }


    /* 44 */
    @Override
    protected ChannelInitializer<SocketChannel> getChannelInitializer(ModbusInboundHandler handler) {
        return (ChannelInitializer<SocketChannel>) new ModbusTcpChannelInitializer(Boolean.valueOf(false), (SimpleChannelInboundHandler) handler);
    }
}


