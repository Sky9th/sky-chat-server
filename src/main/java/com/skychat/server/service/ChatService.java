package com.skychat.server.service;

import com.skychat.server.ServerApplication;
import com.skychat.server.model.Chat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class ChatService {

    private static final Logger log = LoggerFactory.getLogger(ServerApplication.class);

    public ChatService() {
        log.info("chat service");
    }
}
