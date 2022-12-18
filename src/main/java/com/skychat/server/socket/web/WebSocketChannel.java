package com.skychat.server.socket.web;

import com.skychat.server.ServerApplication;
import com.skychat.server.socket.Group;
import com.skychat.server.socket.handler.PlayerHandler;
import com.skychat.server.socket.handler.ChatHandler;
import com.skychat.server.socket.handler.ResponseHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.skychat.server.enums.SocketType;

@Component
public class WebSocketChannel extends ChannelInitializer<SocketChannel> {

    private static final Logger log = LoggerFactory.getLogger(ServerApplication.class);

    @Autowired
    WebSocketHandler webSocketHandler;

    @Autowired
    PlayerHandler playerHandler;

    @Autowired
    ChatHandler chatHandler;

    @Autowired
    ResponseHandler responseHandler;

    @Autowired
    Group group;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        group.resource = SocketType.WEBSOCKET;
        log.info(String.valueOf(group.resource));
        socketChannel.pipeline().addLast("http-codec", new HttpServerCodec());
        socketChannel.pipeline().addLast("aggregator", new HttpObjectAggregator(65535));
        socketChannel.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
        socketChannel.pipeline().addLast("handler", webSocketHandler);
        socketChannel.pipeline().addLast("player", playerHandler);
        socketChannel.pipeline().addLast("chat", chatHandler);
        socketChannel.pipeline().addLast("response", responseHandler);
    }
}
