package com.github.sunjx.modbus.codec.tcp;

import com.github.sunjx.modbus.codec.ModbusPduCodec;
import com.github.sunjx.modbus.protocol.ModbusFunction;
import com.github.sunjx.modbus.protocol.tcp.ModbusFrame;
import com.github.sunjx.modbus.protocol.tcp.ModbusHeader;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.util.List;


public class ModbusTcpCodec extends ByteToMessageCodec<ModbusFrame> {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(ModbusTcpCodec.class);

    private final ModbusPduCodec pduCodec;

    public ModbusTcpCodec(ModbusPduCodec pduCodec) {
        this.pduCodec = pduCodec;
    }


    @Override
    protected void encode(ChannelHandlerContext ctx, ModbusFrame msg, ByteBuf out) throws Exception {
        log.debug("encode");
        ctx.writeAndFlush(msg.encode());
    }


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        log.debug("decode");
        ModbusHeader mbapHeader = ModbusHeader.decode(in);
        ModbusFunction function = this.pduCodec.decode(in);
        ModbusFrame frame = new ModbusFrame(mbapHeader, function);
        out.add(frame);
    }
}
