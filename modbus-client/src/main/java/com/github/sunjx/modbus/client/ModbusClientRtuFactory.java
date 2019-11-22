package com.github.sunjx.modbus.client;

import com.github.sunjx.modbus.channel.ModbusChannelInitializer;
import com.github.sunjx.modbus.handler.ModbusRequestHandler;
import com.github.sunjx.modbus.handler.ModbusResponseHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;


public class ModbusClientRtuFactory {
    private static class ModbusClientRtuFactoryHolder {
         private static final ModbusClientRtuFactory INSTANCE = new ModbusClientRtuFactory();
    }

    /* 36 */   protected static final InternalLogger log = InternalLoggerFactory.getInstance(ModbusClientRtuFactory.class);


    /* 39 */
    public static ModbusClientRtuFactory getInstance() {
        return ModbusClientRtuFactoryHolder.INSTANCE;
    }


    /* 43 */
    public ModbusClient4RtuSlave createClient4Slave(int port, ModbusRequestHandler handle) throws Exception {
        return createClient4Slave("127.0.0.1", port, handle);
    }


    public ModbusClient4RtuSlave createClient4Slave(String hotst, int port, ModbusRequestHandler handle) throws Exception {
        /* 48 */
        ModbusClient4RtuSlave modbusClient4Slave = new ModbusClient4RtuSlave(hotst, port);
        /* 49 */
        modbusClient4Slave.setup(handle);
        /* 50 */
        return modbusClient4Slave;
    }


    public ModbusClient4RtuSlave createClient4Slave(String hotst, int port, ModbusChannelInitializer handle) throws Exception {
        /* 56 */
        ModbusClient4RtuSlave modbusClient4Slave = new ModbusClient4RtuSlave(hotst, port);
        /* 57 */
        modbusClient4Slave.setup((ChannelInitializer<SocketChannel>) handle);
        /* 58 */
        return modbusClient4Slave;
    }


    /* 62 */
    public ModbusClient4RtuMaster createClient4Master(int port, ModbusResponseHandler handle) throws Exception {
        return createClient4Master("127.0.0.1", port, handle);
    }


    public ModbusClient4RtuMaster createClient4Master(String hotst, int port, ModbusResponseHandler handle) throws Exception {
        /* 68 */
        ModbusClient4RtuMaster modbusClient4Master = new ModbusClient4RtuMaster(hotst, port);
        /* 69 */
        modbusClient4Master.setup(handle);
        /* 70 */
        return modbusClient4Master;
    }


    public ModbusClient4RtuMaster createClient4Master(String hotst, int port, ModbusChannelInitializer handle) throws Exception {
        /* 77 */
        ModbusClient4RtuMaster modbusClient4Master = new ModbusClient4RtuMaster(hotst, port);
        /* 78 */
        modbusClient4Master.setup((ChannelInitializer<SocketChannel>) handle);
        /* 79 */
        return modbusClient4Master;
    }
}

