package org.example.controller;

import org.example.model.BaseEntity;
import org.example.service.BaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * Base controller with common REST endpoints
 *
 * @param <T> Entity type that extends BaseEntity
 * @param <ID> Type of the entity's ID
 * @param <S> Service type that implements BaseService
 */
public abstract class BaseController<T extends BaseEntity, ID, S extends BaseService<T, ID>> {

    protected final S service;

    public BaseController(S service) {
        this.service = service;
    }

    /**
     * Get a specific entity by its ID.
     *
     * @param id the entity ID
     * @return the entity if found
     */
    @GetMapping("/{id}")
    public ResponseEntity<T> getById(@PathVariable ID id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Create a new entity.
     *
     * @param entity the entity to create
     * @return the created entity
     */
    @PostMapping
    public ResponseEntity<T> create(@RequestBody T entity) {
        T savedEntity = service.save(entity);
        return ResponseEntity.ok(savedEntity);
    }

    /**
     * Update an existing entity.
     *
     * @param id the ID of the entity to update
     * @param entity the updated entity details
     * @return the updated entity if found
     */
    @PutMapping("/{id}")
    public ResponseEntity<T> update(@PathVariable ID id, @RequestBody T entity) {
        return service.update(id, entity)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Delete an entity by its ID.
     *
     * @param id the ID of the entity to delete
     * @return no content if deleted, not found if the entity was not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable ID id) {
        boolean deleted = service.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
    
    /**
     * Get all entities by owner ID.
     * This method should be implemented by subclasses.
     * 
     * @param ownerId the owner ID
     * @return list of entities owned by the specified owner ID
     */
    public abstract ResponseEntity<?> getByOwnerId(String ownerId);
}
