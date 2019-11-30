package com.github.sunjx.modbus.codec.rtu;

import com.github.sunjx.modbus.codec.ModbusPduCodec;
import com.github.sunjx.modbus.common.util.ByteUtil;
import com.github.sunjx.modbus.common.util.RtuCrcUtil;
import com.github.sunjx.modbus.protocol.ModbusFunction;
import com.github.sunjx.modbus.protocol.tcp.ModbusFrame;
import com.github.sunjx.modbus.protocol.tcp.ModbusHeader;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.util.List;


public class ModbusRtuCodec2 extends ByteToMessageCodec<ModbusFrame> {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(ModbusRtuCodec2.class);
    protected final ModbusPduCodec pduCodec;

    public ModbusRtuCodec2(ModbusPduCodec pduCodec) {
        this.pduCodec = pduCodec;
    }


    @Override
    protected void encode(ChannelHandlerContext ctx, ModbusFrame msg, ByteBuf out) throws Exception {
        log.debug("encode:" + ctx.channel().remoteAddress());
        ByteBuf sendBuf = ctx.alloc().heapBuffer(1 + msg.getHeader().getLength() - 1 + 2);
        sendBuf.writeByte(msg.getHeader().getUnitIdentifier());
        sendBuf.writeBytes(msg.getFunction().encode());
        writeRtuCRC(sendBuf);
        ctx.writeAndFlush(sendBuf);
    }

    private void writeRtuCRC(ByteBuf buffer) {
        int startReaderIndex = buffer.readerIndex();
        int crc = RtuCrcUtil.calculateCRC(buffer);
        buffer.readerIndex(startReaderIndex);
        buffer.writeByte((byte) (0xFF & crc >> 8));
        buffer.writeByte((byte) (0xFF & crc));
    }


    /**
     * modbus rtu 拆包
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int startIndex = in.readerIndex();
        log.debug(String.format("decode:%s", ctx.channel().remoteAddress()));
        while (in.readableBytes() >= ModbusRtuCodecUtil.getMessageMinSize() && in.readableBytes() >= ModbusRtuCodecUtil.getMessageLength(in, startIndex)) {
            short unitId = in.readUnsignedByte();
            ModbusFunction function = this.pduCodec.decode(in);
            int crc = in.readUnsignedShort();
            log.debug(String.format("decode:%s,%s,%s,%s", startIndex, unitId, function.getFunctionCode(), crc));
            int transactionId = -crc;
            int pduLength = function.calculateLength();
            int protocolIdentifier = 0;
            ModbusHeader mbapHeader = new ModbusHeader(transactionId, protocolIdentifier, pduLength, unitId);
            ModbusFrame frame = new ModbusFrame(mbapHeader, function);

            out.add(frame);

            startIndex = in.readerIndex();
        }
    }
}

