 package com.github.sunjx.modbus.func.response;

 import com.github.zengfr.easymodbus4j.func.AbstractReadResponse;
 import io.netty.buffer.ByteBuf;
 import io.netty.buffer.Unpooled;
 import java.util.BitSet;



















 public class ReadCoilsResponse
   extends AbstractReadResponse
 {
   private short byteCount;
   private BitSet coilStatus;

/* 32 */   public ReadCoilsResponse() { super((short)1); }


   public ReadCoilsResponse(BitSet coilStatus) {
/* 36 */     super((short)1);

/* 38 */     byte[] coils = coilStatus.toByteArray();


/* 41 */     if (coils.length > 250) {
/* 42 */       throw new IllegalArgumentException();
     }

/* 45 */     this.byteCount = (short)coils.length;
/* 46 */     this.coilStatus = coilStatus;
   }


/* 50 */   public BitSet getCoilStatus() { return this.coilStatus; }



/* 54 */   public short getByteCount() { return this.byteCount; }




/* 59 */   @Override
public int calculateLength() { return 2 + this.byteCount; }



   @Override
   public ByteBuf encode() {
/* 64 */     ByteBuf buf = Unpooled.buffer(calculateLength());
/* 65 */     buf.writeByte(getFunctionCode());
/* 66 */     buf.writeByte(this.byteCount);
/* 67 */     buf.writeBytes(this.coilStatus.toByteArray());

/* 69 */     return buf;
   }


   public void decode(ByteBuf data) {
/* 74 */     this.byteCount = data.readUnsignedByte();

/* 76 */     byte[] coils = new byte[this.byteCount];
/* 77 */     data.readBytes(coils);

/* 79 */     this.coilStatus = BitSet.valueOf(coils);
   }



/* 84 */   public String toString() { return "ReadCoilsResponse{byteCount=" + this.byteCount + ", coilStatus=" + this.coilStatus + '}'; }
 }


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4j\func\response\ReadCoilsResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */