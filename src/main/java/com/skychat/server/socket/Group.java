package com.skychat.server.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.skychat.server.ServerApplication;
import com.skychat.server.enums.SocketType;
import com.skychat.server.json.TcpSend;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Group {

    private static final Logger log = LoggerFactory.getLogger(ServerApplication.class);

    public final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public SocketType resource;

    public ChannelFuture sendTo (Channel channel, Object send) {
        Object str = this.buildSendObject(send);
        return channel.writeAndFlush(str);
    }

    public ChannelGroupFuture sendAll (Object send) {
        Object str = this.buildSendObject(send);
        log.info("send all number:" + channels.size());
        return channels.writeAndFlush(str);
    }

    private Object buildSendObject (Object send) {
        String json;
        if (send instanceof TcpSend) {
            json = JSON.toJSONString(send, SerializerFeature.DisableCircularReferenceDetect);
        } else {
            TcpSend tcpSend = new TcpSend();
            tcpSend.msg = (String)send;
            json = JSON.toJSONString(tcpSend);
        }
        log.info("send all["+resource+"]:" + json);
        if (resource.equals(SocketType.WEBSOCKET)) {
            TextWebSocketFrame str = new TextWebSocketFrame(json);
            return str;
        } else {
            ByteBuf str = Unpooled.copiedBuffer(json, CharsetUtil.UTF_8);
            return str;
        }
    }

}
