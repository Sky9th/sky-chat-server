package com.skychat.server.json;

import com.skychat.server.enums.SendType;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class TcpSendPer {

    public SendType type = SendType.PERSONAL;
    public String msg;
    public String id;

}
