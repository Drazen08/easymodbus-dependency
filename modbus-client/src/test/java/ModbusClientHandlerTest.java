import com.github.sunjx.modbus.func.request.*;
import com.github.sunjx.modbus.func.response.*;
import com.github.sunjx.modbus.handler.ModbusRequestHandler;
import com.github.sunjx.modbus.protocol.tcp.ModbusFrame;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @version V1.0
 * @author: sunjx
 * @create: 2019/12/3
 */
@Slf4j
public class ModbusClientHandlerTest extends ModbusRequestHandler {
    private final String ChannelKey;
    AtomicInteger integer = new AtomicInteger(1);

    ModbusClientHandlerTest(String key) {
        super();
        this.ChannelKey = key;
    }

    private void doLog(ByteBuf buf) {
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        log.info("---------------------");
        log.info("receiveBuffer:[{}]", ByteBufUtil.hexDump(bytes));
        log.info("---------------------");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ModbusFrame frame) throws Exception {
        super.channelRead0(ctx, frame);
        doLog(frame.encode());
    }

    private void startActive(ChannelHandlerContext ctx) {
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeBytes(("DTU:" + ChannelKey).getBytes(StandardCharsets.UTF_8));
        buffer.writeBytes("\0".getBytes());
        ctx.writeAndFlush(buffer);
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        startActive(ctx);
    }

    @Override
    protected int getRespTransactionIdByReqTransactionId(int paramInt) {
        if (integer.get() >= 65534) {
            integer.set(1);
        }
        return integer.incrementAndGet();
    }

    @Override
    protected ReadCoilsResponse readCoils(short paramShort, ReadCoilsRequest paramReadCoilsRequest) {
        return null;
    }

    @Override
    protected ReadDiscreteInputsResponse readDiscreteInputs(short paramShort, ReadDiscreteInputsRequest paramReadDiscreteInputsRequest) {
        return null;
    }

    @Override
    protected ReadInputRegistersResponse readInputRegisters(short paramShort, ReadInputRegistersRequest paramReadInputRegistersRequest) {
        return null;
    }

    @Override
    protected ReadHoldingRegistersResponse readHoldingRegisters(short paramShort, ReadHoldingRegistersRequest paramReadHoldingRegistersRequest) {
        return new ReadHoldingRegistersResponse(new int[]{231, 24, 34});
    }

    @Override
    protected WriteSingleCoilResponse writeSingleCoil(short paramShort, WriteSingleCoilRequest paramWriteSingleCoilRequest) {
        return null;
    }

    @Override
    protected WriteSingleRegisterResponse writeSingleRegister(short paramShort, WriteSingleRegisterRequest paramWriteSingleRegisterRequest) {
        return null;
    }

    @Override
    protected WriteMultipleCoilsResponse writeMultipleCoils(short paramShort, WriteMultipleCoilsRequest paramWriteMultipleCoilsRequest) {
        return null;
    }

    @Override
    protected WriteMultipleRegistersResponse writeMultipleRegisters(short paramShort, WriteMultipleRegistersRequest paramWriteMultipleRegistersRequest) {
        return null;
    }
}
