package com.skychat.server.json;

import com.alibaba.fastjson.JSONObject;

public class Player {

    public String mail;
    public String positionX;
    public String positionY;

    public Player () {
    }

    public Player (JSONObject jsonObject) {
        this.mail = jsonObject.getString("Mail");
        this.positionX = jsonObject.getString("PositionX");
        this.positionY = jsonObject.getString("PositionY");
    }

}
