package info4.gl.coopcycle.web.rest;

import info4.gl.coopcycle.repository.GroceryRepository;
import info4.gl.coopcycle.service.GroceryService;
import info4.gl.coopcycle.service.dto.GroceryDTO;
import info4.gl.coopcycle.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
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
 * REST controller for managing {@link info4.gl.coopcycle.domain.Grocery}.
 */
@RestController
@RequestMapping("/api")
public class GroceryResource {

    private final Logger log = LoggerFactory.getLogger(GroceryResource.class);

    private static final String ENTITY_NAME = "grocery";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GroceryService groceryService;

    private final GroceryRepository groceryRepository;

    public GroceryResource(GroceryService groceryService, GroceryRepository groceryRepository) {
        this.groceryService = groceryService;
        this.groceryRepository = groceryRepository;
    }

    /**
     * {@code POST  /groceries} : Create a new grocery.
     *
     * @param groceryDTO the groceryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new groceryDTO, or with status {@code 400 (Bad Request)} if the grocery has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/groceries")
    public ResponseEntity<GroceryDTO> createGrocery(@Valid @RequestBody GroceryDTO groceryDTO) throws URISyntaxException {
        log.debug("REST request to save Grocery : {}", groceryDTO);
        if (groceryDTO.getId() != null) {
            throw new BadRequestAlertException("A new grocery cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroceryDTO result = groceryService.save(groceryDTO);
        return ResponseEntity
            .created(new URI("/api/groceries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /groceries/:id} : Updates an existing grocery.
     *
     * @param id the id of the groceryDTO to save.
     * @param groceryDTO the groceryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groceryDTO,
     * or with status {@code 400 (Bad Request)} if the groceryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the groceryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/groceries/{id}")
    public ResponseEntity<GroceryDTO> updateGrocery(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody GroceryDTO groceryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Grocery : {}, {}", id, groceryDTO);
        if (groceryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, groceryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!groceryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GroceryDTO result = groceryService.update(groceryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, groceryDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /groceries/:id} : Partial updates given fields of an existing grocery, field will ignore if it is null
     *
     * @param id the id of the groceryDTO to save.
     * @param groceryDTO the groceryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groceryDTO,
     * or with status {@code 400 (Bad Request)} if the groceryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the groceryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the groceryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/groceries/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GroceryDTO> partialUpdateGrocery(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody GroceryDTO groceryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Grocery partially : {}, {}", id, groceryDTO);
        if (groceryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, groceryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!groceryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GroceryDTO> result = groceryService.partialUpdate(groceryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, groceryDTO.getId())
        );
    }

    /**
     * {@code GET  /groceries} : get all the groceries.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of groceries in body.
     */
    @GetMapping("/groceries")
    public List<GroceryDTO> getAllGroceries(@RequestParam(required = false) String filter) {
        if ("payment-is-null".equals(filter)) {
            log.debug("REST request to get all Grocerys where payment is null");
            return groceryService.findAllWherePaymentIsNull();
        }
        log.debug("REST request to get all Groceries");
        return groceryService.findAll();
    }

    /**
     * {@code GET  /groceries/:id} : get the "id" grocery.
     *
     * @param id the id of the groceryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the groceryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/groceries/{id}")
    public ResponseEntity<GroceryDTO> getGrocery(@PathVariable String id) {
        log.debug("REST request to get Grocery : {}", id);
        Optional<GroceryDTO> groceryDTO = groceryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(groceryDTO);
    }

    /**
     * {@code DELETE  /groceries/:id} : delete the "id" grocery.
     *
     * @param id the id of the groceryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/groceries/{id}")
    public ResponseEntity<Void> deleteGrocery(@PathVariable String id) {
        log.debug("REST request to delete Grocery : {}", id);
        groceryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
