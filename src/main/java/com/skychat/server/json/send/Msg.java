package com.skychat.server.json.send;

import com.skychat.server.enums.SendType;
import lombok.Data;

@Data
public class Msg implements Send {

    public SendType type = SendType.MSG;
    public String msg;

}
