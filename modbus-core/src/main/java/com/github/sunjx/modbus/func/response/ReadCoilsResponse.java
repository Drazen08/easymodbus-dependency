package com.github.sunjx.modbus.func.response;

import com.github.sunjx.modbus.func.AbstractReadResponse;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.BitSet;


public class ReadCoilsResponse
        extends AbstractReadResponse {
    private short byteCount;
    private BitSet coilStatus;

    public ReadCoilsResponse() {
        super((short) 1);
    }


    public ReadCoilsResponse(BitSet coilStatus) {
        super((short) 1);

        byte[] coils = coilStatus.toByteArray();

        if (coils.length > 250) {
            throw new IllegalArgumentException();
        }

        this.byteCount = (short) coils.length;
        this.coilStatus = coilStatus;
    }


    public BitSet getCoilStatus() {
        return this.coilStatus;
    }


    public short getByteCount() {
        return this.byteCount;
    }


    @Override
    public int calculateLength() {
        return 2 + this.byteCount;
    }


    @Override
    public ByteBuf encode() {
        ByteBuf buf = Unpooled.buffer(calculateLength());
        buf.writeByte(getFunctionCode());
        buf.writeByte(this.byteCount);
        buf.writeBytes(this.coilStatus.toByteArray());
        return buf;
    }


    @Override
    public void decode(ByteBuf data) {
        this.byteCount = data.readUnsignedByte();

        byte[] coils = new byte[this.byteCount];
        data.readBytes(coils);

        this.coilStatus = BitSet.valueOf(coils);
    }


    /* 84 */
    @Override
    public String toString() {
        return "ReadCoilsResponse{byteCount=" + this.byteCount + ", coilStatus=" + this.coilStatus + '}';
    }
}


