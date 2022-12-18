package com.skychat.server.service;

import com.skychat.server.ServerApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class ChatService {

    private static final Logger log = LoggerFactory.getLogger(ServerApplication.class);

    public ChatService() {
        log.info("chat service");
    }
}
