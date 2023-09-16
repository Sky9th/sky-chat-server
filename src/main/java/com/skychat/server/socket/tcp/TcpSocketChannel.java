package com.skychat.server.socket.tcp;

import com.skychat.server.ServerApplication;
import com.skychat.server.enums.SocketType;
import com.skychat.server.socket.Group;
import com.skychat.server.socket.handler.ChatHandler;
import com.skychat.server.socket.handler.PlayerHandler;
import com.skychat.server.socket.handler.ResponseHandler;
import com.skychat.server.socket.runnable.Broadcast;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

@Component
public class TcpSocketChannel extends ChannelInitializer<SocketChannel> {

    private static final Logger log = LoggerFactory.getLogger(ServerApplication.class);

    @Autowired
    TcpSocketHandler tcpSocketHandler;
    @Autowired
    PlayerHandler playerHandler;
    @Autowired
    ChatHandler chatHandler;
    @Autowired
    ResponseHandler responseHandler;
    @Autowired
    Group group;

    public TcpSocketChannel() {
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ByteBuf delimiter = Unpooled.copiedBuffer("\r\n".getBytes());
        group.resource = SocketType.TCPSOCKET;

        socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
        socketChannel.pipeline().addLast("decoder", new StringDecoder(Charset.forName("UTF-8")));
        socketChannel.pipeline().addLast("encoder", new StringEncoder(Charset.forName("UTF-8")));
        socketChannel.pipeline().addLast("tcpSocketHandler", tcpSocketHandler);
        socketChannel.pipeline().addLast("playerHandler", playerHandler);
        socketChannel.pipeline().addLast("chatHandler", chatHandler);
        socketChannel.pipeline().addLast("responseHandler", responseHandler);
    }
}
