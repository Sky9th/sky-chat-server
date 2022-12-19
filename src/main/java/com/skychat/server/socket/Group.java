package com.skychat.server.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.skychat.server.ServerApplication;
import com.skychat.server.enums.SendType;
import com.skychat.server.enums.SocketType;
import com.skychat.server.json.TcpSendAll;
import com.skychat.server.json.TcpSendPer;
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

    public ChannelFuture sendTo (Channel channel, Object send) throws Exception {
        Object str = this.buildSendObject(send);
        return channel.writeAndFlush(str);
    }

    public ChannelGroupFuture sendAll (Object send) throws Exception {
        Object str = this.buildSendObject(send);
        log.info("send all number:" + channels.size());
        return channels.writeAndFlush(str);
    }

    private Object buildSendObject (Object send) throws Exception {
        String json = "";
        String type = "";
        String sendStr;
        if (!(send instanceof String)) {
            if (send instanceof TcpSendAll) {
                type = String.valueOf(((TcpSendAll) send).type);
                json = JSON.toJSONString(send, SerializerFeature.DisableCircularReferenceDetect);
            } else if  (send instanceof TcpSendPer)  {
                type = String.valueOf(((TcpSendPer) send).type);
                json = JSON.toJSONString(send, SerializerFeature.DisableCircularReferenceDetect);
            }
            if (type.equals("")) {
                throw new Exception("error msg type");
            }
        } else {
            TcpSendAll tcpSend = new TcpSendAll();
            tcpSend.type = SendType.ALL;
            tcpSend.msg = (String)send;
            type = String.valueOf(SendType.ALL);
            json = JSON.toJSONString(tcpSend);
        }

        String typeNum = String.format("%02d", SendType.valueOf(type).ordinal());
        sendStr = typeNum + json + "\r\n";

        log.info("send all["+resource+"]:" + sendStr);
        if (resource.equals(SocketType.WEBSOCKET)) {
            TextWebSocketFrame str = new TextWebSocketFrame(sendStr);
            return str;
        } else {
            ByteBuf str = Unpooled.copiedBuffer(sendStr, CharsetUtil.UTF_8);
            return str;
        }
    }

}
