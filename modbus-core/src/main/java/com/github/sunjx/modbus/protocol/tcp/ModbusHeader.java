package com.github.sunjx.modbus.protocol.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;


public class ModbusHeader {
    private final int transactionIdentifier;
    private final int protocolIdentifier;
    private final int length;
    private final short unitIdentifier;

    public ModbusHeader(int transactionIdentifier, int protocolIdentifier, int pduLength, short unitIdentifier) {
        /* 33 */
        this.transactionIdentifier = transactionIdentifier;
        /* 34 */
        this.protocolIdentifier = protocolIdentifier;
        /* 35 */
        this.length = pduLength + 1;
        /* 36 */
        this.unitIdentifier = unitIdentifier;
    }


    /* 40 */
    public int getLength() {
        return this.length;
    }


    /* 44 */
    public int getProtocolIdentifier() {
        return this.protocolIdentifier;
    }


    /* 48 */
    public int getTransactionIdentifier() {
        return this.transactionIdentifier;
    }


    /* 52 */
    public short getUnitIdentifier() {
        return this.unitIdentifier;
    }


    public static ModbusHeader decode(ByteBuf buffer) {
        /* 56 */
        return new ModbusHeader(buffer.readUnsignedShort(), buffer
                .readUnsignedShort(), buffer
                .readUnsignedShort() - 1, buffer
                .readUnsignedByte());
    }

    public ByteBuf encode() {
        /* 63 */
        ByteBuf buf = Unpooled.buffer();

        // 写2 byte 定长
        buf.writeShort(this.transactionIdentifier);

        buf.writeShort(this.protocolIdentifier);

        buf.writeShort(this.length);

        buf.writeByte(this.unitIdentifier);

        return buf;
    }


    /* 75 */
    @Override
    public String toString() {
        return "T=" + this.transactionIdentifier + " P=" + this.protocolIdentifier + " L=" + this.length + " U=" + this.unitIdentifier;
    }
}


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4j\protocol\tcp\ModbusHeader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */