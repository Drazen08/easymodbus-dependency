package com.github.sunjx.modbus.codec.tcp;

import com.github.sunjx.modbus.codec.util.ModbusFunctionDecoderUtil;
import com.github.sunjx.modbus.protocol.ModbusFunction;
import com.github.sunjx.modbus.protocol.tcp.ModbusFrame;
import com.github.sunjx.modbus.protocol.tcp.ModbusHeader;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.util.List;


public class ModbusTcpDecoder
        extends ByteToMessageDecoder {
    /*  57 */   private static final InternalLogger log = InternalLoggerFactory.getInstance(ModbusTcpDecoder.class);

    private final boolean isSlave;

    /*  61 */
    public ModbusTcpDecoder(boolean isSlave) {
        this.isSlave = isSlave;
    }


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) {
        /*  66 */
        log.debug("decode");
        /*  67 */
        if (buffer.capacity() < 8) {
            return;
        }
        /*  70 */
        buffer.markReaderIndex();
        /*  71 */
        ModbusFrame frame = decodeFrame(buffer, this.isSlave);
        /*  72 */
        if (frame != null) {
            /*  73 */
            out.add(frame);
        } else {
            /*  75 */
            buffer.resetReaderIndex();
            /*  76 */
            ctx.fireChannelRead(buffer);
        }
    }

    public static ModbusFrame decodeFrame(ByteBuf buffer, boolean isSlave) {
        /*  81 */
        if (buffer.capacity() < 8) {
            /*  82 */
            return null;
        }

        /*  85 */
        ModbusHeader mbapHeader = ModbusHeader.decode(buffer);
        /*  86 */
        ModbusFunction function = decodeFunction(buffer, isSlave);
        /*  87 */
        ModbusFrame frame = new ModbusFrame(mbapHeader, function);
        /*  88 */
        return frame;
    }

    public static ModbusFunction decodeFunction(ByteBuf buffer, boolean isRequest) {
        /*  92 */
        ModbusFunction function = null;
        /*  93 */
        short functionCode = buffer.readUnsignedByte();
        /*  94 */
        if (isRequest) {
            /*  95 */
            function = ModbusFunctionDecoderUtil.decodeReqFunction(functionCode);
        } else {
            /*  97 */
            function = ModbusFunctionDecoderUtil.decodeRespFunction(functionCode);
        }
        /*  99 */
        function.decode(buffer.readBytes(buffer.readableBytes()));
        /* 100 */
        return function;
    }
}


/* Location:              D:\logs\easymodbus4j-codec-0.0.5.jar!\com\github\zengfr\easymodbus4j\codec\tcp\ModbusTcpDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */