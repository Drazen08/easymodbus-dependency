package com.github.sunjx.modbus.codec.ping;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.nio.charset.Charset;
import java.util.List;


public class ModbusPingDecoder extends MessageToMessageDecoder<ByteBuf> {

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ModbusPingDecoder.class);
    private final Charset charset;
    private final String heartBeat;

    public ModbusPingDecoder(String heartBeat, Charset charset) {
        if (charset != null && heartBeat != null) {
            this.heartBeat = heartBeat;
            this.charset = charset;
        } else {
            throw new NullPointerException("heartBeat/charset");
        }
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        in.retain();
        boolean success = false;
        int heartBeatByteLen = this.heartBeat.length();
        if (in.readableBytes() >= heartBeatByteLen) {
            byte[] headArray = new byte[heartBeatByteLen];
            in.markReaderIndex();
            in.readBytes(headArray);
            String head = new String(headArray, this.charset);
            success = this.heartBeat.equalsIgnoreCase(head);
            if (success) {
                logger.debug(String.format("decode isHeartBeat:%s", success));
                out.add(head);
            } else {
                in.resetReaderIndex();
            }
        }

        if (!success) {
            ctx.fireChannelRead(in);
        }

    }
}

