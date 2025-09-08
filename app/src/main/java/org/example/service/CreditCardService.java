package org.example.service;

import org.example.dto.CreditCardDTO;
import org.example.model.CreditCard;
import org.example.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CreditCardService {

    private final CreditCardRepository creditCardRepository;

    @Autowired
    public CreditCardService(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    /**
     * Retrieve all credit cards for a specific owner ID.
     *
     * @param ownerId the ID number of the card owner
     * @return list of credit cards owned by the specified ID
     */
    public List<CreditCard> getCardsByOwnerId(String ownerId) {
        return creditCardRepository.findByOwnerId(ownerId);
    }

    /**
     * Get a specific credit card by its ID.
     *
     * @param id the credit card ID
     * @return the credit card if found, or empty optional otherwise
     */
    public Optional<CreditCard> getCardById(Long id) {
        return creditCardRepository.findById(id);
    }

    /**
     * Save a new credit card.
     *
     * @param creditCard the credit card to save
     * @return the saved credit card
     */
    public CreditCard saveCard(CreditCard creditCard) {
        return creditCardRepository.save(creditCard);
    }

    /**
     * Update an existing credit card.
     *
     * @param id the ID of the credit card to update
     * @param updatedCard the updated credit card details
     * @return the updated credit card if found, or empty optional otherwise
     */
    public Optional<CreditCard> updateCard(Long id, CreditCard updatedCard) {
        if (!creditCardRepository.existsById(id)) {
            return Optional.empty();
        }
        updatedCard.setId(id);
        return Optional.of(creditCardRepository.save(updatedCard));
    }

    /**
     * Delete a credit card by its ID.
     *
     * @param id the ID of the credit card to delete
     * @return true if deleted, false if the card was not found
     */
    public boolean deleteCard(Long id) {
        if (!creditCardRepository.existsById(id)) {
            return false;
        }
        creditCardRepository.deleteById(id);
        return true;
    }
    
    /**
     * Retrieve all credit card DTOs for a specific owner ID.
     * This method doesn't include transactions.
     *
     * @param ownerId the ID number of the card owner
     * @return list of credit card DTOs owned by the specified ID
     */
    public List<CreditCardDTO> getCardDTOsByOwnerId(String ownerId) {
        return creditCardRepository.findByOwnerId(ownerId)
                .stream()
                .map(CreditCardDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Get a specific credit card DTO by its ID.
     * This method doesn't include transactions.
     *
     * @param id the credit card ID
     * @return the credit card DTO if found, or empty optional otherwise
     */
    public Optional<CreditCardDTO> getCardDTOById(Long id) {
        return creditCardRepository.findById(id)
                .map(CreditCardDTO::new);
    }
}
