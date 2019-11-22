 package com.github.sunjx.modbus.event;

 public class SyncStateEvent
 {
   public boolean read;

   public SyncStateEvent(Short funcCode, boolean read, boolean write) {
/*  8 */     this.read = read;
/*  9 */     this.write = write;
/* 10 */     this.funcCode = funcCode;
   }

   public boolean write;
   public Short funcCode;
 }


/* Location:              D:\logs\easymodbus4j-0.0.5.jar!\com\github\zengfr\easymodbus4j\com.github.sunjx.modbus.event\SyncStateEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */