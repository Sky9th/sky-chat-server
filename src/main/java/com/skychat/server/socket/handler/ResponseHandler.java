package com.skychat.server.socket.handler;

import com.skychat.server.ServerApplication;
import com.skychat.server.json.Msg;
import com.skychat.server.json.send.All;
import com.skychat.server.service.PlayerStationService;
import com.skychat.server.socket.Group;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.ChannelGroupFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@ChannelHandler.Sharable
public class ResponseHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(ServerApplication.class);

    @Autowired
    PlayerStationService playerStationService;

    @Autowired
    Group group;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("ResponseHandler channelRead");
        All tcpSend = new All();
        tcpSend.playerList = playerStationService.playerList;
        group.sendTo(ctx.channel(), tcpSend);
        ctx.fireChannelRead(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //发生异常，关闭通道
        log.info(("close :" + cause.getLocalizedMessage()));
        cause.printStackTrace();
        ctx.close();
    }
}
