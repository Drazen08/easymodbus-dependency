package com.github.sunjx.modbus.handle.impl;

import com.github.sunjx.modbus.func.AbstractRequest;
import com.github.sunjx.modbus.handler.ModbusResponseHandler;
import com.github.sunjx.modbus.logging.ChannelLogger;
import com.github.sunjx.modbus.processor.ModbusMasterResponseProcessor;
import com.github.sunjx.modbus.protocol.ModbusFunction;
import com.github.sunjx.modbus.protocol.tcp.ModbusFrame;
import com.github.sunjx.modbus.util.ModbusFrameUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;


@Sharable
public class ModbusMasterResponseHandler extends ModbusResponseHandler {
    private static final ChannelLogger log = ChannelLogger.getLogger(ModbusMasterResponseHandler.class);

    private ModbusMasterResponseProcessor processor;


    public short getTransactionIdentifierOffset() {
        return this.processor.getTransactionIdentifierOffset();
    }

    public ModbusMasterResponseHandler(ModbusMasterResponseProcessor processor) {
        super(true);
        this.processor = processor;
    }


    @Override
    protected boolean processResponseFrame(Channel channel, ModbusFrame frame) {
        /* 48 */
        if (this.processor.isShowFrameDetail()) {
            /* 49 */
            ModbusFrameUtil.showFrameLog(log, channel, frame, true);
        }
        /* 51 */
        return super.processResponseFrame(channel, frame);
    }


    /* 56 */
    @Override
    protected int getReqTransactionIdByRespTransactionId(int respTransactionIdentifierOffset) {
        return respTransactionIdentifierOffset - getTransactionIdentifierOffset();
    }


    /* 60 */
    protected int getRespTransactionIdByReqTransactionId(int reqTransactionIdentifier) {
        return reqTransactionIdentifier + getTransactionIdentifierOffset();
    }


    @Override
    public ModbusFrame getResponseCache(int reqTransactionIdentifier, short funcCode) throws Exception {
        /* 65 */
        int respTransactionIdentifier = getRespTransactionIdByReqTransactionId(reqTransactionIdentifier);
        /* 66 */
        return super.getResponseCache(respTransactionIdentifier, funcCode);
    }

    @Override
    protected boolean processResponseFrame(Channel channel, int unitId, AbstractRequest reqFunc, ModbusFunction respFunc) {
        return this.processor.processResponseFrame(channel, unitId, reqFunc, respFunc);
    }

}
