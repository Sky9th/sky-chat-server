package com.skychat.server.socket.handler;

import com.skychat.server.ServerApplication;
import com.skychat.server.dao.ChatDao;
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
        log.info("chat handler channelRead:" + tcpResponse);
        if (tcpResponse.msg != null && tcpResponse.msg.content != null && tcpResponse.msg.time != null) {
            try (SqlSession sqlSession = mybatisUtils.getSqlSession()) {
                ChatDao mapper = sqlSession.getMapper(ChatDao.class);
                log.info(tcpResponse.msg.toString());
                log.info(tcpResponse.msg.content);
                Chat chat = new Chat(1, 0, tcpResponse.msg.content);
                Object res = mapper.insert(chat);
                sqlSession.commit();

                playerStationService.msgList.add(playerStationService.msgList.size(), tcpResponse.msg);
                log.info(String.valueOf(playerStationService.msgList.size()));
                log.info(res.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
