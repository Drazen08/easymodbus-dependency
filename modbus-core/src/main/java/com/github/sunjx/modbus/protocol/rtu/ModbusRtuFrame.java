package com.github.sunjx.modbus.protocol.rtu;

import com.github.sunjx.modbus.common.util.RtuCrcUtil;
import com.github.sunjx.modbus.protocol.ModbusFunction;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;


public class ModbusRtuFrame {
    private final short unitIdentifier;
    private final ModbusFunction function;

    public ModbusRtuFrame(short unitIdentifier, ModbusFunction function) {
        /* 34 */
        this.unitIdentifier = unitIdentifier;
        /* 35 */
        this.function = function;
    }


    /* 39 */
    public short getUnitId() {
        return this.unitIdentifier;
    }


    /* 43 */
    public ModbusFunction getFunction() {
        return this.function;
    }


    public ByteBuf encode() {
        /* 47 */
        ByteBuf buf = Unpooled.buffer();

        /* 49 */
        buf.writeByte(this.unitIdentifier);
        /* 50 */
        buf.writeBytes(this.function.encode());
        /* 51 */
        writeRtuCRC(buf);
        /* 52 */
        return buf;
    }

    public static void writeRtuCRC(ByteBuf buffer) {
        /* 55 */
        int startReaderIndex = buffer.readerIndex();
        /* 56 */
        int crc = RtuCrcUtil.calculateCRC(buffer);
        /* 57 */
        buffer.readerIndex(startReaderIndex);
        /* 58 */
        buffer.writeByte((byte) (0xFF & crc >> 8));
        /* 59 */
        buffer.writeByte((byte) (0xFF & crc));
    }


    /* 63 */
    @Override
    public String toString() {
        return this.unitIdentifier + " " + this.function;
    }
}


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4j\protocol\rtu\ModbusRtuFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */