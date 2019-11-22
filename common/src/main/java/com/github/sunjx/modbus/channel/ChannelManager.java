package com.github.sunjx.modbus.channel;

import io.netty.channel.Channel;

import java.util.Collection;

public interface ChannelManager {
    Collection<Channel> getChannels();

    void removeChannel(Channel paramChannel);

    void addChannel(Channel paramChannel);

    Channel getChannel(String paramString);

    String getKey(Channel paramChannel);

    void close();
}
