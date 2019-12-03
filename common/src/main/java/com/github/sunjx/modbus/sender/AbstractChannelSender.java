package com.github.sunjx.modbus.sender;

import com.github.sunjx.modbus.ModbusConsts;
import com.github.sunjx.modbus.cache.ModebusFrameCacheFactory;
import com.github.sunjx.modbus.handler.ModbusChannelSyncHandler;
import com.github.sunjx.modbus.handler.ModbusResponseHandler;
import com.github.sunjx.modbus.protocol.ModbusFunction;
import com.github.sunjx.modbus.protocol.tcp.ModbusFrame;
import com.github.sunjx.modbus.protocol.tcp.ModbusHeader;
import com.github.sunjx.modbus.util.ModbusTransactionIdUtil;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import io.netty.util.concurrent.GenericFutureListener;


public abstract class AbstractChannelSender {
    private short protocolIdentifier;
    private short unitIdentifier;
    private Channel channel;

    /*  44 */
    public Channel getChannel() {
        return this.channel;
    }


    /*  48 */
    public AbstractChannelSender(Channel channel) {
        this(channel, ModbusConsts.DEFAULT_UNIT_IDENTIFIER, (short) 0);
    }


    /*  52 */
    public AbstractChannelSender(Channel channel, short unitIdentifier) {
        this(channel, unitIdentifier, (short) 0);
    }


    public AbstractChannelSender(Channel channel, short unitId, short protocolIdentifier) {
        /*  56 */
        this.channel = channel;
        /*  57 */
        this.unitIdentifier = unitId;
        /*  58 */
        this.protocolIdentifier = protocolIdentifier;
    }

    protected int callModbusFunction(ModbusFunction function) throws NullPointerException {
        if (getChannel() == null) {
            throw new NullPointerException("com/github/sunjx/modbus/channel");
        }
        ModbusFrame frame = buildModbusFrame(function);
        return sendModbusFrame(frame);
    }

    protected ModbusFrame buildModbusFrame(ModbusFunction function) {
        ModbusHeader header = buildModbusHeader(function);
        return new ModbusFrame(header, function);
    }

    protected ModbusHeader buildModbusHeader(ModbusFunction function) {
        int transactionId = ModbusTransactionIdUtil.calculateTransactionId();
        int pduLength = function.calculateLength();
        ModbusHeader header = new ModbusHeader(transactionId, this.protocolIdentifier, pduLength, this.unitIdentifier);
        return header;
    }

    public int sendModbusFrame(final ModbusFrame frame) {
        final Short funcCode = frame.getFunction().getFunctionCode();
        ModebusFrameCacheFactory.getInstance().getRequestCache(funcCode).put(frame);
        final int transactionId = ModbusTransactionIdUtil.getTransactionIdByRemote(this.channel);
        ChannelFutureListener channelFutureListener = new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    ModebusFrameCacheFactory.getInstance().getRequestCache(funcCode).put(transactionId, frame);
                } else {
                    channelFuture.channel().close();
                }
            }
        };

        Channel channel = getChannel();
        channelSyncTryAcquire(channel, funcCode);
        channel.writeAndFlush(frame).addListener(channelFutureListener);
        return (frame.getHeader().getTransactionIdentifier() < 0) ? transactionId : frame.getHeader().getTransactionIdentifier();
    }


    /* 103 */
    protected boolean isChannelSync(Channel channel) {
        return (channel.pipeline().get("sync") != null);
    }


    protected void channelSyncTryAcquire(Channel channel, Short funcCode) {
        /* 107 */
        if (isChannelSync(channel)) {
            /* 108 */
            ModbusChannelSyncHandler handler = (ModbusChannelSyncHandler) channel.pipeline().get("sync");
            /* 109 */
            handler.tryAcquire(channel, funcCode);
        }
    }

    protected <V extends ModbusFunction> V callModbusFunctionSync(ModbusFunction function) throws Exception {
        int transactionId = callModbusFunction(function);
        short funcCode = function.getFunctionCode();
        return (V) getResponseHandler(getChannel()).getResponseCache(transactionId, funcCode).getFunction();
    }


    protected ModbusResponseHandler getResponseHandler(Channel channel) {
        ModbusResponseHandler handler = (ModbusResponseHandler) channel.pipeline().get("responseHandler");
        if (handler == null) {
            throw new NullPointerException("com/github/sunjx/modbus/handler");
        }
        return handler;
    }
}


/* Location:              D:\logs\easymodbus4j-0.0.5.jar!\com\github\zengfr\easymodbus4j\com.github.sunjx.modbus.sender\AbstractChannelSender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */