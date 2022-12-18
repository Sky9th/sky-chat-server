package com.skychat.server.service;

import com.skychat.server.ServerApplication;
import com.skychat.server.json.Msg;
import com.skychat.server.json.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class PlayerStationService {

    private static final Logger log = LoggerFactory.getLogger(ServerApplication.class);

    public Map<String, Player> playerList = new HashMap<>();
    public List<Msg> msgList = new ArrayList<>();

    public PlayerStationService () {
        log.info("player station service");
    }

    public void clearMsg () {
        msgList = new ArrayList<>();
        log.info("player station service: clear msg");
    }
}
