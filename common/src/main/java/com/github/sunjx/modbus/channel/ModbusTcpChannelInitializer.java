 package com.github.sunjx.modbus.channel;

 import com.github.zengfr.easymodbus4j.codec.ModbusPduCodec;
 import com.github.zengfr.easymodbus4j.codec.ModbusPduReqCodec;
 import com.github.zengfr.easymodbus4j.codec.ModbusPduRespCodec;
 import com.github.zengfr.easymodbus4j.codec.tcp.ModbusTcpCodec;
 import com.github.zengfr.easymodbus4j.codec.tcp.ModbusTcpLFBFrameDecoder;
 import com.github.zengfr.easymodbus4j.protocol.tcp.ModbusFrame;
 import io.netty.channel.ChannelHandler;
 import io.netty.channel.ChannelHandler.Sharable;
 import io.netty.channel.ChannelPipeline;
 import io.netty.channel.SimpleChannelInboundHandler;




















 @Sharable
 public class ModbusTcpChannelInitializer
   extends ModbusChannelInitializer
 {
/* 37 */   public ModbusTcpChannelInitializer(Boolean isSlave, SimpleChannelInboundHandler<ModbusFrame> handler) { super(isSlave, handler); }



   protected void initPipelinePreProcessHandler(ChannelPipeline pipeline) {
/* 42 */     pipeline.addLast("framer", (ChannelHandler)new ModbusTcpLFBFrameDecoder(true));
/* 43 */     if (this.isSlave.booleanValue()) {
/* 44 */       pipeline.addLast("codec", (ChannelHandler)new ModbusTcpCodec((ModbusPduCodec)new ModbusPduReqCodec()));
     } else {
/* 46 */       pipeline.addLast("codec", (ChannelHandler)new ModbusTcpCodec((ModbusPduCodec)new ModbusPduRespCodec()));
     }
   }

   protected void initPipelinePostProcessHandler(ChannelPipeline pipeline) {}
 }


/* Location:              D:\logs\easymodbus4j-0.0.5.jar!\com\github\zengfr\easymodbus4j\com.github.sunjx.modbus.channel\ModbusTcpChannelInitializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */