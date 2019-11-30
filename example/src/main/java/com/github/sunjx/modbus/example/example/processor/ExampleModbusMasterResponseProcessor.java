package com.github.sunjx.modbus.example.example.processor;

import com.alibaba.fastjson.JSON;

import com.github.sunjx.modbus.example.dto.PointDto;
import com.github.sunjx.modbus.example.util.DateUtils;
import com.github.sunjx.modbus.example.util.SimpleSendData;
import com.github.sunjx.modbus.example.util.StringUtil;
import com.github.sunjx.modbus.func.AbstractRequest;
import com.github.sunjx.modbus.func.request.ReadCoilsRequest;
import com.github.sunjx.modbus.func.request.ReadDiscreteInputsRequest;
import com.github.sunjx.modbus.func.request.ReadHoldingRegistersRequest;
import com.github.sunjx.modbus.func.response.ReadCoilsResponse;
import com.github.sunjx.modbus.func.response.ReadDiscreteInputsResponse;
import com.github.sunjx.modbus.func.response.ReadHoldingRegistersResponse;
import com.github.sunjx.modbus.processor.AbstractModbusProcessor;
import com.github.sunjx.modbus.processor.ModbusMasterResponseProcessor;
import com.github.sunjx.modbus.protocol.ModbusFunction;
import com.github.sunjx.modbus.util.ModbusFunctionUtil;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;


public class ExampleModbusMasterResponseProcessor extends AbstractModbusProcessor implements ModbusMasterResponseProcessor {
    private String underLine;
    private String slant;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ExampleModbusMasterResponseProcessor.class);

    public ExampleModbusMasterResponseProcessor(short transactionIdentifierOffset) {
        super(transactionIdentifierOffset, true);
        this.underLine = "_";
        this.slant = "/";
    }

    @Override
    public boolean processResponseFrame(Channel channel, int unitId, AbstractRequest reqFunc, ModbusFunction respFunc) {
        boolean success = isRequestResponseMatch(reqFunc, respFunc);
        if (respFunc instanceof ReadCoilsResponse) {
            ReadCoilsResponse resp = (ReadCoilsResponse) respFunc;
            if (reqFunc instanceof ReadCoilsRequest) {
                ReadCoilsRequest req = (ReadCoilsRequest) reqFunc;
                success = true;
            }
        }

        if (respFunc instanceof ReadDiscreteInputsResponse) {
            ReadDiscreteInputsResponse resp = (ReadDiscreteInputsResponse) respFunc;
            byte[] resutArray = resp.getInputStatus().toByteArray();
            byte[] valuesArray = ModbusFunctionUtil.getFunctionValues(respFunc);
            if (reqFunc instanceof ReadDiscreteInputsRequest) {
                ReadDiscreteInputsRequest req = (ReadDiscreteInputsRequest) reqFunc;
                success = true;
            }
        }

        if (respFunc instanceof ReadHoldingRegistersResponse) {
            ReadHoldingRegistersResponse resp = (ReadHoldingRegistersResponse) respFunc;
            if (reqFunc instanceof ReadHoldingRegistersRequest) {
                ReadHoldingRegistersRequest req = (ReadHoldingRegistersRequest) reqFunc;
                success = true;
                try {
                    String channelKey = (String) channel.attr(AttributeKey.valueOf("DtuName")).get();
                    PointDto pointDto = new PointDto();
                    pointDto.setDtu(channelKey);
                    pointDto.setPointName(pointDto.getDtu() + this.underLine + unitId + this.underLine + req.getStartingAddress());
                    pointDto.setPointDesc(pointDto.getPointName());
                    pointDto.setTime(DateUtils.getDateTime());
                    pointDto.setValue(StringUtil.toString(resp.getRegisters()[0]));

                    BigDecimal value = new BigDecimal(pointDto.getValue());

                    String deviceSign = req.getDeviceSign();

                    String[] arr = deviceSign.split(this.underLine);
                    String calculation = arr[0];
                    BigDecimal de = new BigDecimal(calculation.substring(1, calculation.length()));
                    if (calculation.startsWith(this.slant)) {
                        pointDto.setValue(StringUtil.toString(value.divide(de, 2, RoundingMode.HALF_UP)));
                    }
                    SimpleSendData.sendData(JSON.toJSONString(Collections.singletonList(pointDto)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            logger.info("ReadHoldingRegistersResponse={}", resp.toString());
        }

        logger.info("远程地址------------" + channel.remoteAddress().toString());
        logger.debug(String.format("success:%s", success));
        return success;
    }
}
