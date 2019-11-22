/*     */ package com.github.sunjx.modbus.common.util;
/*     */ 
/*     */ import com.github.zengfr.easymodbus4j.common.RegisterOrder;
/*     */ import java.nio.ByteBuffer;
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
/*     */ public class RegistersUtil
/*     */ {
/*     */   public static int[] getRandomRegisters(int length, int minInt, int maxInt, Random random) {
/*  26 */     int[] registers = new int[length];
/*  27 */     for (int i = 0; i < registers.length; i++) {
/*  28 */       registers[i] = minInt + random.nextInt(maxInt);
/*     */     }
/*  30 */     return registers;
/*     */   }
/*     */   
/*     */   public static byte[] toByteArray(int value) {
/*  34 */     byte[] result = new byte[2];
/*  35 */     result[1] = (byte)(value >> 8);
/*  36 */     result[0] = (byte)value;
/*  37 */     return result;
/*     */   }
/*     */ 
/*     */   
/*  41 */   public static byte[] toByteArrayShort(short value) { return ByteBuffer.allocate(2).putShort(value).array(); }
/*     */ 
/*     */ 
/*     */   
/*  45 */   public static byte[] toByteArrayInt(int value) { return ByteBuffer.allocate(4).putInt(value).array(); }
/*     */ 
/*     */ 
/*     */   
/*  49 */   public static byte[] toByteArrayLong(long value) { return ByteBuffer.allocate(8).putLong(value).array(); }
/*     */ 
/*     */ 
/*     */   
/*  53 */   public static byte[] toByteArrayDouble(double value) { return ByteBuffer.allocate(8).putDouble(value).array(); }
/*     */ 
/*     */ 
/*     */   
/*  57 */   public static byte[] toByteArrayFloat(float value) { return ByteBuffer.allocate(4).putFloat(value).array(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float convertRegistersToFloat(int[] registers) throws IllegalArgumentException {
/*  67 */     if (registers.length != 2)
/*  68 */       throw new IllegalArgumentException("Input Array length invalid"); 
/*  69 */     int highRegister = registers[1];
/*  70 */     int lowRegister = registers[0];
/*  71 */     byte[] highRegisterBytes = toByteArray(highRegister);
/*  72 */     byte[] lowRegisterBytes = toByteArray(lowRegister);
/*  73 */     byte[] floatBytes = { highRegisterBytes[1], highRegisterBytes[0], lowRegisterBytes[1], lowRegisterBytes[0] };
/*  74 */     return ByteBuffer.wrap(floatBytes).getFloat();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double convertRegistersToDouble(int[] registers) throws IllegalArgumentException {
/*  85 */     if (registers.length != 4)
/*  86 */       throw new IllegalArgumentException("Input Array length invalid"); 
/*  87 */     byte[] highRegisterBytes = toByteArray(registers[3]);
/*  88 */     byte[] highLowRegisterBytes = toByteArray(registers[2]);
/*  89 */     byte[] lowHighRegisterBytes = toByteArray(registers[1]);
/*  90 */     byte[] lowRegisterBytes = toByteArray(registers[0]);
/*  91 */     byte[] doubleBytes = { highRegisterBytes[1], highRegisterBytes[0], highLowRegisterBytes[1], highLowRegisterBytes[0], lowHighRegisterBytes[1], lowHighRegisterBytes[0], lowRegisterBytes[1], lowRegisterBytes[0] };
/*     */     
/*  93 */     return ByteBuffer.wrap(doubleBytes).getDouble();
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
/*     */   public static double convertRegistersToDouble(int[] registers, RegisterOrder registerOrder) throws IllegalArgumentException {
/* 105 */     if (registers.length != 4)
/* 106 */       throw new IllegalArgumentException("Input Array length invalid"); 
/* 107 */     int[] swappedRegisters = { registers[0], registers[1], registers[2], registers[3] };
/* 108 */     if (registerOrder == RegisterOrder.HighLow)
/* 109 */       swappedRegisters = new int[] { registers[3], registers[2], registers[1], registers[0] }; 
/* 110 */     return convertRegistersToDouble(swappedRegisters);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float convertRegistersToFloat(int[] registers, RegisterOrder registerOrder) throws IllegalArgumentException {
/* 121 */     int[] swappedRegisters = { registers[0], registers[1] };
/* 122 */     if (registerOrder == RegisterOrder.HighLow)
/* 123 */       swappedRegisters = new int[] { registers[1], registers[0] }; 
/* 124 */     return convertRegistersToFloat(swappedRegisters);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long convertRegistersToLong(int[] registers) throws IllegalArgumentException {
/* 135 */     if (registers.length != 4)
/* 136 */       throw new IllegalArgumentException("Input Array length invalid"); 
/* 137 */     byte[] highRegisterBytes = toByteArray(registers[3]);
/* 138 */     byte[] highLowRegisterBytes = toByteArray(registers[2]);
/* 139 */     byte[] lowHighRegisterBytes = toByteArray(registers[1]);
/* 140 */     byte[] lowRegisterBytes = toByteArray(registers[0]);
/* 141 */     byte[] longBytes = { highRegisterBytes[1], highRegisterBytes[0], highLowRegisterBytes[1], highLowRegisterBytes[0], lowHighRegisterBytes[1], lowHighRegisterBytes[0], lowRegisterBytes[1], lowRegisterBytes[0] };
/*     */     
/* 143 */     return ByteBuffer.wrap(longBytes).getLong();
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
/*     */   public static long convertRegistersToLong(int[] registers, RegisterOrder registerOrder) throws IllegalArgumentException {
/* 156 */     if (registers.length != 4)
/* 157 */       throw new IllegalArgumentException("Input Array length invalid"); 
/* 158 */     int[] swappedRegisters = { registers[0], registers[1], registers[2], registers[3] };
/* 159 */     if (registerOrder == RegisterOrder.HighLow)
/* 160 */       swappedRegisters = new int[] { registers[3], registers[2], registers[1], registers[0] }; 
/* 161 */     return convertRegistersToLong(swappedRegisters);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int convertRegistersToInt(int[] registers) throws IllegalArgumentException {
/* 171 */     if (registers.length != 2)
/* 172 */       throw new IllegalArgumentException("Input Array length invalid"); 
/* 173 */     int highRegister = registers[1];
/* 174 */     int lowRegister = registers[0];
/* 175 */     byte[] highRegisterBytes = toByteArray(highRegister);
/* 176 */     byte[] lowRegisterBytes = toByteArray(lowRegister);
/* 177 */     byte[] doubleBytes = { highRegisterBytes[1], highRegisterBytes[0], lowRegisterBytes[1], lowRegisterBytes[0] };
/* 178 */     return ByteBuffer.wrap(doubleBytes).getInt();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int convertRegistersToInt(int[] registers, RegisterOrder registerOrder) throws IllegalArgumentException {
/* 189 */     int[] swappedRegisters = { registers[0], registers[1] };
/* 190 */     if (registerOrder == RegisterOrder.HighLow)
/* 191 */       swappedRegisters = new int[] { registers[1], registers[0] }; 
/* 192 */     return convertRegistersToInt(swappedRegisters);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] convertFloatToRegisters(float floatValue) {
/* 202 */     byte[] floatBytes = toByteArrayFloat(floatValue);
/* 203 */     byte[] highRegisterBytes = { 0, 0, floatBytes[0], floatBytes[1] };
/*     */ 
/*     */     
/* 206 */     byte[] lowRegisterBytes = { 0, 0, floatBytes[2], floatBytes[3] };
/*     */ 
/*     */     
/* 209 */     int[] returnValue = { ByteBuffer.wrap(lowRegisterBytes).getInt(), ByteBuffer.wrap(highRegisterBytes).getInt() };
/* 210 */     return returnValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] convertFloatToRegisters(float floatValue, RegisterOrder registerOrder) {
/* 221 */     int[] registerValues = convertFloatToRegisters(floatValue);
/* 222 */     int[] returnValue = registerValues;
/* 223 */     if (registerOrder == RegisterOrder.HighLow)
/* 224 */       returnValue = new int[] { registerValues[1], registerValues[0] }; 
/* 225 */     return returnValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] convertIntToRegisters(int intValue) {
/* 235 */     byte[] doubleBytes = toByteArrayInt(intValue);
/* 236 */     byte[] highRegisterBytes = { 0, 0, doubleBytes[0], doubleBytes[1] };
/*     */ 
/*     */     
/* 239 */     byte[] lowRegisterBytes = { 0, 0, doubleBytes[2], doubleBytes[3] };
/*     */ 
/*     */     
/* 242 */     int[] returnValue = { ByteBuffer.wrap(lowRegisterBytes).getInt(), ByteBuffer.wrap(highRegisterBytes).getInt() };
/* 243 */     return returnValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] convertIntToRegisters(int intValue, RegisterOrder registerOrder) {
/* 254 */     int[] registerValues = convertIntToRegisters(intValue);
/* 255 */     int[] returnValue = registerValues;
/* 256 */     if (registerOrder == RegisterOrder.HighLow)
/* 257 */       returnValue = new int[] { registerValues[1], registerValues[0] }; 
/* 258 */     return returnValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] convertLongToRegisters(long longValue) {
/* 268 */     byte[] doubleBytes = toByteArrayLong(longValue);
/* 269 */     byte[] highhighRegisterBytes = { 0, 0, doubleBytes[0], doubleBytes[1] };
/*     */ 
/*     */     
/* 272 */     byte[] highlowRegisterBytes = { 0, 0, doubleBytes[2], doubleBytes[3] };
/*     */ 
/*     */     
/* 275 */     byte[] lowHighRegisterBytes = { 0, 0, doubleBytes[4], doubleBytes[5] };
/* 276 */     byte[] lowlowRegisterBytes = { 0, 0, doubleBytes[6], doubleBytes[7] };
/*     */ 
/*     */ 
/*     */     
/* 280 */     int[] returnValue = { ByteBuffer.wrap(lowlowRegisterBytes).getInt(), ByteBuffer.wrap(lowHighRegisterBytes).getInt(), ByteBuffer.wrap(highlowRegisterBytes).getInt(), ByteBuffer.wrap(highhighRegisterBytes).getInt() };
/* 281 */     return returnValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] convertLongToRegisters(long longValue, RegisterOrder registerOrder) {
/* 292 */     int[] registerValues = convertLongToRegisters(longValue);
/* 293 */     int[] returnValue = registerValues;
/* 294 */     if (registerOrder == RegisterOrder.HighLow)
/* 295 */       returnValue = new int[] { registerValues[3], registerValues[2], registerValues[1], registerValues[0] }; 
/* 296 */     return returnValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] convertDoubleToRegisters(double doubleValue) {
/* 306 */     byte[] doubleBytes = toByteArrayDouble(doubleValue);
/* 307 */     byte[] highhighRegisterBytes = { 0, 0, doubleBytes[0], doubleBytes[1] };
/*     */ 
/*     */     
/* 310 */     byte[] highlowRegisterBytes = { 0, 0, doubleBytes[2], doubleBytes[3] };
/*     */ 
/*     */     
/* 313 */     byte[] lowHighRegisterBytes = { 0, 0, doubleBytes[4], doubleBytes[5] };
/* 314 */     byte[] lowlowRegisterBytes = { 0, 0, doubleBytes[6], doubleBytes[7] };
/*     */ 
/*     */ 
/*     */     
/* 318 */     int[] returnValue = { ByteBuffer.wrap(lowlowRegisterBytes).getInt(), ByteBuffer.wrap(lowHighRegisterBytes).getInt(), ByteBuffer.wrap(highlowRegisterBytes).getInt(), ByteBuffer.wrap(highhighRegisterBytes).getInt() };
/* 319 */     return returnValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] convertDoubleToRegisters(double doubleValue, RegisterOrder registerOrder) {
/* 330 */     int[] registerValues = convertDoubleToRegisters(doubleValue);
/* 331 */     int[] returnValue = registerValues;
/* 332 */     if (registerOrder == RegisterOrder.HighLow)
/* 333 */       returnValue = new int[] { registerValues[3], registerValues[2], registerValues[1], registerValues[0] }; 
/* 334 */     return returnValue;
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
/*     */   public static String convertRegistersToString(int[] registers, int offset, int stringLength) {
/* 346 */     byte[] result = new byte[stringLength];
/* 347 */     byte[] registerResult = new byte[2];
/*     */     
/* 349 */     for (int i = 0; i < stringLength / 2; i++) {
/* 350 */       registerResult = toByteArray(registers[offset + i]);
/* 351 */       result[i * 2] = registerResult[0];
/* 352 */       result[i * 2 + 1] = registerResult[1];
/*     */     } 
/* 354 */     return new String(result);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] convertStringToRegisters(String stringToconvert) {
/* 364 */     byte[] array = stringToconvert.getBytes();
/* 365 */     int[] returnarray = new int[stringToconvert.length() / 2 + stringToconvert.length() % 2];
/* 366 */     for (int i = 0; i < returnarray.length; i++) {
/* 367 */       returnarray[i] = array[i * 2];
/* 368 */       if (i * 2 + 1 < array.length) {
/* 369 */         returnarray[i] = returnarray[i] | array[i * 2 + 1] << 8;
/*     */       }
/*     */     } 
/* 372 */     return returnarray;
/*     */   }
/*     */ }


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4j\commo\\util\RegistersUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */