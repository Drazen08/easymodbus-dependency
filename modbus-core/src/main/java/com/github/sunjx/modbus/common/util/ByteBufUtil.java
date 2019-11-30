package com.github.sunjx.modbus.common.util;

import io.netty.buffer.ByteBuf;


public class ByteBufUtil {

    /**
     * bytebuf -> byte[] 转换
     */
    public static byte[] toBytes(ByteBuf msg) {
        byte[] req = new byte[msg.readableBytes()];
        msg.readBytes(req);
        return req;
    }
}

