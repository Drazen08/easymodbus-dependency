package com.github.sunjx.modbus.cache;

import com.github.sunjx.modbus.ModbusConfs;
import com.github.sunjx.modbus.protocol.tcp.ModbusFrame;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.concurrent.TimeUnit;


public class ModebusFrameCache {
    private static Logger log = LoggerFactory.getLogger(ModebusFrameCache.class);
    private Cache<Integer, ModbusFrame> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(ModbusConfs.FRAME_CACHE_EXPIRE, TimeUnit.MILLISECONDS).build();

    public ModbusFrame get(Integer key) {
        log.debug("get:{}", key);
        return (ModbusFrame) this.cache.getIfPresent(key);
    }


    public void put(ModbusFrame value) {
        put(Integer.valueOf(value.getHeader().getTransactionIdentifier()), value);
    }


    public void put(Integer key, ModbusFrame value) {
        this.cache.put(key, value);
        log.debug("put:{},{}", key, Short.valueOf(value.getFunction().getFunctionCode()));
    }


    public Set<Integer> keySet() {
        return this.cache.asMap().keySet();
    }


    public long size() {
        return this.cache.size();
    }


    public void remove(Integer key) {
        this.cache.invalidate(key);
        log.debug("remove:{}", key);
    }
}

