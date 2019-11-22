 package com.github.sunjx.modbus.common.util;
















 public class ParseStringUtil
 {
   public static Integer parseInt(String[] vs, int index, Integer def) {
/* 21 */     if (vs != null && vs.length > index && !vs[index].isEmpty()) {
/* 22 */       return Integer.valueOf(vs[index]);
     }
/* 24 */     return def;
   }
   public static Short parseShort(String[] vs, int index, Short def) {
/* 27 */     if (vs != null && vs.length > index && !vs[index].isEmpty()) {
/* 28 */       return Short.valueOf(vs[index]);
     }
/* 30 */     return def;
   }
   public static String parseString(String[] vs, int index, String def) {
/* 33 */     if (vs != null && vs.length > index && !vs[index].isEmpty()) {
/* 34 */       return vs[index];
     }
/* 36 */     return def;
   }

   public static boolean[] parseBooleanArray(String[] v) {
/* 40 */     boolean[] values = new boolean[v.length];
/* 41 */     for (int i = 0; i < v.length; i++) {
/* 42 */       values[i] = parseBoolean(v[i]).booleanValue();
     }
/* 44 */     return values;
   }

   public static Boolean parseBoolean(String[] vs, int index, boolean def) {
/* 48 */     if (vs != null && vs.length > index) {
/* 49 */       return parseBoolean(vs[index]);
     }
/* 51 */     return Boolean.valueOf(def);
   }


/* 55 */   public static Boolean parseBoolean(String v) { return Boolean.valueOf((v != null && (Boolean.valueOf(v).booleanValue() || "T".contains(v) || "true".contains(v)))); }
 }


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4j\commo\\util\ParseStringUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */