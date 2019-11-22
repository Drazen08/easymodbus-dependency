
package com.github.sunjx.modbus.sender.util;


import com.github.sunjx.modbus.common.util.IntArrayUtil;
import com.github.sunjx.modbus.common.util.ParseStringUtil;
import com.github.sunjx.modbus.protocol.ModbusFunction;
import com.github.sunjx.modbus.sender.ChannelSender;
import com.github.sunjx.modbus.sender.ChannelSenderFactory;
import io.netty.channel.Channel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;


public class ModbusRequestSendUtil {
    public enum PriorityStrategy {
        /*  39 */     Channel, Req;

    }


    /*  42 */   private static final InternalLogger log = InternalLoggerFactory.getInstance(ModbusRequestSendUtil.class);


    public static void sendRequests(Collection<Channel> channels, List<String> reqs, boolean isAllUseAsync, int fixedDelay, PriorityStrategy strategy) {
        /*  45 */
        switch (strategy) {

            case Channel:
                /*  47 */
                sendRequestsByChannelFirst(channels, reqs, isAllUseAsync, fixedDelay);

            case Req:
                /*  49 */
                sendRequestsByReqFirst(channels, reqs, isAllUseAsync, fixedDelay);

                break;

        }

    }


    private static void sendRequestsByChannelFirst(Collection<Channel> channels, List<String> reqs, boolean isAllUseAsync, int fixedDelay) {
        /*  55 */
        for (Channel channel : channels) {
            /*  56 */
            if (channel == null || !channel.isActive() || !channel.isOpen() || !channel.isWritable()) {
                continue;
            }
            /*  58 */
            ChannelSender sender = ChannelSenderFactory.getInstance().get(channel);
            /*  59 */
            for (String str : reqs) {

                try {
                    /*  61 */
                    long startTime = System.currentTimeMillis();
                    /*  62 */
                    String[] args = str.split("[;|]");
                    /*  63 */
                    if (args.length >= 3) {
                        /*  64 */
                        String funcString = args[0];
                        /*  65 */
                        String addressString = args[1];
                        /*  66 */
                        String value = args[2];
                        /*  67 */
                        if (isAllUseAsync && !funcString.endsWith("Async")) {
                            /*  68 */
                            funcString = funcString + "Async";

                        }
                        /*  70 */
                        if (!funcString.endsWith("Async")) {
                            /*  71 */
                            sendSyncFunc(sender, funcString, addressString, value);

                        } else {
                            /*  73 */
                            sendAsyncFunc(sender, funcString, addressString, value);
                            /*  74 */
                        }
                        long endTime = System.currentTimeMillis();
                        /*  75 */
                        long span = endTime - startTime;
                        /*  76 */
                        if (fixedDelay - span > 0L) {
                            /*  77 */
                            Thread.sleep(fixedDelay - span);

                        }

                    }
                    /*  80 */
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | IllegalArgumentException | SecurityException | InterruptedException ex) {
                    /*  81 */
                    log.error("sendRequestsByChannelFirst", ex);

                }

            }

        }

    }


    private static void sendRequestsByReqFirst(Collection<Channel> channels, List<String> reqs, boolean isAllUseAsync, int fixedDelay) {
        /*  88 */
        for (String str : reqs) {
            /*  89 */
            long startTime = System.currentTimeMillis();
            /*  90 */
            for (Channel channel : channels) {

                try {
                    /*  92 */
                    if (channel == null || !channel.isActive() || !channel.isOpen() || !channel.isWritable()) {
                        continue;
                    }
                    /*  94 */
                    ChannelSender sender = ChannelSenderFactory.getInstance().get(channel);
                    /*  95 */
                    String[] args = str.split("[;|]");
                    /*  96 */
                    if (args.length >= 3) {
                        /*  97 */
                        String funcString = args[0];
                        /*  98 */
                        String addressString = args[1];
                        /*  99 */
                        String value = args[2];
                        /* 100 */
                        if (isAllUseAsync && !funcString.endsWith("Async")) {
                            /* 101 */
                            funcString = funcString + "Async";

                        }
                        /* 103 */
                        if (!funcString.endsWith("Async")) {
                            /* 104 */
                            sendSyncFunc(sender, funcString, addressString, value);
                            continue;

                        }
                        /* 106 */
                        sendAsyncFunc(sender, funcString, addressString, value);

                    }
                    /* 108 */
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | IllegalArgumentException | SecurityException ex) {
                    /* 109 */
                    log.error("sendRequestsByReqFirst", ex);

                }

            }
            /* 112 */
            long endTime = System.currentTimeMillis();
            /* 113 */
            long span = endTime - startTime;
            /* 114 */
            if (span < fixedDelay) {

                try {
                    /* 116 */
                    Thread.sleep(fixedDelay - span);
                    /* 117 */
                } catch (InterruptedException ex) {
                    /* 118 */
                    log.error("sendRequestsByReqFirst", ex);

                }

            }

        }

    }


    public static ModbusFunction sendSyncFunc(ChannelSender sender, String funcString, String addressStr, String value) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        /* 126 */
        ModbusFunction respFunction = null;
        /* 127 */
        if (!funcString.startsWith("#")) {
            /* 128 */
            Integer address = Integer.valueOf(addressStr);
            /* 129 */
            if ("TF".contains(value)) {
                /* 130 */
                respFunction = ModbusChannelSenderUtil.sendSyncFunc(sender, funcString, address.intValue(), value.equalsIgnoreCase("T"));
                /* 131 */
            } else if (value.contains(",")) {
                /* 132 */
                String[] values = value.split("[,]");
                /* 133 */
                if (value.contains("T") || value.contains("F")) {
                    /* 134 */
                    respFunction = ModbusChannelSenderUtil.sendSyncFunc(sender, funcString, address.intValue(), ParseStringUtil.parseBooleanArray(values));

                } else {
                    /* 136 */
                    respFunction = ModbusChannelSenderUtil.sendSyncFunc(sender, funcString, address.intValue(), IntArrayUtil.toIntArray(values));

                }

            } else {
                /* 139 */
                respFunction = ModbusChannelSenderUtil.sendSyncFunc(sender, funcString, address.intValue(), Integer.valueOf(value).intValue());

            }

        }
        /* 142 */
        return respFunction;

    }


    public static int sendAsyncFunc(ChannelSender sender, String funcString, String addressStr, String value) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        /* 147 */
        int transactionId = 0;
        /* 148 */
        if (!funcString.startsWith("#")) {
            /* 149 */
            Integer address = Integer.valueOf(addressStr);
            /* 150 */
            if (!funcString.endsWith("Async")) {
                /* 151 */
                funcString = funcString + "Async";

            }
            /* 153 */
            if ("TF".contains(value)) {
                /* 154 */
                transactionId = ModbusChannelSenderUtil.sendAsyncFunc(sender, funcString, address.intValue(), value.equalsIgnoreCase("T"));
                /* 155 */
            } else if (value.contains(",")) {
                /* 156 */
                String[] values = value.split("[,]");
                /* 157 */
                if (value.contains("T") || value.contains("F")) {
                    /* 158 */
                    transactionId = ModbusChannelSenderUtil.sendAsyncFunc(sender, funcString, address.intValue(), ParseStringUtil.parseBooleanArray(values));

                } else {
                    /* 160 */
                    transactionId = ModbusChannelSenderUtil.sendAsyncFunc(sender, funcString, address.intValue(), IntArrayUtil.toIntArray(values));

                }

            } else {
                /* 163 */
                transactionId = ModbusChannelSenderUtil.sendAsyncFunc(sender, funcString, address.intValue(), Integer.valueOf(value).intValue());

            }

        }
        /* 166 */
        return transactionId;

    }

}


/* Location:              D:\logs\easymodbus4j-0.0.5.jar!\com\github\zengfr\easymodbus4j\sende\\util\ModbusRequestSendUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */