package com.skychat.server.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.skychat.server.ServerApplication;
import com.skychat.server.enums.SendType;
import com.skychat.server.enums.SocketType;
import com.skychat.server.json.send.*;
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

    public ChannelFuture sendTo (Channel channel, Object send) throws Exception {
        log.info("send to:" + channel.id());
        Object str = this.buildSendObject(send);
        return channel.writeAndFlush(str);
    }

    public ChannelGroupFuture sendAll (Object send) throws Exception {
        log.info("send all number:" + channels.size());
        Object str = this.buildSendObject(send);
        return channels.writeAndFlush(str);
    }

    private <U, T> U buildSendObject (T send) throws Exception {
        String json = "";
        String type = "";
        String sendStr;
        if (send instanceof String) {
            Msg msg = new Msg();
            msg.msg = (String)send;
            type = String.valueOf(msg.type);
            json = JSON.toJSONString(msg, SerializerFeature.DisableCircularReferenceDetect);
        } else {
            if (send instanceof All) {
                type = String.valueOf(SendType.ALL);
            } else if (send instanceof InActive) {
                type = String.valueOf(SendType.INACTIVE);
            } else if (send instanceof Active) {
                type = String.valueOf(SendType.ACTIVE);
            } else if (send instanceof Msg) {
                type = String.valueOf(SendType.MSG);
            } else {
                throw new Exception("error msg type");
            }
            json = JSON.toJSONString(send, SerializerFeature.DisableCircularReferenceDetect);
        }

        String typeNum = String.format("%02d", SendType.valueOf(type).ordinal());
        sendStr = typeNum + json + "\r\n";

        U str;
        log.info("send all["+resource+"]:" + sendStr);
        if (resource.equals(SocketType.WEBSOCKET)) {
            str = (U)new TextWebSocketFrame(sendStr);
        } else {
            str = (U)Unpooled.copiedBuffer(sendStr, CharsetUtil.UTF_8);
        }
        return str;
    }

}
