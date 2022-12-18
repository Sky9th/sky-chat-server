package com.skychat.server.socket.web;

import com.skychat.server.ServerApplication;
import com.skychat.server.service.PlayerStationService;
import com.skychat.server.socket.BasicInboundHandler;
import com.skychat.server.socket.Group;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
public class WebSocketHandler extends BasicInboundHandler {

    private static final Logger log = LoggerFactory.getLogger(ServerApplication.class);

    private WebSocketServerHandshaker handshake;

    @Autowired
    private PlayerStationService playerStationService;

    @Autowired
    Group group;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("receive msg:" + msg.toString());
        if (msg instanceof FullHttpRequest) {
            log.info("http request");
            handleHttpRequest(ctx, (FullHttpRequest)msg);
        } else if (msg instanceof  WebSocketFrame){
            log.info("socket request");
            String res = handlerWebSocketFrame(ctx, (WebSocketFrame) msg);
            if (res == "") {
                return;
            }
            String str = String.valueOf(msg);
            log.info("收到客户端" + ctx.channel().remoteAddress() + "发送的消息：" + str);
            //{"ID":"testID","SessionKey":"web167065874063943ab4c96c42","Msg":{"Time":"2022/12/13 22:32:02","Content":"tab"},"PlayerInfo":{"Mail":"testMail","PositionX":"2.096","PositionY":"-7.51"}}
            ctx.fireChannelRead(res);
        }
    }

    private String handlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame){
        // 判断是否关闭链路的指令
        if (frame instanceof CloseWebSocketFrame) {
            handshake.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return "";
        }
        // 判断是否ping消息
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return "";
        }
        // 本例程仅支持文本消息，不支持二进制消息
        if (!(frame instanceof TextWebSocketFrame)) {
            log.debug("本例程仅支持文本消息，不支持二进制消息");
            throw new UnsupportedOperationException(String.format("%s frame types not supported", frame.getClass().getName()));
        }
        // 返回应答消息
        String request = ((TextWebSocketFrame) frame).text();
        return request;
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        //要求Upgrade为websocket，过滤掉get/Post
        if (!req.decoderResult().isSuccess() || (!"websocket".equals(req.headers().get("Upgrade")))) {
            //若不是websocket方式，则创建BAD_REQUEST的req，返回给客户端
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://localhost:6666/websocket", null, false);
        handshake = wsFactory.newHandshaker(req);
        log.info(handshake.toString());
        if (handshake == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handshake.handshake(ctx.channel(), req);
        }
    }

    /**
     * 拒绝不合法的请求，并返回错误信息
     * */
    private static void sendHttpResponse(ChannelHandlerContext ctx,
                                         FullHttpRequest req, DefaultFullHttpResponse res) {
        // 返回应答给客户端
        if (res.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(),
                    CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
        }
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        // 如果是非Keep-Alive，关闭连接
        if (res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }
}