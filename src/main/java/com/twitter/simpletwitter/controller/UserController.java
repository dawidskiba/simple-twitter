package com.twitter.simpletwitter.controller;

import com.twitter.simpletwitter.model.Message;
import com.twitter.simpletwitter.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.NoSuchElementException;
import java.util.Set;

@RestController
@RequestMapping("/twitter")
public class UserController {

    @Autowired
    private DataRepository repository;

    @GetMapping(path = "/{user}", produces = "application/json")
    public ResponseEntity<Set<Message>> loadUserMessages(@PathVariable String user) {
        try {
            return ResponseEntity.ok(repository.loadMessages(user));
        } catch (NoSuchElementException nsee) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/{user}", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void saveMessage(@PathVariable String user, @RequestBody @Valid Message message) {
        repository.saveMessage(user, message);
    }

    @GetMapping(path = "/{user}/follow")
    public ResponseEntity<?> followUser(@PathVariable String user, @RequestParam("publisher") String publisher) {
        try {
            repository.followUser(user, publisher);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException nsee) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException ise) {
            return ResponseEntity.badRequest().build();
        }
    }
}
