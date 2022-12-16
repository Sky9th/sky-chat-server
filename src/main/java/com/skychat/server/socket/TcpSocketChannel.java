package com.skychat.server.socket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

@Component
public class TcpSocketChannel extends ChannelInitializer<SocketChannel> {

    @Autowired
    PlayerHandler playerHandler;
    @Autowired
    ChatHandler chatHandler;
    @Autowired
    ResponseHandler responseHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ByteBuf delimiter = Unpooled.copiedBuffer("\r\n".getBytes());
        socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
        socketChannel.pipeline().addLast("decoder", new StringDecoder(Charset.forName("UTF-8")));
        socketChannel.pipeline().addLast("encoder", new StringEncoder(Charset.forName("UTF-8")));
        socketChannel.pipeline().addLast(playerHandler);
        socketChannel.pipeline().addLast(chatHandler);
        socketChannel.pipeline().addLast(responseHandler);
    }
}
