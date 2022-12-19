package com.skychat.server.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.skychat.server.enums.SendType;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class TcpSendAll {

    public SendType type = SendType.ALL;
    public String msg;
    public Map<String, Player> playerList = new HashMap<>();
    public List<Msg> msgList = new ArrayList<>();

}
