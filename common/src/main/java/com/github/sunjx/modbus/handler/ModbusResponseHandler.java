/*     */ package com.github.sunjx.modbus.handler;
/*     */ 
/*     */ import com.github.zengfr.easymodbus4j.ModbusConfs;
/*     */ import com.github.zengfr.easymodbus4j.cache.ModebusFrameCache;
/*     */ import com.github.zengfr.easymodbus4j.cache.ModebusFrameCacheFactory;
/*     */ import com.github.zengfr.easymodbus4j.event.SyncStateEvent;
/*     */ import com.github.zengfr.easymodbus4j.func.AbstractRequest;
/*     */ import com.github.zengfr.easymodbus4j.func.response.ErrorFunctionResponse;
/*     */ import com.github.zengfr.easymodbus4j.logging.ChannelLogger;
/*     */ import com.github.zengfr.easymodbus4j.protocol.ModbusFunction;
/*     */ import com.github.zengfr.easymodbus4j.protocol.tcp.ModbusFrame;
/*     */ import com.github.zengfr.easymodbus4j.util.ModbusTransactionIdUtil;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.util.Attribute;
/*     */ import io.netty.util.AttributeKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ModbusResponseHandler
/*     */   extends ModbusInboundHandler
/*     */ {
/*  41 */   private static final ChannelLogger log = ChannelLogger.getLogger(ModbusResponseHandler.class);
/*     */   private boolean isCacheResponse = false;
/*  43 */   private int responseFrameIgnoreLengthThreshold = 0;
/*     */ 
/*     */   
/*  46 */   public ModbusResponseHandler(boolean isCacheResponse) { this(isCacheResponse, ModbusConfs.RESPONS_EFRAME_IGNORE_LENGTH_THRESHOLD); }
/*     */ 
/*     */   
/*     */   public ModbusResponseHandler(boolean isCacheResponse, int responseFrameIgnoreLengthThreshold) {
/*  50 */     this.isCacheResponse = isCacheResponse;
/*  51 */     this.responseFrameIgnoreLengthThreshold = responseFrameIgnoreLengthThreshold;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void channelRead0(ChannelHandlerContext ctx, ModbusFrame response) throws Exception {
/*  56 */     log.debug(ctx.channel(), "channelRead0", new Object[0]);
/*  57 */     cacheResponse(response);
/*  58 */     boolean isFrameIgnored = isFrameIgnored(response);
/*     */     
/*  60 */     AttributeKey<Boolean> key = AttributeKey.valueOf("isFrameIgnored");
/*  61 */     Attribute<Boolean> attr = ctx.channel().attr(key);
/*  62 */     attr.set(Boolean.valueOf(isFrameIgnored));
/*     */     
/*  64 */     if (!isFrameIgnored) {
/*  65 */       boolean success = processResponseFrame(ctx.channel(), response);
/*  66 */       if (success) {
/*  67 */         ctx.fireUserEventTriggered(new SyncStateEvent(Short.valueOf(response.getFunction().getFunctionCode()), true, false));
/*     */       } else {
/*  69 */         log.debug(ctx.channel(), "success:" + success, new Object[0]);
/*     */       } 
/*     */     } else {
/*  72 */       log.debug(ctx.channel(), "isFrameIgnored:" + isFrameIgnored, new Object[0]);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean isFrameIgnored(ModbusFrame response) {
/*  77 */     int ignoreLengthThreshold = this.responseFrameIgnoreLengthThreshold;
/*  78 */     return (ignoreLengthThreshold > 0 && response.getHeader().getLength() > ignoreLengthThreshold);
/*     */   }
/*     */   
/*     */   protected void cacheResponse(ModbusFrame response) {
/*  82 */     if (this.isCacheResponse && response != null) {
/*  83 */       Short funcCode = Short.valueOf(response.getFunction().getFunctionCode());
/*  84 */       int respTransactionIdentifier = response.getHeader().getTransactionIdentifier();
/*  85 */       ModebusFrameCacheFactory.getInstance().getResponseCache(funcCode).put(Integer.valueOf(respTransactionIdentifier), response);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean processResponseFrame(Channel channel, ModbusFrame respFrame) {
/*  90 */     boolean success = false;
/*  91 */     int respTransactionIdentifier = respFrame.getHeader().getTransactionIdentifier();
/*  92 */     int reqTransactionIdentifier = getReqTransactionIdByRespTransactionId(respTransactionIdentifier);
/*  93 */     if (respTransactionIdentifier < 0 || reqTransactionIdentifier < 0) {
/*  94 */       respTransactionIdentifier = reqTransactionIdentifier = ModbusTransactionIdUtil.getTransactionIdByRemote(channel);
/*     */     }
/*  96 */     short funcCode = respFrame.getFunction().getFunctionCode();
/*  97 */     ModbusFrame reqFrame = getRequestCache(reqTransactionIdentifier, funcCode);
/*  98 */     AbstractRequest reqFunc = null;
/*  99 */     if (reqFrame != null) {
/* 100 */       reqFunc = (AbstractRequest)reqFrame.getFunction();
/*     */     } else {
/* 102 */       log.warn(channel, String.format("req is null:%s;%s;%s;%s", new Object[] { Integer.valueOf(reqTransactionIdentifier), Integer.valueOf(respTransactionIdentifier), getRequestCache(funcCode).keySet(), respFrame }), new Object[0]);
/*     */     } 
/* 104 */     short unitId = respFrame.getHeader().getUnitIdentifier();
/* 105 */     ModbusFunction respFunc = respFrame.getFunction();
/* 106 */     if (reqFrame == null || reqFrame.getHeader().getUnitIdentifier() == unitId) {
/* 107 */       success = processResponseFrame(channel, unitId, reqFunc, respFunc);
/* 108 */       if (success) {
/* 109 */         removeRequestCache(reqTransactionIdentifier, funcCode);
/*     */       }
/*     */     } 
/* 112 */     return success;
/*     */   }
/*     */ 
/*     */   
/* 116 */   protected ModebusFrameCache getRequestCache(short funcCode) { return ModebusFrameCacheFactory.getInstance().getRequestCache(Short.valueOf(funcCode)); }
/*     */ 
/*     */   
/*     */   protected ModbusFrame getRequestCache(int reqTransactionIdentifier, short funcCode) {
/* 120 */     ModbusFrame reqFrame = getRequestCache(funcCode).get(Integer.valueOf(reqTransactionIdentifier));
/* 121 */     return reqFrame;
/*     */   }
/*     */ 
/*     */   
/* 125 */   protected void removeRequestCache(int reqTransactionIdentifier, short funcCode) { getRequestCache(funcCode).remove(Integer.valueOf(reqTransactionIdentifier)); }
/*     */ 
/*     */ 
/*     */   
/* 129 */   protected ModebusFrameCache getResponseCache(short funcCode) { return ModebusFrameCacheFactory.getInstance().getResponseCache(Short.valueOf(funcCode)); }
/*     */ 
/*     */   
/*     */   public ModbusFrame getResponseCache(int respTransactionIdentifier, short funcCode) throws Exception {
/*     */     ModbusFrame frame;
/* 134 */     long timeoutTime = System.currentTimeMillis() + ModbusConfs.SYNC_RESPONSE_TIMEOUT;
/*     */     
/* 136 */     ModebusFrameCache responseCache = getResponseCache(funcCode);
/*     */     do {
/* 138 */       frame = responseCache.get(Integer.valueOf(respTransactionIdentifier));
/* 139 */       if (frame != null)
/* 140 */         continue;  Thread.sleep(22L);
/*     */     }
/* 142 */     while (frame == null && timeoutTime - System.currentTimeMillis() > 0L);
/* 143 */     long size = responseCache.size();
/* 144 */     if (frame != null) {
/* 145 */       responseCache.remove(Integer.valueOf(respTransactionIdentifier));
/*     */     }
/* 147 */     if (frame == null)
/* 148 */       throw new Exception(String.format("resp is null!(%s,%s,%s,%s)", new Object[] { Integer.valueOf(ModbusConfs.SYNC_RESPONSE_TIMEOUT), Integer.valueOf(respTransactionIdentifier), Long.valueOf(size), responseCache.keySet() })); 
/* 149 */     if (frame.getFunction() instanceof ErrorFunctionResponse) {
/* 150 */       throw new Exception("" + ((ErrorFunctionResponse)frame.getFunction()).getExceptionMessage());
/*     */     }
/* 152 */     return frame;
/*     */   }
/*     */   
/*     */   protected abstract int getReqTransactionIdByRespTransactionId(int paramInt);
/*     */   
/*     */   protected abstract boolean processResponseFrame(Channel paramChannel, int paramInt, AbstractRequest paramAbstractRequest, ModbusFunction paramModbusFunction);
/*     */ }


/* Location:              D:\logs\easymodbus4j-0.0.5.jar!\com\github\zengfr\easymodbus4j\com.github.sunjx.modbus.handler\ModbusResponseHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */