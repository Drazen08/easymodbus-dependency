 package com.github.sunjx.modbus.codec.tcp;

 import io.netty.buffer.ByteBuf;
 import io.netty.channel.ChannelHandlerContext;
 import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
 import io.netty.util.internal.logging.InternalLogger;
 import io.netty.util.internal.logging.InternalLoggerFactory;


 public class ModbusTcpLFBFrameDecoder
   extends LengthFieldBasedFrameDecoder
 {
/* 32 */   private static final InternalLogger log = InternalLoggerFactory.getInstance(ModbusTcpLFBFrameDecoder.class);
   private boolean skipUnknowBytes = false;

   public ModbusTcpLFBFrameDecoder(boolean skipUnknowBytes) {
/* 36 */     super(260, 4, 2);
/* 37 */     this.skipUnknowBytes = skipUnknowBytes;
   }


/* 41 */   public ModbusTcpLFBFrameDecoder() { this(false); }



   @Override
   protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
/* 46 */     if (this.skipUnknowBytes) {
/* 47 */       boolean skip = false;
/* 48 */       int i = in.readableBytes();
/* 49 */       if (i < 8) {
/* 50 */         skip = true;
       }
/* 52 */       if (i > 260) {
/* 53 */         skip = true;
       }
/* 55 */       if (skip) {
/* 56 */         in.skipBytes(in.readableBytes());
/* 57 */         log.debug(String.format("skip Bytes:%s;%s;", new Object[] { Boolean.valueOf(skip), Integer.valueOf(i) }));
       }
     }
/* 60 */     Object decoded = super.decode(ctx, in);
/* 61 */     return decoded;
   }
 }


/* Location:              D:\logs\easymodbus4j-codec-0.0.5.jar!\com\github\zengfr\easymodbus4j\codec\tcp\ModbusTcpLFBFrameDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */