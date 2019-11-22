 package com.github.sunjx.modbus.handle.impl;

 import com.github.zengfr.easymodbus4j.func.AbstractRequest;
 import com.github.zengfr.easymodbus4j.handler.ModbusResponseHandler;
 import com.github.zengfr.easymodbus4j.logging.ChannelLogger;
 import com.github.zengfr.easymodbus4j.processor.ModbusMasterResponseProcessor;
 import com.github.zengfr.easymodbus4j.protocol.ModbusFunction;
 import com.github.zengfr.easymodbus4j.protocol.tcp.ModbusFrame;
 import com.github.zengfr.easymodbus4j.util.ModbusFrameUtil;
 import io.netty.channel.Channel;
 import io.netty.channel.ChannelHandler.Sharable;



















 @Sharable
 public class ModbusMasterResponseHandler
   extends ModbusResponseHandler
 {
/* 35 */   private static final ChannelLogger log = ChannelLogger.getLogger(ModbusMasterResponseHandler.class);

   private ModbusMasterResponseProcessor processor;

/* 39 */   public short getTransactionIdentifierOffset() { return this.processor.getTransactionIdentifierOffset(); }

   public ModbusMasterResponseHandler(ModbusMasterResponseProcessor processor) {
/* 42 */     super(true);
/* 43 */     this.processor = processor;
   }


   protected boolean processResponseFrame(Channel channel, ModbusFrame frame) {
/* 48 */     if (this.processor.isShowFrameDetail()) {
/* 49 */       ModbusFrameUtil.showFrameLog(log, channel, frame, true);
     }
/* 51 */     return super.processResponseFrame(channel, frame);
   }



/* 56 */   protected int getReqTransactionIdByRespTransactionId(int respTransactionIdentifierOffset) { return respTransactionIdentifierOffset - getTransactionIdentifierOffset(); }



/* 60 */   protected int getRespTransactionIdByReqTransactionId(int reqTransactionIdentifier) { return reqTransactionIdentifier + getTransactionIdentifierOffset(); }



   public ModbusFrame getResponseCache(int reqTransactionIdentifier, short funcCode) throws Exception {
/* 65 */     int respTransactionIdentifier = getRespTransactionIdByReqTransactionId(reqTransactionIdentifier);
/* 66 */     return super.getResponseCache(respTransactionIdentifier, funcCode);
   }



/* 71 */   protected boolean processResponseFrame(Channel channel, int unitId, AbstractRequest reqFunc, ModbusFunction respFunc) { return this.processor.processResponseFrame(channel, unitId, reqFunc, respFunc); }
 }


/* Location:              D:\logs\easymodbus4j-extension-0.0.5.jar!\com\github\zengfr\easymodbus4j\handle\impl\ModbusMasterResponseHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.2
 */