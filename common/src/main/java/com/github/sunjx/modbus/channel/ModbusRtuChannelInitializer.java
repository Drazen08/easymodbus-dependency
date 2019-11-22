 package com.github.sunjx.modbus.channel;

 import com.github.zengfr.easymodbus4j.ModbusConfs;
 import com.github.zengfr.easymodbus4j.codec.ModbusPduCodec;
 import com.github.zengfr.easymodbus4j.codec.ModbusPduReqCodec;
 import com.github.zengfr.easymodbus4j.codec.ModbusPduRespCodec;
 import com.github.zengfr.easymodbus4j.codec.rtu.ModbusRtuCodec2;
 import com.github.zengfr.easymodbus4j.handler.ModbusChannelSyncHandler;
 import com.github.zengfr.easymodbus4j.protocol.tcp.ModbusFrame;
 import io.netty.channel.ChannelHandler;
 import io.netty.channel.ChannelHandler.Sharable;
 import io.netty.channel.ChannelPipeline;
 import io.netty.channel.SimpleChannelInboundHandler;




















 @Sharable
 public class ModbusRtuChannelInitializer
   extends ModbusChannelInitializer
 {
/* 38 */   public ModbusRtuChannelInitializer(Boolean isSlave, SimpleChannelInboundHandler<ModbusFrame> handler) { super(isSlave, handler); }



   protected void initPipelinePreProcessHandler(ChannelPipeline pipeline) {
/* 43 */     if (this.isSlave.booleanValue()) {
/* 44 */       pipeline.addLast("codec", (ChannelHandler)new ModbusRtuCodec2((ModbusPduCodec)new ModbusPduReqCodec()));
     } else {
/* 46 */       pipeline.addLast("codec", (ChannelHandler)new ModbusRtuCodec2((ModbusPduCodec)new ModbusPduRespCodec()));
     }
   }



   protected void initPipelinePostProcessHandler(ChannelPipeline pipeline) {
/* 53 */     if (!this.isSlave.booleanValue())
     {

/* 56 */       pipeline.addLast("sync", (ChannelHandler)new ModbusChannelSyncHandler(ModbusConfs.SYNC_TRYACQUIRE_TIMEOUT_Millis));
     }
   }
 }


/* Location:              D:\logs\easymodbus4j-0.0.5.jar!\com\github\zengfr\easymodbus4j\com.github.sunjx.modbus.channel\ModbusRtuChannelInitializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */