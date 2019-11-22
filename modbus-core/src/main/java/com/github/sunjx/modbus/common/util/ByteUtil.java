/*     */ package com.github.sunjx.modbus.common.util;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.DoubleBuffer;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.nio.LongBuffer;
/*     */ import java.nio.ShortBuffer;
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
/*     */ public class ByteUtil
/*     */ {
/*     */   public static byte[] toByteArrayInt(int[] values) {
/*  30 */     ByteBuffer bf = ByteBuffer.allocate(4 * values.length);
/*  31 */     for (int value : values)
/*  32 */       bf.putInt(value); 
/*  33 */     return bf.array();
/*     */   }
/*     */   
/*     */   public static byte[] floatToByte(float[] input) {
/*  37 */     byte[] ret = new byte[input.length * 4];
/*  38 */     for (int x = 0; x < input.length; x++) {
/*  39 */       ByteBuffer.wrap(ret, x * 4, 4).putFloat(input[x]);
/*     */     }
/*  41 */     return ret;
/*     */   }
/*     */   
/*     */   public static float[] byteToFloat(byte[] input) {
/*  45 */     float[] ret = new float[input.length / 4];
/*  46 */     for (int x = 0; x < input.length; x += 4) {
/*  47 */       ret[x / 4] = ByteBuffer.wrap(input, x, 4).getFloat();
/*     */     }
/*  49 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*  53 */   public static float[] toFloatArray(byte[] bytes) { return toFloatArray(bytes, true); }
/*     */ 
/*     */   
/*     */   public static float[] toFloatArray(byte[] bytes, boolean bigEndian) {
/*  57 */     ByteBuffer buffer = ByteBuffer.wrap(bytes).order(bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
/*  58 */     FloatBuffer fb = buffer.asFloatBuffer();
/*  59 */     float[] floatArray = new float[fb.limit()];
/*  60 */     fb.get(floatArray);
/*  61 */     return floatArray;
/*     */   }
/*     */ 
/*     */   
/*  65 */   public static double[] toDoubleArray(byte[] bytes) { return toDoubleArray(bytes, true); }
/*     */ 
/*     */   
/*     */   public static double[] toDoubleArray(byte[] bytes, boolean bigEndian) {
/*  69 */     ByteBuffer buffer = ByteBuffer.wrap(bytes).order(bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
/*  70 */     DoubleBuffer db = buffer.asDoubleBuffer();
/*  71 */     double[] doubleArray = new double[db.limit()];
/*  72 */     db.get(doubleArray);
/*  73 */     return doubleArray;
/*     */   }
/*     */ 
/*     */   
/*  77 */   public static long[] toLongArray(byte[] bytes) { return toLongArray(bytes, true); }
/*     */ 
/*     */   
/*     */   public static long[] toLongArray(byte[] bytes, boolean bigEndian) {
/*  81 */     ByteBuffer buffer = ByteBuffer.wrap(bytes).order(bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
/*  82 */     LongBuffer lb = buffer.asLongBuffer();
/*  83 */     long[] longArray = new long[lb.limit()];
/*  84 */     lb.get(longArray);
/*  85 */     return longArray;
/*     */   }
/*     */ 
/*     */   
/*  89 */   public static int[] toIntArray(byte[] bytes) { return toIntArray(bytes, true); }
/*     */ 
/*     */   
/*     */   public static int[] toIntArray(byte[] bytes, boolean bigEndian) {
/*  93 */     ByteBuffer buffer = ByteBuffer.wrap(bytes).order(bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
/*  94 */     IntBuffer ib = buffer.asIntBuffer();
/*  95 */     int[] intArray = new int[ib.limit()];
/*  96 */     ib.get(intArray);
/*  97 */     return intArray;
/*     */   }
/*     */ 
/*     */   
/* 101 */   public static int[] toUShortArray(byte[] bytes) { return toUShortArray(bytes, true); }
/*     */ 
/*     */   
/*     */   public static int[] toUShortArray(byte[] bytes, boolean bigEndian) {
/* 105 */     short[] shortArray = toShortArray(bytes, bigEndian);
/* 106 */     int[] ushortArray = new int[shortArray.length];
/* 107 */     for (int i = 0; i < shortArray.length; i++) {
/* 108 */       ushortArray[i] = shortArray[i] & 0xFFFF;
/*     */     }
/* 110 */     return ushortArray;
/*     */   }
/*     */ 
/*     */   
/* 114 */   public static short[] toShortArray(byte[] bytes) { return toShortArray(bytes, true); }
/*     */ 
/*     */   
/*     */   public static short[] toShortArray(byte[] bytes, boolean bigEndian) {
/* 118 */     ByteBuffer buffer = ByteBuffer.wrap(bytes).order(bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
/* 119 */     ShortBuffer sb = buffer.asShortBuffer();
/* 120 */     short[] shortArray = new short[sb.limit()];
/* 121 */     sb.get(shortArray);
/* 122 */     return shortArray;
/*     */   }
/*     */ 
/*     */   
/* 126 */   public static char[] toCharArray(byte[] bytes) { return toCharArray(bytes, true); }
/*     */ 
/*     */   
/*     */   public static char[] toCharArray(byte[] bytes, boolean bigEndian) {
/* 130 */     ByteBuffer buffer = ByteBuffer.wrap(bytes).order(bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
/* 131 */     CharBuffer cb = buffer.asCharBuffer();
/* 132 */     char[] charArray = new char[cb.limit()];
/* 133 */     cb.get(charArray);
/* 134 */     return charArray;
/*     */   }
/*     */ }


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4j\commo\\util\ByteUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */