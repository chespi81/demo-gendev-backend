package org.example.controller;

import org.example.model.BaseEntity;
import org.example.service.BaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * Test class for BaseController functionality.
 * This class provides abstract tests for the common CRUD operations in BaseController.
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class BaseControllerTest<T extends BaseEntity, ID, S extends BaseService<T, ID>, C extends BaseController<T, ID, S>> {

    @Mock
    protected S mockService;

    protected abstract C createController();
    protected abstract T createEntity();
    protected abstract ID createId();
    protected abstract void setupServiceMocks();

    @Test
    public void testGetById_Success() {
        // Setup
        C controller = createController();
        T entity = createEntity();
        ID id = createId();
        
        when(mockService.getById(id)).thenReturn(Optional.of(entity));
        
        // Execute
        ResponseEntity<T> response = controller.getById(id);
        
        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(entity, response.getBody());
    }

    @Test
    public void testGetById_NotFound() {
        // Setup
        C controller = createController();
        ID id = createId();
        
        when(mockService.getById(id)).thenReturn(Optional.empty());
        
        // Execute
        ResponseEntity<T> response = controller.getById(id);
        
        // Verify
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testCreate() {
        // Setup
        C controller = createController();
        T entity = createEntity();
        
        when(mockService.save(any())).thenReturn(entity);
        
        // Execute
        ResponseEntity<T> response = controller.create(entity);
        
        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(entity, response.getBody());
    }

    @Test
    public void testUpdate_Success() {
        // Setup
        C controller = createController();
        T entity = createEntity();
        ID id = createId();
        
        when(mockService.update(eq(id), any())).thenReturn(Optional.of(entity));
        
        // Execute
        ResponseEntity<T> response = controller.update(id, entity);
        
        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(entity, response.getBody());
    }

    @Test
    public void testUpdate_NotFound() {
        // Setup
        C controller = createController();
        T entity = createEntity();
        ID id = createId();
        
        when(mockService.update(eq(id), any())).thenReturn(Optional.empty());
        
        // Execute
        ResponseEntity<T> response = controller.update(id, entity);
        
        // Verify
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testDelete_Success() {
        // Setup
        C controller = createController();
        ID id = createId();
        
        when(mockService.delete(id)).thenReturn(true);
        
        // Execute
        ResponseEntity<Void> response = controller.delete(id);
        
        // Verify
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDelete_NotFound() {
        // Setup
        C controller = createController();
        ID id = createId();
        
        when(mockService.delete(id)).thenReturn(false);
        
        // Execute
        ResponseEntity<Void> response = controller.delete(id);
        
        // Verify
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
