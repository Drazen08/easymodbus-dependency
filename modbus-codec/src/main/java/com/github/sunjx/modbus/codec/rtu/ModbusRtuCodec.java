 package com.github.sunjx.modbus.codec.rtu;

 import com.github.zengfr.easymodbus4j.codec.ModbusPduCodec;
 import com.github.zengfr.easymodbus4j.protocol.ModbusFunction;
 import com.github.zengfr.easymodbus4j.protocol.rtu.ModbusRtuFrame;
 import io.netty.buffer.ByteBuf;
 import io.netty.channel.ChannelHandlerContext;
 import io.netty.handler.codec.ByteToMessageCodec;
 import io.netty.util.internal.logging.InternalLogger;
 import io.netty.util.internal.logging.InternalLoggerFactory;
 import java.util.List;




















 public class ModbusRtuCodec
   extends ByteToMessageCodec<ModbusRtuFrame>
 {
/* 35 */   private static final InternalLogger log = InternalLoggerFactory.getInstance(ModbusRtuCodec.class);

   protected final ModbusPduCodec pduCodec;

/* 39 */   public ModbusRtuCodec(ModbusPduCodec pduCodec) { this.pduCodec = pduCodec; }



   protected void encode(ChannelHandlerContext ctx, ModbusRtuFrame msg, ByteBuf out) throws Exception {
/* 44 */     log.debug("encode");
/* 45 */     out.writeBytes(msg.encode());
   }


   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/* 50 */     log.debug("decode");
/* 51 */     int startIndex = in.readerIndex();
/* 52 */     while (in.readableBytes() >= ModbusRtuCodecUtil.getMessageMinSize() && in.readableBytes() >= ModbusRtuCodecUtil.getMessageLength(in, startIndex)) {
/* 53 */       short unitId = in.readUnsignedByte();
/* 54 */       ModbusFunction function = (ModbusFunction)this.pduCodec.decode(in);
/* 55 */       int crc = in.readUnsignedShort();

/* 57 */       ModbusRtuFrame frame = new ModbusRtuFrame(unitId, function);
/* 58 */       if (frame != null) {
/* 59 */         out.add(frame);
       }
/* 61 */       startIndex = in.readerIndex();
     }
   }
 }


/* Location:              D:\logs\easymodbus4j-codec-0.0.5.jar!\com\github\zengfr\easymodbus4j\codec\rtu\ModbusRtuCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */