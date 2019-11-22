package com.github.sunjx.modbus.channel;

import com.google.common.collect.Maps;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Collection;
import java.util.Map;


public class ChannelManagerImpl implements ChannelManager {
    /**
     * k -> channel remote addr, v -> channel
     */
    protected Map<String, Channel> channels = Maps.newConcurrentMap();

    protected ChannelGroup clientChannels = new DefaultChannelGroup(getClass().getSimpleName(),
            GlobalEventExecutor.INSTANCE);


    @Override
    public String getKey(Channel channel) {
        return channel.remoteAddress().toString();
    }


    @Override
    public void removeChannel(Channel channel) {
        this.channels.remove(getKey(channel));
        this.clientChannels.remove(channel);
    }


    @Override
    public void addChannel(Channel channel) {
        this.channels.put(getKey(channel), channel);
        this.clientChannels.add(channel);
    }


    @Override
    public Channel getChannel(String channelAddress) {
        return this.channels.get(channelAddress);
    }


    @Override
    public void close() {
        this.clientChannels.close().awaitUninterruptibly();
    }


    @Override
    public Collection<Channel> getChannels() {
        return this.channels.values();
    }
}

