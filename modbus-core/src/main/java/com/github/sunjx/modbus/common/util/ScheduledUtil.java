 package com.github.sunjx.modbus.common.util;

 import io.netty.util.concurrent.DefaultThreadFactory;
 import java.util.concurrent.Executors;
 import java.util.concurrent.ScheduledExecutorService;
 import java.util.concurrent.ScheduledFuture;
 import java.util.concurrent.ThreadFactory;
 import java.util.concurrent.TimeUnit;






















 public class ScheduledUtil
 {
   private static ScheduledExecutorService scheduledExecutorService;

   static  {
/* 36 */     DefaultThreadFactory defaultThreadFactory = new DefaultThreadFactory("com.github.sunjx.modbus.schedule");
/* 37 */     scheduledExecutorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() * 2 - 1, (ThreadFactory)defaultThreadFactory);
   }


/* 41 */   public static ScheduledFuture<?> schedule(Runnable runnable, long milliseconds) { return scheduledExecutorService.schedule(runnable, milliseconds, TimeUnit.MILLISECONDS); }





/* 47 */   public static ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long milliseconds) { return scheduledExecutorService.scheduleAtFixedRate(runnable, milliseconds, milliseconds, TimeUnit.MILLISECONDS); }





/* 53 */   public static ScheduledFuture<?> scheduleWithFixedDelay(Runnable runnable, long milliseconds) { return scheduledExecutorService.scheduleWithFixedDelay(runnable, milliseconds, milliseconds, TimeUnit.MILLISECONDS); }
 }


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4j\commo\\util\ScheduledUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */