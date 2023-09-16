package com.skychat.server.json;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

public class Msg {

    public String time;
    public String content;
    public String email;

    public Msg (JSONObject jsonObject) {
        this.time = jsonObject.getString("time");
        this.content = jsonObject.getString("content");
    }
    
}
