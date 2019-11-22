 package com.github.sunjx.modbus.client;

 import com.github.zengfr.easymodbus4j.channel.ModbusRtuChannelInitializer;
 import com.github.zengfr.easymodbus4j.handler.ModbusInboundHandler;
 import com.github.zengfr.easymodbus4j.handler.ModbusRequestHandler;
 import io.netty.channel.ChannelInitializer;
 import io.netty.channel.SimpleChannelInboundHandler;
 import io.netty.channel.socket.SocketChannel;




















 public class ModbusClient4RtuSlave
   extends ModbusClient
 {
/* 32 */   public ModbusClient4RtuSlave(String host, int port) { super(host, port); }




/* 37 */   public void setup(ModbusRequestHandler handler) throws Exception { super.setup(handler); }





/* 43 */   protected ChannelInitializer<SocketChannel> getChannelInitializer(ModbusInboundHandler handler) { return (ChannelInitializer<SocketChannel>)new ModbusRtuChannelInitializer(Boolean.valueOf(true), (SimpleChannelInboundHandler)handler); }
 }


/* Location:              D:\logs\easymodbus4j-client-0.0.5.jar!\com\github\zengfr\easymodbus4j\client\ModbusClient4RtuSlave.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */