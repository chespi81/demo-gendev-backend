package org.example.model;

import java.time.LocalDateTime;

/**
 * A simple model class representing a message.
 */
public class Message {
    private String content;
    private LocalDateTime timestamp;

    // Default constructor
    public Message() {
        this.timestamp = LocalDateTime.now();
    }

    // Constructor with content
    public Message(String content) {
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and setters
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
