package com.skychat.server.socket.handler;

import com.skychat.server.ServerApplication;
import com.skychat.server.json.TcpResponse;
import com.skychat.server.service.PlayerStationService;
import com.skychat.server.socket.Group;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
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
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("PlayerHandler channelRead");
        //获取客户端发送过来的消息
        String str = String.valueOf(msg);
        String id = ctx.channel().id().toString();
        TcpResponse tcpResponse = new TcpResponse(str);
        playerStationService.waitingPlayerList.remove(id);
        playerStationService.playerList.put(id, tcpResponse.playerInfo);
        ctx.fireChannelRead(msg);
    }

}