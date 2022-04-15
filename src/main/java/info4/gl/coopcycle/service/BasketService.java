package info4.gl.coopcycle.service;

import info4.gl.coopcycle.service.dto.BasketDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link info4.gl.coopcycle.domain.Basket}.
 */
public interface BasketService {
    /**
     * Save a basket.
     *
     * @param basketDTO the entity to save.
     * @return the persisted entity.
     */
    BasketDTO save(BasketDTO basketDTO);

    /**
     * Updates a basket.
     *
     * @param basketDTO the entity to update.
     * @return the persisted entity.
     */
    BasketDTO update(BasketDTO basketDTO);

    /**
     * Partially updates a basket.
     *
     * @param basketDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BasketDTO> partialUpdate(BasketDTO basketDTO);

    /**
     * Get all the baskets.
     *
     * @return the list of entities.
     */
    List<BasketDTO> findAll();
    /**
     * Get all the BasketDTO where Grocery is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<BasketDTO> findAllWhereGroceryIsNull();

    /**
     * Get the "id" basket.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BasketDTO> findOne(String id);

    /**
     * Delete the "id" basket.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
