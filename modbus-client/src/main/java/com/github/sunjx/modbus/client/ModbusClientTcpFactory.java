package com.github.sunjx.modbus.client;

import com.github.sunjx.modbus.channel.ModbusChannelInitializer;
import com.github.sunjx.modbus.handler.ModbusRequestHandler;
import com.github.sunjx.modbus.handler.ModbusResponseHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;


public class ModbusClientTcpFactory {
    private static class ModbusClientTcpFactoryHolder {
        private static final ModbusClientTcpFactory INSTANCE = new ModbusClientTcpFactory();
    }

    protected static final InternalLogger log = InternalLoggerFactory.getInstance(ModbusClientTcpFactory.class);

    public static ModbusClientTcpFactory getInstance() {
        return ModbusClientTcpFactoryHolder.INSTANCE;
    }

    public ModbusClient4TcpSlave createClient4Slave(int port, ModbusRequestHandler handle) throws Exception {
        return createClient4Slave("127.0.0.1", port, handle);
    }


    public ModbusClient4TcpSlave createClient4Slave(String hotst, int port, ModbusRequestHandler handle) throws Exception {
        ModbusClient4TcpSlave modbusClient4Slave = new ModbusClient4TcpSlave(hotst, port);
        modbusClient4Slave.setup(handle);
        return modbusClient4Slave;
    }


    public ModbusClient4TcpSlave createClient4Slave(String hotst, int port, ModbusChannelInitializer handle) throws Exception {
        ModbusClient4TcpSlave modbusClient4Slave = new ModbusClient4TcpSlave(hotst, port);
        modbusClient4Slave.setup(handle);
        return modbusClient4Slave;
    }


    public ModbusClient4TcpMaster createClient4Master(int port, ModbusResponseHandler handle) throws Exception {
        return createClient4Master("127.0.0.1", port, handle);
    }


    public ModbusClient4TcpMaster createClient4Master(String hotst, int port, ModbusResponseHandler handle) throws Exception {
        ModbusClient4TcpMaster modbusClient4Master = new ModbusClient4TcpMaster(hotst, port);
        modbusClient4Master.setup(handle);
        return modbusClient4Master;
    }


    public ModbusClient4TcpMaster createClient4Master(String hotst, int port, ModbusChannelInitializer handle) throws Exception {
        ModbusClient4TcpMaster modbusClient4Master = new ModbusClient4TcpMaster(hotst, port);
        modbusClient4Master.setup(handle);
        return modbusClient4Master;
    }
}
