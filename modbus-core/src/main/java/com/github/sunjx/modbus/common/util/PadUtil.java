 package com.github.sunjx.modbus.common.util;

















 public class PadUtil
 {
/* 21 */   public static String padWhitespaceLeft(String s, int len) { return String.format("%1$" + len + "s", new Object[] { s }); }



/* 25 */   public static String padWhitespaceRight(String s, int len) { return String.format("%1$-" + len + "s", new Object[] { s }); }


   public static String padLeft(String src, int len, char ch) {
/* 29 */     int diff = len - src.length();
/* 30 */     if (diff <= 0) {
/* 31 */       return src;
     }

/* 34 */     char[] charr = new char[len];
/* 35 */     System.arraycopy(src.toCharArray(), 0, charr, 0, src.length());
/* 36 */     for (int i = src.length(); i < len; i++) {
/* 37 */       charr[i] = ch;
     }
/* 39 */     return new String(charr);
   }

   public static String padRight(String src, int len, char ch) {
/* 43 */     int diff = len - src.length();
/* 44 */     if (diff <= 0) {
/* 45 */       return src;
     }

/* 48 */     char[] charr = new char[len];
/* 49 */     System.arraycopy(src.toCharArray(), 0, charr, diff, src.length());
/* 50 */     for (int i = 0; i < diff; i++) {
/* 51 */       charr[i] = ch;
     }
/* 53 */     return new String(charr);
   }
 }


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4j\commo\\util\PadUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */