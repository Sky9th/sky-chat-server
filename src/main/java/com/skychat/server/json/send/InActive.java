package com.skychat.server.json.send;

import com.skychat.server.enums.SendType;
import lombok.Data;

@Data
public class InActive implements Send {

    public SendType type = SendType.INACTIVE;
    public String msg;
    public String id;

}
