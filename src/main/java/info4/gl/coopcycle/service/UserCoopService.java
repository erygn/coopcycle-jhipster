package info4.gl.coopcycle.service;

import info4.gl.coopcycle.service.dto.UserCoopDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link info4.gl.coopcycle.domain.UserCoop}.
 */
public interface UserCoopService {
    /**
     * Save a userCoop.
     *
     * @param userCoopDTO the entity to save.
     * @return the persisted entity.
     */
    UserCoopDTO save(UserCoopDTO userCoopDTO);

    /**
     * Updates a userCoop.
     *
     * @param userCoopDTO the entity to update.
     * @return the persisted entity.
     */
    UserCoopDTO update(UserCoopDTO userCoopDTO);

    /**
     * Partially updates a userCoop.
     *
     * @param userCoopDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UserCoopDTO> partialUpdate(UserCoopDTO userCoopDTO);

    /**
     * Get all the userCoops.
     *
     * @return the list of entities.
     */
    List<UserCoopDTO> findAll();

    /**
     * Get all the userCoops with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserCoopDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" userCoop.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserCoopDTO> findOne(Long id);

    /**
     * Delete the "id" userCoop.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
