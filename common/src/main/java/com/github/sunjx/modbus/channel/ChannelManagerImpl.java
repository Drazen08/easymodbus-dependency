 package com.github.sunjx.modbus.channel;

 import com.google.common.collect.Maps;
 import io.netty.channel.Channel;
 import io.netty.channel.group.ChannelGroup;
 import io.netty.channel.group.DefaultChannelGroup;
 import io.netty.util.concurrent.EventExecutor;
 import io.netty.util.concurrent.GlobalEventExecutor;
 import java.util.Collection;
 import java.util.Map;


















 public class ChannelManagerImpl
   implements ChannelManager
 {
/* 32 */   protected Map<String, Channel> channels = Maps.newConcurrentMap();
/* 33 */   protected ChannelGroup clientChannels = (ChannelGroup)new DefaultChannelGroup(getClass().getSimpleName(), (EventExecutor)GlobalEventExecutor.INSTANCE);




/* 38 */   public String getKey(Channel channel) { return channel.remoteAddress().toString(); }



   public void removeChannel(Channel channel) {
/* 43 */     this.channels.remove(getKey(channel));
/* 44 */     this.clientChannels.remove(channel);
   }


   public void addChannel(Channel channel) {
/* 49 */     this.channels.put(getKey(channel), channel);
/* 50 */     this.clientChannels.add(channel);
   }



/* 55 */   public Channel getChannel(String channelAddress) { return this.channels.get(channelAddress); }




/* 60 */   public void close() { this.clientChannels.close().awaitUninterruptibly(); }




/* 65 */   public Collection<Channel> getChannels() { return this.channels.values(); }
 }


/* Location:              D:\logs\easymodbus4j-0.0.5.jar!\com\github\zengfr\easymodbus4j\com.github.sunjx.modbus.channel\ChannelManagerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */