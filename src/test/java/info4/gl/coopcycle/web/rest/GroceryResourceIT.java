package info4.gl.coopcycle.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import info4.gl.coopcycle.IntegrationTest;
import info4.gl.coopcycle.domain.Grocery;
import info4.gl.coopcycle.repository.GroceryRepository;
import info4.gl.coopcycle.service.dto.GroceryDTO;
import info4.gl.coopcycle.service.mapper.GroceryMapper;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GroceryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GroceryResourceIT {

    private static final String DEFAULT_ADRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADRESS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/groceries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private GroceryRepository groceryRepository;

    @Autowired
    private GroceryMapper groceryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGroceryMockMvc;

    private Grocery grocery;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Grocery createEntity(EntityManager em) {
        Grocery grocery = new Grocery().adress(DEFAULT_ADRESS);
        return grocery;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Grocery createUpdatedEntity(EntityManager em) {
        Grocery grocery = new Grocery().adress(UPDATED_ADRESS);
        return grocery;
    }

    @BeforeEach
    public void initTest() {
        grocery = createEntity(em);
    }

    @Test
    @Transactional
    void createGrocery() throws Exception {
        int databaseSizeBeforeCreate = groceryRepository.findAll().size();
        // Create the Grocery
        GroceryDTO groceryDTO = groceryMapper.toDto(grocery);
        restGroceryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groceryDTO)))
            .andExpect(status().isCreated());

        // Validate the Grocery in the database
        List<Grocery> groceryList = groceryRepository.findAll();
        assertThat(groceryList).hasSize(databaseSizeBeforeCreate + 1);
        Grocery testGrocery = groceryList.get(groceryList.size() - 1);
        assertThat(testGrocery.getAdress()).isEqualTo(DEFAULT_ADRESS);
    }

    @Test
    @Transactional
    void createGroceryWithExistingId() throws Exception {
        // Create the Grocery with an existing ID
        grocery.setId("existing_id");
        GroceryDTO groceryDTO = groceryMapper.toDto(grocery);

        int databaseSizeBeforeCreate = groceryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroceryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groceryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Grocery in the database
        List<Grocery> groceryList = groceryRepository.findAll();
        assertThat(groceryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAdressIsRequired() throws Exception {
        int databaseSizeBeforeTest = groceryRepository.findAll().size();
        // set the field null
        grocery.setAdress(null);

        // Create the Grocery, which fails.
        GroceryDTO groceryDTO = groceryMapper.toDto(grocery);

        restGroceryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groceryDTO)))
            .andExpect(status().isBadRequest());

        List<Grocery> groceryList = groceryRepository.findAll();
        assertThat(groceryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGroceries() throws Exception {
        // Initialize the database
        grocery.setId(UUID.randomUUID().toString());
        groceryRepository.saveAndFlush(grocery);

        // Get all the groceryList
        restGroceryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grocery.getId())))
            .andExpect(jsonPath("$.[*].adress").value(hasItem(DEFAULT_ADRESS)));
    }

    @Test
    @Transactional
    void getGrocery() throws Exception {
        // Initialize the database
        grocery.setId(UUID.randomUUID().toString());
        groceryRepository.saveAndFlush(grocery);

        // Get the grocery
        restGroceryMockMvc
            .perform(get(ENTITY_API_URL_ID, grocery.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(grocery.getId()))
            .andExpect(jsonPath("$.adress").value(DEFAULT_ADRESS));
    }

    @Test
    @Transactional
    void getNonExistingGrocery() throws Exception {
        // Get the grocery
        restGroceryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGrocery() throws Exception {
        // Initialize the database
        grocery.setId(UUID.randomUUID().toString());
        groceryRepository.saveAndFlush(grocery);

        int databaseSizeBeforeUpdate = groceryRepository.findAll().size();

        // Update the grocery
        Grocery updatedGrocery = groceryRepository.findById(grocery.getId()).get();
        // Disconnect from session so that the updates on updatedGrocery are not directly saved in db
        em.detach(updatedGrocery);
        updatedGrocery.adress(UPDATED_ADRESS);
        GroceryDTO groceryDTO = groceryMapper.toDto(updatedGrocery);

        restGroceryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, groceryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(groceryDTO))
            )
            .andExpect(status().isOk());

        // Validate the Grocery in the database
        List<Grocery> groceryList = groceryRepository.findAll();
        assertThat(groceryList).hasSize(databaseSizeBeforeUpdate);
        Grocery testGrocery = groceryList.get(groceryList.size() - 1);
        assertThat(testGrocery.getAdress()).isEqualTo(UPDATED_ADRESS);
    }

    @Test
    @Transactional
    void putNonExistingGrocery() throws Exception {
        int databaseSizeBeforeUpdate = groceryRepository.findAll().size();
        grocery.setId(UUID.randomUUID().toString());

        // Create the Grocery
        GroceryDTO groceryDTO = groceryMapper.toDto(grocery);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroceryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, groceryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(groceryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Grocery in the database
        List<Grocery> groceryList = groceryRepository.findAll();
        assertThat(groceryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGrocery() throws Exception {
        int databaseSizeBeforeUpdate = groceryRepository.findAll().size();
        grocery.setId(UUID.randomUUID().toString());

        // Create the Grocery
        GroceryDTO groceryDTO = groceryMapper.toDto(grocery);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroceryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(groceryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Grocery in the database
        List<Grocery> groceryList = groceryRepository.findAll();
        assertThat(groceryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGrocery() throws Exception {
        int databaseSizeBeforeUpdate = groceryRepository.findAll().size();
        grocery.setId(UUID.randomUUID().toString());

        // Create the Grocery
        GroceryDTO groceryDTO = groceryMapper.toDto(grocery);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroceryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groceryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Grocery in the database
        List<Grocery> groceryList = groceryRepository.findAll();
        assertThat(groceryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGroceryWithPatch() throws Exception {
        // Initialize the database
        grocery.setId(UUID.randomUUID().toString());
        groceryRepository.saveAndFlush(grocery);

        int databaseSizeBeforeUpdate = groceryRepository.findAll().size();

        // Update the grocery using partial update
        Grocery partialUpdatedGrocery = new Grocery();
        partialUpdatedGrocery.setId(grocery.getId());

        partialUpdatedGrocery.adress(UPDATED_ADRESS);

        restGroceryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrocery.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGrocery))
            )
            .andExpect(status().isOk());

        // Validate the Grocery in the database
        List<Grocery> groceryList = groceryRepository.findAll();
        assertThat(groceryList).hasSize(databaseSizeBeforeUpdate);
        Grocery testGrocery = groceryList.get(groceryList.size() - 1);
        assertThat(testGrocery.getAdress()).isEqualTo(UPDATED_ADRESS);
    }

    @Test
    @Transactional
    void fullUpdateGroceryWithPatch() throws Exception {
        // Initialize the database
        grocery.setId(UUID.randomUUID().toString());
        groceryRepository.saveAndFlush(grocery);

        int databaseSizeBeforeUpdate = groceryRepository.findAll().size();

        // Update the grocery using partial update
        Grocery partialUpdatedGrocery = new Grocery();
        partialUpdatedGrocery.setId(grocery.getId());

        partialUpdatedGrocery.adress(UPDATED_ADRESS);

        restGroceryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrocery.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGrocery))
            )
            .andExpect(status().isOk());

        // Validate the Grocery in the database
        List<Grocery> groceryList = groceryRepository.findAll();
        assertThat(groceryList).hasSize(databaseSizeBeforeUpdate);
        Grocery testGrocery = groceryList.get(groceryList.size() - 1);
        assertThat(testGrocery.getAdress()).isEqualTo(UPDATED_ADRESS);
    }

    @Test
    @Transactional
    void patchNonExistingGrocery() throws Exception {
        int databaseSizeBeforeUpdate = groceryRepository.findAll().size();
        grocery.setId(UUID.randomUUID().toString());

        // Create the Grocery
        GroceryDTO groceryDTO = groceryMapper.toDto(grocery);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroceryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, groceryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(groceryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Grocery in the database
        List<Grocery> groceryList = groceryRepository.findAll();
        assertThat(groceryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGrocery() throws Exception {
        int databaseSizeBeforeUpdate = groceryRepository.findAll().size();
        grocery.setId(UUID.randomUUID().toString());

        // Create the Grocery
        GroceryDTO groceryDTO = groceryMapper.toDto(grocery);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroceryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(groceryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Grocery in the database
        List<Grocery> groceryList = groceryRepository.findAll();
        assertThat(groceryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGrocery() throws Exception {
        int databaseSizeBeforeUpdate = groceryRepository.findAll().size();
        grocery.setId(UUID.randomUUID().toString());

        // Create the Grocery
        GroceryDTO groceryDTO = groceryMapper.toDto(grocery);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroceryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(groceryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Grocery in the database
        List<Grocery> groceryList = groceryRepository.findAll();
        assertThat(groceryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGrocery() throws Exception {
        // Initialize the database
        grocery.setId(UUID.randomUUID().toString());
        groceryRepository.saveAndFlush(grocery);

        int databaseSizeBeforeDelete = groceryRepository.findAll().size();

        // Delete the grocery
        restGroceryMockMvc
            .perform(delete(ENTITY_API_URL_ID, grocery.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Grocery> groceryList = groceryRepository.findAll();
        assertThat(groceryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
