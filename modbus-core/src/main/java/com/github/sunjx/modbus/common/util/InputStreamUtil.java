 package com.github.sunjx.modbus.common.util;

 import java.io.File;
 import java.io.FileInputStream;
 import java.io.FileNotFoundException;
 import java.io.InputStream;
















 public class InputStreamUtil
 {
/* 25 */   protected static String path = System.getProperty("user.dir");
   public static InputStream getStream(String resourceName) throws FileNotFoundException {
/* 27 */     InputStream input = null;
/* 28 */     if (input == null) {
/* 29 */       input = getFileStream(path + "/" + resourceName);
     }
/* 31 */     if (input == null) {
/* 32 */       input = getClassResourceStream(resourceName);
     }
/* 34 */     if (input == null) {
/* 35 */       input = getClassLoaderResourceStream(resourceName);
     }

/* 38 */     return input;
   }

   public static InputStream getFileStream(String resourceName) throws FileNotFoundException {
/* 42 */     if ((new File(resourceName)).exists())
/* 43 */       return new FileInputStream(resourceName); 
/* 44 */     return null;
   }


/* 48 */   public static InputStream getClassResourceStream(String resourceName) { return getClassResourceStream(InputStreamUtil.class, resourceName); }



/* 52 */   public static <T> InputStream getClassResourceStream(Class<T> cls, String resourceName) { return cls.getResourceAsStream(resourceName); }



/* 56 */   public static InputStream getClassLoaderResourceStream(String resourceName) { return InputStreamUtil.class.getClassLoader().getResourceAsStream(resourceName); }
 }


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4j\commo\\util\InputStreamUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */