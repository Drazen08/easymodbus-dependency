 package com.github.sunjx.modbus.queue;

 import java.util.concurrent.Delayed;
 import java.util.concurrent.TimeUnit;

 public class DelayedItem<T> implements Delayed {
   private T item;

   public DelayedItem(T item, long delay) {
/* 10 */     this.item = item;
/* 11 */     this.timeout += System.nanoTime();
   }
   private long timeout;

/* 15 */   public T getItem() { return this.item; }



   public int compareTo(Delayed delayed) {
/* 20 */     if (delayed == this)
/* 21 */       return 0; 
/* 22 */     DelayedItem<T> t = (DelayedItem<T>)delayed;
/* 23 */     long d = getDelay(TimeUnit.NANOSECONDS) - t.getDelay(TimeUnit.NANOSECONDS);
/* 24 */     return (d == 0L) ? 0 : ((d < 0L) ? -1 : 1);
   }




/* 30 */   public long getDelay(TimeUnit unit) { return unit.convert(this.timeout - System.nanoTime(), TimeUnit.NANOSECONDS); }
 }


/* Location:              D:\logs\easymodbus4j-0.0.5.jar!\com\github\zengfr\easymodbus4j\com.github.sunjx.modbus.queue\DelayedItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */