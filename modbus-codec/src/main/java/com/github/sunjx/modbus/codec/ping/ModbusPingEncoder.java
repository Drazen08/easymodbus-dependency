package com.github.sunjx.modbus.codec.ping;

import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;

/**
 * 心跳包加格式
 */
public class ModbusPingEncoder
        extends StringEncoder {
    /* 29 */
    public ModbusPingEncoder(Charset charset) {
        super(charset);
    }
}
