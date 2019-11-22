 package com.github.sunjx.modbus.func.response;

 import com.github.zengfr.easymodbus4j.func.AbstractReadResponse;
 import io.netty.buffer.ByteBuf;
 import io.netty.buffer.Unpooled;
 import java.util.BitSet;



















 public class ReadDiscreteInputsResponse
   extends AbstractReadResponse
 {
   private short byteCount;
   private BitSet inputStatus;

/* 32 */   public ReadDiscreteInputsResponse() { super((short)2); }


   public ReadDiscreteInputsResponse(BitSet inputStatus) {
/* 36 */     super((short)2);

/* 38 */     byte[] inputs = inputStatus.toByteArray();


/* 41 */     if (inputs.length > 250) {
/* 42 */       throw new IllegalArgumentException();
     }

/* 45 */     this.byteCount = (short)inputs.length;
/* 46 */     this.inputStatus = inputStatus;
   }


/* 50 */   public BitSet getInputStatus() { return this.inputStatus; }



/* 54 */   public short getByteCount() { return this.byteCount; }




/* 59 */   public int calculateLength() { return 2 + this.byteCount; }



   public ByteBuf encode() {
/* 64 */     ByteBuf buf = Unpooled.buffer(calculateLength());
/* 65 */     buf.writeByte(getFunctionCode());
/* 66 */     buf.writeByte(this.byteCount);
/* 67 */     buf.writeBytes(this.inputStatus.toByteArray());

/* 69 */     return buf;
   }


   public void decode(ByteBuf data) {
/* 74 */     this.byteCount = data.readUnsignedByte();

/* 76 */     byte[] inputs = new byte[this.byteCount];
/* 77 */     data.readBytes(inputs);

/* 79 */     this.inputStatus = BitSet.valueOf(inputs);
   }



/* 84 */   public String toString() { return "ReadDiscreteInputsResponse{byteCount=" + this.byteCount + ", coilStatus=" + this.inputStatus + '}'; }
 }


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4j\func\response\ReadDiscreteInputsResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */