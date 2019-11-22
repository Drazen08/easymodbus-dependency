package com.github.sunjx.modbus.handler;

import com.github.sunjx.modbus.func.request.*;
import com.github.sunjx.modbus.func.response.*;
import com.github.sunjx.modbus.logging.ChannelLogger;
import com.github.sunjx.modbus.protocol.ModbusFunction;
import com.github.sunjx.modbus.protocol.tcp.ModbusFrame;
import com.github.sunjx.modbus.protocol.tcp.ModbusHeader;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.GenericFutureListener;


/**
 * modbus 消息处理 handler
 */
public abstract class ModbusRequestHandler
        extends ModbusInboundHandler {
    private static final ChannelLogger log = ChannelLogger.getLogger(ModbusRequestHandler.class);


    protected void channelRead0(ChannelHandlerContext ctx, ModbusFrame frame) throws Exception {
        Channel channel = ctx.channel();
        log.debug(channel, "channelRead0", new Object[0]);
        ModbusFunction responseFunc = processRequestFrame(channel, frame);
        ModbusFrame responseFrame = buildResponseModbusFrame(frame, responseFunc);
        channel.write(responseFrame).addListener((GenericFutureListener) ChannelFutureListener.CLOSE_ON_FAILURE);
    }

    protected ModbusFrame buildResponseModbusFrame(ModbusFrame reqFrame, ModbusFunction response) {
        int respTransactionId = getRespTransactionIdByReqTransactionId(reqFrame.getHeader().getTransactionIdentifier());
        ModbusHeader header = new ModbusHeader(respTransactionId, reqFrame.getHeader().getProtocolIdentifier(), response.calculateLength(), reqFrame.getHeader().getUnitIdentifier());
        return new ModbusFrame(header, response);
    }

    protected ModbusFunction processRequestFrame(Channel channel, ModbusFrame frame) {
        Object object;
        /* 69 */
        ModbusFunction function = frame.getFunction();
        /* 70 */
        short unitIdentifier = frame.getHeader().getUnitIdentifier();
        /* 71 */
        if (function instanceof WriteSingleCoilRequest) {
            /* 72 */
            WriteSingleCoilRequest request = (WriteSingleCoilRequest) function;
            /* 73 */
            object = writeSingleCoil(unitIdentifier, request);
            /* 74 */
        } else if (function instanceof WriteSingleRegisterRequest) {
            /* 75 */
            WriteSingleRegisterRequest request = (WriteSingleRegisterRequest) function;
            /* 76 */
            object = writeSingleRegister(unitIdentifier, request);
            /* 77 */
        } else if (function instanceof ReadCoilsRequest) {
            /* 78 */
            ReadCoilsRequest request = (ReadCoilsRequest) function;
            /* 79 */
            object = readCoils(unitIdentifier, request);
            /* 80 */
        } else if (function instanceof ReadDiscreteInputsRequest) {
            /* 81 */
            ReadDiscreteInputsRequest request = (ReadDiscreteInputsRequest) function;
            /* 82 */
            object = readDiscreteInputs(unitIdentifier, request);
            /* 83 */
        } else if (function instanceof ReadInputRegistersRequest) {
            /* 84 */
            ReadInputRegistersRequest request = (ReadInputRegistersRequest) function;
            /* 85 */
            object = readInputRegisters(unitIdentifier, request);
            /* 86 */
        } else if (function instanceof ReadHoldingRegistersRequest) {
            /* 87 */
            ReadHoldingRegistersRequest request = (ReadHoldingRegistersRequest) function;
            /* 88 */
            object = readHoldingRegisters(unitIdentifier, request);
            /* 89 */
        } else if (function instanceof WriteMultipleRegistersRequest) {
            /* 90 */
            WriteMultipleRegistersRequest request = (WriteMultipleRegistersRequest) function;
            /* 91 */
            object = writeMultipleRegisters(unitIdentifier, request);
            /* 92 */
        } else if (function instanceof WriteMultipleCoilsRequest) {
            /* 93 */
            WriteMultipleCoilsRequest request = (WriteMultipleCoilsRequest) function;
            /* 94 */
            object = writeMultipleCoils(unitIdentifier, request);
        } else {
            /* 96 */
            throw new UnsupportedOperationException("Function not supported!" + function);
        }
        /* 98 */
        return (ModbusFunction) object;
    }

    protected abstract int getRespTransactionIdByReqTransactionId(int paramInt);

    protected abstract ReadCoilsResponse readCoils(short paramShort, ReadCoilsRequest paramReadCoilsRequest);

    protected abstract ReadDiscreteInputsResponse readDiscreteInputs(short paramShort, ReadDiscreteInputsRequest paramReadDiscreteInputsRequest);

    protected abstract ReadInputRegistersResponse readInputRegisters(short paramShort, ReadInputRegistersRequest paramReadInputRegistersRequest);


    protected abstract ReadHoldingRegistersResponse readHoldingRegisters(short paramShort, ReadHoldingRegistersRequest paramReadHoldingRegistersRequest);

    protected abstract WriteSingleCoilResponse writeSingleCoil(short paramShort, WriteSingleCoilRequest paramWriteSingleCoilRequest);

    protected abstract WriteSingleRegisterResponse writeSingleRegister(short paramShort, WriteSingleRegisterRequest paramWriteSingleRegisterRequest);

    protected abstract WriteMultipleCoilsResponse writeMultipleCoils(short paramShort, WriteMultipleCoilsRequest paramWriteMultipleCoilsRequest);

    protected abstract WriteMultipleRegistersResponse writeMultipleRegisters(short paramShort, WriteMultipleRegistersRequest paramWriteMultipleRegistersRequest);
}


/* Location:              D:\logs\easymodbus4j-0.0.5.jar!\com\github\zengfr\easymodbus4j\com.github.sunjx.modbus.handler\ModbusRequestHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */