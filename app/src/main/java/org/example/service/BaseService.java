package org.example.service;

import org.example.model.BaseEntity;

import java.util.Optional;

/**
 * Generic service interface with common CRUD operations
 *
 * @param <T> Entity type that extends BaseEntity
 * @param <ID> Type of the entity's ID
 */
public interface BaseService<T extends BaseEntity, ID> {

    /**
     * Get a specific entity by its ID.
     *
     * @param id the entity ID
     * @return the entity if found, or empty optional otherwise
     */
    Optional<T> getById(ID id);

    /**
     * Save a new entity.
     *
     * @param entity the entity to save
     * @return the saved entity
     */
    T save(T entity);

    /**
     * Update an existing entity.
     *
     * @param id the ID of the entity to update
     * @param updatedEntity the updated entity details
     * @return the updated entity if found, or empty optional otherwise
     */
    Optional<T> update(ID id, T updatedEntity);

    /**
     * Delete an entity by its ID.
     *
     * @param id the ID of the entity to delete
     * @return true if deleted, false if the entity was not found
     */
    boolean delete(ID id);
}
