package org.example.repository;

import org.example.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    /**
     * Find all credit cards by owner ID.
     * 
     * @param ownerId the ID number of the card owner
     * @return list of credit cards owned by the specified ID
     */
    List<CreditCard> findByOwnerId(String ownerId);
}
