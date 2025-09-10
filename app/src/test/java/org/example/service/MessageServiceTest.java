package org.example.service;

import org.example.model.Message;
import org.example.repository.MessageRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MessageServiceTest {

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private MessageService messageService;

    private Message testMessage;
    private final Long testId = 1L;
    private final String testContent = "Test message content";
    private final LocalDateTime testTimestamp = LocalDateTime.now();

    @Before
    public void setUp() {
        testMessage = new Message();
        testMessage.setId(testId);
        testMessage.setContent(testContent);
        testMessage.setTimestamp(testTimestamp);
    }

    @Test
    public void testGetAllMessages() {
        // Arrange
        List<Message> expectedMessages = Arrays.asList(testMessage);
        when(messageRepository.findAll()).thenReturn(expectedMessages);

        // Act
        List<Message> resultMessages = messageService.getAllMessages();

        // Assert
        assertEquals(expectedMessages, resultMessages);
        verify(messageRepository).findAll();
    }

    @Test
    public void testGetMessageById_WhenMessageExists() {
        // Arrange
        when(messageRepository.findById(testId)).thenReturn(Optional.of(testMessage));

        // Act
        Optional<Message> result = messageService.getMessageById(testId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testMessage, result.get());
        verify(messageRepository).findById(testId);
    }

    @Test
    public void testGetMessageById_WhenMessageDoesNotExist() {
        // Arrange
        when(messageRepository.findById(testId)).thenReturn(Optional.empty());

        // Act
        Optional<Message> result = messageService.getMessageById(testId);

        // Assert
        assertFalse(result.isPresent());
        verify(messageRepository).findById(testId);
    }

    @Test
    public void testCreateMessage() {
        // Arrange
        when(messageRepository.save(testMessage)).thenReturn(testMessage);

        // Act
        Message result = messageService.createMessage(testMessage);

        // Assert
        assertEquals(testMessage, result);
        verify(messageRepository).save(testMessage);
    }

    @Test
    public void testUpdateMessage_WhenMessageExists() {
        // Arrange
        Message updatedMessage = new Message();
        updatedMessage.setContent("Updated message content");
        updatedMessage.setTimestamp(LocalDateTime.now());

        when(messageRepository.existsById(testId)).thenReturn(true);
        when(messageRepository.save(any(Message.class))).thenAnswer(invocation -> {
            Message savedMessage = invocation.getArgument(0);
            assertEquals(testId, savedMessage.getId());
            return savedMessage;
        });

        // Act
        Optional<Message> result = messageService.updateMessage(testId, updatedMessage);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testId, result.get().getId());
        assertEquals("Updated message content", result.get().getContent());
        verify(messageRepository).existsById(testId);
        verify(messageRepository).save(any(Message.class));
    }

    @Test
    public void testUpdateMessage_WhenMessageDoesNotExist() {
        // Arrange
        Message updatedMessage = new Message();
        updatedMessage.setContent("Updated message content");
        updatedMessage.setTimestamp(LocalDateTime.now());

        when(messageRepository.existsById(testId)).thenReturn(false);

        // Act
        Optional<Message> result = messageService.updateMessage(testId, updatedMessage);

        // Assert
        assertFalse(result.isPresent());
        verify(messageRepository).existsById(testId);
        verify(messageRepository, never()).save(any(Message.class));
    }

    @Test
    public void testDeleteMessage_WhenMessageExists() {
        // Arrange
        when(messageRepository.existsById(testId)).thenReturn(true);
        doNothing().when(messageRepository).deleteById(testId);

        // Act
        boolean result = messageService.deleteMessage(testId);

        // Assert
        assertTrue(result);
        verify(messageRepository).existsById(testId);
        verify(messageRepository).deleteById(testId);
    }

    @Test
    public void testDeleteMessage_WhenMessageDoesNotExist() {
        // Arrange
        when(messageRepository.existsById(testId)).thenReturn(false);

        // Act
        boolean result = messageService.deleteMessage(testId);

        // Assert
        assertFalse(result);
        verify(messageRepository).existsById(testId);
        verify(messageRepository, never()).deleteById(any());
    }
}