package info4.gl.coopcycle.service;

import info4.gl.coopcycle.service.dto.PaymentDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link info4.gl.coopcycle.domain.Payment}.
 */
public interface PaymentService {
    /**
     * Save a payment.
     *
     * @param paymentDTO the entity to save.
     * @return the persisted entity.
     */
    PaymentDTO save(PaymentDTO paymentDTO);

    /**
     * Updates a payment.
     *
     * @param paymentDTO the entity to update.
     * @return the persisted entity.
     */
    PaymentDTO update(PaymentDTO paymentDTO);

    /**
     * Partially updates a payment.
     *
     * @param paymentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PaymentDTO> partialUpdate(PaymentDTO paymentDTO);

    /**
     * Get all the payments.
     *
     * @return the list of entities.
     */
    List<PaymentDTO> findAll();

    /**
     * Get the "id" payment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PaymentDTO> findOne(String id);

    /**
     * Delete the "id" payment.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
