package com.skychat.server.socket.runnable;

import com.skychat.server.ServerApplication;
import com.skychat.server.json.Msg;
import com.skychat.server.json.send.All;
import com.skychat.server.service.PlayerStationService;
import com.skychat.server.socket.Group;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.ChannelGroupFutureListener;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Broadcast implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(ServerApplication.class);

    @Autowired
    Group group;

    @Autowired
    PlayerStationService playerStationService;

    @SneakyThrows
    @Override
    public void run() {
        log.info("run broadcast task");
        while (true) {
            if (group.channels.size() > 0) {
                All tcpSend = new All();
                if (playerStationService.playerList.size() > 0) {
                    tcpSend.playerList = playerStationService.playerList;
                    for(String id: tcpSend.playerList.keySet()) {
                        if (playerStationService.msgList.containsKey(id)){
                            while (playerStationService.msgList.get(id).size() > 0) {
                                if (!tcpSend.msgList.containsKey(id)) tcpSend.msgList.put(id, new ArrayList<Msg>());
                                tcpSend.msgList.get(id).add(playerStationService.msgList.get(id).poll());
                            }
                        }
                    }
                }
                group.sendAll(tcpSend);
                Thread.sleep(50);
            }
        }
    }
}
