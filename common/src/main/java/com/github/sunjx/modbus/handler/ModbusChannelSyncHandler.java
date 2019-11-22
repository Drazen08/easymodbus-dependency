/*     */ package com.github.sunjx.modbus.handler;
/*     */ 
/*     */ import com.github.zengfr.easymodbus4j.common.util.ScheduledUtil;
/*     */ import com.github.zengfr.easymodbus4j.event.SyncStateEvent;
/*     */ import com.github.zengfr.easymodbus4j.logging.ChannelLogger;
/*     */ import com.google.common.collect.Maps;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelDuplexHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.util.Attribute;
/*     */ import io.netty.util.AttributeKey;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Semaphore;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
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
/*     */ public class ModbusChannelSyncHandler
/*     */   extends ChannelDuplexHandler
/*     */ {
/*  44 */   private static final ChannelLogger log = ChannelLogger.getLogger(ModbusChannelSyncHandler.class);
/*  45 */   private static final TimeUnit unit = TimeUnit.MILLISECONDS;
/*  46 */   private static final Random random = new Random();
/*     */   
/*     */   private Map<Short, Semaphore> semaphoreMap;
/*     */   private Map<Short, AtomicInteger> waitingMap;
/*     */   private int permits;
/*     */   private boolean isAutoAcquire;
/*     */   private boolean isAutoRelease;
/*     */   private long timeoutMillis;
/*     */   
/*  55 */   public ModbusChannelSyncHandler(long timeoutMillis) { this(1, false, false, timeoutMillis); }
/*     */ 
/*     */ 
/*     */   
/*  59 */   public ModbusChannelSyncHandler(boolean isAutoAcquire, boolean isAutoRelease, long timeoutMillis) { this(1, isAutoAcquire, isAutoRelease, timeoutMillis); }
/*     */ 
/*     */   
/*     */   public ModbusChannelSyncHandler(int permits, boolean isAutoAcquire, boolean isAutoRelease, long timeoutMillis) {
/*  63 */     this.permits = permits;
/*  64 */     this.isAutoAcquire = isAutoAcquire;
/*  65 */     this.isAutoRelease = isAutoRelease;
/*  66 */     this.timeoutMillis = timeoutMillis;
/*  67 */     this.semaphoreMap = Maps.newConcurrentMap();
/*  68 */     this.waitingMap = Maps.newConcurrentMap();
/*  69 */     for (Short i = Short.valueOf((short)0); i.shortValue() <= 43; short_1 = i, short_2 = i = Short.valueOf((short)(i.shortValue() + 1))) {
/*  70 */       Short short_2, short_1; this.waitingMap.put(i, new AtomicInteger(0));
/*  71 */       this.semaphoreMap.put(i, new Semaphore(this.permits, true));
/*     */     } 
/*  73 */     Runnable runnable = () -> {
/*  74 */         Set<Short> keys = this.waitingMap.keySet();
/*  75 */         for (Short key : keys) {
/*  76 */           AtomicInteger waiting = this.waitingMap.get(key);
/*  77 */           if (waiting.get() > 0) {
/*  78 */             waiting.decrementAndGet();
/*     */           }
/*     */         } 
/*     */       };
/*  82 */     ScheduledUtil.scheduleWithFixedDelay(runnable, this.timeoutMillis);
/*     */   }
/*     */   
/*     */   public static void fireUserSyncStateEvent(ChannelHandlerContext ctx, Short funcCode, boolean read, boolean write) throws Exception {
/*  86 */     SyncStateEvent evt = new SyncStateEvent(funcCode, read, write);
/*  87 */     ctx.fireUserEventTriggered(evt);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/*  92 */     if (this.isAutoAcquire);
/*     */ 
/*     */     
/*  95 */     super.write(ctx, msg, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 100 */   public void read(ChannelHandlerContext ctx) throws Exception { super.read(ctx); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/* 105 */     log.debug(ctx.channel(), "channelRead", new Object[0]);
/* 106 */     super.channelRead(ctx, msg);
/* 107 */     if (this.isAutoRelease);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
/* 114 */     if (evt instanceof SyncStateEvent) {
/* 115 */       SyncStateEvent event = (SyncStateEvent)evt;
/* 116 */       processSyncStateEvent(ctx.channel(), event);
/*     */     } else {
/*     */       
/* 119 */       super.userEventTriggered(ctx, evt);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void processSyncStateEvent(Channel channel, SyncStateEvent event) {
/* 124 */     if (event.read) {
/* 125 */       tryRelease(channel, event.funcCode);
/*     */     }
/* 127 */     if (event.write) {
/* 128 */       tryAcquire(channel, event.funcCode);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean tryAcquire(Channel channel, Short funcCode) {
/* 133 */     AtomicInteger waiting = this.waitingMap.get(funcCode);
/* 134 */     int waitingCount = waiting.getAndIncrement();
/* 135 */     if (waitingCount < 0) {
/* 136 */       waitingCount = 0;
/* 137 */       waiting.set(waitingCount);
/*     */     } 
/* 139 */     return tryAcquire(channel, funcCode, waitingCount);
/*     */   }
/*     */   
/*     */   protected boolean tryAcquire(Channel channel, Short funcCode, int waitingCount) {
/* 143 */     boolean success = false;
/* 144 */     int key = buildRandom();
/*     */     try {
/* 146 */       showLog(channel, funcCode, key, "tryAcquire:" + waitingCount);
/* 147 */       success = ((Semaphore)this.semaphoreMap.get(funcCode)).tryAcquire(this.timeoutMillis * (waitingCount + 1), unit);
/* 148 */       showLog(channel, funcCode, key, "acquire:" + waitingCount + " " + success);
/* 149 */     } catch (InterruptedException ex) {
/* 150 */       log.error(channel, ex);
/*     */     } 
/* 152 */     return success;
/*     */   }
/*     */   
/*     */   public void tryRelease(Channel channel, Short funcCode) {
/* 156 */     int key = buildRandom();
/* 157 */     AttributeKey<Boolean> attrKey = AttributeKey.valueOf("isFrameIgnored");
/* 158 */     Attribute<Boolean> attr = channel.attr(attrKey);
/* 159 */     Boolean isFrameIgnored = (Boolean)attr.get();
/* 160 */     showLog(channel, funcCode, key, "isFrameIgnored:" + isFrameIgnored);
/* 161 */     if (isFrameIgnored == null || isFrameIgnored.equals(Boolean.valueOf(false))) {
/* 162 */       showLog(channel, funcCode, key, "tryRelease");
/* 163 */       Semaphore semaphore = this.semaphoreMap.get(funcCode);
/* 164 */       if (semaphore.availablePermits() < this.permits) {
/* 165 */         semaphore.release();
/* 166 */         AtomicInteger waiting = this.waitingMap.get(funcCode);
/* 167 */         waiting.decrementAndGet();
/* 168 */         showLog(channel, funcCode, key, "release");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 174 */   private static int buildRandom() { return random.nextInt(); }
/*     */ 
/*     */   
/*     */   private void showLog(Channel channel, Short funcCode, int key, String mothod) {
/* 178 */     Semaphore semaphore = this.semaphoreMap.get(funcCode);
/* 179 */     log.debug(channel, String.format("%s,%s,%s,avail:%s,q:%s", new Object[] { Integer.valueOf(key), funcCode, mothod, Integer.valueOf(semaphore.availablePermits()), Integer.valueOf(semaphore.getQueueLength()) }), new Object[0]);
/*     */   }
/*     */ }


/* Location:              D:\logs\easymodbus4j-0.0.5.jar!\com\github\zengfr\easymodbus4j\com.github.sunjx.modbus.handler\ModbusChannelSyncHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */