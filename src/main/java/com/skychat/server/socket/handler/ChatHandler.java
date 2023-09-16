package com.skychat.server.socket.handler;

import com.skychat.server.ServerApplication;
import com.skychat.server.dao.ChatDao;
import com.skychat.server.json.Msg;
import com.skychat.server.json.TcpResponse;
import com.skychat.server.model.Chat;
import com.skychat.server.service.PlayerStationService;
import com.skychat.server.utils.MybatisUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

@Component
@ChannelHandler.Sharable
public class ChatHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(ServerApplication.class);

    @Autowired
    private MybatisUtils mybatisUtils;

    @Autowired
    private PlayerStationService playerStationService;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        TcpResponse tcpResponse = new TcpResponse(String.valueOf(msg));
        log.info("ChatHandler channelRead:" + tcpResponse);
        if (tcpResponse.msg != null && tcpResponse.msg.content != null && tcpResponse.msg.time != null) {
            try (SqlSession sqlSession = mybatisUtils.getSqlSession()) {
                String id = ctx.channel().id().toString();
                ChatDao mapper = sqlSession.getMapper(ChatDao.class);
                Chat chat = new Chat(1, 0, tcpResponse.msg.content);
                Object res = mapper.insert(chat);
                sqlSession.commit();

                if (playerStationService.msgList.containsKey(id)) {
                    playerStationService.msgList.get(id).offer(tcpResponse.msg);
                } else {
                    Queue<Msg>  msgQueue = new LinkedList<>();
                    msgQueue.offer(tcpResponse.msg);
                    playerStationService.msgList.put(id, msgQueue);
                }
                log.info(String.valueOf(playerStationService.msgList.size()));
                log.info(res.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ctx.fireChannelRead(msg);
    }
}
