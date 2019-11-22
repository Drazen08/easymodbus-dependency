package com.github.sunjx.modbus.schedule;

import com.github.sunjx.modbus.common.util.ScheduledUtil;
import com.github.sunjx.modbus.sender.util.ModbusRequestSendUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.netty.channel.Channel;
import io.netty.util.internal.logging.InternalLogger;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;


public abstract class ModbusMasterSchedule {
    /* 42 */   private Cache<Integer, List<String>> cache = CacheBuilder.newBuilder().expireAfterWrite(300000L, TimeUnit.MILLISECONDS).build();

    protected abstract ModbusRequestSendUtil.PriorityStrategy getPriorityStrategy();

    protected abstract List<String> buildReqsList();

    protected List<String> getReqsListByCache() {
        /* 45 */
        Integer key = Integer.valueOf(0);
        /* 46 */
        List<String> reqStrs = (List<String>) this.cache.getIfPresent(key);
        /* 47 */
        if (reqStrs == null || reqStrs.isEmpty()) {
            /* 48 */
            reqStrs = buildReqsList();
            /* 49 */
            this.cache.put(key, reqStrs);
        }
        /* 51 */
        return reqStrs;
    }

    protected abstract int getFixedDelay();

    protected abstract InternalLogger getLogger();

    public void schedule(Collection<Channel> channels, int sleep) {
        /* 55 */
        getLogger().debug(String.format("com.github.sunjx.modbus.schedule->channels:%s,sleep:%s ms", new Object[]{Integer.valueOf(channels.size()), Integer.valueOf(sleep)}));
        /* 56 */
        sendRequests4Auto(channels, sleep);
    }

    public void run(Collection<Channel> channels) {
        /* 60 */
        List<String> reqStrs = buildReqsList();
        /* 61 */
        getLogger().debug(String.format("channels:%s,reqStrs:%s", new Object[]{Integer.valueOf(channels.size()), Integer.valueOf(reqStrs.size())}));
        /* 62 */
        ModbusRequestSendUtil.sendRequests(channels, reqStrs, true, getFixedDelay(), getPriorityStrategy());
    }

    private void sendRequests4Auto(Collection<Channel> channels, int sleep) {
        /* 66 */
        Runnable r = () -> {
            try {
                /* 68 */
                List<String> reqStrs = getReqsListByCache();
                /* 69 */
                getLogger().debug(String.format("channels:%s,reqStrs:%s,sleep:%s ms", new Object[]{Integer.valueOf(channels.size()), Integer.valueOf(reqStrs.size()), Integer.valueOf(sleep)}));
                /* 70 */
                ModbusRequestSendUtil.sendRequests(channels, reqStrs, true, getFixedDelay(), getPriorityStrategy());
                /* 71 */
            } catch (Exception ex) {
                /* 72 */
                getLogger().error(ex);
            }
        };
        /* 75 */
        ScheduledUtil.scheduleWithFixedDelay(r, sleep);
    }
}


/* Location:              D:\logs\easymodbus4j-0.0.5.jar!\com\github\zengfr\easymodbus4j\com.github.sunjx.modbus.schedule\ModbusMasterSchedule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */