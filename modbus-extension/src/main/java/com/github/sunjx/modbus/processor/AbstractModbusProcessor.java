 package com.github.sunjx.modbus.processor;

 import com.github.zengfr.easymodbus4j.func.AbstractRequest;
 import com.github.zengfr.easymodbus4j.protocol.ModbusFunction;
 import com.github.zengfr.easymodbus4j.util.ModbusFunctionUtil;




















 public abstract class AbstractModbusProcessor
   implements ModbusProcessor
 {
   private short transactionIdentifierOffset;
   private boolean isShowFrameDetail;

/* 32 */   public AbstractModbusProcessor() { this((short)0, true); }


   public AbstractModbusProcessor(short transactionIdentifierOffset, boolean isShowFrameDetail) {
/* 36 */     this.transactionIdentifierOffset = transactionIdentifierOffset;
/* 37 */     this.isShowFrameDetail = isShowFrameDetail;
   }



/* 42 */   protected boolean isRequestResponseMatch(AbstractRequest reqFunc, ModbusFunction respFunc) { return (reqFunc != null && respFunc != null && reqFunc.getFunctionCode() == respFunc.getFunctionCode()); }


   protected boolean isRequestResponseValueMatch(AbstractRequest reqFunc, ModbusFunction respFunc) {
/* 46 */     byte[] respFuncValuesArray = ModbusFunctionUtil.getFunctionValues(respFunc);
/* 47 */     return isRequestResponseValueMatch(reqFunc, respFuncValuesArray);
   }

   protected boolean isRequestResponseValueMatch(AbstractRequest reqFunc, byte[] respFuncValuesArray) {
/* 51 */     if (reqFunc == null)
/* 52 */       return false; 
/* 53 */     int quantityOfInputRegisters = reqFunc.getValue();
/* 54 */     return (quantityOfInputRegisters * 2 == respFuncValuesArray.length || (respFuncValuesArray.length == 1 && quantityOfInputRegisters == respFuncValuesArray.length));
   }


/* 58 */   public short getTransactionIdentifierOffset() { return this.transactionIdentifierOffset; }



/* 62 */   public boolean isShowFrameDetail() { return this.isShowFrameDetail; }
 }


/* Location:              D:\logs\easymodbus4j-extension-0.0.5.jar!\com\github\zengfr\easymodbus4j\processor\AbstractModbusProcessor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.2
 */