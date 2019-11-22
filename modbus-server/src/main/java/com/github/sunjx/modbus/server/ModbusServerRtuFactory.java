package com.github.sunjx.modbus.server;

import com.github.sunjx.modbus.channel.ModbusChannelInitializer;
import com.github.sunjx.modbus.handler.ModbusRequestHandler;
import com.github.sunjx.modbus.handler.ModbusResponseHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;


public class ModbusServerRtuFactory {
    private static class ModbusServerRtuFactoryHolder {
        /* 31 */     private static final ModbusServerRtuFactory INSTANCE = new ModbusServerRtuFactory();
    }

    /* 34 */   protected static final InternalLogger log = InternalLoggerFactory.getInstance(ModbusServerRtuFactory.class);


    /* 37 */
    public static ModbusServerRtuFactory getInstance() {
        return ModbusServerRtuFactoryHolder.INSTANCE;
    }


    public ModbusServer4RtuSlave createServer4Slave(int port, ModbusRequestHandler handle) throws Exception {
        /* 42 */
        ModbusServer4RtuSlave modbusServer4Slave = new ModbusServer4RtuSlave(port);
        /* 43 */
        modbusServer4Slave.setup(handle);
        /* 44 */
        return modbusServer4Slave;
    }

    public ModbusServer4RtuSlave createServer4Slave(int port, ModbusChannelInitializer handle) throws Exception {
        /* 48 */
        ModbusServer4RtuSlave modbusServer4Slave = new ModbusServer4RtuSlave(port);
        /* 49 */
        modbusServer4Slave.setup((ChannelInitializer<SocketChannel>) handle);
        /* 50 */
        return modbusServer4Slave;
    }


    public ModbusServer4RtuMaster createServer4Master(int port, ModbusResponseHandler handle) throws Exception {
        /* 55 */
        ModbusServer4RtuMaster modbusServer4Master = new ModbusServer4RtuMaster(port);
        /* 56 */
        modbusServer4Master.setup(handle);
        /* 57 */
        return modbusServer4Master;
    }


    public ModbusServer4RtuMaster createServer4Master(int port, ModbusChannelInitializer handle) throws Exception {
        /* 62 */
        ModbusServer4RtuMaster modbusServer4Master = new ModbusServer4RtuMaster(port);
        /* 63 */
        modbusServer4Master.setup((ChannelInitializer<SocketChannel>) handle);
        /* 64 */
        return modbusServer4Master;
    }
}


/* Location:              D:\logs\easymodbus4j-server-0.0.5.jar!\com\github\zengfr\easymodbus4j\server\ModbusServerRtuFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */