package com.skychat.server.socket.tcp;

import com.skychat.server.ServerApplication;
import com.skychat.server.json.Player;
import com.skychat.server.json.TcpResponse;
import com.skychat.server.service.PlayerStationService;
import com.skychat.server.socket.Group;
import com.skychat.server.socket.BasicInboundHandler;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
public class TcpSocketHandler extends BasicInboundHandler {

    private static final Logger log = LoggerFactory.getLogger(ServerApplication.class);

    @Autowired
    PlayerStationService playerStationService;

    @Autowired
    Group group;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //获取客户端发送过来的消息
        String str = String.valueOf(msg);
        log.info("TcpSocketHandler channelRead:" + ctx.channel().remoteAddress() + " msg：" + str);
        ctx.fireChannelRead(msg);
    }

}