package com.github.sunjx.modbus.codec.rtu;

import com.github.sunjx.modbus.util.ModbusFunctionUtil;
import io.netty.buffer.ByteBuf;


public class ModbusRtuCodecUtil {
    private static final int MessageMinSize = 5;
    private static final int FunctionFieldIndex = 1;
    private static final int LengthFieldIndex = 2;

    public ModbusRtuCodecUtil() {
    }

    public static int getMessageMinSize() {
        return 5;
    }

    private static int getLength(ByteBuf in, int startIndex) {
        return in.getUnsignedByte(startIndex + 2);
    }

    private static short getFunctionCode(ByteBuf in, int startIndex) {
        return in.getUnsignedByte(startIndex + 1);
    }

    public static int getMessageLength(ByteBuf in, int startIndex) {
        short functionCode = getFunctionCode(in, startIndex);
        if (ModbusFunctionUtil.isError(functionCode)) {
            return 5;
        } else {
            switch(functionCode) {
                case 1:
                case 2:
                case 3:
                case 4:
                    int length = getLength(in, startIndex);
                    return 3 + length + 2;
                case 5:
                case 6:
                case 15:
                case 16:
                    return 8;
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                default:
                    return 0;
            }
        }
    }
}


/* Location:              D:\logs\easymodbus4j-codec-0.0.5.jar!\com\github\zengfr\easymodbus4j\codec\rtu\ModbusRtuCodecUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */