package com.skychat.server.json.send;

import com.skychat.server.enums.SendType;
import lombok.Data;

@Data
public class Active implements Send {

    public SendType type = SendType.ACTIVE;
    public String msg;
    public String id;

}
