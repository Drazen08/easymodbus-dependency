 package com.github.sunjx.modbus.common.util;

 import java.util.Arrays;
 import java.util.Collection;
 import java.util.List;
 import java.util.stream.Collector;
 import java.util.stream.Collectors;
















 public class IntArrayUtil
 {
/* 26 */   public static int[] toIntArray(Collection<Integer> values) { return values.stream().mapToInt(Integer::intValue).toArray(); }



/* 30 */   public static Integer[] toIntegerArray(int[] values) { return Arrays.stream(values).boxed().toArray(x$0 -> new Integer[x$0]); }




/* 35 */   public static List<Integer> toIntegerList(int[] values) { return Arrays.stream(values).boxed().collect((Collector)Collectors.toList()); }


/* 38 */   public static int[] toIntArray(Integer[] values) { return Arrays.stream(values).mapToInt(Integer::valueOf).toArray(); }


   public static int[] toIntArray(String[] values) {
/* 42 */     int[] ints = new int[values.length];
/* 43 */     for (int i = 0; i < values.length; i++) {
/* 44 */       ints[i] = Integer.valueOf(values[i]).intValue();
     }
/* 46 */     return ints;
   }
 }


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4j\commo\\util\IntArrayUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */