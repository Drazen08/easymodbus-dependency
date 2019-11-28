package com.github.sunjx.modbus.client;

import com.github.sunjx.modbus.channel.ModbusTcpChannelInitializer;
import com.github.sunjx.modbus.handler.ModbusInboundHandler;
import com.github.sunjx.modbus.handler.ModbusResponseHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;


public class ModbusClient4TcpMaster
        extends ModbusClient {
    /* 32 */
    public ModbusClient4TcpMaster(String host, int port) {
        super(host, port);
    }


    /* 37 */
    @Override
    public void setup(ModbusResponseHandler handler) throws Exception {
        super.setup(handler);
    }


    /* 43 */
    @Override
    protected ChannelInitializer<SocketChannel> getChannelInitializer(ModbusInboundHandler handler) {
        return (ChannelInitializer<SocketChannel>) new ModbusTcpChannelInitializer(Boolean.valueOf(false), (SimpleChannelInboundHandler) handler);
    }
}


/* Location:              D:\logs\easymodbus4j-client-0.0.5.jar!\com\github\zengfr\easymodbus4j\client\ModbusClient4TcpMaster.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */