package com.twitter.simpletwitter.repository;

import com.twitter.simpletwitter.model.Follower;
import com.twitter.simpletwitter.model.Message;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import static com.google.common.collect.Sets.newHashSet;
import static com.google.common.collect.Sets.newTreeSet;
import static java.util.stream.Collectors.toSet;

@Component
public class UserRepository implements DataRepository {

    private Map<String, Set<Message>> messageStorage = new ConcurrentHashMap<>();
    private Map<String, Set<Follower>> followersStorage = new ConcurrentHashMap<>();

    @Override
    public void saveMessage(String user, Message message) {
        message.setUser(user);
        message.setTimestamp(LocalDateTime.now());

        if (isUserExists(user)) {
            messageStorage.get(user).add(message);
        } else {
            messageStorage.put(user, newMessageTreeSet(message));
            followersStorage.put(user, newHashSet());
        }
    }

    @Override
    public Set<Message> loadMessages(String user) {
        if (!isUserExists(user)) {
            throw new NoSuchElementException();
        } else {
            Set<Message> userMessages = messageStorage.get(user);
            Set<Message> followersMessages = getFollowersMessages(user);
            userMessages.addAll(followersMessages);
            return userMessages;
        }
    }

    @Override
    public void followUser(String user, String publisher) {
        if (!isUserExists(user) || !isUserExists(publisher)) {
            throw new NoSuchElementException();
        } else if (user.equalsIgnoreCase(publisher)) {
            throw new IllegalStateException();
        } else {
            Follower follower = new Follower(publisher);
            followersStorage.get(user).add(follower);
        }
    }

    private boolean isUserExists(String user) {
        return messageStorage.containsKey(user);
    }

    private Set<Message> newMessageTreeSet(Message message) {
        TreeSet<Message> messageTreeSet = newTreeSet();
        messageTreeSet.add(message);
        return messageTreeSet;
    }

    private Set<Message> getFollowersMessages(String user) {
        return followersStorage.get(user).stream()
                .map(this::getMessagesAfterFollowingDate)
                .filter(messages -> !messages.isEmpty())
                .flatMap(Collection::stream)
                .collect(toSet());
    }

    private Set<Message> getMessagesAfterFollowingDate(Follower follower) {
        return messageStorage.get(follower.getName()).stream()
                .filter(message -> message.getTimestamp().isAfter(follower.getFollowingSinceTime()))
                .collect(toSet());
    }
}
