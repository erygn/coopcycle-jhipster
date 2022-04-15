package info4.gl.coopcycle.web.rest;

import info4.gl.coopcycle.repository.UserCoopRepository;
import info4.gl.coopcycle.service.UserCoopService;
import info4.gl.coopcycle.service.dto.UserCoopDTO;
import info4.gl.coopcycle.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link info4.gl.coopcycle.domain.UserCoop}.
 */
@RestController
@RequestMapping("/api")
public class UserCoopResource {

    private final Logger log = LoggerFactory.getLogger(UserCoopResource.class);

    private static final String ENTITY_NAME = "userCoop";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserCoopService userCoopService;

    private final UserCoopRepository userCoopRepository;

    public UserCoopResource(UserCoopService userCoopService, UserCoopRepository userCoopRepository) {
        this.userCoopService = userCoopService;
        this.userCoopRepository = userCoopRepository;
    }

    /**
     * {@code POST  /user-coops} : Create a new userCoop.
     *
     * @param userCoopDTO the userCoopDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userCoopDTO, or with status {@code 400 (Bad Request)} if the userCoop has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-coops")
    public ResponseEntity<UserCoopDTO> createUserCoop(@Valid @RequestBody UserCoopDTO userCoopDTO) throws URISyntaxException {
        log.debug("REST request to save UserCoop : {}", userCoopDTO);
        if (userCoopDTO.getId() != null) {
            throw new BadRequestAlertException("A new userCoop cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserCoopDTO result = userCoopService.save(userCoopDTO);
        return ResponseEntity
            .created(new URI("/api/user-coops/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-coops/:id} : Updates an existing userCoop.
     *
     * @param id the id of the userCoopDTO to save.
     * @param userCoopDTO the userCoopDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userCoopDTO,
     * or with status {@code 400 (Bad Request)} if the userCoopDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userCoopDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-coops/{id}")
    public ResponseEntity<UserCoopDTO> updateUserCoop(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UserCoopDTO userCoopDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UserCoop : {}, {}", id, userCoopDTO);
        if (userCoopDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userCoopDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userCoopRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserCoopDTO result = userCoopService.update(userCoopDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userCoopDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-coops/:id} : Partial updates given fields of an existing userCoop, field will ignore if it is null
     *
     * @param id the id of the userCoopDTO to save.
     * @param userCoopDTO the userCoopDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userCoopDTO,
     * or with status {@code 400 (Bad Request)} if the userCoopDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userCoopDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userCoopDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-coops/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserCoopDTO> partialUpdateUserCoop(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UserCoopDTO userCoopDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserCoop partially : {}, {}", id, userCoopDTO);
        if (userCoopDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userCoopDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userCoopRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserCoopDTO> result = userCoopService.partialUpdate(userCoopDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userCoopDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /user-coops} : get all the userCoops.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userCoops in body.
     */
    @GetMapping("/user-coops")
    public List<UserCoopDTO> getAllUserCoops(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all UserCoops");
        return userCoopService.findAll();
    }

    /**
     * {@code GET  /user-coops/:id} : get the "id" userCoop.
     *
     * @param id the id of the userCoopDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userCoopDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-coops/{id}")
    public ResponseEntity<UserCoopDTO> getUserCoop(@PathVariable Long id) {
        log.debug("REST request to get UserCoop : {}", id);
        Optional<UserCoopDTO> userCoopDTO = userCoopService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userCoopDTO);
    }

    /**
     * {@code DELETE  /user-coops/:id} : delete the "id" userCoop.
     *
     * @param id the id of the userCoopDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-coops/{id}")
    public ResponseEntity<Void> deleteUserCoop(@PathVariable Long id) {
        log.debug("REST request to delete UserCoop : {}", id);
        userCoopService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
