package com.github.sunjx.modbus.func.request;

import com.github.sunjx.modbus.common.util.BitSetUtil;
import com.github.sunjx.modbus.func.AbstractRequest;
import io.netty.buffer.ByteBuf;

import java.util.BitSet;


public class WriteMultipleCoilsRequest
        extends AbstractRequest {
    private short byteCount;
    private BitSet outputsValue;

    /* 36 */
    public WriteMultipleCoilsRequest() {
        super((short) 15);
    }


    /* 40 */
    public WriteMultipleCoilsRequest(int startingAddress, int quantityOfOutputs, boolean[] outputsValue) {
        this(startingAddress, quantityOfOutputs, BitSetUtil.fromArray(outputsValue));
    }


    public WriteMultipleCoilsRequest(int startingAddress, int quantityOfOutputs, BitSet outputsValue) {
        /* 44 */
        super((short) 15, startingAddress, quantityOfOutputs);

        /* 46 */
        byte[] coils = outputsValue.toByteArray();


        /* 49 */
        if (coils.length > 246) {
            /* 50 */
            throw new IllegalArgumentException();
        }

        /* 53 */
        this.byteCount = (short) coils.length;
        /* 54 */
        this.outputsValue = outputsValue;
    }


    /* 58 */
    public short getByteCount() {
        return this.byteCount;
    }


    /* 62 */
    public BitSet getOutputsValue() {
        return this.outputsValue;
    }


    /* 66 */
    public int getQuantityOfOutputs() {
        return this.value;
    }


    /* 70 */
    public int getStartingAddress() {
        return this.address;
    }


    /* 75 */
    @Override
    public int calculateLength() {
        return super.calculateLength() + 1 + this.byteCount;
    }


    @Override
    public ByteBuf encode() {
        /* 80 */
        ByteBuf buf = super.encode();
        /* 81 */
        buf.writeByte(this.byteCount);
        /* 82 */
        buf.writeBytes(this.outputsValue.toByteArray());

        /* 84 */
        return buf;
    }


    @Override
    public void decode(ByteBuf data) {
        /* 89 */
        super.decode(data);
        /* 90 */
        this.byteCount = data.readUnsignedByte();
        /* 91 */
        byte[] coils = new byte[this.byteCount];
        /* 92 */
        data.readBytes(coils);
        /* 93 */
        this.outputsValue = BitSet.valueOf(coils);
    }


    @Override
    /* 98 */ public String toString() {
        return "WriteMultipleCoilsRequest{startingAddress=" + this.address + ", quantityOfOutputs=" + this.value + ", byteCount=" + this.byteCount + ", outputsValue=" + this.outputsValue + '}';
    }
}

