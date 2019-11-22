/*     */ package com.github.sunjx.modbus.func.request;
/*     */ 
/*     */ import com.github.sunjx.modbus.func.AbstractRequest;
import io.netty.buffer.ByteBuf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WriteMultipleRegistersRequest
/*     */   extends AbstractRequest
/*     */ {
/*     */   private short byteCount;
/*     */   private int[] registers;
/*     */   
/*  33 */   public WriteMultipleRegistersRequest() { super((short)16); }
/*     */ 
/*     */   
/*     */   public WriteMultipleRegistersRequest(int startingAddress, int quantityOfRegisters, int[] registers) {
/*  37 */     super((short)16, startingAddress, quantityOfRegisters);
/*     */ 
/*     */     
/*  40 */     if (registers.length > 125) {
/*  41 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/*  44 */     this.byteCount = (short)(registers.length * 2);
/*  45 */     this.registers = registers;
/*     */   }
/*     */ 
/*     */   
/*  49 */   public short getByteCount() { return this.byteCount; }
/*     */ 
/*     */ 
/*     */   
/*  53 */   public int getQuantityOfRegisters() { return this.value; }
/*     */ 
/*     */ 
/*     */   
/*  57 */   public int getStartingAddress() { return this.address; }
/*     */ 
/*     */ 
/*     */   
/*  61 */   public int[] getRegisters() { return this.registers; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   @Override
public int calculateLength() { return super.calculateLength() + 1 + this.byteCount; }
/*     */ 
/*     */ 
/*     */   
/*     */   @Override
public ByteBuf encode() {
/*  71 */     ByteBuf buf = super.encode();
/*  72 */     buf.writeByte(this.byteCount);
/*  73 */     for (int i = 0; i < this.registers.length; i++) {
/*  74 */       buf.writeShort(this.registers[i]);
/*     */     }
/*     */     
/*  77 */     return buf;
/*     */   }
/*     */ 
/*     */   
/*     */   public void decode(ByteBuf data) {
/*  82 */     super.decode(data);
/*  83 */     this.byteCount = data.readUnsignedByte();
/*  84 */     this.registers = new int[this.byteCount / 2];
/*  85 */     for (int i = 0; i < this.registers.length; i++) {
/*  86 */       this.registers[i] = data.readUnsignedShort();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  92 */     StringBuilder registersStr = new StringBuilder();
/*  93 */     registersStr.append("{");
/*  94 */     for (int i = 0; i < this.registers.length; i++) {
/*  95 */       registersStr.append(i);
/*  96 */       registersStr.append("=");
/*  97 */       registersStr.append(this.registers[i]);
/*  98 */       registersStr.append(",");
/*     */     } 
/* 100 */     registersStr.delete(registersStr.length() - 1, registersStr.length());
/* 101 */     registersStr.append("}");
/*     */     
/* 103 */     return "WriteMultipleRegistersRequest{startingAddress=" + this.address + ", quantityOfRegisters=" + this.value + ", byteCount=" + this.byteCount + ", com.github.sunjx.modbus.registers=" + registersStr + '}';
/*     */   }
/*     */ }


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4j\func\request\WriteMultipleRegistersRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */