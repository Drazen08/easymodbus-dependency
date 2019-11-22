/*     */ package com.github.sunjx.modbus.sender;
/*     */ 
/*     */ import com.github.zengfr.easymodbus4j.ModbusConsts;
/*     */ import com.github.zengfr.easymodbus4j.cache.ModebusFrameCacheFactory;
/*     */ import com.github.zengfr.easymodbus4j.handler.ModbusChannelSyncHandler;
/*     */ import com.github.zengfr.easymodbus4j.handler.ModbusResponseHandler;
/*     */ import com.github.zengfr.easymodbus4j.protocol.ModbusFunction;
/*     */ import com.github.zengfr.easymodbus4j.protocol.tcp.ModbusFrame;
/*     */ import com.github.zengfr.easymodbus4j.protocol.tcp.ModbusHeader;
/*     */ import com.github.zengfr.easymodbus4j.util.ModbusTransactionIdUtil;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */
/*     */ import io.netty.util.concurrent.GenericFutureListener;
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
/*     */ 
/*     */ public abstract class AbstractChannelSender
/*     */ {
/*     */   private short protocolIdentifier;
/*     */   private short unitIdentifier;
/*     */   private Channel channel;
/*     */   
/*  44 */   public Channel getChannel() { return this.channel; }
/*     */ 
/*     */ 
/*     */   
/*  48 */   public AbstractChannelSender(Channel channel) { this(channel, ModbusConsts.DEFAULT_UNIT_IDENTIFIER, (short)0); }
/*     */ 
/*     */ 
/*     */   
/*  52 */   public AbstractChannelSender(Channel channel, short unitIdentifier) { this(channel, unitIdentifier, (short)0); }
/*     */ 
/*     */   
/*     */   public AbstractChannelSender(Channel channel, short unitId, short protocolIdentifier) {
/*  56 */     this.channel = channel;
/*  57 */     this.unitIdentifier = unitId;
/*  58 */     this.protocolIdentifier = protocolIdentifier;
/*     */   }
/*     */   
/*     */   protected int callModbusFunction(ModbusFunction function) throws NullPointerException {
/*  62 */     if (getChannel() == null) {
/*  63 */       throw new NullPointerException("com/github/sunjx/modbus/channel");
/*     */     }
/*  65 */     ModbusFrame frame = buildModbusFrame(function);
/*  66 */     return sendModbusFrame(frame);
/*     */   }
/*     */   
/*     */   protected ModbusFrame buildModbusFrame(ModbusFunction function) {
/*  70 */     ModbusHeader header = buildModbusHeader(function);
/*  71 */     return new ModbusFrame(header, function);
/*     */   }
/*     */   
/*     */   protected ModbusHeader buildModbusHeader(ModbusFunction function) {
/*  75 */     int transactionId = ModbusTransactionIdUtil.calculateTransactionId();
/*  76 */     int pduLength = function.calculateLength();
/*  77 */     ModbusHeader header = new ModbusHeader(transactionId, this.protocolIdentifier, pduLength, this.unitIdentifier);
/*  78 */     return header;
/*     */   }
/*     */   
/*     */   public int sendModbusFrame(final ModbusFrame frame) {
/*  82 */     final Short funcCode = Short.valueOf(frame.getFunction().getFunctionCode());
/*  83 */     ModebusFrameCacheFactory.getInstance().getRequestCache(funcCode).put(frame);
/*  84 */     final int transactionId = ModbusTransactionIdUtil.getTransactionIdByRemote(this.channel);
/*  85 */     ChannelFutureListener channelFutureListener = new ChannelFutureListener()
/*     */       {
/*     */         public void operationComplete(ChannelFuture channelFuture) throws Exception {
/*  88 */           if (channelFuture.isSuccess()) {
/*  89 */             ModebusFrameCacheFactory.getInstance().getRequestCache(funcCode).put(Integer.valueOf(transactionId), frame);
/*     */           } else {
/*  91 */             channelFuture.channel().close();
/*     */           } 
/*     */         }
/*     */       };
/*  95 */     Channel channel = getChannel();
/*  96 */     channelSyncTryAcquire(channel, funcCode);
/*  97 */     channel.writeAndFlush(frame).addListener((GenericFutureListener)channelFutureListener);
/*     */     
/*  99 */     return (frame.getHeader().getTransactionIdentifier() < 0) ? transactionId : frame.getHeader().getTransactionIdentifier();
/*     */   }
/*     */ 
/*     */   
/* 103 */   protected boolean isChannelSync(Channel channel) { return (channel.pipeline().get("sync") != null); }
/*     */ 
/*     */   
/*     */   protected void channelSyncTryAcquire(Channel channel, Short funcCode) {
/* 107 */     if (isChannelSync(channel)) {
/* 108 */       ModbusChannelSyncHandler handler = (ModbusChannelSyncHandler)channel.pipeline().get("sync");
/* 109 */       handler.tryAcquire(channel, funcCode);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected <V extends ModbusFunction> V callModbusFunctionSync(ModbusFunction function) throws Exception {
/* 114 */     int transactionId = callModbusFunction(function);
/* 115 */     short funcCode = function.getFunctionCode();
/* 116 */     return (V)getResponseHandler(getChannel()).getResponseCache(transactionId, funcCode).getFunction();
/*     */   }
/*     */ 
/*     */   
/*     */   protected ModbusResponseHandler getResponseHandler(Channel channel) {
/* 121 */     ModbusResponseHandler handler = (ModbusResponseHandler)channel.pipeline().get("responseHandler");
/* 122 */     if (handler == null) {
/* 123 */       throw new NullPointerException("com/github/sunjx/modbus/handler");
/*     */     }
/* 125 */     return handler;
/*     */   }
/*     */ }


/* Location:              D:\logs\easymodbus4j-0.0.5.jar!\com\github\zengfr\easymodbus4j\com.github.sunjx.modbus.sender\AbstractChannelSender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */