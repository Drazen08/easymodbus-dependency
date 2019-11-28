package com.github.sunjx.modbus.registers;

import java.util.HashMap;


public class RegistersFactory
        extends HashMap<Short, Registers> {
    private static final long serialVersionUID = 628379880359409400L;

    private static class RegistersFactoryHolder {
        /* 28 */     private static final RegistersFactory INSTANCE = new RegistersFactory();
    }


    /* 32 */
    public static RegistersFactory getInstance() {
        return RegistersFactoryHolder.INSTANCE;
    }


    public Registers getOrInit(Short key) {
        /* 36 */
        if (!containsKey(key)) {
            /* 37 */
            put(key, new Registers());
        }
        /* 39 */
        return get(key);
    }
}


/* Location:              D:\logs\easymodbus4j-0.0.5.jar!\com\github\zengfr\easymodbus4j\com.github.sunjx.modbus.registers\RegistersFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */