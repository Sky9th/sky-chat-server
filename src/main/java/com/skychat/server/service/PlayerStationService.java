package com.skychat.server.service;

import com.skychat.server.ServerApplication;
import com.skychat.server.json.Msg;
import com.skychat.server.json.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class PlayerStationService {

    private static final Logger log = LoggerFactory.getLogger(ServerApplication.class);

    public Map<String, Player> waitingPlayerList = new HashMap<>();
    public Map<String, Player> playerList = new HashMap<>();
    public Map<String, Queue<Msg>> msgList = new HashMap();

    public PlayerStationService () {
        log.info("player station service");
    }
}
