package com.skychat.server.socket;

import com.skychat.server.ServerApplication;
import com.skychat.server.json.Player;
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
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("channelRegistered");
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
        log.info("channelActive");
        group.sendAll("client connected, client count:" + group.channels.size());
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        group.sendAll("client disconnected, client count:" + group.channels.size());
        ctx.fireChannelInactive();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        ChannelId id = channel.id();
        playerStationService.playerList.remove(id.toString());
        group.channels.remove(channel);
    }

}