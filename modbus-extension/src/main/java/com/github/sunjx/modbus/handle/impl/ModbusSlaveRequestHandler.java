package com.github.sunjx.modbus.handle.impl;

import com.github.sunjx.modbus.func.request.*;
import com.github.sunjx.modbus.func.response.*;
import com.github.sunjx.modbus.handler.ModbusRequestHandler;
import com.github.sunjx.modbus.logging.ChannelLogger;
import com.github.sunjx.modbus.processor.ModbusSlaveRequestProcessor;
import com.github.sunjx.modbus.protocol.ModbusFunction;
import com.github.sunjx.modbus.protocol.tcp.ModbusFrame;
import com.github.sunjx.modbus.util.ModbusFrameUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;


@Sharable
public class ModbusSlaveRequestHandler
        extends ModbusRequestHandler {
    /*  52 */   private static final ChannelLogger log = ChannelLogger.getLogger(ModbusSlaveRequestHandler.class);

    private ModbusSlaveRequestProcessor processor;


    /*  57 */
    public short getTransactionIdentifierOffset() {
        return this.processor.getTransactionIdentifierOffset();
    }


    /*  62 */
    public ModbusSlaveRequestHandler(ModbusSlaveRequestProcessor processor) {
        this.processor = processor;
    }


    /*  67 */
    @Override
    protected int getRespTransactionIdByReqTransactionId(int reqTransactionIdentifier) {
        return reqTransactionIdentifier + getTransactionIdentifierOffset();
    }


    @Override
    protected ModbusFunction processRequestFrame(Channel channel, ModbusFrame frame) {
        /*  72 */
        if (this.processor.isShowFrameDetail()) {
            /*  73 */
            ModbusFrameUtil.showFrameLog(log, channel, frame, true);
        }
        /*  75 */
        return super.processRequestFrame(channel, frame);
    }


    /*  81 */
    @Override
    protected WriteSingleCoilResponse writeSingleCoil(short unitIdentifier, WriteSingleCoilRequest request) {
        return this.processor.writeSingleCoil(unitIdentifier, request);
    }


    /*  86 */
    @Override
    protected WriteSingleRegisterResponse writeSingleRegister(short unitIdentifier, WriteSingleRegisterRequest request) {
        return this.processor.writeSingleRegister(unitIdentifier, request);
    }


    /*  92 */
    @Override
    protected ReadCoilsResponse readCoils(short unitIdentifier, ReadCoilsRequest request) {
        return this.processor.readCoils(unitIdentifier, request);
    }


    /*  98 */
    @Override
    protected ReadDiscreteInputsResponse readDiscreteInputs(short unitIdentifier, ReadDiscreteInputsRequest request) {
        return this.processor.readDiscreteInputs(unitIdentifier, request);
    }


    /* 104 */
    @Override
    protected ReadInputRegistersResponse readInputRegisters(short unitIdentifier, ReadInputRegistersRequest request) {
        return this.processor.readInputRegisters(unitIdentifier, request);
    }


    /* 110 */
    @Override
    protected ReadHoldingRegistersResponse readHoldingRegisters(short unitIdentifier, ReadHoldingRegistersRequest request) {
        return this.processor.readHoldingRegisters(unitIdentifier, request);
    }


    /* 116 */
    @Override
    protected WriteMultipleCoilsResponse writeMultipleCoils(short unitIdentifier, WriteMultipleCoilsRequest request) {
        return this.processor.writeMultipleCoils(unitIdentifier, request);
    }


    /* 122 */
    @Override
    protected WriteMultipleRegistersResponse writeMultipleRegisters(short unitIdentifier, WriteMultipleRegistersRequest request) {
        return this.processor.writeMultipleRegisters(unitIdentifier, request);
    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, ModbusFrame modbusFrame) throws Exception {

    }
}

