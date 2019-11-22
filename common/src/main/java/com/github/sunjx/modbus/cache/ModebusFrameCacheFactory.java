 package com.github.sunjx.modbus.cache;

 import io.netty.util.internal.logging.InternalLogger;
 import io.netty.util.internal.logging.InternalLoggerFactory;
 import java.util.Map;
 import java.util.concurrent.ConcurrentHashMap;



 public class ModebusFrameCacheFactory
 {
   private static class ModebusFrameCacheFactoryHolder
   {
/* 31 */     private static final ModebusFrameCacheFactory INSTANCE = new ModebusFrameCacheFactory();
   }

/* 34 */   protected static final InternalLogger log = InternalLoggerFactory.getInstance(ModebusFrameCacheFactory.class);


/* 37 */   public static ModebusFrameCacheFactory getInstance() { return INSTANCE; }


/* 40 */   private final Map<String, ModebusFrameCache> cacheMap = new ConcurrentHashMap<>();

   protected ModebusFrameCache getCache(String key) {
/* 43 */     if (!this.cacheMap.containsKey(key)) {
/* 44 */       synchronized (this.cacheMap) {
/* 45 */         if (!this.cacheMap.containsKey(key)) {
/* 46 */           this.cacheMap.put(key, new ModebusFrameCache());
         }
       }
     }
/* 50 */     return this.cacheMap.get(key);
   }


/* 54 */   public ModebusFrameCache getRequestCache(Short funcCode) { return getCache("req" + funcCode); }



/* 58 */   public ModebusFrameCache getResponseCache(Short funcCode) { return getCache("resp" + funcCode); }
 }


/* Location:              D:\logs\easymodbus4j-0.0.5.jar!\com\github\zengfr\easymodbus4j\com.github.sunjx.modbus.cache\ModebusFrameCacheFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */