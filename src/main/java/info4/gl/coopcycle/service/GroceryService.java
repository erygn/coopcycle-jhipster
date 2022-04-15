package info4.gl.coopcycle.service;

import info4.gl.coopcycle.service.dto.GroceryDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link info4.gl.coopcycle.domain.Grocery}.
 */
public interface GroceryService {
    /**
     * Save a grocery.
     *
     * @param groceryDTO the entity to save.
     * @return the persisted entity.
     */
    GroceryDTO save(GroceryDTO groceryDTO);

    /**
     * Updates a grocery.
     *
     * @param groceryDTO the entity to update.
     * @return the persisted entity.
     */
    GroceryDTO update(GroceryDTO groceryDTO);

    /**
     * Partially updates a grocery.
     *
     * @param groceryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<GroceryDTO> partialUpdate(GroceryDTO groceryDTO);

    /**
     * Get all the groceries.
     *
     * @return the list of entities.
     */
    List<GroceryDTO> findAll();
    /**
     * Get all the GroceryDTO where Payment is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<GroceryDTO> findAllWherePaymentIsNull();

    /**
     * Get the "id" grocery.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GroceryDTO> findOne(String id);

    /**
     * Delete the "id" grocery.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
