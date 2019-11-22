/*     */ package com.github.sunjx.modbus.registers;
/*     */ 
/*     */ import com.github.zengfr.easymodbus4j.common.RegisterOrder;
/*     */ import com.github.zengfr.easymodbus4j.common.util.IntArrayUtil;
/*     */ import com.github.zengfr.easymodbus4j.common.util.RegistersUtil;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public class Registers
/*     */ {
/*  29 */   private final Map<Integer, Boolean> coils = new HashMap<>();
/*  30 */   private final Map<Integer, Boolean> inputs = new HashMap<>();
/*  31 */   private final Map<Integer, Short> holdingRegisters = new HashMap<>();
/*  32 */   private final Map<Integer, Short> inputRegisters = new HashMap<>();
/*     */   
/*     */   public void setHoldingRegister(int offset, float[] values) {
/*  35 */     if (validateOffset(offset)) {
/*  36 */       ArrayList<Integer> list = Lists.newArrayList();
/*  37 */       for (int i = 0; i < values.length; i++) {
/*  38 */         int[] vv = RegistersUtil.convertFloatToRegisters(values[i], RegisterOrder.HighLow); int arrayOfInt[], j; byte b;
/*  39 */         for (arrayOfInt = vv, j = arrayOfInt.length, b = 0; b < j; ) { Integer v = Integer.valueOf(arrayOfInt[b]);
/*  40 */           list.add(v); b++; }
/*     */       
/*     */       } 
/*  43 */       int[] registers = IntArrayUtil.toIntArray(list);
/*  44 */       setHoldingRegister(offset, registers);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setHoldingRegister(int offset, Integer... values) {
/*  49 */     if (validateOffset(offset)) {
/*  50 */       ArrayList<Integer> list = Lists.newArrayList();
/*  51 */       for (int i = 0; i < values.length; i++) {
/*  52 */         int[] vv = RegistersUtil.convertIntToRegisters(values[i].intValue(), RegisterOrder.HighLow); int arrayOfInt[], j; byte b;
/*  53 */         for (arrayOfInt = vv, j = arrayOfInt.length, b = 0; b < j; ) { Integer v = Integer.valueOf(arrayOfInt[b]);
/*  54 */           list.add(v); b++; }
/*     */       
/*     */       } 
/*  57 */       int[] registers = IntArrayUtil.toIntArray(list);
/*  58 */       setHoldingRegister(offset, registers);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void setHoldingRegister(int offset, int[] registers) {
/*  63 */     if (validateOffset(offset))
/*  64 */       for (int i = 0; i < registers.length; i++)
/*  65 */         setHoldingRegister(offset + i, (short)registers[i]);  
/*     */   }
/*     */   
/*     */   public void setHoldingRegister(int offset, short[] registers) {
/*  69 */     if (validateOffset(offset))
/*  70 */       for (int i = 0; i < registers.length; i++)
/*  71 */         setHoldingRegister(offset + i, registers[i]);  
/*     */   }
/*     */   
/*     */   public void setInputRegister(int offset, short[] registers) {
/*  75 */     if (validateOffset(offset))
/*  76 */       for (int i = 0; i < registers.length; i++)
/*  77 */         setInputRegister(offset + i, registers[i]);  
/*     */   }
/*     */   
/*     */   protected boolean validateOffset(int offset) {
/*  81 */     if (offset < 0 || offset > 65535)
/*  82 */       return false; 
/*  83 */     return true;
/*     */   }
/*     */   
/*     */   public synchronized void setInputRegister(int offset, short value) {
/*  87 */     if (validateOffset(offset))
/*  88 */       this.inputRegisters.put(Integer.valueOf(offset), Short.valueOf(value)); 
/*     */   }
/*     */   
/*     */   public synchronized void setHoldingRegister(int offset, short value) {
/*  92 */     if (validateOffset(offset))
/*  93 */       this.holdingRegisters.put(Integer.valueOf(offset), Short.valueOf(value)); 
/*     */   }
/*     */   
/*     */   public synchronized void setInput(int offset, boolean value) {
/*  97 */     if (validateOffset(offset))
/*  98 */       this.inputs.put(Integer.valueOf(offset), Boolean.valueOf(value)); 
/*     */   }
/*     */   
/*     */   public synchronized void setCoil(int offset, boolean value) {
/* 102 */     if (validateOffset(offset)) {
/* 103 */       this.coils.put(Integer.valueOf(offset), Boolean.valueOf(value));
/*     */     }
/*     */   }
/*     */   
/* 107 */   public boolean getCoil(int offset) { return getBoolean(offset, this.coils); }
/*     */ 
/*     */ 
/*     */   
/* 111 */   public boolean getInput(int offset) { return getBoolean(offset, this.inputs); }
/*     */ 
/*     */ 
/*     */   
/* 115 */   public short getInputRegister(int offset) { return getShort(offset, this.inputRegisters); }
/*     */ 
/*     */ 
/*     */   
/* 119 */   public float getInputRegister4Float(int offset) { return getRegister4Float(offset, this.inputRegisters); }
/*     */ 
/*     */ 
/*     */   
/* 123 */   public int getInputRegister4Int(int offset) { return getRegister4Int(offset, this.inputRegisters); }
/*     */ 
/*     */ 
/*     */   
/* 127 */   public short getHoldingRegister(int offset) { return getShort(offset, this.holdingRegisters); }
/*     */ 
/*     */ 
/*     */   
/* 131 */   public float getHoldingRegister4Float(int offset) { return getRegister4Float(offset, this.holdingRegisters); }
/*     */ 
/*     */ 
/*     */   
/* 135 */   public int getHoldingRegister4Int(int offset) { return getRegister4Int(offset, this.holdingRegisters); }
/*     */ 
/*     */ 
/*     */   
/* 139 */   public int[] getInputRegisters(int offset, int registersCount) { return getRegisters(offset, this.inputRegisters, registersCount); }
/*     */ 
/*     */ 
/*     */   
/* 143 */   public int[] getHoldingRegisters(int offset, int registersCount) { return getRegisters(offset, this.holdingRegisters, registersCount); }
/*     */ 
/*     */   
/*     */   protected float getRegister4Float(int offset, Map<Integer, Short> map) {
/* 147 */     int[] registers = getRegisters(offset, map, 4);
/* 148 */     return RegistersUtil.convertRegistersToFloat(registers);
/*     */   }
/*     */   
/*     */   protected int getRegister4Int(int offset, Map<Integer, Short> map) {
/* 152 */     int[] registers = getRegisters(offset, map, 2);
/* 153 */     return RegistersUtil.convertRegistersToInt(registers);
/*     */   }
/*     */   
/*     */   protected int[] getRegisters(int offset, Map<Integer, Short> map, int registersCount) {
/* 157 */     int[] rs = new int[registersCount];
/* 158 */     for (int i = 0; i < registersCount; i++)
/* 159 */       rs[i] = getShort(offset + i, map); 
/* 160 */     return rs;
/*     */   }
/*     */   
/*     */   protected short getShort(int offset, Map<Integer, Short> map) {
/* 164 */     Short value = map.get(Integer.valueOf(offset));
/* 165 */     if (value == null) {
/* 166 */       return 0;
/*     */     }
/* 168 */     return value.shortValue();
/*     */   }
/*     */   
/*     */   protected boolean getBoolean(int offset, Map<Integer, Boolean> map) {
/* 172 */     Boolean value = map.get(Integer.valueOf(offset));
/* 173 */     if (value == null) {
/* 174 */       return false;
/*     */     }
/* 176 */     return value.booleanValue();
/*     */   }
/*     */ }


/* Location:              D:\logs\easymodbus4j-0.0.5.jar!\com\github\zengfr\easymodbus4j\com.github.sunjx.modbus.registers\Registers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */