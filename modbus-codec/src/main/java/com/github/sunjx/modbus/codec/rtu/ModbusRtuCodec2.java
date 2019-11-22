 package com.github.sunjx.modbus.codec.rtu;

 import com.github.zengfr.easymodbus4j.codec.ModbusPduCodec;
 import com.github.zengfr.easymodbus4j.common.util.RtuCrcUtil;
 import com.github.zengfr.easymodbus4j.protocol.ModbusFunction;
 import com.github.zengfr.easymodbus4j.protocol.tcp.ModbusFrame;
 import com.github.zengfr.easymodbus4j.protocol.tcp.ModbusHeader;
 import io.netty.buffer.ByteBuf;
 import io.netty.channel.ChannelHandlerContext;
 import io.netty.handler.codec.ByteToMessageCodec;
 import io.netty.util.internal.logging.InternalLogger;
 import io.netty.util.internal.logging.InternalLoggerFactory;
 import java.util.List;





















 public class ModbusRtuCodec2
   extends ByteToMessageCodec<ModbusFrame>
 {
/* 38 */   private static final InternalLogger log = InternalLoggerFactory.getInstance(ModbusRtuCodec2.class);
   protected final ModbusPduCodec pduCodec;

/* 41 */   public ModbusRtuCodec2(ModbusPduCodec pduCodec) { this.pduCodec = pduCodec; }



   protected void encode(ChannelHandlerContext ctx, ModbusFrame msg, ByteBuf out) throws Exception {
/* 46 */     log.debug("encode:" + ctx.channel().remoteAddress());
/* 47 */     ByteBuf sendBuf = ctx.alloc().heapBuffer(1 + msg.getHeader().getLength() - 1 + 2);
/* 48 */     sendBuf.writeByte(msg.getHeader().getUnitIdentifier());
/* 49 */     sendBuf.writeBytes(msg.getFunction().encode());
/* 50 */     writeRtuCRC(sendBuf);

/* 52 */     ctx.writeAndFlush(sendBuf);
   }

   private void writeRtuCRC(ByteBuf buffer) {
/* 56 */     int startReaderIndex = buffer.readerIndex();
/* 57 */     int crc = RtuCrcUtil.calculateCRC(buffer);
/* 58 */     buffer.readerIndex(startReaderIndex);
/* 59 */     buffer.writeByte((byte)(0xFF & crc >> 8));
/* 60 */     buffer.writeByte((byte)(0xFF & crc));
   }


   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/* 65 */     int startIndex = in.readerIndex();
/* 66 */     log.debug(String.format("decode:%s", new Object[] { ctx.channel().remoteAddress() }));
/* 67 */     while (in.readableBytes() >= ModbusRtuCodecUtil.getMessageMinSize() && in.readableBytes() >= ModbusRtuCodecUtil.getMessageLength(in, startIndex)) {
/* 68 */       short unitId = in.readUnsignedByte();
/* 69 */       ModbusFunction function = (ModbusFunction)this.pduCodec.decode(in);
/* 70 */       int crc = in.readUnsignedShort();
/* 71 */       log.debug(String.format("decode:%s,%s,%s,%s", new Object[] { Integer.valueOf(startIndex), Short.valueOf(unitId), Short.valueOf(function.getFunctionCode()), Integer.valueOf(crc) }));
/* 72 */       int transactionId = -crc;
/* 73 */       int pduLength = function.calculateLength();
/* 74 */       int protocolIdentifier = 0;
/* 75 */       ModbusHeader mbapHeader = new ModbusHeader(transactionId, protocolIdentifier, pduLength, unitId);
/* 76 */       ModbusFrame frame = new ModbusFrame(mbapHeader, function);
/* 77 */       if (frame != null) {
/* 78 */         out.add(frame);
       }
/* 80 */       startIndex = in.readerIndex();
     }
   }
 }


/* Location:              D:\logs\easymodbus4j-codec-0.0.5.jar!\com\github\zengfr\easymodbus4j\codec\rtu\ModbusRtuCodec2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */