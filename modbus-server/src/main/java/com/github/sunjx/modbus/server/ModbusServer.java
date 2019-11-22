package com.github.sunjx.modbus.server;



import com.github.sunjx.modbus.channel.ChannelManager;
import com.github.sunjx.modbus.channel.ChannelManagerImpl;
import com.github.sunjx.modbus.handler.ModbusInboundHandler;
import com.github.sunjx.modbus.handler.ModbusRequestHandler;
import com.github.sunjx.modbus.handler.ModbusResponseHandler;
import io.netty.bootstrap.ServerBootstrap;
 import io.netty.channel.Channel;
 import io.netty.channel.ChannelFuture;
 import io.netty.channel.ChannelHandler;
 import io.netty.channel.ChannelInitializer;
 import io.netty.channel.ChannelOption;
 import io.netty.channel.EventLoopGroup;
 import io.netty.channel.nio.NioEventLoopGroup;
 import io.netty.channel.socket.SocketChannel;
 import io.netty.channel.socket.nio.NioServerSocketChannel;
 import io.netty.util.concurrent.DefaultThreadFactory;
 import io.netty.util.concurrent.GenericFutureListener;
 import io.netty.util.internal.logging.InternalLogger;
 import io.netty.util.internal.logging.InternalLoggerFactory;
 import java.beans.PropertyChangeListener;
 import java.beans.PropertyChangeSupport;
 import java.util.Set;
 import java.util.concurrent.ThreadFactory;

 public abstract class ModbusServer
         extends ChannelManagerImpl
         {
       public enum CONNECTION_STATES
             {
        /*  50 */     listening, down, clientsConnected;
        
    }

    
    /*  53 */   protected static final InternalLogger log = InternalLoggerFactory.getInstance(ModbusServer.class);
       public static final String PROP_CONNECTIONSTATE = "connectionState";
       private final int port;
       private ServerBootstrap bootstrap;
    /*  57 */   private CONNECTION_STATES connectionState = CONNECTION_STATES.down;
    /*  58 */   private final transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    
       private Channel parentChannel;

    
    /*  62 */
    public ModbusServer(int port) {
        this.port = port;
    }

    
    
    
    protected void setup(ModbusRequestHandler reqhandler) throws Exception {
        /*  66 */
        reqhandler.setChannelManager((ChannelManager) this);
        /*  67 */
        ChannelInitializer<SocketChannel> handler = getChannelInitializer((ModbusInboundHandler) reqhandler);
        /*  68 */
        setup(handler);
        
    }

    
    
    protected void setup(ModbusResponseHandler resphandler) throws Exception {
        /*  72 */
        resphandler.setChannelManager((ChannelManager) this);
        /*  73 */
        ChannelInitializer<SocketChannel> handler = getChannelInitializer((ModbusInboundHandler) resphandler);
        /*  74 */
        setup(handler);
        
    }

    
    
    /*  78 */
    public void setup(ChannelInitializer<SocketChannel> handler) throws Exception {
        init(handler);
    }

    
    
    
    protected abstract ChannelInitializer<SocketChannel> getChannelInitializer(ModbusInboundHandler paramModbusInboundHandler);

    
    
    
    protected void init(ChannelInitializer<SocketChannel> handler) throws Exception {
        
        try {
            /*  86 */
            int threads = Math.max(3, Runtime.getRuntime().availableProcessors() * 2 - 1);
            /*  87 */
            final NioEventLoopGroup bossGroup = new NioEventLoopGroup(threads, (ThreadFactory) new DefaultThreadFactory("boss", false));
            /*  88 */
            final NioEventLoopGroup workerGroup = new NioEventLoopGroup(threads, (ThreadFactory) new DefaultThreadFactory("worker", false));
            
            /*  90 */
            this.bootstrap = new ServerBootstrap();
            /*  91 */
            ((ServerBootstrap) ((ServerBootstrap) ((ServerBootstrap) this.bootstrap.group((EventLoopGroup) nioEventLoopGroup1, (EventLoopGroup) nioEventLoopGroup2).channel(NioServerSocketChannel.class)).option(ChannelOption.SO_REUSEADDR, Boolean.valueOf(true))).option(ChannelOption.SO_BACKLOG, Integer.valueOf(1024)))
/*  92 */.childOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(true)).childOption(ChannelOption.SO_KEEPALIVE, Boolean.valueOf(true)).childHandler((ChannelHandler) handler);
            /*  93 */
            log.info("bind port:" + this.port);
            /*  94 */
            this.parentChannel = this.bootstrap.bind(this.port).sync().channel();
            
            /*  96 */
            setConnectionState(CONNECTION_STATES.listening);
            
            /*  98 */
            this.parentChannel.closeFuture().addListener(new GenericFutureListener<ChannelFuture>()
                     {
                
                @Override
                public void operationComplete(ChannelFuture future) throws Exception
                 {
                    /* 102 */
                    workerGroup.shutdownGracefully();
                    /* 103 */
                    bossGroup.shutdownGracefully();
                    
                    /* 105 */
                    ModbusServer.this.setConnectionState(ModbusServer.CONNECTION_STATES.down);
                    
                }
                
            });
            /* 108 */
        } catch (Exception ex) {
            /* 109 */
            setConnectionState(CONNECTION_STATES.down);
            /* 110 */
            log.error("init", ex);
            
            /* 112 */
            throw new Exception("ConnectionException:" + ex.getLocalizedMessage());
            
        }
        
    }

    
    
    /* 117 */
    public CONNECTION_STATES getConnectionState() {
        return this.connectionState;
    }

    
    
    
    public void setConnectionState(CONNECTION_STATES connectionState) {
        /* 121 */
        CONNECTION_STATES oldConnectionState = this.connectionState;
        /* 122 */
        this.connectionState = connectionState;
        /* 123 */
        this.propertyChangeSupport.firePropertyChange("connectionState", oldConnectionState, connectionState);
        
    }

    
    
    /* 127 */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.addPropertyChangeListener(listener);
    }

    
    
    
    /* 131 */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.removePropertyChangeListener(listener);
    }

    
    
    
    @Override
    public void close() {
        /* 135 */
        if (this.parentChannel != null) {
            /* 136 */
            this.parentChannel.close().awaitUninterruptibly();
            
        }
        /* 138 */
        super.close();
        
    }

    
    
    
    @Override
    public void removeChannel(Channel channel) {
        /* 143 */
        super.removeChannel(channel);
        /* 144 */
        setConnectionState(CONNECTION_STATES.clientsConnected);
        
    }

    
    
    
    @Override
    public void addChannel(Channel channel) {
        /* 149 */
        super.addChannel(channel);
        /* 150 */
        setConnectionState(CONNECTION_STATES.clientsConnected);
        
    }

    
    
    public Channel getChannelByIp(String channelAddress) {
        /* 154 */
        Set<String> keys = this.channels.keySet();
        /* 155 */
        for (String key : keys) {
            /* 156 */
            if (key != null && key.indexOf(channelAddress) < 0) {
                /* 157 */
                return (Channel) this.channels.get(key);
                
            }
            
        }
        /* 160 */
        return null;
        
    }
    
}


/* Location:              D:\logs\easymodbus4j-server-0.0.5.jar!\com\github\zengfr\easymodbus4j\server\ModbusServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */