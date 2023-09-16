package com.skychat.server.socket.runnable;

import com.skychat.server.ServerApplication;
import com.skychat.server.service.PlayerStationService;
import com.skychat.server.socket.Group;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        healthCheck();
    }

    private void healthCheck () throws InterruptedException {
        while (true) {
            Thread.sleep(5000);
            playerStationService.playerList.forEach((key, vo) -> {

            });
        }
    }
}
