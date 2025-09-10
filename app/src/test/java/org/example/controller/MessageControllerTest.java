package org.example.controller;

import org.example.model.Message;
import org.example.service.MessageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MessageControllerTest {

    @Mock
    private MessageService messageService;

    @InjectMocks
    private MessageController messageController;

    private Message message1;
    private Message message2;
    private List<Message> messageList;

    @Before
    public void setup() {
        // Set up test data
        message1 = new Message();
        message1.setId(1L);
        message1.setContent("Test message 1");
        message1.setTimestamp(LocalDateTime.now());
        
        message2 = new Message();
        message2.setId(2L);
        message2.setContent("Test message 2");
        message2.setTimestamp(LocalDateTime.now());

        messageList = new ArrayList<>();
        messageList.add(message1);
        messageList.add(message2);
    }

    @Test
    public void testGetAllMessages() {
        // Mock the service behavior
        when(messageService.getAllMessages()).thenReturn(messageList);

        // Call the method to test
        List<Message> result = messageController.getAllMessages();

        // Verify the result
        assertEquals(2, result.size());
        assertEquals(message1, result.get(0));
        assertEquals(message2, result.get(1));
    }

    @Test
    public void testGetMessageById_Success() {
        // Mock the service behavior
        when(messageService.getMessageById(1L)).thenReturn(Optional.of(message1));

        // Call the method to test
        ResponseEntity<Message> response = messageController.getMessageById(1L);

        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(message1, response.getBody());
    }

    @Test
    public void testGetMessageById_NotFound() {
        // Mock the service behavior
        when(messageService.getMessageById(999L)).thenReturn(Optional.empty());

        // Call the method to test
        ResponseEntity<Message> response = messageController.getMessageById(999L);

        // Verify the result
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreateMessage() {
        // Create a new message for the test
        Message newMessage = new Message();
        newMessage.setContent("New test message");
        // timestamp is set in constructor

        // Mock the service behavior
        when(messageService.createMessage(any(Message.class))).thenReturn(newMessage);

        // Call the method to test
        ResponseEntity<Message> response = messageController.createMessage(newMessage);

        // Verify the result
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(newMessage, response.getBody());
    }

    @Test
    public void testUpdateMessage_Success() {
        // Create an updated message for the test
        Message updatedMessage = new Message();
        updatedMessage.setId(1L);
        updatedMessage.setContent("Updated content");
        updatedMessage.setTimestamp(LocalDateTime.now());

        // Mock the service behavior
        when(messageService.updateMessage(eq(1L), any(Message.class))).thenReturn(Optional.of(updatedMessage));

        // Call the method to test
        ResponseEntity<Message> response = messageController.updateMessage(1L, updatedMessage);

        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedMessage, response.getBody());
    }

    @Test
    public void testUpdateMessage_NotFound() {
        // Create an updated message for the test
        Message updatedMessage = new Message();
        updatedMessage.setId(999L);
        updatedMessage.setContent("Updated content");
        updatedMessage.setTimestamp(LocalDateTime.now());

        // Mock the service behavior
        when(messageService.updateMessage(eq(999L), any(Message.class))).thenReturn(Optional.empty());

        // Call the method to test
        ResponseEntity<Message> response = messageController.updateMessage(999L, updatedMessage);

        // Verify the result
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteMessage_Success() {
        // Mock the service behavior
        when(messageService.deleteMessage(1L)).thenReturn(true);

        // Call the method to test
        ResponseEntity<Void> response = messageController.deleteMessage(1L);

        // Verify the result
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteMessage_NotFound() {
        // Mock the service behavior
        when(messageService.deleteMessage(999L)).thenReturn(false);

        // Call the method to test
        ResponseEntity<Void> response = messageController.deleteMessage(999L);

        // Verify the result
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}