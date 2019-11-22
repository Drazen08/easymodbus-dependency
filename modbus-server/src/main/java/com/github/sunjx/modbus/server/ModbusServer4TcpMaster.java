 package com.github.zengfr.easymodbus4j.server;

 import com.github.zengfr.easymodbus4j.channel.ModbusTcpChannelInitializer;
 import com.github.zengfr.easymodbus4j.handler.ModbusInboundHandler;
 import com.github.zengfr.easymodbus4j.handler.ModbusResponseHandler;
 import io.netty.channel.ChannelInitializer;
 import io.netty.channel.SimpleChannelInboundHandler;
 import io.netty.channel.socket.SocketChannel;





















 public class ModbusServer4TcpMaster
   extends ModbusServer
 {
/* 33 */   public ModbusServer4TcpMaster(int port) { super(port); }




/* 38 */   public void setup(ModbusResponseHandler handler) throws Exception { super.setup(handler); }





/* 44 */   protected ChannelInitializer<SocketChannel> getChannelInitializer(ModbusInboundHandler handler) { return (ChannelInitializer<SocketChannel>)new ModbusTcpChannelInitializer(Boolean.valueOf(false), (SimpleChannelInboundHandler)handler); }
 }


/* Location:              D:\logs\easymodbus4j-server-0.0.5.jar!\com\github\zengfr\easymodbus4j\server\ModbusServer4TcpMaster.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */