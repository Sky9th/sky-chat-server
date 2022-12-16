package com.skychat.server.dao;

import com.skychat.server.model.Chat;

import java.util.List;

public interface ChatDao {

    List<Chat> getList();

    Long insert(Chat chat);

}
