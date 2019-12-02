package com.github.sunjx.modbus;

public class ModbusConfs {
    public static boolean MASTER_SHOW_DEBUG_LOG = true;
    public static boolean SLAVE_SHOW_DEBUG_LOG = true;
    public static int DEFAULT_MODBUS_PORT = 502;
    public static int DEFAULT_MODBUS_PORT1 = 12502;
    public static int DEFAULT_MODBUS_PORT2 = 23502;
    public static int DEFAULT_MODBUS_PORT3 = 34502;
    public static int DEFAULT_MODBUS_PORT4 = 45502;
    public static int DEFAULT_MODBUS_PORT5 = 56502;

    public static int SYNC_RESPONSE_TIMEOUT = 3100;
    public static int FRAME_CACHE_EXPIRE = SYNC_RESPONSE_TIMEOUT + 100;
    public static int IDLE_TIMEOUT_SECOND = 0;
    public static int RESPONS_EFRAME_IGNORE_LENGTH_THRESHOLD = 0;
    public static int SYNC_TRYACQUIRE_TIMEOUT_Millis = 2000;
}
