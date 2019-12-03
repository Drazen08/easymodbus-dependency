package com.github.sunjx.modbus.func.response;

import com.github.sunjx.modbus.func.AbstractReadResponse;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;


public class ReadHoldingRegistersResponse
        extends AbstractReadResponse {
    private short byteCount;
    private int[] registers;

    /* 31 */
    public ReadHoldingRegistersResponse() {
        super((short) 3);
    }


    public ReadHoldingRegistersResponse(int[] registers) {
        super((short) 3);
        if (registers.length > 125) {
            throw new IllegalArgumentException();
        }
        this.byteCount = (short) (registers.length * 2);
        this.registers = registers;
    }


    /* 47 */
    public int[] getRegisters() {
        return this.registers;
    }


    /* 51 */
    public short getByteCount() {
        return this.byteCount;
    }


    /* 56 */
    @Override
    public int calculateLength() {
        return 2 + this.byteCount;
    }


    @Override
    public ByteBuf encode() {
        ByteBuf buf = Unpooled.buffer(calculateLength());
        buf.writeByte(getFunctionCode());
        buf.writeByte(this.byteCount);
        for (int i = 0; i < this.registers.length; i++) {
            buf.writeShort(this.registers[i]);
        }
        return buf;
    }


    @Override
    public void decode(ByteBuf data) {
        this.byteCount = data.readUnsignedByte();
        this.registers = new int[this.byteCount / 2];
        for (int i = 0; i < this.registers.length; i++) {
            this.registers[i] = data.readUnsignedShort();
        }
    }


    @Override
    public String toString() {
        StringBuilder registersStr = new StringBuilder();
        registersStr.append("{");
        for (int i = 0; i < this.registers.length; i++) {
            registersStr.append(i);
            registersStr.append("=");
            registersStr.append(this.registers[i]);
            registersStr.append(",");
        }
        registersStr.delete(registersStr.length() - 1, registersStr.length());
        registersStr.append("}");

        return "ReadHoldingRegistersResponse{byteCount=" + this.byteCount + ", inputRegisters=" + registersStr + '}';
    }
}

