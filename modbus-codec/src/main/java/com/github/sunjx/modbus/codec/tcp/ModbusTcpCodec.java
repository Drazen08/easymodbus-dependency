 package com.github.sunjx.modbus.codec.tcp;

 import com.github.zengfr.easymodbus4j.codec.ModbusPduCodec;
 import com.github.zengfr.easymodbus4j.protocol.ModbusFunction;
 import com.github.zengfr.easymodbus4j.protocol.tcp.ModbusFrame;
 import com.github.zengfr.easymodbus4j.protocol.tcp.ModbusHeader;
 import io.netty.buffer.ByteBuf;
 import io.netty.channel.ChannelHandlerContext;
 import io.netty.handler.codec.ByteToMessageCodec;
 import io.netty.util.internal.logging.InternalLogger;
 import io.netty.util.internal.logging.InternalLoggerFactory;
 import java.util.List;




















 public class ModbusTcpCodec
   extends ByteToMessageCodec<ModbusFrame>
 {
/* 36 */   private static final InternalLogger log = InternalLoggerFactory.getInstance(ModbusTcpCodec.class);

   private final ModbusPduCodec pduCodec;

/* 40 */   public ModbusTcpCodec(ModbusPduCodec pduCodec) { this.pduCodec = pduCodec; }



   protected void encode(ChannelHandlerContext ctx, ModbusFrame msg, ByteBuf out) throws Exception {
/* 45 */     log.debug("encode");
/* 46 */     ctx.writeAndFlush(msg.encode());
   }


   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/* 51 */     log.debug("decode");
/* 52 */     ModbusHeader mbapHeader = ModbusHeader.decode(in);
/* 53 */     ModbusFunction function = (ModbusFunction)this.pduCodec.decode(in);
/* 54 */     ModbusFrame frame = new ModbusFrame(mbapHeader, function);
/* 55 */     if (frame != null)
/* 56 */       out.add(frame); 
   }
 }


/* Location:              D:\logs\easymodbus4j-codec-0.0.5.jar!\com\github\zengfr\easymodbus4j\codec\tcp\ModbusTcpCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */