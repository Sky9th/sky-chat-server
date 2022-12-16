package com.skychat.server.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.skychat.server.ServerApplication;
import com.skychat.server.json.Player;
import com.skychat.server.json.TcpResponse;
import com.skychat.server.service.PlayerStationService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
public class PlayerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(ServerApplication.class);

    @Autowired
    PlayerStationService playerStationService;

    @Autowired
    Group group;

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        ChannelId id = channel.id();
        log.info(id.toString());
        log.info(String.valueOf(playerStationService));
        playerStationService.playerList.put(id.toString(), new Player());
        group.channels.add(channel);
        ctx.fireChannelRegistered();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        group.channels.writeAndFlush(Unpooled.copiedBuffer("client connected, client count:" + group.channels.size(), CharsetUtil.UTF_8));
        ctx.fireChannelActive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //获取客户端发送过来的消息
        String str = String.valueOf(msg);
        log.info("收到客户端" + ctx.channel().remoteAddress() + "发送的消息：" + str);
        //{"ID":"testID","SessionKey":"web167065874063943ab4c96c42","Msg":{"time":"2022/12/13 22:32:02","content":""},"PlayInfo":{"Mail":"testMail","PositionX":"2.096","PositionY":"-7.51"}}
        TcpResponse tcpResponse = new TcpResponse(str);

        playerStationService.playerList.put(ctx.channel().id().toString(), tcpResponse.PlayerInfo);
        ctx.fireChannelRead(msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        group.channels.writeAndFlush(Unpooled.copiedBuffer("client disconnected, client count:" + group.channels.size(), CharsetUtil.UTF_8));
        ctx.fireChannelInactive();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        ChannelId id = channel.id();
        playerStationService.playerList.remove(id.toString());
        group.channels.remove(channel);
        group.channels.writeAndFlush(Unpooled.copiedBuffer("client connected, client count:" + group.channels.size(), CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info(("close :" + cause.getLocalizedMessage()));
        ctx.fireExceptionCaught(cause);
    }

}