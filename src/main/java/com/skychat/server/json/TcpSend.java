package com.skychat.server.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class TcpSend {

    public Map<String, Player> playerList = new HashMap<>();
    public List<Msg> msgList = new ArrayList<>();

}
