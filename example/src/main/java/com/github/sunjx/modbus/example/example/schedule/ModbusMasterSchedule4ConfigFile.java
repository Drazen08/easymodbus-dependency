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
package com.github.sunjx.modbus.example.example.schedule;


import com.github.sunjx.modbus.common.util.FileUtil;
import com.github.sunjx.modbus.common.util.InputStreamUtil;
import com.github.sunjx.modbus.common.util.ScheduledUtil;
import com.github.sunjx.modbus.schedule.ModbusMasterSchedule;
import com.github.sunjx.modbus.sender.ChannelSender;
import com.github.sunjx.modbus.sender.ChannelSenderFactory;
import com.github.sunjx.modbus.sender.util.ModbusRequestSendUtil;
import com.google.common.collect.Lists;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

/**
 * @author zengfr QQ:362505707/1163551688 Email:zengfr3000@qq.com
 * https://github.com/zengfr/easymodbus4j
 */
public class ModbusMasterSchedule4ConfigFile extends ModbusMasterSchedule {
    private static final InternalLogger logger = InternalLoggerFactory
            .getInstance(ModbusMasterSchedule4ConfigFile.class);

    protected static String configFileName = "autoSend.txt";

    protected static List<String> parseReqs(String fileName) {
        List<String> configStrings = readConfig(fileName);
        List<String> reqStrings = parseReqs(configStrings);
        return reqStrings;
    }

    protected static List<String> parseReqs(List<String> configStrings) {
        List<String> reqStrings = Lists.newArrayList(configStrings);
        if (!configStrings.isEmpty()) {
            for (int i = 0; i < reqStrings.size(); i++) {
                if (reqStrings.get(i).startsWith("#")) {
                    reqStrings.remove(i);
                    i--;
                }
            }
        }
        return reqStrings;
    }

    protected static List<String> readConfig(String fileName) {
        logger.info("readConfig:" + fileName);
        List<String> strList = Lists.newArrayList();
        try {
            InputStream input = InputStreamUtil.getStream(fileName);
            if (input != null) {
                strList = FileUtil.readLines(input, StandardCharsets.UTF_8);
            }
        } catch (IOException ex) {
            logger.error("readConfig:" + fileName + " ex:", ex);
        }
        return strList;
    }

    private static String getFunctionStringByCode(short functionCode){
        switch (functionCode){
            case 1:
                return "readCoils";
            case 2:
                return "readDiscreteInputs";
            case 3:
                return "readHoldingRegisters";
            case 4:
                return "readInputRegisters";
            case 5:
                return "writeSingleCoil";
            case 6:
                return "writeSingleRegister";
            case 15:
                return "writeMultipleCoils";
            case 16:
                return "writeMultipleRegisters";

        }
        return "";
    }

    private static void sendRequestsByChannel(Channel channel, List<ModbusDataPointConfig> reqs, boolean isAllUseAsync, int fixedDelay) {
        ChannelSender sender = ChannelSenderFactory.getInstance().get(channel);
        for (ModbusDataPointConfig dpConfig : reqs) {
            try {
                long startTime = System.currentTimeMillis();

                String funcString = getFunctionStringByCode(dpConfig.functionCode)+"Async";

                ModbusRequestSendUtil.sendAsyncFunc(sender, funcString, String.valueOf(dpConfig.regAddr), String.valueOf(dpConfig.regCount));

                long span = System.currentTimeMillis() - startTime;
                if (fixedDelay - span > 0L) {
                    Thread.sleep(fixedDelay - span);
                }

            } catch (InvocationTargetException | IllegalAccessException | IllegalArgumentException | SecurityException | InterruptedException | NoSuchMethodException ex) {
                logger.error("sendRequestsByChannel", ex);
            }
        }

    }

    @Override
    protected int getFixedDelay() {
        return 0;
    }

    @Override
    protected ModbusRequestSendUtil.PriorityStrategy getPriorityStrategy() {
        return ModbusRequestSendUtil.PriorityStrategy.Channel;
    }

    @Override
    protected InternalLogger getLogger() {

        return logger;
    }

    @Override
    protected List<String> buildReqsList() {
        return parseReqs("/" + configFileName);
    }

    @Override
    public void schedule(Collection<Channel> channels, int sleep) {
        this.getLogger().debug(String.format("schedule->channels:%s,sleep:%s ms", channels.size(), sleep));
        this.sendRequests4Auto(channels, sleep);
    }

    private void sendRequests4Auto(Collection<Channel> channels, int sleep) {
        Runnable r = () -> {
            try {
                this.getLogger().debug(String.format("channels:%s, sleep:%s ms", channels.size(), sleep));
                for (Channel channel : channels) {
                    if (channel == null || !channel.isActive() || !channel.isOpen() || !channel.isWritable()) {
                        continue;
                    }
                    String dtuName = (String) channel.attr(AttributeKey.valueOf("DtuName")).get();
                    if (dtuName == null) {
                        continue;
                    }
                    List<ModbusDataPointConfig> dpConfigs = DtuConfigLoader.getInstance().loadDtuConfig(dtuName);
                    if (dpConfigs.size() == 0) {
                        continue;
                    }

                    sendRequestsByChannel(channel, dpConfigs, true, sleep);

                }
            } catch (Exception ex) {
                this.getLogger().error(ex);
            }

        };
        ScheduledUtil.scheduleWithFixedDelay(r, (long) sleep);
    }


}
