package com.skychat.server.json;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

public class Player {

    public String mail;
    public String positionX;
    public String positionY;

    public Player () {
    }

    public Player (JSONObject jsonObject) {
        this.mail = jsonObject.getString("mail");
        this.positionX = jsonObject.getString("positionX");
        this.positionY = jsonObject.getString("positionY");
    }

}
