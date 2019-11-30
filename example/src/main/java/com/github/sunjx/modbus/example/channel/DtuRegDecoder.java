package com.github.sunjx.modbus.example.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.AttributeKey;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class DtuRegDecoder extends MessageToMessageDecoder<ByteBuf> {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(DtuRegDecoder.class);
    private final String dtuNamePrefix;

    public DtuRegDecoder(String dtuNamePrefix) {
        if (dtuNamePrefix != null) {
            this.dtuNamePrefix = dtuNamePrefix;
        } else {
            throw new NullPointerException("dtuNamePrefix");
        }
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        in.retain();

        //DTU注册包的格式为 dtuNamePrefix + dtuName + \0, dtuName最大长度为64
        int prefixLen = this.dtuNamePrefix.length();
        if (in.readableBytes() < prefixLen) {
            ctx.fireChannelRead(in);
            return;
        }

        byte[] headArray = new byte[prefixLen];
        in.markReaderIndex();
        in.readBytes(headArray);
        String head = new String(headArray);

        if (this.dtuNamePrefix.equalsIgnoreCase(head)) {
            ByteBuf buf = Unpooled.buffer(64);
            int readCount = 1;
            while (in.readableBytes() > 0) {
                byte inByte = in.readByte();
                if (inByte == 0) {
                    String dtuName = readCount == 1 ? "" : buf.toString(StandardCharsets.UTF_8);
                    ctx.channel().attr(AttributeKey.valueOf("DtuName")).set(dtuName);
                    logger.debug(String.format("decode dtu reg:%s", dtuName));
                    out.add(dtuName);
                    return;
                }
                if (readCount > 64) {
                    break;
                }
                buf.writeByte(inByte);
                readCount++;
            }
        }
        //注意：上面所有读取成功的执行分支都使用return跳出，所有执行到此的分支都是无法匹配。
        in.resetReaderIndex();
        ctx.fireChannelRead(in);
    }
}
