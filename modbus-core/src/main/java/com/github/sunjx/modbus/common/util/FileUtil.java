 package com.github.sunjx.modbus.common.util;

 import java.io.BufferedReader;
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.FileNotFoundException;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.InputStreamReader;
 import java.io.Reader;
 import java.nio.charset.Charset;
 import java.util.ArrayList;
 import java.util.List;
















 public class FileUtil
 {
   public static List<String> readLines(File file, Charset encoding) throws IOException {
/* 33 */     try (InputStream in = openInputStream(file)) {
/* 34 */       return readLines(in, encoding);
     }
   }
   public static List<String> readLines(InputStream input, Charset encoding) throws IOException {
/* 38 */     InputStreamReader reader = new InputStreamReader(input, encoding);
/* 39 */     return readLines(reader);
   }

/* 42 */   public static BufferedReader toBufferedReader(Reader reader) { return (reader instanceof BufferedReader) ? (BufferedReader)reader : new BufferedReader(reader); }

   public static List<String> readLines(Reader input) throws IOException {
/* 45 */     BufferedReader reader = toBufferedReader(input);
/* 46 */     List<String> list = new ArrayList<>();
/* 47 */     String line = reader.readLine();
/* 48 */     while (line != null) {
/* 49 */       list.add(line);
/* 50 */       line = reader.readLine();
     }
/* 52 */     return list;
   }
   public static FileInputStream openInputStream(File file) throws IOException {
/* 55 */     if (file.exists()) {
/* 56 */       if (file.isDirectory()) {
/* 57 */         throw new IOException("File '" + file + "' exists but is a directory");
       }
/* 59 */       if (!file.canRead()) {
/* 60 */         throw new IOException("File '" + file + "' cannot be read");
       }
     } else {
/* 63 */       throw new FileNotFoundException("File '" + file + "' does not exist");
     }
/* 65 */     return new FileInputStream(file);
   }
 }


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4j\commo\\util\FileUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */