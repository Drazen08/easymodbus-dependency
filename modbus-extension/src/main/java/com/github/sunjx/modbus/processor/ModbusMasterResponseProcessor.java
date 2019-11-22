package com.github.sunjx.modbus.processor;

import com.github.zengfr.easymodbus4j.func.AbstractRequest;
import com.github.zengfr.easymodbus4j.protocol.ModbusFunction;
import io.netty.channel.Channel;

public interface ModbusMasterResponseProcessor extends ModbusProcessor {
  boolean processResponseFrame(Channel paramChannel, int paramInt, AbstractRequest paramAbstractRequest, ModbusFunction paramModbusFunction);
}


/* Location:              D:\logs\easymodbus4j-extension-0.0.5.jar!\com\github\zengfr\easymodbus4j\processor\ModbusMasterResponseProcessor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.2
 */