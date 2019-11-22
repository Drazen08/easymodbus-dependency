package com.github.sunjx.modbus.logging;

import io.netty.channel.Channel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import org.slf4j.MDC;


public class ChannelLogger {
    protected final InternalLogger log;

    /* 31 */
    public static ChannelLogger getLogger(Class<?> cls) {
        return new ChannelLogger(cls);
    }


    /* 37 */
    public ChannelLogger(Class<?> cls) {
        this.log = InternalLoggerFactory.getInstance(cls);
    }


    protected String buildMsg(Channel channel, String format, Object... args) {
        /* 41 */
        if (args == null || args.length <= 0) {
            /* 42 */
            return String.format("%s|%s", new Object[]{getChannelName(channel), format});
        }
        /* 44 */
        return String.format("%s|%s", new Object[]{getChannelName(channel), String.format(format, args)});
    }

    /* 47 */
    protected String buildMsg(Channel channel, Throwable ex) {
        return String.format("%s|%s", new Object[]{getChannelName(channel), ex.getMessage()});
    }

    protected static String getChannelName(Channel channel) {
        /* 50 */
        if (channel != null && channel.remoteAddress() != null) {
            /* 51 */
            return channel.remoteAddress().toString().replace('/', '.').replace(':', '.');
        }
        /* 53 */
        return "null";
    }

    /* 56 */
    public static void putMDC(Channel channel) {
        MDC.put("com.github.sunjx.modbus.channel", getChannelName(channel));
    }

    public void debug(Channel channel, String format, Object... args) {
        /* 59 */
        if (this.log.isDebugEnabled()) {
            /* 60 */
            putMDC(channel);
            /* 61 */
            this.log.debug(buildMsg(channel, format, args));
        }
    }

    public void info(Channel channel, String format, Object... args) {
        /* 66 */
        if (this.log.isInfoEnabled()) {
            /* 67 */
            putMDC(channel);
            /* 68 */
            this.log.info(buildMsg(channel, format, args));
        }
    }

    public void warn(Channel channel, String format, Object... args) {
        /* 72 */
        if (this.log.isWarnEnabled()) {
            /* 73 */
            putMDC(channel);
            /* 74 */
            this.log.warn(buildMsg(channel, format, args));
        }
    }

    public void error(Channel channel, Throwable ex) {
        /* 78 */
        if (this.log.isErrorEnabled()) {
            /* 79 */
            putMDC(channel);
            /* 80 */
            this.log.error(buildMsg(channel, ex), ex);
        }
    }
}


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4j\logging\ChannelLogger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */