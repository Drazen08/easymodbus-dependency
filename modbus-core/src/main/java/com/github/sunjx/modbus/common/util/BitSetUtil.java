/*     */ package com.github.sunjx.modbus.common.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.BitSet;
/*     */ import java.util.List;
/*     */ import java.util.Random;
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
/*     */ public class BitSetUtil
/*     */ {
/*  27 */   public static BitSet copy(BitSet b) { return (BitSet)b.clone(); }
/*     */ 
/*     */ 
/*     */   
/*     */   public static BitSet getRandomBits(int n, Random random) {
/*  32 */     BitSet bits = new BitSet(n);
/*  33 */     for (int i = 0; i < n; i++) {
/*  34 */       bits.set(i, random.nextBoolean());
/*     */     }
/*  36 */     return bits;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String toString(BitSet bits, int n) {
/*  41 */     StringBuilder sb = new StringBuilder();
/*  42 */     for (int i = 0; i < n; i++) {
/*  43 */       sb.append(bits.get(i) ? "1 " : "0 ");
/*     */     }
/*  45 */     return sb.toString();
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
/*  56 */   public static String toString(BitSet bits) { return toString(bits, bits.length()); }
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
/*     */   public static boolean innerProduct(BitSet a, BitSet b) {
/*  69 */     BitSet buffer = copy(a);
/*  70 */     buffer.and(b);
/*  71 */     return isOdd(buffer.cardinality());
/*     */   }
/*     */ 
/*     */   
/*  75 */   private static boolean isOdd(int n) { return (n % 2 != 0); }
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
/*     */   public static BitSet fromList(List<Boolean> list) {
/*  89 */     BitSet bitset = new BitSet(list.size());
/*  90 */     for (int i = 0; i < list.size(); i++) {
/*  91 */       bitset.set(i, ((Boolean)list.get(i)).booleanValue());
/*     */     }
/*  93 */     return bitset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BitSet fromArray(boolean[] array) {
/* 103 */     BitSet bitset = new BitSet(array.length);
/* 104 */     for (int i = 0; i < array.length; i++) {
/* 105 */       bitset.set(i, array[i]);
/*     */     }
/* 107 */     return bitset;
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
/*     */   public static boolean[] toArray(BitSet bitset, int length) {
/* 119 */     if (length < 0) {
/* 120 */       throw new IllegalArgumentException("Size of array must not be negative but was + " + length);
/*     */     }
/* 122 */     boolean[] array = new boolean[length];
/* 123 */     for (int i = 0; i < length; i++) {
/* 124 */       array[i] = bitset.get(i);
/*     */     }
/* 126 */     return array;
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
/*     */   public static List<Boolean> toList(BitSet bitset, int n) {
/* 138 */     if (n < 0) {
/* 139 */       throw new IllegalArgumentException("Size of list must not be negative but was + " + n);
/*     */     }
/* 141 */     List<Boolean> list = new ArrayList<>(n);
/* 142 */     for (int i = 0; i < n; i++) {
/* 143 */       list.add(Boolean.valueOf(bitset.get(i)));
/*     */     }
/* 145 */     return list;
/*     */   }
/*     */ }


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4j\commo\\util\BitSetUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */