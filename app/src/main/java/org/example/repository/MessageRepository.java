package org.example.repository;

import org.example.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    // Spring Data JPA generará automáticamente los métodos CRUD básicos
    // Se pueden añadir métodos personalizados aquí si es necesario
}
