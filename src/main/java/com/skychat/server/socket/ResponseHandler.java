package com.skychat.server.socket;

import com.alibaba.fastjson.JSON;
import com.skychat.server.ServerApplication;
import com.skychat.server.json.TcpSend;
import com.skychat.server.service.PlayerStationService;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.ChannelGroupFutureListener;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
public class ResponseHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(ServerApplication.class);

    @Autowired
    PlayerStationService playerStationService;

    @Autowired
    Group group;

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        TcpSend tcpSend = new TcpSend();
        if (playerStationService.msgList.size() > 0) {
            tcpSend.msgList = playerStationService.msgList;
        }
        tcpSend.playerList = playerStationService.playerList;

        ChannelGroupFuture future = group.channels.writeAndFlush(Unpooled.copiedBuffer(JSON.toJSONString(tcpSend), CharsetUtil.UTF_8));
        log.info("Send Client" + tcpSend.toString());
        future.addListener((ChannelGroupFutureListener) channelFutures -> playerStationService.clearMsg());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //发生异常，关闭通道
        log.info(("close :" + cause.getLocalizedMessage()));
        cause.printStackTrace();
        ctx.close();
    }
}
