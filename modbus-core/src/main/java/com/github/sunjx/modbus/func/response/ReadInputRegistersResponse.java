package com.github.sunjx.modbus.func.response;

import com.github.sunjx.modbus.func.AbstractReadResponse;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;


public class ReadInputRegistersResponse
        extends AbstractReadResponse {
    private short byteCount;
    private int[] inputRegisters;

    public ReadInputRegistersResponse() {
        super((short) 4);
    }


    public ReadInputRegistersResponse(int[] inputRegisters) {
        super((short) 4);

        if (inputRegisters.length > 125) {
            throw new IllegalArgumentException();
        }
        this.byteCount = (short) (inputRegisters.length * 2);
        this.inputRegisters = inputRegisters;
    }

    public int[] getInputRegisters() {
        return this.inputRegisters;
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
        /* 61 */
        ByteBuf buf = Unpooled.buffer(calculateLength());
        /* 62 */
        buf.writeByte(getFunctionCode());
        /* 63 */
        buf.writeByte(this.byteCount);

        /* 65 */
        for (int i = 0; i < this.inputRegisters.length; i++) {
            /* 66 */
            buf.writeShort(this.inputRegisters[i]);
        }

        /* 69 */
        return buf;
    }


    @Override
    public void decode(ByteBuf data) {
        /* 74 */
        this.byteCount = data.readUnsignedByte();

        /* 76 */
        this.inputRegisters = new int[this.byteCount / 2];
        /* 77 */
        for (int i = 0; i < this.inputRegisters.length; i++) {
            /* 78 */
            this.inputRegisters[i] = data.readUnsignedShort();
        }
    }


    public String toString() {
        /* 84 */
        StringBuilder registers = new StringBuilder();
        /* 85 */
        registers.append("{");
        /* 86 */
        for (int i = 0; i < this.inputRegisters.length; i++) {
            /* 87 */
            registers.append(i);
            /* 88 */
            registers.append("=");
            /* 89 */
            registers.append(this.inputRegisters[i]);
            /* 90 */
            registers.append(",");
        }
        /* 92 */
        registers.delete(registers.length() - 1, registers.length());
        /* 93 */
        registers.append("}");

        /* 95 */
        return "ReadInputRegistersResponse{byteCount=" + this.byteCount + ", inputRegisters=" + registers + '}';
    }
}


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4j\func\response\ReadInputRegistersResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */