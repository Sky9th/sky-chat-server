package com.skychat.server.service;

import com.skychat.server.ServerApplication;
import com.skychat.server.model.User;
import com.skychat.server.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    private static final Logger log = LoggerFactory.getLogger(ServerApplication.class);

    public UserService () {
        log.info("user service");
    }

    @Autowired
    UserRepository userRepository;

    public List<User> findAll() {
        return (List<User>)userRepository.findAll();
    }
}
