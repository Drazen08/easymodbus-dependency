 package com.github.sunjx.modbus.func.response;

 import com.github.zengfr.easymodbus4j.func.AbstractReadResponse;
 import io.netty.buffer.ByteBuf;
 import io.netty.buffer.Unpooled;



















 public class ReadHoldingRegistersResponse
   extends AbstractReadResponse
 {
   private short byteCount;
   private int[] registers;

/* 31 */   public ReadHoldingRegistersResponse() { super((short)3); }


   public ReadHoldingRegistersResponse(int[] registers) {
/* 35 */     super((short)3);


/* 38 */     if (registers.length > 125) {
/* 39 */       throw new IllegalArgumentException();
     }

/* 42 */     this.byteCount = (short)(registers.length * 2);
/* 43 */     this.registers = registers;
   }


/* 47 */   public int[] getRegisters() { return this.registers; }



/* 51 */   public short getByteCount() { return this.byteCount; }




/* 56 */   public int calculateLength() { return 2 + this.byteCount; }



   public ByteBuf encode() {
/* 61 */     ByteBuf buf = Unpooled.buffer(calculateLength());
/* 62 */     buf.writeByte(getFunctionCode());
/* 63 */     buf.writeByte(this.byteCount);

/* 65 */     for (int i = 0; i < this.registers.length; i++) {
/* 66 */       buf.writeShort(this.registers[i]);
     }

/* 69 */     return buf;
   }


   public void decode(ByteBuf data) {
/* 74 */     this.byteCount = data.readUnsignedByte();

/* 76 */     this.registers = new int[this.byteCount / 2];
/* 77 */     for (int i = 0; i < this.registers.length; i++) {
/* 78 */       this.registers[i] = data.readUnsignedShort();
     }
   }


   public String toString() {
/* 84 */     StringBuilder registersStr = new StringBuilder();
/* 85 */     registersStr.append("{");
/* 86 */     for (int i = 0; i < this.registers.length; i++) {
/* 87 */       registersStr.append(i);
/* 88 */       registersStr.append("=");
/* 89 */       registersStr.append(this.registers[i]);
/* 90 */       registersStr.append(",");
     }
/* 92 */     registersStr.delete(registersStr.length() - 1, registersStr.length());
/* 93 */     registersStr.append("}");

/* 95 */     return "ReadHoldingRegistersResponse{byteCount=" + this.byteCount + ", inputRegisters=" + registersStr + '}';
   }
 }


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4j\func\response\ReadHoldingRegistersResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */