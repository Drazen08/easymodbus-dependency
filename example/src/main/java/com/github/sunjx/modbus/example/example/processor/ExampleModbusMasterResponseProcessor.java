/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.sunjx.modbus.example.example.processor;

import com.alibaba.fastjson.JSON;
import com.github.sunjx.modbus.example.dto.PointDto;
import com.github.sunjx.modbus.example.example.schedule.DtuConfigLoader;
import com.github.sunjx.modbus.example.example.schedule.ModbusDataPointConfig;
import com.github.sunjx.modbus.example.util.DateUtils;
import com.github.sunjx.modbus.example.util.SimpleSendData;
import com.github.sunjx.modbus.example.util.StringUtil;
import com.github.sunjx.modbus.func.AbstractRequest;
import com.github.sunjx.modbus.func.response.ReadHoldingRegistersResponse;
import com.github.sunjx.modbus.processor.AbstractModbusProcessor;
import com.github.sunjx.modbus.processor.ModbusMasterResponseProcessor;

import com.github.sunjx.modbus.protocol.ModbusFunction;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

public class ExampleModbusMasterResponseProcessor extends AbstractModbusProcessor implements ModbusMasterResponseProcessor {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ExampleModbusMasterResponseProcessor.class);

    public ExampleModbusMasterResponseProcessor(short transactionIdentifierOffset) {
        super(transactionIdentifierOffset, true);
    }

    @Override
    public boolean processResponseFrame(Channel channel, int unitId, AbstractRequest reqFunc, ModbusFunction respFunc) {
        String dtuName = (String) channel.attr(AttributeKey.valueOf("DtuName")).get();
        boolean success = this.isRequestResponseMatch(reqFunc, respFunc);

        short regAddr = (short) reqFunc.getAddress();
        short regCount = (short) reqFunc.getValue();
        short functionCode = reqFunc.getFunctionCode();
        //TODO: 设备ID现在还没有修正，现在默认使用1，正确的方法应该是从reqFunc里读到
        ModbusDataPointConfig dpConfig = DtuConfigLoader.getInstance().searchDtuDataPoint(dtuName, (short) unitId, functionCode, regAddr, regCount);

        if (dpConfig != null) {

            if (!success) {
                logger.warn("Modbus响应与请求不匹配，失败。 DTU=%s, 点位=%s", dtuName, dpConfig.dataPointName);
                return false;
            }

            //TODO: 这里就是处理读取到值的地方，注意不同的读取方法需要使用不同的处理方式
            logger.debug(String.format("读取成功：DTU=%s, 点位=%s, 值=%f",
                    dtuName == null ? "[EMPTY]" : dtuName,
                    dpConfig.dataPointName,
                    getReturnValueFromResponse(respFunc, dpConfig)));
            PointDto pointDto = new PointDto();
            pointDto.setDtu(StringUtil.toString(dtuName));
            pointDto.setPointName(pointDto.getDtu() + StringUtil.toString(dpConfig.dataPointName));
            pointDto.setPointDesc(pointDto.getPointName());
            pointDto.setValue(pointDto.getDtu() + StringUtil.toString(getReturnValueFromResponse(respFunc, dpConfig)));
            pointDto.setTime(DateUtils.getDateTime());

            // 在这里 发送mqtt 上报点位数据
            SimpleSendData.sendData(JSON.toJSONString(pointDto));
        }
        return success;
    }


//        if (respFunc instanceof ReadCoilsResponse) {
//            ReadCoilsResponse resp = (ReadCoilsResponse) respFunc;
//            if (reqFunc instanceof ReadCoilsRequest) {
//                ReadCoilsRequest req = (ReadCoilsRequest) reqFunc;
//                // process business logic
//                success = true;
//            }
//        }
//        if (respFunc instanceof ReadDiscreteInputsResponse) {
//            ReadDiscreteInputsResponse resp = (ReadDiscreteInputsResponse) respFunc;
//            byte[] resutArray = resp.getInputStatus().toByteArray();
//            byte[] valuesArray = ModbusFunctionUtil.getFunctionValues(respFunc);
//            if (reqFunc instanceof ReadDiscreteInputsRequest) {
//                ReadDiscreteInputsRequest req = (ReadDiscreteInputsRequest) reqFunc;
//                // process business logic
//                success = true;
//            }
//        }


    //TODO: 这个方法用来根据功能码来确定具体的返回值，目前只处理了3，其他的也需要对应实现,可以参考上面函数被注释的部分
    private Object getReturnValueFromResponse(ModbusFunction respFunc, ModbusDataPointConfig dpConfig) {
        switch (respFunc.getFunctionCode()) {
            case 3:
                int value = ((ReadHoldingRegistersResponse) respFunc).getRegisters()[0];
                return value * dpConfig.scale + dpConfig.offset;
            default:
                return null;
        }
    }
}
