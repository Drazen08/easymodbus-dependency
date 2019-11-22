 package com.github.sunjx.modbus.sender;

 import com.google.common.collect.Maps;
 import io.netty.channel.Channel;
 import java.util.Map;




















 public class ChannelSenderFactory
 {
   private static class ChannelSenderFactoryHolder
   {
/* 30 */     private static final ChannelSenderFactory INSTANCE = new ChannelSenderFactory();
   }


/* 34 */   public static ChannelSenderFactory getInstance() { return INSTANCE; }


/* 37 */   private Map<String, ChannelSender> channelSendersMap = Maps.newConcurrentMap();

   public ChannelSender get(Channel channel) {
/* 40 */     String key = channel.toString();
/* 41 */     if (!this.channelSendersMap.containsKey(key)) {
/* 42 */       this.channelSendersMap.put(key, new ChannelSender(channel));
     }
/* 44 */     return this.channelSendersMap.get(key);
   }
 }


/* Location:              D:\logs\easymodbus4j-0.0.5.jar!\com\github\zengfr\easymodbus4j\com.github.sunjx.modbus.sender\ChannelSenderFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */