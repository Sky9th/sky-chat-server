package com.skychat.server.controller;

import com.skychat.server.ServerApplication;
import com.skychat.server.enums.SocketType;
import com.skychat.server.socket.Server;
import com.skychat.server.socket.tcp.TcpSocketChannel;
import com.skychat.server.socket.web.WebSocketChannel;
import io.netty.channel.ChannelInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class NettyStartListener implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ServerApplication.class);

    @Resource
    private Server socketServer;

    @Autowired
    Server socket;

    @Autowired
    TcpSocketChannel tcpSocketChannel;

    @Autowired
    WebSocketChannel webSocketChannel;

    @Value("${spring.socket.channel}")
    private SocketType channel;

    @Async
    @Override
    public void run(String... args) throws Exception {
        ChannelInitializer channelInitializer;
        int port;
        switch (channel) {
            case TCPSOCKET:
                channelInitializer = tcpSocketChannel;
                port = 6666;
                break;
            case WEBSOCKET:
                channelInitializer = webSocketChannel;
                port = 6667;
                break;
            default:
                throw new InterruptedException("channel miss");
        }
        log.info("start "+ channel +" socket in port:" + port);
        socketServer.start(channelInitializer, port);
    }
}