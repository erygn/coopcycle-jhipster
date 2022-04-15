package info4.gl.coopcycle.service;

import info4.gl.coopcycle.service.dto.CooperativeDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link info4.gl.coopcycle.domain.Cooperative}.
 */
public interface CooperativeService {
    /**
     * Save a cooperative.
     *
     * @param cooperativeDTO the entity to save.
     * @return the persisted entity.
     */
    CooperativeDTO save(CooperativeDTO cooperativeDTO);

    /**
     * Updates a cooperative.
     *
     * @param cooperativeDTO the entity to update.
     * @return the persisted entity.
     */
    CooperativeDTO update(CooperativeDTO cooperativeDTO);

    /**
     * Partially updates a cooperative.
     *
     * @param cooperativeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CooperativeDTO> partialUpdate(CooperativeDTO cooperativeDTO);

    /**
     * Get all the cooperatives.
     *
     * @return the list of entities.
     */
    List<CooperativeDTO> findAll();

    /**
     * Get the "id" cooperative.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CooperativeDTO> findOne(Long id);

    /**
     * Delete the "id" cooperative.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
