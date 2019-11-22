 package com.github.sunjx.modbus.sender;

 import java.util.concurrent.CountDownLatch;
 import java.util.concurrent.Future;
 import java.util.concurrent.TimeUnit;

 public class SyncFuture<T>
   implements Future<T> {
/*  9 */   private CountDownLatch latch = new CountDownLatch(1);

   private T response;

/* 13 */   private long beginTime = System.currentTimeMillis();




/* 18 */   public boolean cancel(boolean mayInterruptIfRunning) { return false; }



/* 22 */   public boolean isCancelled() { return false; }


   public boolean isDone() {
/* 26 */     if (this.response != null) {
/* 27 */       return true;
     }
/* 29 */     return false;
   }


   public T get() throws InterruptedException {
/* 34 */     this.latch.await();
/* 35 */     return this.response;
   }


   public T get(long timeout, TimeUnit unit) throws InterruptedException {
/* 40 */     if (this.latch.await(timeout, unit)) {
/* 41 */       return this.response;
     }
/* 43 */     return null;
   }

   public void setResponse(T response) {
/* 47 */     this.response = response;
/* 48 */     this.latch.countDown();
   }

/* 51 */   public long getBeginTime() { return this.beginTime; }
 }


/* Location:              D:\logs\easymodbus4j-0.0.5.jar!\com\github\zengfr\easymodbus4j\com.github.sunjx.modbus.sender\SyncFuture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */