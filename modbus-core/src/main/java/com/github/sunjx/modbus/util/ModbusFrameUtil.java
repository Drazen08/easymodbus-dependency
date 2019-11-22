package com.github.sunjx.modbus.util;


import com.github.sunjx.modbus.common.util.ByteBufUtil;
import com.github.sunjx.modbus.common.util.ByteUtil;
import com.github.sunjx.modbus.common.util.HexUtil;
import com.github.sunjx.modbus.common.util.PadUtil;
import com.github.sunjx.modbus.logging.ChannelLogger;
import com.github.sunjx.modbus.protocol.ModbusFunction;
import com.github.sunjx.modbus.protocol.tcp.ModbusFrame;
import com.sun.deploy.util.StringUtils;
import io.netty.channel.Channel;
import io.netty.util.internal.logging.InternalLogger;

import java.util.BitSet;


public class ModbusFrameUtil {
    /* 37 */   private static String splitLine = PadUtil.padLeft("-", 128, '-');


    /* 40 */
    public static void showFrameLog(ChannelLogger log, Channel channel, ModbusFrame frame, boolean isShowHex) {
        log.debug(channel, getFrameLog(channel, frame, isShowHex), new Object[0]);
    }


    /* 44 */
    public static void showFrameLog(InternalLogger log, Channel channel, ModbusFrame frame, boolean isShowHex) {
        log.debug(getFrameLog(channel, frame, isShowHex));
    }


    public static String getFrameLog(Channel channel, ModbusFrame frame, boolean isShowHex) {
        /* 48 */
        byte[] bytes = ByteBufUtil.toBytes(frame.encode());
        /* 49 */
        String v1 = HexUtil.bytesToHexString(bytes, " ");
        /* 50 */
        String v2 = frame.toString();

        /* 52 */
        boolean isResponseFrame = frame.getFunction().toString().contains("Response");
        /* 53 */
        int transactionId = frame.getHeader().getTransactionIdentifier();

        /* 55 */
        StringBuilder sBuilder = new StringBuilder();
        /* 56 */
        sBuilder.append(String.format("%s", new Object[]{getChannelAddress(channel)}));
        /* 57 */
        if (isShowHex) {
            /* 58 */
            sBuilder.append(String.format("\r\n%s", new Object[]{splitLine}));
            /* 59 */
            sBuilder.append(String.format("\r\n%s", new Object[]{v1}));
        }
        /* 61 */
        sBuilder.append(String.format("\r\n%s", new Object[]{splitLine}));

        /* 63 */
        if (isResponseFrame) {
            /* 64 */
            sBuilder.append(String.format("\r\n%s %s", new Object[]{(transactionId < 0) ? "RTU" : "TCP", v2}));
            /* 65 */
            sBuilder.append(String.format("\r\n%s", new Object[]{splitLine}));
            /* 66 */
            sBuilder.append(getFunctionLog(frame.getFunction()));
        } else {
            /* 68 */
            sBuilder.append(String.format("\r\n%s", new Object[]{v2}));
            /* 69 */
            sBuilder.append(String.format("\r\n%s", new Object[]{splitLine}));
        }
        /* 71 */
        return sBuilder.toString();
    }

    public static String getFunctionLog(ModbusFunction function) {
        /* 75 */
        StringBuilder sBuilder = new StringBuilder();
        /* 76 */
        byte[] valuesArray = ModbusFunctionUtil.getFunctionValues(function);
        /* 77 */
        sBuilder.append(String.format("\r\nhex    :%s", new Object[]{HexUtil.bytesToHexString(valuesArray, " ")}));
        /* 78 */
        sBuilder.append(String.format("\r\nbitset :%s", new Object[]{BitSet.valueOf(valuesArray)}));
        /* 79 */
        sBuilder.append(String.format("\r\nbyte   :%s", new Object[]{StringUtils.join(valuesArray, ',')}));
        /* 80 */
        sBuilder.append(String.format("\r\nshort  :%s", new Object[]{StringUtils.join(ByteUtil.toShortArray(valuesArray), ',')}));
        /* 81 */
        sBuilder.append(String.format("\r\nushort :%s", new Object[]{StringUtils.join(ByteUtil.toUShortArray(valuesArray), ',')}));
        /* 82 */
        sBuilder.append(String.format("\r\nint    :%s", new Object[]{StringUtils.join(ByteUtil.toIntArray(valuesArray), ',')}));
        /* 83 */
        sBuilder.append(String.format("\r\nlong   b:%s", new Object[]{StringUtils.join(ByteUtil.toLongArray(valuesArray), ',')}));
        /* 84 */
        sBuilder.append(String.format("\r\nfloat  b:%s", new Object[]{StringUtils.join(ByteUtil.toFloatArray(valuesArray), ',')}));
        /* 85 */
        sBuilder.append(String.format("\r\ndouble b:%s", new Object[]{StringUtils.join(ByteUtil.toDoubleArray(valuesArray), ',')}));
        /* 86 */
        sBuilder.append(String.format("\r\nchar   b:%s", new Object[]{StringUtils.join(ByteUtil.toCharArray(valuesArray), ',')}));

        /* 88 */
        sBuilder.append(String.format("\r\nlong   l:%s", new Object[]{StringUtils.join(ByteUtil.toLongArray(valuesArray, false), ',')}));
        /* 89 */
        sBuilder.append(String.format("\r\nfloat  l:%s", new Object[]{StringUtils.join(ByteUtil.toFloatArray(valuesArray, false), ',')}));
        /* 90 */
        sBuilder.append(String.format("\r\ndouble l:%s", new Object[]{StringUtils.join(ByteUtil.toDoubleArray(valuesArray, false), ',')}));
        /* 91 */
        sBuilder.append(String.format("\r\nchar   l:%s", new Object[]{StringUtils.join(ByteUtil.toCharArray(valuesArray, false), ',')}));
        /* 92 */
        sBuilder.append(String.format("\r\n%s", new Object[]{splitLine}));
        /* 93 */
        return sBuilder.toString();
    }


    /* 97 */
    private static String getChannelAddress(Channel channel) {
        return channel.remoteAddress().toString();
    }
}


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4\\util\ModbusFrameUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */