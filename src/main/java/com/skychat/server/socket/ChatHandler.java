package com.skychat.server.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.skychat.server.ServerApplication;
import com.skychat.server.dao.ChatDao;
import com.skychat.server.json.TcpResponse;
import com.skychat.server.model.Chat;
import com.skychat.server.service.ChatService;
import com.skychat.server.service.PlayerStationService;
import com.skychat.server.utils.MybatisUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.security.util.Debug;

import java.util.List;

@Component
@ChannelHandler.Sharable
public class ChatHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(ServerApplication.class);

    public static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Autowired
    private MybatisUtils mybatisUtils;

    @Autowired
    private PlayerStationService playerStationService;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        TcpResponse tcpResponse = new TcpResponse(String.valueOf(msg));
        if (tcpResponse.Msg != null && tcpResponse.Msg.Content != null && tcpResponse.Msg.Time != null) {
            try (SqlSession sqlSession = mybatisUtils.getSqlSession()) {
                ChatDao mapper = sqlSession.getMapper(ChatDao.class);
                log.info(tcpResponse.Msg.toString());
                log.info(tcpResponse.Msg.Content);
                Chat chat = new Chat(1, 0, tcpResponse.Msg.Content);
                Object res = mapper.insert(chat);
                sqlSession.commit();

                playerStationService.msgList.add(playerStationService.msgList.size(), tcpResponse.Msg);
                log.info(res.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info(("close :" + cause.getLocalizedMessage()));
        ctx.fireExceptionCaught(cause);
    }
}
