package com.github.sunjx.modbus.handler;

import com.github.sunjx.modbus.ModbusConfs;
import com.github.sunjx.modbus.cache.ModebusFrameCache;
import com.github.sunjx.modbus.cache.ModebusFrameCacheFactory;
import com.github.sunjx.modbus.event.SyncStateEvent;
import com.github.sunjx.modbus.func.AbstractRequest;
import com.github.sunjx.modbus.func.response.ErrorFunctionResponse;
import com.github.sunjx.modbus.logging.ChannelLogger;
import com.github.sunjx.modbus.protocol.ModbusFunction;
import com.github.sunjx.modbus.protocol.tcp.ModbusFrame;
import com.github.sunjx.modbus.util.ModbusTransactionIdUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;


public abstract class ModbusResponseHandler
        extends ModbusInboundHandler {
    private static final ChannelLogger log = ChannelLogger.getLogger(ModbusResponseHandler.class);
    private boolean isCacheResponse = false;
    private int responseFrameIgnoreLengthThreshold = 0;


    /*  46 */
    public ModbusResponseHandler(boolean isCacheResponse) {
        this(isCacheResponse, ModbusConfs.RESPONS_EFRAME_IGNORE_LENGTH_THRESHOLD);
    }


    public ModbusResponseHandler(boolean isCacheResponse, int responseFrameIgnoreLengthThreshold) {
        this.isCacheResponse = isCacheResponse;
        this.responseFrameIgnoreLengthThreshold = responseFrameIgnoreLengthThreshold;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ModbusFrame response) throws Exception {
        log.debug(ctx.channel(), "channelRead0", new Object[0]);
        this.cacheResponse(response);
        boolean isFrameIgnored = this.isFrameIgnored(response);
        AttributeKey<Boolean> key = AttributeKey.valueOf("isFrameIgnored");
        Attribute<Boolean> attr = ctx.channel().attr(key);
        attr.set(isFrameIgnored);
        if (!isFrameIgnored) {
            boolean success = this.processResponseFrame(ctx.channel(), response);
            if (success) {
                ctx.fireUserEventTriggered(new SyncStateEvent(response.getFunction().getFunctionCode(), true, false));
            } else {
                log.debug(ctx.channel(), "success:" + success, new Object[0]);
            }
        } else {
            log.debug(ctx.channel(), "isFrameIgnored:" + isFrameIgnored, new Object[0]);
        }

    }

    protected boolean isFrameIgnored(ModbusFrame response) {
        /*  77 */
        int ignoreLengthThreshold = this.responseFrameIgnoreLengthThreshold;
        /*  78 */
        return (ignoreLengthThreshold > 0 && response.getHeader().getLength() > ignoreLengthThreshold);
    }

    protected void cacheResponse(ModbusFrame response) {
        if (this.isCacheResponse && response != null) {
            Short funcCode = response.getFunction().getFunctionCode();
            int respTransactionIdentifier = response.getHeader().getTransactionIdentifier();
            ModebusFrameCacheFactory.getInstance().getResponseCache(funcCode).put(respTransactionIdentifier, response);
        }
    }

    protected boolean processResponseFrame(Channel channel, ModbusFrame respFrame) {
        boolean success = false;
        int respTransactionIdentifier = respFrame.getHeader().getTransactionIdentifier();
        int reqTransactionIdentifier = getReqTransactionIdByRespTransactionId(respTransactionIdentifier);
        if (respTransactionIdentifier < 0 || reqTransactionIdentifier < 0) {
            respTransactionIdentifier = reqTransactionIdentifier = ModbusTransactionIdUtil.getTransactionIdByRemote(channel);
        }
        short funcCode = respFrame.getFunction().getFunctionCode();
        ModbusFrame reqFrame = getRequestCache(reqTransactionIdentifier, funcCode);
        AbstractRequest reqFunc = null;
        if (reqFrame != null) {
            reqFunc = (AbstractRequest) reqFrame.getFunction();
        } else {
            log.warn(channel, String.format("req is null:%s;%s;%s;%s", new Object[]{Integer.valueOf(reqTransactionIdentifier), Integer.valueOf(respTransactionIdentifier), getRequestCache(funcCode).keySet(), respFrame}), new Object[0]);
        }
        short unitId = respFrame.getHeader().getUnitIdentifier();
        ModbusFunction respFunc = respFrame.getFunction();
        if (reqFrame == null || reqFrame.getHeader().getUnitIdentifier() == unitId) {
            success = processResponseFrame(channel, unitId, reqFunc, respFunc);
            if (success) {
                removeRequestCache(reqTransactionIdentifier, funcCode);
            }
        }
        return success;
    }


    /* 116 */
    protected ModebusFrameCache getRequestCache(short funcCode) {
        return ModebusFrameCacheFactory.getInstance().getRequestCache(Short.valueOf(funcCode));
    }


    protected ModbusFrame getRequestCache(int reqTransactionIdentifier, short funcCode) {
        /* 120 */
        ModbusFrame reqFrame = getRequestCache(funcCode).get(Integer.valueOf(reqTransactionIdentifier));
        /* 121 */
        return reqFrame;
    }


    /* 125 */
    protected void removeRequestCache(int reqTransactionIdentifier, short funcCode) {
        getRequestCache(funcCode).remove(Integer.valueOf(reqTransactionIdentifier));
    }


    /* 129 */
    protected ModebusFrameCache getResponseCache(short funcCode) {
        return ModebusFrameCacheFactory.getInstance().getResponseCache(Short.valueOf(funcCode));
    }


    public ModbusFrame getResponseCache(int respTransactionIdentifier, short funcCode) throws Exception {
        ModbusFrame frame;
        long timeoutTime = System.currentTimeMillis() + (long)ModbusConfs.SYNC_RESPONSE_TIMEOUT;
        ModebusFrameCache responseCache = getResponseCache(funcCode);
        do {
            frame = responseCache.get(Integer.valueOf(respTransactionIdentifier));
            if (frame != null) {
                continue;
            }
            Thread.sleep(22L);
        } while (frame == null && timeoutTime - System.currentTimeMillis() > 0L);
        /* 143 */
        long size = responseCache.size();
        /* 144 */
        if (frame != null) {
            /* 145 */
            responseCache.remove(Integer.valueOf(respTransactionIdentifier));
        }
        /* 147 */
        if (frame == null) {
            throw new Exception(String.format("resp is null!(%s,%s,%s,%s)", new Object[]{Integer.valueOf(ModbusConfs.SYNC_RESPONSE_TIMEOUT), Integer.valueOf(respTransactionIdentifier), Long.valueOf(size), responseCache.keySet()}));
        } else if (frame.getFunction() instanceof ErrorFunctionResponse) {
            throw new Exception("" + ((ErrorFunctionResponse) frame.getFunction()).getExceptionMessage());
        }
        /* 152 */
        return frame;
    }

    protected abstract int getReqTransactionIdByRespTransactionId(int paramInt);

    protected abstract boolean processResponseFrame(Channel paramChannel, int paramInt, AbstractRequest paramAbstractRequest, ModbusFunction paramModbusFunction);
}
