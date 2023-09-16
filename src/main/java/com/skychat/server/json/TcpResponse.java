package com.skychat.server.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class TcpResponse {

    public String id;
    public String sessionKey;
    public Msg msg;
    public Player playerInfo;

    public TcpResponse (String json) {
        JSONObject jsonObject =  JSON.parseObject(json);

        this.id = jsonObject.getString("id");
        this.sessionKey = jsonObject.getString("sessionKey");
        this.msg = new Msg(jsonObject.getJSONObject("msg"));;
        this.playerInfo = new Player(jsonObject.getJSONObject("playerInfo"));;
        
    }

}
