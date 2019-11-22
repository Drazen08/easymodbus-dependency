 package com.github.sunjx.modbus.codec.ping;

 import io.netty.buffer.ByteBuf;
 import io.netty.channel.ChannelHandlerContext;
 import io.netty.handler.codec.MessageToMessageDecoder;
 import io.netty.util.internal.logging.InternalLogger;
 import io.netty.util.internal.logging.InternalLoggerFactory;
 import java.nio.charset.Charset;
 import java.util.List;




















 public class ModbusPingDecoder
   extends MessageToMessageDecoder<ByteBuf>
 {
/* 33 */   private static final InternalLogger log = InternalLoggerFactory.getInstance(ModbusPingDecoder.class);
   private final Charset charset;
   private final String heartBeat;

   public ModbusPingDecoder(String heartBeat, Charset charset) {
/* 38 */     if (charset == null || heartBeat == null) {
/* 39 */       throw new NullPointerException("heartBeat/charset");
     }
/* 41 */     this.heartBeat = heartBeat;
/* 42 */     this.charset = charset;
   }


   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/* 47 */     in.retain();
/* 48 */     boolean success = false;
/* 49 */     int heartBeatByteLen = this.heartBeat.length();
/* 50 */     if (in.readableBytes() >= heartBeatByteLen) {
/* 51 */       byte[] headArray = new byte[heartBeatByteLen];
/* 52 */       in.markReaderIndex();
/* 53 */       in.readBytes(headArray);
/* 54 */       String head = new String(headArray, this.charset);
/* 55 */       success = this.heartBeat.equalsIgnoreCase(head);
/* 56 */       if (success) {
/* 57 */         log.debug(String.format("decode isHeartBeat:%s", new Object[] { Boolean.valueOf(success) }));
/* 58 */         out.add(head);
       } else {
/* 60 */         in.resetReaderIndex();
       }
     }
/* 63 */     if (!success)
/* 64 */       ctx.fireChannelRead(in); 
   }
 }


/* Location:              D:\logs\easymodbus4j-codec-0.0.5.jar!\com\github\zengfr\easymodbus4j\codec\ping\ModbusPingDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */