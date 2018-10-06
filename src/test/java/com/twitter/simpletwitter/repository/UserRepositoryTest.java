package com.twitter.simpletwitter.repository;

import com.twitter.simpletwitter.model.Message;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.fest.assertions.Assertions.assertThat;

public class UserRepositoryTest {

    private UserRepository tested;

    @Before
    public void setUp() throws Exception {
        tested = new UserRepository();
    }

    @Test
    public void shouldSaveNewUserAndHisMessage() {
        // given
        String user = "test";
        Message message = newMessage();

        // when
        tested.saveMessage(user, message);

        // then
        Set<Message> messages = tested.loadMessages(user);
        assertThat(messages).isNotNull();
        assertThat(messages).isNotEmpty();
    }

    @Test
    public void shouldLoadMessagesForGivenUser() {
        // given
        String user = "test";
        Message message = newMessage();
        tested.saveMessage(user, message);

        // when
        Set<Message> messages = tested.loadMessages(user);

        // then
        assertThat(messages).isNotNull();
        assertThat(messages).isNotEmpty();
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowExcWhenTryingToLoadMessagesForUnknownUser() {
        // given
        String user = "test";

        // when
        tested.loadMessages(user);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowExcWhenUserTryingToFollowUnknownPublisher() {
        // given
        String user = "user";
        String userToFollow = "user2";
        Message message = newMessage();
        tested.saveMessage(user, message);

        // when
        tested.followUser(user, userToFollow);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExcWhenUserTryingToFollowHimself() {
        // given
        String user = "user";
        Message message = newMessage();
        tested.saveMessage(user, message);

        // when
        tested.followUser(user, user);
    }

    private Message newMessage() {
        Message message = new Message();
        message.setUser("");
        message.setMessage("");
        message.setTimestamp(LocalDateTime.now());

        return message;
    }
}
