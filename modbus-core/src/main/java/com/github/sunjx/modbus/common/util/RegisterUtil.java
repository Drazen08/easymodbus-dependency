/*     */ package com.github.sunjx.modbus.common.util;
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
/*     */ public class RegisterUtil
/*     */ {
/*  22 */   public static final String toHex(byte[] data) { return toHex(data, 0, data.length); }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String toHex(byte[] data, int off, int length) {
/*  27 */     StringBuffer buf = new StringBuffer(data.length * 2);
/*  28 */     for (int i = off; i < length; i++) {
/*     */       
/*  30 */       if ((data[i] & 0xFF) < 16) {
/*  31 */         buf.append("0");
/*     */       }
/*  33 */       buf.append(Long.toString((data[i] & 0xFF), 16));
/*  34 */       if (i < data.length - 1) {
/*  35 */         buf.append(" ");
/*     */       }
/*     */     } 
/*  38 */     return buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final byte[] toHex(int i) {
/*  50 */     StringBuffer buf = new StringBuffer(2);
/*     */     
/*  52 */     if ((i & 0xFF) < 16) {
/*  53 */       buf.append("0");
/*     */     }
/*  55 */     buf.append(Long.toString((i & 0xFF), 16).toUpperCase());
/*  56 */     return buf.toString().getBytes();
/*     */   }
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
/*  76 */   public static final int registerToUnsignedShort(byte[] bytes) { return (bytes[0] & 0xFF) << 8 | bytes[1] & 0xFF; }
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
/*     */   public static final byte[] unsignedShortToRegister(int v) {
/*  98 */     byte[] register = new byte[2];
/*  99 */     register[0] = (byte)(0xFF & v >> 8);
/* 100 */     register[1] = (byte)(0xFF & v);
/* 101 */     return register;
/*     */   }
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
/* 121 */   public static final short registerToShort(byte[] bytes) { return (short)(bytes[0] << 8 | bytes[1] & 0xFF); }
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
/* 142 */   public static final short registerToShort(byte[] bytes, int idx) { return (short)(bytes[idx] << 8 | bytes[idx + 1] & 0xFF); }
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
/*     */   public static final byte[] shortToRegister(short s) {
/* 161 */     byte[] register = new byte[2];
/* 162 */     register[0] = (byte)(0xFF & s >> 8);
/* 163 */     register[1] = (byte)(0xFF & s);
/* 164 */     return register;
/*     */   }
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
/* 183 */   public static final int registersToInt(byte[] bytes) { return (bytes[0] & 0xFF) << 24 | (bytes[1] & 0xFF) << 16 | (bytes[2] & 0xFF) << 8 | bytes[3] & 0xFF; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final byte[] intToRegisters(int v) {
/* 193 */     byte[] registers = new byte[4];
/* 194 */     registers[0] = (byte)(0xFF & v >> 24);
/* 195 */     registers[1] = (byte)(0xFF & v >> 16);
/* 196 */     registers[2] = (byte)(0xFF & v >> 8);
/* 197 */     registers[3] = (byte)(0xFF & v);
/* 198 */     return registers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 208 */   public static final long registersToLong(byte[] bytes) { return (bytes[0] & 0xFF) << 56L | (bytes[1] & 0xFF) << 48L | (bytes[2] & 0xFF) << 40L | (bytes[3] & 0xFF) << 32L | (bytes[4] & 0xFF) << 24L | (bytes[5] & 0xFF) << 16L | (bytes[6] & 0xFF) << 8L | (bytes[7] & 0xFF); }
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
/*     */   public static final byte[] longToRegisters(long v) {
/* 220 */     byte[] registers = new byte[8];
/* 221 */     registers[0] = (byte)(int)(0xFFL & v >> 56L);
/* 222 */     registers[1] = (byte)(int)(0xFFL & v >> 48L);
/* 223 */     registers[2] = (byte)(int)(0xFFL & v >> 40L);
/* 224 */     registers[3] = (byte)(int)(0xFFL & v >> 32L);
/* 225 */     registers[4] = (byte)(int)(0xFFL & v >> 24L);
/* 226 */     registers[5] = (byte)(int)(0xFFL & v >> 16L);
/* 227 */     registers[6] = (byte)(int)(0xFFL & v >> 8L);
/* 228 */     registers[7] = (byte)(int)(0xFFL & v);
/* 229 */     return registers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 239 */   public static final float registersToFloat(byte[] bytes) { return Float.intBitsToFloat((bytes[0] & 0xFF) << 24 | (bytes[1] & 0xFF) << 16 | (bytes[2] & 0xFF) << 8 | bytes[3] & 0xFF); }
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
/* 250 */   public static final byte[] floatToRegisters(float f) { return intToRegisters(Float.floatToIntBits(f)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 260 */   public static final double registersToDouble(byte[] bytes) { return Double.longBitsToDouble((bytes[0] & 0xFF) << 56L | (bytes[1] & 0xFF) << 48L | (bytes[2] & 0xFF) << 40L | (bytes[3] & 0xFF) << 32L | (bytes[4] & 0xFF) << 24L | (bytes[5] & 0xFF) << 16L | (bytes[6] & 0xFF) << 8L | (bytes[7] & 0xFF)); }
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
/* 272 */   public static final byte[] doubleToRegisters(double d) { return longToRegisters(Double.doubleToLongBits(d)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 282 */   public static final int unsignedByteToInt(byte b) { return b & 0xFF; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 292 */   public static final byte lowByte(int wd) { return (new Integer(0xFF & wd)).byteValue(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 301 */   public static final byte hiByte(int wd) { return (new Integer(0xFF & wd >> 8)).byteValue(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int makeWord(int hibyte, int lowbyte) {
/* 311 */     int hi = 0xFF & hibyte;
/* 312 */     int low = 0xFF & lowbyte;
/* 313 */     return hi << 8 | low;
/*     */   }
/*     */ }


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4j\commo\\util\RegisterUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */