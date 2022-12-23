package com.skychat.server.json;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class Player {

    public String mail;
    public String positionX;
    public String positionY;
    public float moveDirX;
    public float moveDirY;

    public Player () {
    }

    public Player (JSONObject jsonObject) {
        this.mail = jsonObject.getString("mail");
        this.positionX = jsonObject.getString("positionX");
        this.positionY = jsonObject.getString("positionY");
        this.moveDirX = jsonObject.getFloat("moveDirX");
        this.moveDirY = jsonObject.getFloat("moveDirY");
    }

}
