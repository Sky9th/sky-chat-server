package com.skychat.server.service;

import com.skychat.server.ServerApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(ServerApplication.class);

    public UserService () {
        log.info("user service");
    }
}
