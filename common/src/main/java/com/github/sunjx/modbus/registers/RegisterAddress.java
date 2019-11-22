 package com.github.sunjx.modbus.registers;
















 public class RegisterAddress
 {
   private int type;
   private int offset;

   public RegisterAddress(int range, int offset) {
/* 24 */     this.type = range;
/* 25 */     this.offset = offset;
   }

   public RegisterAddress(int address) {
/* 29 */     if (address < 10000) {
/* 30 */       this.type = 1;
/* 31 */       this.offset = address - 1;
/* 32 */     } else if (address < 20000) {
/* 33 */       this.type = 2;
/* 34 */       this.offset = address - 10001;
/* 35 */     } else if (address < 40000) {
/* 36 */       this.type = 4;
/* 37 */       this.offset = address - 30001;
     } else {
/* 39 */       this.type = 3;
/* 40 */       this.offset = address - 40001;
     }
   }


/* 45 */   public int getType() { return this.type; }



/* 49 */   public int getOffset() { return this.offset; }
 }


/* Location:              D:\logs\easymodbus4j-0.0.5.jar!\com\github\zengfr\easymodbus4j\com.github.sunjx.modbus.registers\RegisterAddress.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */