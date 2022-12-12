package com.skychat.server.model;

import com.skychat.server.ServerApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    private static final Logger log = LoggerFactory.getLogger(ServerApplication.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long Id;

    public User () {
        log.info("user model");
    }

    @Override
    public String toString() {
        return "User{" +
                "Id=" + Id +
                '}';
    }
}
