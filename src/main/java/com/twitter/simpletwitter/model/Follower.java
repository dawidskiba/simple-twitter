package com.twitter.simpletwitter.model;

import java.time.LocalDateTime;

public class Follower {

    private LocalDateTime followingSinceTime;

    private String name;

    public Follower(String name) {
        this.followingSinceTime = LocalDateTime.now();
        this.name = name;
    }

    public LocalDateTime getFollowingSinceTime() {
        return followingSinceTime;
    }

    public void setFollowingSinceTime(LocalDateTime followingSinceTime) {
        this.followingSinceTime = followingSinceTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
