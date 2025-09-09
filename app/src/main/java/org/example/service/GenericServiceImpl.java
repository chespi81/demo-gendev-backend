package org.example.service;

import org.example.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Generic service implementation with common CRUD operations
 *
 * @param <T> Entity type that extends BaseEntity
 * @param <ID> Type of the entity's ID
 * @param <R> Type of the repository that extends JpaRepository
 */
public abstract class GenericServiceImpl<T extends BaseEntity, ID, R extends JpaRepository<T, ID>> implements BaseService<T, ID> {

    protected final R repository;

    public GenericServiceImpl(R repository) {
        this.repository = repository;
    }

    @Override
    public Optional<T> getById(ID id) {
        return repository.findById(id);
    }

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<T> update(ID id, T updatedEntity) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        updatedEntity.setId((Long)id);
        return Optional.of(repository.save(updatedEntity));
    }

    @Override
    public boolean delete(ID id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
