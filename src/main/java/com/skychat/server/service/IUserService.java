package com.skychat.server.service;

import com.skychat.server.model.User;
import com.skychat.server.repository.UserRepository;

import java.util.List;

public interface IUserService {

    public List<User> findAll();

}
