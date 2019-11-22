 package com.github.sunjx.modbus.util;

 import com.github.zengfr.easymodbus4j.protocol.tcp.ModbusFrame;
 import io.netty.channel.Channel;
 import java.util.concurrent.atomic.AtomicInteger;





















 public class ModbusTransactionIdUtil
 {
/* 29 */   private static AtomicInteger lastTransactionId = new AtomicInteger();

   public static int calculateTransactionId() {
/* 32 */     if (lastTransactionId.get() >= 64300) {
/* 33 */       lastTransactionId.set(0);
     }
/* 35 */     return lastTransactionId.incrementAndGet();
   }

   public static int getTransactionId(ModbusFrame frame) {
/* 39 */     int transactionIdentifier = frame.getHeader().getTransactionIdentifier();
/* 40 */     return transactionIdentifier;
   }

   public static int getTransactionIdByLocal(Channel channel) {
/* 44 */     int transactionIdentifier = channel.localAddress().toString().hashCode();
/* 45 */     return transactionIdentifier;
   }

   public static int getTransactionIdByRemote(Channel channel) {
/* 49 */     int transactionIdentifier = channel.remoteAddress().toString().hashCode();
/* 50 */     return transactionIdentifier;
   }
 }


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4\\util\ModbusTransactionIdUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */