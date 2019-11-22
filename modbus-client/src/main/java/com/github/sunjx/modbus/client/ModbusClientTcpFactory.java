 package com.github.sunjx.modbus.client;

 import com.github.zengfr.easymodbus4j.channel.ModbusChannelInitializer;
 import com.github.zengfr.easymodbus4j.handler.ModbusRequestHandler;
 import com.github.zengfr.easymodbus4j.handler.ModbusResponseHandler;
 import io.netty.channel.ChannelInitializer;
 import io.netty.channel.socket.SocketChannel;
 import io.netty.util.internal.logging.InternalLogger;
 import io.netty.util.internal.logging.InternalLoggerFactory;



















 public class ModbusClientTcpFactory
 {
   private static class ModbusClientTcpFactoryHolder
   {
/* 33 */     private static final ModbusClientTcpFactory INSTANCE = new ModbusClientTcpFactory();
   }

/* 36 */   protected static final InternalLogger log = InternalLoggerFactory.getInstance(ModbusClientTcpFactory.class);


/* 39 */   public static ModbusClientTcpFactory getInstance() { return INSTANCE; }



/* 43 */   public ModbusClient4TcpSlave createClient4Slave(int port, ModbusRequestHandler handle) throws Exception { return createClient4Slave("127.0.0.1", port, handle); }



   public ModbusClient4TcpSlave createClient4Slave(String hotst, int port, ModbusRequestHandler handle) throws Exception {
/* 48 */     ModbusClient4TcpSlave modbusClient4Slave = new ModbusClient4TcpSlave(hotst, port);
/* 49 */     modbusClient4Slave.setup(handle);
/* 50 */     return modbusClient4Slave;
   }



   public ModbusClient4TcpSlave createClient4Slave(String hotst, int port, ModbusChannelInitializer handle) throws Exception {
/* 56 */     ModbusClient4TcpSlave modbusClient4Slave = new ModbusClient4TcpSlave(hotst, port);
/* 57 */     modbusClient4Slave.setup((ChannelInitializer<SocketChannel>)handle);
/* 58 */     return modbusClient4Slave;
   }


/* 62 */   public ModbusClient4TcpMaster createClient4Master(int port, ModbusResponseHandler handle) throws Exception { return createClient4Master("127.0.0.1", port, handle); }




   public ModbusClient4TcpMaster createClient4Master(String hotst, int port, ModbusResponseHandler handle) throws Exception {
/* 68 */     ModbusClient4TcpMaster modbusClient4Master = new ModbusClient4TcpMaster(hotst, port);
/* 69 */     modbusClient4Master.setup(handle);
/* 70 */     return modbusClient4Master;
   }




   public ModbusClient4TcpMaster createClient4Master(String hotst, int port, ModbusChannelInitializer handle) throws Exception {
/* 77 */     ModbusClient4TcpMaster modbusClient4Master = new ModbusClient4TcpMaster(hotst, port);
/* 78 */     modbusClient4Master.setup((ChannelInitializer<SocketChannel>)handle);
/* 79 */     return modbusClient4Master;
   }
 }


/* Location:              D:\logs\easymodbus4j-client-0.0.5.jar!\com\github\zengfr\easymodbus4j\client\ModbusClientTcpFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */