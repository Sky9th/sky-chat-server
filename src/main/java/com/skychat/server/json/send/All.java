package com.skychat.server.json.send;

import com.skychat.server.enums.SendType;
import com.skychat.server.json.Msg;
import com.skychat.server.json.Player;
import lombok.Data;

import java.util.*;

@Data
public class All implements Send {

    public SendType type = SendType.ALL;
    public String msg;
    public Map<String, Player> playerList = new HashMap<>();
    public Map<String, List<Msg>> msgList = new HashMap<>();

}
