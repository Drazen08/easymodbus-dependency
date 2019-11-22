package com.github.sunjx.modbus.client;

import com.github.sunjx.modbus.cache.ModebusFrameCacheFactory;
import com.github.sunjx.modbus.channel.ChannelManager;
import com.github.sunjx.modbus.channel.ChannelManagerImpl;
import com.github.sunjx.modbus.channel.ChannelReconnectable;
import com.github.sunjx.modbus.handler.ModbusInboundHandler;
import com.github.sunjx.modbus.handler.ModbusRequestHandler;
import com.github.sunjx.modbus.handler.ModbusResponseHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
/*     */
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import lombok.extern.slf4j.Slf4j;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public abstract class ModbusClient
        extends ChannelManagerImpl
        implements ChannelReconnectable {

    protected static final InternalLogger log = InternalLoggerFactory.getInstance(ModbusClient.class);

    public enum CONNECTION_STATES {
        connected, notConnected, pending;
    }


    public static final String PROP_CONNECTIONSTATE = "connectionState";
    private final transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    private final String host;
    private final int port;
    private Channel channel;
    private Bootstrap bootstrap;
    private EventLoopGroup workerGroup;
    private CONNECTION_STATES connectionState = CONNECTION_STATES.notConnected;

    public ModbusClient(String host, int port) {
        this.host = host;
        this.port = port;
    }


    protected void setup(ModbusRequestHandler reqhandler) throws Exception {
        reqhandler.setChannelManager((ChannelManager) this);
        ChannelInitializer<SocketChannel> handler = getChannelInitializer((ModbusInboundHandler) reqhandler);
        setup(handler);
    }

    protected void setup(ModbusResponseHandler resphandler) throws Exception {
        resphandler.setChannelManager((ChannelManager) this);
        ChannelInitializer<SocketChannel> handler = getChannelInitializer((ModbusInboundHandler) resphandler);
        setup(handler);
    }

    public void setup(ChannelInitializer<SocketChannel> handler) throws Exception {
        init(handler);
    }


    protected void init(ChannelInitializer<SocketChannel> handler) throws Exception {
        try {
            int threads = Math.max(2, Runtime.getRuntime().availableProcessors() * 2 - 1);
            this.workerGroup = (EventLoopGroup) new NioEventLoopGroup(threads, (ThreadFactory) new DefaultThreadFactory("client", false));
            this.bootstrap = new Bootstrap();
            this.bootstrap.group(this.workerGroup);
            this.bootstrap.channel(NioSocketChannel.class);
            this.bootstrap.option(ChannelOption.SO_KEEPALIVE, Boolean.valueOf(true));
            this.bootstrap.handler((ChannelHandler) handler);
            doConnect(this.workerGroup);
        } catch (Exception ex) {
            setConnectionState(CONNECTION_STATES.notConnected);
            log.error("init", ex);
            throw new Exception("ConnectionException:" + ex.getLocalizedMessage());
        }
    }


    @Override
    public void reConnect() {
        try {
            log.info(String.format("reConnect:%s,%s", new Object[]{this.host, Integer.valueOf(this.port)}));
            doConnect(this.workerGroup);
        } catch (InterruptedException ex) {
            log.error("reConnect", ex);
        }
    }

    public void doConnect(final EventLoopGroup workerGroup) throws InterruptedException {
        if (this.channel != null && this.channel.isActive()) {
            return;
        }
        setConnectionState(CONNECTION_STATES.pending);
        log.info(String.format("connect:%s,%s", new Object[]{this.host, Integer.valueOf(this.port)}));
        ChannelFuture f = this.bootstrap.connect(this.host, this.port);
        f.addListener((GenericFutureListener) new ChannelFutureListener() {
            /**
             *
             */
            @Override
            public void operationComplete(ChannelFuture futureListener) throws Exception {
                /* 128 */
                if (futureListener.isSuccess()) {
                    /* 129 */
                    ModbusClient.this.channel = futureListener.channel();
                    /* 130 */
                    ModbusClient.this.setConnectionState(CONNECTION_STATES.connected);
                    /* 131 */
                    ModbusClient.log.info(String.format("connect:%s,%s successfully", new Object[]{ModbusClient.access$100(this.this$0), Integer.valueOf(ModbusClient.access$200(this.this$0))}));
                    /* 132 */
                    ModbusClient.this.channel.closeFuture().addListener(new GenericFutureListener<ChannelFuture>() {
                        @Override
                        public void operationComplete(ChannelFuture future) throws Exception {
                            /* 136 */
                            ModbusClient.this.setConnectionState(CONNECTION_STATES.notConnected);
                        }
                    });
                } else {
                    /* 140 */
                    ModbusClient.log.info(String.format("connect:%s,%s failed, try connect after 10s", new Object[]{ModbusClient.access$100(this.this$0), Integer.valueOf(ModbusClient.access$200(this.this$0))}));
                    /* 141 */
                    ModbusClient.this.bindSchedule4DoConnect(futureListener.channel(), workerGroup);
                }
            }
        });
    }


    /* 149 */
    private void bindSchedule4DoConnect(Channel channel, final EventLoopGroup workerGroup) {
        channel.eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    /* 153 */
                    ModbusClient.log.info(String.format("doConnect", new Object[0]));
                    /* 154 */
                    ModbusClient.this.doConnect(workerGroup);
                    /* 155 */
                } catch (InterruptedException ex) {
                    /* 156 */
                    ModbusClient.log.error("doConnect", ex);
                }
            }
        },10L, TimeUnit.SECONDS);
    }


    /* 163 */
    public Channel getChannel() {
        return this.channel;
    }


    @Override
    public void close() {
        if (this.channel != null) {
            this.channel.close().awaitUninterruptibly();
        }
    }


    public CONNECTION_STATES getConnectionState() {
        return this.connectionState;
    }


    public void setConnectionState(CONNECTION_STATES connectionState) {
        CONNECTION_STATES oldConnectionState = this.connectionState;
        this.connectionState = connectionState;
        this.propertyChangeSupport.firePropertyChange("connectionState", oldConnectionState, connectionState);
    }


    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.addPropertyChangeListener(listener);
    }


    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.removePropertyChangeListener(listener);
    }

    protected abstract ChannelInitializer<SocketChannel> getChannelInitializer(ModbusInboundHandler paramModbusInboundHandler);
}

