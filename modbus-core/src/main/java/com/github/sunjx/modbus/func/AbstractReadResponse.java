package com.github.sunjx.modbus.func;


import com.github.sunjx.modbus.protocol.ModbusFunction;

public abstract class AbstractReadResponse
        extends ModbusFunction {
    /* 27 */
    public AbstractReadResponse(short functionCode) {
        super(functionCode);
    }
}

