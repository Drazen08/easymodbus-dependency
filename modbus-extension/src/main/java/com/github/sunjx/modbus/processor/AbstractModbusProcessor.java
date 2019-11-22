package com.github.sunjx.modbus.processor;


import com.github.sunjx.modbus.func.AbstractRequest;
import com.github.sunjx.modbus.protocol.ModbusFunction;
import com.github.sunjx.modbus.util.ModbusFunctionUtil;

public abstract class AbstractModbusProcessor
        implements ModbusProcessor {
    private short transactionIdentifierOffset;
    private boolean isShowFrameDetail;

    /* 32 */
    public AbstractModbusProcessor() {
        this((short) 0, true);
    }


    public AbstractModbusProcessor(short transactionIdentifierOffset, boolean isShowFrameDetail) {
        /* 36 */
        this.transactionIdentifierOffset = transactionIdentifierOffset;
        /* 37 */
        this.isShowFrameDetail = isShowFrameDetail;
    }


    /* 42 */
    protected boolean isRequestResponseMatch(AbstractRequest reqFunc, ModbusFunction respFunc) {
        return (reqFunc != null && respFunc != null && reqFunc.getFunctionCode() == respFunc.getFunctionCode());
    }


    protected boolean isRequestResponseValueMatch(AbstractRequest reqFunc, ModbusFunction respFunc) {
        /* 46 */
        byte[] respFuncValuesArray = ModbusFunctionUtil.getFunctionValues(respFunc);
        /* 47 */
        return isRequestResponseValueMatch(reqFunc, respFuncValuesArray);
    }

    protected boolean isRequestResponseValueMatch(AbstractRequest reqFunc, byte[] respFuncValuesArray) {
        /* 51 */
        if (reqFunc == null)
            /* 52 */ return false;
        /* 53 */
        int quantityOfInputRegisters = reqFunc.getValue();
        /* 54 */
        return (quantityOfInputRegisters * 2 == respFuncValuesArray.length || (respFuncValuesArray.length == 1 && quantityOfInputRegisters == respFuncValuesArray.length));
    }


    /* 58 */
    @Override
    public short getTransactionIdentifierOffset() {
        return this.transactionIdentifierOffset;
    }


    /* 62 */
    @Override
    public boolean isShowFrameDetail() {
        return this.isShowFrameDetail;
    }
}


/* Location:              D:\logs\easymodbus4j-extension-0.0.5.jar!\com\github\zengfr\easymodbus4j\processor\AbstractModbusProcessor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.2
 */