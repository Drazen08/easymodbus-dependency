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
/*     */ public class HexUtil
/*     */ {
/*     */   public static String str2HexStr(String str) {
/*  21 */     char[] chars = "0123456789ABCDEF".toCharArray();
/*  22 */     StringBuilder sb = new StringBuilder("");
/*  23 */     byte[] bs = str.getBytes();
/*     */     
/*  25 */     for (int i = 0; i < bs.length; i++) {
/*  26 */       int bit = (bs[i] & 0xF0) >> 4;
/*  27 */       sb.append(chars[bit]);
/*  28 */       bit = bs[i] & 0xF;
/*  29 */       sb.append(chars[bit]);
/*     */     } 
/*  31 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public static byte[] hexStringToByte(String hex) {
/*  35 */     int len = hex.length() / 2;
/*  36 */     byte[] result = new byte[len];
/*  37 */     char[] achar = hex.toCharArray();
/*  38 */     for (int i = 0; i < len; i++) {
/*  39 */       int pos = i * 2;
/*  40 */       result[i] = (byte)(charToByte(achar[pos]) << 4 | charToByte(achar[pos + 1]));
/*     */     } 
/*  42 */     return result;
/*     */   }
/*     */   
/*     */   private static int charToByte(char c) {
/*  46 */     byte b = (byte)"0123456789ABCDEF".indexOf(c);
/*  47 */     return b;
/*     */   }
/*     */ 
/*     */   
/*  51 */   public static final String bytesToHexString(byte[] bArray) { return bytesToHexString(bArray, ""); }
/*     */ 
/*     */ 
/*     */   
/*  55 */   public static final String bytesToHexString(byte[] bArray, String split) { return bytesToHexString(bArray, -1, split); }
/*     */ 
/*     */ 
/*     */   
/*  59 */   public static final String bytesToHexString(byte[] bArray, int len) { return bytesToHexString(bArray, len, " "); }
/*     */ 
/*     */   
/*     */   public static final String bytesToHexString(byte[] bArray, int len, String split) {
/*  63 */     StringBuffer sb = new StringBuffer(bArray.length);
/*     */     
/*  65 */     if (len < 0)
/*  66 */       len = bArray.length; 
/*  67 */     for (int i = 0; i < len; i++) {
/*  68 */       if (split != null && split.length() > 0)
/*  69 */         sb.append(split); 
/*  70 */       String sTemp = Integer.toHexString(0xFF & bArray[i]);
/*  71 */       if (sTemp.length() < 2)
/*  72 */         sb.append(0); 
/*  73 */       sb.append(sTemp.toUpperCase());
/*     */     } 
/*  75 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*  79 */   public static int isOdd(int num) { return num & 0x1; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   public static int HexToInt(String inHex) { return Integer.parseInt(inHex, 16); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   public static byte HexToByte(String inHex) { return (byte)Integer.parseInt(inHex, 16); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   public static String Byte2Hex(Byte inByte) { return String.format("%02x", new Object[] { inByte }).toUpperCase(); }
/*     */ }


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4j\commo\\util\HexUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */