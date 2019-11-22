 package com.github.zengfr.easymodbus4j.server;

 import com.github.zengfr.easymodbus4j.channel.ModbusChannelInitializer;
 import com.github.zengfr.easymodbus4j.handler.ModbusRequestHandler;
 import com.github.zengfr.easymodbus4j.handler.ModbusResponseHandler;
 import io.netty.channel.ChannelInitializer;
 import io.netty.channel.socket.SocketChannel;
 import io.netty.util.internal.logging.InternalLogger;
 import io.netty.util.internal.logging.InternalLoggerFactory;

















 public class ModbusServerTcpFactory
 {
   private static class ModbusServerTcpFactoryHolder
   {
/* 31 */     private static final ModbusServerTcpFactory INSTANCE = new ModbusServerTcpFactory();
   }

/* 34 */   protected static final InternalLogger log = InternalLoggerFactory.getInstance(ModbusServerTcpFactory.class);


/* 37 */   public static ModbusServerTcpFactory getInstance() { return INSTANCE; }



   public ModbusServer4TcpSlave createServer4Slave(int port, ModbusRequestHandler handle) throws Exception {
/* 42 */     ModbusServer4TcpSlave modbusServer4Slave = new ModbusServer4TcpSlave(port);
/* 43 */     modbusServer4Slave.setup(handle);
/* 44 */     return modbusServer4Slave;
   }

   public ModbusServer4TcpSlave createServer4Slave(int port, ModbusChannelInitializer handle) throws Exception {
/* 48 */     ModbusServer4TcpSlave modbusServer4Slave = new ModbusServer4TcpSlave(port);
/* 49 */     modbusServer4Slave.setup((ChannelInitializer<SocketChannel>)handle);
/* 50 */     return modbusServer4Slave;
   }


   public ModbusServer4TcpMaster createServer4Master(int port, ModbusResponseHandler handle) throws Exception {
/* 55 */     ModbusServer4TcpMaster modbusServer4Master = new ModbusServer4TcpMaster(port);
/* 56 */     modbusServer4Master.setup(handle);
/* 57 */     return modbusServer4Master;
   }


   public ModbusServer4TcpMaster createServer4Master(int port, ModbusChannelInitializer handle) throws Exception {
/* 62 */     ModbusServer4TcpMaster modbusServer4Master = new ModbusServer4TcpMaster(port);
/* 63 */     modbusServer4Master.setup((ChannelInitializer<SocketChannel>)handle);
/* 64 */     return modbusServer4Master;
   }
 }


/* Location:              D:\logs\easymodbus4j-server-0.0.5.jar!\com\github\zengfr\easymodbus4j\server\ModbusServerTcpFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */