package com.twitter.simpletwitter.model;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class Message implements Comparable<Message> {

    private String user;

    private LocalDateTime timestamp;

    @Size(min = 1, max = 140)
    private String message;

    public Message() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int compareTo(Message msg) {
        return msg.getTimestamp().compareTo(this.getTimestamp());
    }
}
