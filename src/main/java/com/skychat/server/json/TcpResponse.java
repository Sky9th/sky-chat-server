package com.skychat.server.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class TcpResponse {

    public String ID;
    public String SessionKey;
    public Msg Msg;
    public Player PlayerInfo;
    
    public TcpResponse (String json) {
        JSONObject jsonObject =  JSON.parseObject(json);

        this.ID = jsonObject.getString("ID");
        this.SessionKey = jsonObject.getString("SessionKey");
        this.Msg = new Msg(jsonObject.getJSONObject("Msg"));;
        this.PlayerInfo = new Player(jsonObject.getJSONObject("PlayerInfo"));;
        
    }

}
