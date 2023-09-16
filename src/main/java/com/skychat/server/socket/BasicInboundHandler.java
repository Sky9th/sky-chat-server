package com.skychat.server.socket;

import com.skychat.server.ServerApplication;
import com.skychat.server.json.Player;
import com.skychat.server.json.send.Active;
import com.skychat.server.json.send.InActive;
import com.skychat.server.service.PlayerStationService;
import io.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
public class BasicInboundHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(ServerApplication.class);

    @Autowired
    PlayerStationService playerStationService;

    @Autowired
    Group group;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("channelRead");
        ctx.fireChannelRead(msg);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("channelRegistered");
        Channel channel = ctx.channel();
        ChannelId id = channel.id();
        log.info(id.toString());
        log.info(String.valueOf(playerStationService));
        playerStationService.waitingPlayerList.put(id.toString(), new Player());
        group.channels.add(channel);
        ctx.fireChannelRegistered();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelActive");
        group.sendAll("client connected, client count:" + group.channels.size());
        Active send = new Active();
        send.id = String.valueOf(ctx.channel().id());
        group.sendTo(ctx.channel(), send);
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        group.sendAll("client disconnected, client count:" + group.channels.size());
        ctx.fireChannelInactive();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.info("channelUnregistered");
        Channel channel = ctx.channel();
        ChannelId id = channel.id();
        playerStationService.playerList.remove(id.toString());
        InActive send = new InActive();
        send.id = id.toString();
        group.sendAll(send);
        group.channels.remove(channel);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("channelReadComplete");
        ctx.fireChannelReadComplete();
    }
}