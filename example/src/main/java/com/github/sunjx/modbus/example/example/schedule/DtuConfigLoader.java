package com.github.sunjx.modbus.example.example.schedule;

import com.github.sunjx.modbus.common.util.FileUtil;
import com.github.sunjx.modbus.common.util.InputStreamUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

//用于读取DTU上的点位配置的类
public class DtuConfigLoader {
    private static DtuConfigLoader instance = new DtuConfigLoader();
    private static final InternalLogger logger = InternalLoggerFactory
            .getInstance(DtuConfigLoader.class);

    private DtuConfigLoader() {
        this.cache = CacheBuilder.newBuilder().expireAfterWrite(300000L, TimeUnit.MILLISECONDS).build();
    }
//
    protected static String path = System.getProperty("user.dir");
    private Cache<String, List<ModbusDataPointConfig>> cache;

    public static DtuConfigLoader getInstance() {
        return instance;
    }

    public List<ModbusDataPointConfig> loadDtuConfig(String dtuName) {
        List<ModbusDataPointConfig> dpConfigs = this.cache.getIfPresent(dtuName);
        if (dpConfigs == null||dpConfigs.size() == 0) {
            dpConfigs = loadDataPointConfig( dtuName + ".txt");
            this.cache.put(dtuName, dpConfigs);
        }
        return dpConfigs;
    }

    /*
    搜索DTU配置中的点位
     */
    public ModbusDataPointConfig searchDtuDataPoint(String dtuName, short unitId, short functionCode, short regAddr, short regCount) {
        List<ModbusDataPointConfig> dpConfigs = loadDtuConfig(dtuName);
        for (ModbusDataPointConfig dpConfig : dpConfigs) {
            if (dpConfig.unitId == unitId && dpConfig.regAddr == regAddr && dpConfig.functionCode == functionCode && dpConfig.regCount == regCount) {
                return dpConfig;
            }
        }
        return null;
    }

    public static List<ModbusDataPointConfig> loadDataPointConfig(String fileName) {
        logger.info("loadDataPointConfig:" + fileName);
        List<ModbusDataPointConfig> dataPointConfigs = Lists.newArrayList();

        //下面这个判断暂时用不上，因为InputStreamUtil会自动从常用目录里寻找文件
//        if (!new File(fileName).exists()) {
//            logger.warn("点位配置文件不存在：%s", fileName);
//            return dataPointConfigs;
//        }

        int lineNumber = 1;
        try {
            InputStream input = InputStreamUtil.getStream(fileName);
            if (input != null) {
                for (String line : FileUtil.readLines(input, StandardCharsets.UTF_8)) {
                    if (!line.matches("^\\s*#.*") && !line.matches("^\\s*$")) {
                        String[] strArr = line.split("[\\t;|]");
                        if (strArr.length < 6) {
                            logger.warn("点位配置文件格式不正确，文件 %s, 第%d行。", fileName, lineNumber);
                        } else {
                            ModbusDataPointConfig dpConfig = new ModbusDataPointConfig();
                            dpConfig.dataPointName = strArr[0];
                            dpConfig.unitId = Integer.valueOf(strArr[1]).byteValue();
                            dpConfig.functionCode = Integer.valueOf(strArr[2]).shortValue();
                            dpConfig.regAddr = Integer.valueOf(strArr[3]).shortValue();
                            dpConfig.regCount = Integer.valueOf(strArr[4]).shortValue();
                            dpConfig.dataType = strArr[5].toLowerCase();
                            if (strArr.length > 6) {
                                dpConfig.scale = Float.valueOf(strArr[6]);
                            }
                            if (strArr.length > 7) {
                                dpConfig.offset = Float.valueOf(strArr[7]);
                            }

                            dataPointConfigs.add(dpConfig);
                        }
                    }
                    lineNumber++;
                }
            }

        } catch (IOException ex) {
            logger.error("readConfig:" + fileName + " ex:", ex);
        } catch (NumberFormatException ex) {
            logger.warn("点位配置文件格式不正确，文件 %s, 第%d行。", fileName, lineNumber);
        }
        return dataPointConfigs;
    }

}