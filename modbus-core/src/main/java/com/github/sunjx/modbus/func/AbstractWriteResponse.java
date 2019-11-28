package com.github.sunjx.modbus.func;


public abstract class AbstractWriteResponse
        extends AbstractFunction {
    /* 25 */
    public AbstractWriteResponse(short functionCode) {
        super(functionCode);
    }


    /* 28 */
    public AbstractWriteResponse(short functionCode, int address, int quantity) {
        super(functionCode, address, quantity);
    }
}

