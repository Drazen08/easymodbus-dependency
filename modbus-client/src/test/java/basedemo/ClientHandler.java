package basedemo;

import com.github.sunjx.modbus.common.util.RtuCrcUtil;
import com.github.sunjx.modbus.protocol.tcp.ModbusFrame;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author sunjx
 */
@ChannelHandler.Sharable
public class ClientHandler extends SimpleChannelInboundHandler<ModbusFrame> {

    private static AtomicInteger unit = new AtomicInteger(1);

    public static int getCount() {
        if (unit.get() >= 65534) {
            unit.set(1);
        }
        return unit.incrementAndGet();
    }

    private String channelKey;

    /**
     *
     */
    public ClientHandler() {
        this.channelKey = ClientDemo.channelKey;
    }

    private static final Logger log = LoggerFactory.getLogger(ClientHandler.class);

    private static boolean active = false;

    private static ChannelHandlerContext context;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client channelActive..");
        startActive(ctx);
        context = ctx;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("channel inactive ");
        ClientDemo.doConnect();
    }

    private void doLog(ModbusFrame frame) {
        ByteBuf encode = frame.encode();
        byte[] bytes = new byte[encode.readableBytes()];
        encode.readBytes(bytes);
        log.info("---------------------");
        log.info("receiveBuffer:[{}]", ByteBufUtil.hexDump(bytes));
        log.info("---------------------");
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ModbusFrame msg) throws Exception {
        doLog(msg);
        answer(ctx, msg);
    }


    private void answer(ChannelHandlerContext channel, ModbusFrame frame) {

        ByteBuf buffer = Unpooled.buffer();
//        buffer.writeBytes(frame.encode());

        String base = "01030BD50002";
        byte[] bytes = ByteBufUtil.decodeHexDump(base);
        buffer.writeBytes(bytes);

//        int paramLength = 2;
//        short paramValue = 2124;
//        buffer.writeInt(paramLength);
//        buffer.writeShort(paramValue);

        int startReaderIndex = buffer.readableBytes();
        int crc = RtuCrcUtil.calculateCRC(buffer);
        buffer.readerIndex(startReaderIndex);
        buffer.writeByte((byte) (0xFF & crc >> 8));
        buffer.writeByte((byte) (0xFF & crc));

        channel.writeAndFlush(buffer);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        System.out.println("error!!!");
    }

    /**
     * 初次链接
     * todo
     *
     * @param ctx
     */
    private void startActive(ChannelHandlerContext ctx) {
        ByteBuf buffer = Unpooled.buffer();

        buffer.writeBytes(("DTU:" + channelKey).getBytes(StandardCharsets.UTF_8));
        buffer.writeBytes("\0".getBytes());

        ctx.writeAndFlush(buffer);
    }

    /**
     * 初次连接的应答
     *
     * @param ctx    ChannelHandlerContext
     * @param result result
     */
    private void startActiveHandler(ChannelHandlerContext ctx, byte[] result) {

    }


    /**
     * 获取会话
     *
     * @return
     */
    public ChannelHandlerContext getContext() {
        return context;
    }

    /**
     * 心跳包方法
     *
     * @param ctx
     */
    private void heartBeatMethod(ChannelHandlerContext ctx) {
//        int i = 2;
//
//        byte[] bytes = new byte[14];
//        bytes[0] = (byte) 0x0012;
//
//        // 状态下表
//        byte[] statebyte = ByteArrUtil.intToByteArray2(machineIndex);
//        System.arraycopy(statebyte, 0, bytes, 1, 2);
//        changeCupNum();
//        byte[] cupaBytes = ByteArrUtil.intToByteArray2(0);
//        byte[] cupbBytes = ByteArrUtil.intToByteArray2(0);
//        // 机器状态
//        bytes[3] = ByteArrUtil.intToByteArray1(i);
//        bytes[4] = (byte) 0x0000;
//        bytes[5] = (byte) 0x0000;
//        bytes[6] = (byte) 0x0000;
//        // ---------------------
//        System.arraycopy(cupaBytes, 0, bytes, 7, 2);
//        System.arraycopy(cupbBytes, 0, bytes, 9, 2);
//        // ----------------------
//
//        ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length - 3);
//        byteBuffer.put(bytes, 0, bytes.length - 3);
//
//        bytes[11] = CRC8.calcCrc8(byteBuffer.array());
//
//        bytes[12] = (byte) 0x00FF;
//        bytes[13] = (byte) 0x00FF;
//
//        ByteBuf pingMessage = Unpooled.buffer();
//        pingMessage.writeBytes(bytes);
//        ctx.writeAndFlush(pingMessage);
//        heartBeat = i;
    }


    /**
     * 制作
     *
     * @param ctx
     * @param result
     */
    private void doorderHandler(ChannelHandlerContext ctx, byte[] result) throws InterruptedException {

    }


    /**
     * 心跳包应答
     *
     * @param ctx
     * @param result
     */
    private void heartBeatHandler(ChannelHandlerContext ctx, byte[] result) {
        log.info("收到心跳包应答");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (!active) {
            Thread.sleep(2000);
            startActive(ctx);
            return;
        }
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case WRITER_IDLE:
                    heartBeatMethod(ctx);
                    System.out.println("send ping to server----------");
                    break;
                case READER_IDLE:
                    break;
                case ALL_IDLE:
                    break;
                default:
                    break;
            }
        }
    }
}
