package com.github.sunjx.modbus.processor;


import com.github.sunjx.modbus.func.AbstractRequest;
import com.github.sunjx.modbus.protocol.ModbusFunction;
import com.github.sunjx.modbus.util.ModbusFunctionUtil;

public abstract class AbstractModbusProcessor implements ModbusProcessor {
    private short transactionIdentifierOffset;
    private boolean isShowFrameDetail;

    public AbstractModbusProcessor() {
        this((short) 0, true);
    }


    public AbstractModbusProcessor(short transactionIdentifierOffset, boolean isShowFrameDetail) {
        this.transactionIdentifierOffset = transactionIdentifierOffset;
        this.isShowFrameDetail = isShowFrameDetail;
    }

    protected boolean isRequestResponseMatch(AbstractRequest reqFunc, ModbusFunction respFunc) {
        return (reqFunc != null && respFunc != null && reqFunc.getFunctionCode() == respFunc.getFunctionCode());
    }


    protected boolean isRequestResponseValueMatch(AbstractRequest reqFunc, ModbusFunction respFunc) {
        byte[] respFuncValuesArray = ModbusFunctionUtil.getFunctionValues(respFunc);
        return isRequestResponseValueMatch(reqFunc, respFuncValuesArray);
    }

    protected boolean isRequestResponseValueMatch(AbstractRequest reqFunc, byte[] respFuncValuesArray) {
        if (reqFunc == null) {
            return false;
        }
        int quantityOfInputRegisters = reqFunc.getValue();
        return (quantityOfInputRegisters * 2 == respFuncValuesArray.length || (respFuncValuesArray.length == 1 && quantityOfInputRegisters == respFuncValuesArray.length));
    }


    @Override
    public short getTransactionIdentifierOffset() {
        return this.transactionIdentifierOffset;
    }


    @Override
    public boolean isShowFrameDetail() {
        return this.isShowFrameDetail;
    }
}

