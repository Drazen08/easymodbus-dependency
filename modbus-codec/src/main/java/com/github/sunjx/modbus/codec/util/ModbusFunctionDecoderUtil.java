package com.github.sunjx.modbus.codec.util;


import com.github.sunjx.modbus.func.request.*;
import com.github.sunjx.modbus.func.response.*;
import com.github.sunjx.modbus.protocol.ModbusFunction;
import com.github.sunjx.modbus.util.ModbusFunctionUtil;

public class ModbusFunctionDecoderUtil {
    public ModbusFunctionDecoderUtil() {
    }

    public static ModbusFunction decodeReqFunction(short functionCode) {
        ModbusFunction function = null;
        switch(functionCode) {
            case 1:
                function = new ReadCoilsRequest();
                break;
            case 2:
                function = new ReadDiscreteInputsRequest();
                break;
            case 3:
                function = new ReadHoldingRegistersRequest();
                break;
            case 4:
                function = new ReadInputRegistersRequest();
                break;
            case 5:
                function = new WriteSingleCoilRequest();
                break;
            case 6:
                function = new WriteSingleRegisterRequest();
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            default:
                break;
            case 15:
                function = new WriteMultipleCoilsRequest();
                break;
            case 16:
                function = new WriteMultipleRegistersRequest();
        }

        return (ModbusFunction)function;
    }

    public static ModbusFunction decodeRespFunction(short functionCode) {
        ModbusFunction function = null;
        switch(functionCode) {
            case 1:
                function = new ReadCoilsResponse();
                break;
            case 2:
                function = new ReadDiscreteInputsResponse();
                break;
            case 3:
                function = new ReadHoldingRegistersResponse();
                break;
            case 4:
                function = new ReadInputRegistersResponse();
                break;
            case 5:
                function = new WriteSingleCoilResponse();
                break;
            case 6:
                function = new WriteSingleRegisterResponse();
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            default:
                break;
            case 15:
                function = new WriteMultipleCoilsResponse();
                break;
            case 16:
                function = new WriteMultipleRegistersResponse();
        }

        if (ModbusFunctionUtil.isError(functionCode)) {
            function = new ErrorFunctionResponse(functionCode);
        } else if (function == null) {
            function = new ErrorFunctionResponse(functionCode, (short)1);
        }

        return (ModbusFunction)function;
    }
}


/* Location:              D:\logs\easymodbus4j-codec-0.0.5.jar!\com\github\zengfr\easymodbus4j\code\\util\ModbusFunctionDecoderUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */