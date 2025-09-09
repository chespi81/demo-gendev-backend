package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    @Operation(
        summary = "Get an entity by ID", 
        description = "Returns a single entity based on its ID",
        security = @SecurityRequirement(name = "bearer-key"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Entity found",
                content = @Content(schema = @Schema(implementation = BaseEntity.class))),
        @ApiResponse(responseCode = "404", description = "Entity not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<T> getById(
            @Parameter(description = "ID of the entity to retrieve") @PathVariable ID id) {
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
    @Operation(
        summary = "Create a new entity", 
        description = "Creates a new entity and returns it with assigned ID",
        security = @SecurityRequirement(name = "bearer-key"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Entity created successfully",
                content = @Content(schema = @Schema(implementation = BaseEntity.class)))
    })
    @PostMapping
    public ResponseEntity<T> create(
            @Parameter(description = "Entity to create", required = true) @RequestBody T entity) {
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
    @Operation(
        summary = "Update an entity", 
        description = "Updates an existing entity based on ID",
        security = @SecurityRequirement(name = "bearer-key"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Entity updated successfully",
                content = @Content(schema = @Schema(implementation = BaseEntity.class))),
        @ApiResponse(responseCode = "404", description = "Entity not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<T> update(
            @Parameter(description = "ID of the entity to update") @PathVariable ID id, 
            @Parameter(description = "Updated entity information", required = true) @RequestBody T entity) {
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
    @Operation(
        summary = "Delete an entity", 
        description = "Deletes an entity based on ID",
        security = @SecurityRequirement(name = "bearer-key"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Entity deleted successfully", content = @Content),
        @ApiResponse(responseCode = "404", description = "Entity not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the entity to delete") @PathVariable ID id) {
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
    @Operation(
        summary = "Get entities by owner ID", 
        description = "Returns all entities belonging to a specific owner",
        security = @SecurityRequirement(name = "bearer-key"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Entities found"),
        @ApiResponse(responseCode = "404", description = "No entities found for owner", content = @Content)
    })
    public abstract ResponseEntity<?> getByOwnerId(
            @Parameter(description = "ID of the owner to retrieve entities for") String ownerId);
}
