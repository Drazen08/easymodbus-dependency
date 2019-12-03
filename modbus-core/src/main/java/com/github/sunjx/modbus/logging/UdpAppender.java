package com.github.sunjx.modbus.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.atomic.AtomicInteger;


public class UdpAppender
        extends AppenderBase<ILoggingEvent> {
    LayoutWrappingEncoder<ILoggingEvent> encoder;
    int port;
    String ip;
    DatagramSocket socket;
    static AtomicInteger portAdd = new AtomicInteger(9002);
    public LayoutWrappingEncoder<ILoggingEvent> getEncoder() {
        return this.encoder;
    }

    private int initPort(){
//        int port = 1024 + (int) ((new Date()).getTime() / 1000L) % 64000 ;
        return portAdd.incrementAndGet();
    }

    /* 42 */
    public void setEncoder(LayoutWrappingEncoder<ILoggingEvent> encoder) {
        this.encoder = encoder;
    }


    /* 45 */
    public int getPort() {
        return this.port;
    }


    /* 48 */
    public void setPort(int port) {
        this.port = port;
    }


    /* 51 */
    public String getIp() {
        return this.ip;
    }


    /* 54 */
    public void setIp(String ip) {
        this.ip = ip;
    }


    /* 58 */
    public DatagramSocket getSocket() {
        return this.socket;
    }


    /* 61 */
    public void setSocket(DatagramSocket socket) {
        this.socket = socket;
    }


    @Override
    public void start() {
        /* 67 */
        if (this.encoder == null) {
            /* 68 */
            addError("no layout of udp appender");
            return;
        }
        /* 71 */
        if (this.socket == null) {
            try {
                /* 73 */

                this.socket = new DatagramSocket(initPort());
                /* 74 */
            } catch (SocketException e) {
                /* 75 */
                e.printStackTrace();
            }
        }
        /* 78 */
        super.start();
    }

    @Override
    protected void append(ILoggingEvent event) {
        try {
            /* 83 */
            byte[] buf = this.encoder.getLayout().doLayout(event).trim().getBytes();
            /* 84 */
            if (buf.length < 65536) {
                InetAddress address = InetAddress.getByName(this.ip);
                DatagramPacket p = new DatagramPacket(buf, buf.length, address, this.port);
                this.socket.send(p);
            }
            /* 89 */
        } catch (Exception e) {
            /* 90 */
            e.printStackTrace();
        }
    }


    @Override
    public void stop() {
        /* 96 */
        if (!this.socket.isClosed()) {
            /* 97 */
            this.socket.close();
        }
        /* 99 */
        super.stop();
    }
}


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4j\logging\UdpAppender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */