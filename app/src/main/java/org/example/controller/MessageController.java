package org.example.controller;

import org.example.model.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * REST controller that demonstrates CRUD operations for messages.
 */
@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final Map<Long, Message> messages = new HashMap<>();
    private final AtomicLong counter = new AtomicLong();

    /**
     * Get all messages.
     */
    @GetMapping
    public List<Message> getAllMessages() {
        return new ArrayList<>(messages.values());
    }

    /**
     * Get a specific message by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        Message message = messages.get(id);
        if (message == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(message);
    }

    /**
     * Create a new message.
     */
    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Long id = counter.incrementAndGet();
        messages.put(id, message);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    /**
     * Update an existing message.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable Long id, @RequestBody Message updatedMessage) {
        if (!messages.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        messages.put(id, updatedMessage);
        return ResponseEntity.ok(updatedMessage);
    }

    /**
     * Delete a message by ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        if (!messages.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        messages.remove(id);
        return ResponseEntity.noContent().build();
    }
}
