package com.skychat.server.json;

import com.alibaba.fastjson.JSONObject;

public class Msg {

    public String Time;
    public String Content;
    public String Email;

    public Msg (JSONObject jsonObject) {
        this.Time = jsonObject.getString("Time");
        this.Content = jsonObject.getString("Content");
    }
    
}
