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
public abstract class ModbusRequestHandler extends ModbusInboundHandler {
    private static final ChannelLogger log = ChannelLogger.getLogger(ModbusRequestHandler.class);


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ModbusFrame frame) throws Exception {
        Channel channel = ctx.channel();
        log.debug(channel, "channelRead0");
        ModbusFunction responseFunc = processRequestFrame(channel, frame);
        ModbusFrame responseFrame = buildResponseModbusFrame(frame, responseFunc);
        channel.write(responseFrame).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
    }

    protected ModbusFrame buildResponseModbusFrame(ModbusFrame reqFrame, ModbusFunction response) {
        int respTransactionId = getRespTransactionIdByReqTransactionId(reqFrame.getHeader().getTransactionIdentifier());
        ModbusHeader header = new ModbusHeader(respTransactionId, reqFrame.getHeader().getProtocolIdentifier(), response.calculateLength(), reqFrame.getHeader().getUnitIdentifier());
        return new ModbusFrame(header, response);
    }

    protected ModbusFunction processRequestFrame(Channel channel, ModbusFrame frame) {
        ModbusFunction object;
        ModbusFunction function = frame.getFunction();
        short unitIdentifier = frame.getHeader().getUnitIdentifier();
        if (function instanceof WriteSingleCoilRequest) {
            WriteSingleCoilRequest request = (WriteSingleCoilRequest) function;
            object = writeSingleCoil(unitIdentifier, request);
        } else if (function instanceof WriteSingleRegisterRequest) {
            WriteSingleRegisterRequest request = (WriteSingleRegisterRequest) function;
            object = writeSingleRegister(unitIdentifier, request);
        } else if (function instanceof ReadCoilsRequest) {
            ReadCoilsRequest request = (ReadCoilsRequest) function;
            object = readCoils(unitIdentifier, request);
        } else if (function instanceof ReadDiscreteInputsRequest) {
            ReadDiscreteInputsRequest request = (ReadDiscreteInputsRequest) function;
            object = readDiscreteInputs(unitIdentifier, request);
        } else if (function instanceof ReadInputRegistersRequest) {
            ReadInputRegistersRequest request = (ReadInputRegistersRequest) function;
            object = readInputRegisters(unitIdentifier, request);
        } else if (function instanceof ReadHoldingRegistersRequest) {
            ReadHoldingRegistersRequest request = (ReadHoldingRegistersRequest) function;
            object = readHoldingRegisters(unitIdentifier, request);
        } else if (function instanceof WriteMultipleRegistersRequest) {
            WriteMultipleRegistersRequest request = (WriteMultipleRegistersRequest) function;
            object = writeMultipleRegisters(unitIdentifier, request);
        } else if (function instanceof WriteMultipleCoilsRequest) {
            WriteMultipleCoilsRequest request = (WriteMultipleCoilsRequest) function;
            object = writeMultipleCoils(unitIdentifier, request);
        } else {
            throw new UnsupportedOperationException("Function not supported!" + function);
        }
        return object;
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


