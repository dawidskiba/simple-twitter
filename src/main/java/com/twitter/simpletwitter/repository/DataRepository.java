package com.twitter.simpletwitter.repository;

import com.twitter.simpletwitter.model.Message;

import java.util.Set;

public interface DataRepository {

    void saveMessage(String user, Message message);

    Set<Message> loadMessages(String user);

    void followUser(String user, String publisher);
}
