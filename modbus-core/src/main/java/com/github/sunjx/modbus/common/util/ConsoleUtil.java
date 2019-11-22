 package com.github.sunjx.modbus.common.util;
















 public class ConsoleUtil
 {
   public static void clearConsole(boolean clear) {
     try {
/* 22 */       if (clear) {
/* 23 */         String os = System.getProperty("os.name");
/* 24 */         if (os.contains("Windows")) {
/* 25 */           (new ProcessBuilder(new String[] { "cmd", "/c", "cls" })).inheritIO().start().waitFor();
         } else {
/* 27 */           Runtime.getRuntime().exec("clear");
         }
       }
/* 30 */     } catch (Exception e) {
/* 31 */       e.printStackTrace();
     }
   }
 }


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4j\commo\\util\ConsoleUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */