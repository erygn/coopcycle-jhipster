package info4.gl.coopcycle.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import info4.gl.coopcycle.IntegrationTest;
import info4.gl.coopcycle.domain.UserCoop;
import info4.gl.coopcycle.repository.UserCoopRepository;
import info4.gl.coopcycle.service.UserCoopService;
import info4.gl.coopcycle.service.dto.UserCoopDTO;
import info4.gl.coopcycle.service.mapper.UserCoopMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link UserCoopResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class UserCoopResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/user-coops";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserCoopRepository userCoopRepository;

    @Mock
    private UserCoopRepository userCoopRepositoryMock;

    @Autowired
    private UserCoopMapper userCoopMapper;

    @Mock
    private UserCoopService userCoopServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserCoopMockMvc;

    private UserCoop userCoop;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserCoop createEntity(EntityManager em) {
        UserCoop userCoop = new UserCoop().name(DEFAULT_NAME);
        return userCoop;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserCoop createUpdatedEntity(EntityManager em) {
        UserCoop userCoop = new UserCoop().name(UPDATED_NAME);
        return userCoop;
    }

    @BeforeEach
    public void initTest() {
        userCoop = createEntity(em);
    }

    @Test
    @Transactional
    void createUserCoop() throws Exception {
        int databaseSizeBeforeCreate = userCoopRepository.findAll().size();
        // Create the UserCoop
        UserCoopDTO userCoopDTO = userCoopMapper.toDto(userCoop);
        restUserCoopMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userCoopDTO)))
            .andExpect(status().isCreated());

        // Validate the UserCoop in the database
        List<UserCoop> userCoopList = userCoopRepository.findAll();
        assertThat(userCoopList).hasSize(databaseSizeBeforeCreate + 1);
        UserCoop testUserCoop = userCoopList.get(userCoopList.size() - 1);
        assertThat(testUserCoop.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createUserCoopWithExistingId() throws Exception {
        // Create the UserCoop with an existing ID
        userCoop.setId(1L);
        UserCoopDTO userCoopDTO = userCoopMapper.toDto(userCoop);

        int databaseSizeBeforeCreate = userCoopRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserCoopMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userCoopDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserCoop in the database
        List<UserCoop> userCoopList = userCoopRepository.findAll();
        assertThat(userCoopList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userCoopRepository.findAll().size();
        // set the field null
        userCoop.setName(null);

        // Create the UserCoop, which fails.
        UserCoopDTO userCoopDTO = userCoopMapper.toDto(userCoop);

        restUserCoopMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userCoopDTO)))
            .andExpect(status().isBadRequest());

        List<UserCoop> userCoopList = userCoopRepository.findAll();
        assertThat(userCoopList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUserCoops() throws Exception {
        // Initialize the database
        userCoopRepository.saveAndFlush(userCoop);

        // Get all the userCoopList
        restUserCoopMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userCoop.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUserCoopsWithEagerRelationshipsIsEnabled() throws Exception {
        when(userCoopServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUserCoopMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(userCoopServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUserCoopsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(userCoopServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUserCoopMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(userCoopServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getUserCoop() throws Exception {
        // Initialize the database
        userCoopRepository.saveAndFlush(userCoop);

        // Get the userCoop
        restUserCoopMockMvc
            .perform(get(ENTITY_API_URL_ID, userCoop.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userCoop.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingUserCoop() throws Exception {
        // Get the userCoop
        restUserCoopMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUserCoop() throws Exception {
        // Initialize the database
        userCoopRepository.saveAndFlush(userCoop);

        int databaseSizeBeforeUpdate = userCoopRepository.findAll().size();

        // Update the userCoop
        UserCoop updatedUserCoop = userCoopRepository.findById(userCoop.getId()).get();
        // Disconnect from session so that the updates on updatedUserCoop are not directly saved in db
        em.detach(updatedUserCoop);
        updatedUserCoop.name(UPDATED_NAME);
        UserCoopDTO userCoopDTO = userCoopMapper.toDto(updatedUserCoop);

        restUserCoopMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userCoopDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userCoopDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserCoop in the database
        List<UserCoop> userCoopList = userCoopRepository.findAll();
        assertThat(userCoopList).hasSize(databaseSizeBeforeUpdate);
        UserCoop testUserCoop = userCoopList.get(userCoopList.size() - 1);
        assertThat(testUserCoop.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingUserCoop() throws Exception {
        int databaseSizeBeforeUpdate = userCoopRepository.findAll().size();
        userCoop.setId(count.incrementAndGet());

        // Create the UserCoop
        UserCoopDTO userCoopDTO = userCoopMapper.toDto(userCoop);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserCoopMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userCoopDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userCoopDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserCoop in the database
        List<UserCoop> userCoopList = userCoopRepository.findAll();
        assertThat(userCoopList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserCoop() throws Exception {
        int databaseSizeBeforeUpdate = userCoopRepository.findAll().size();
        userCoop.setId(count.incrementAndGet());

        // Create the UserCoop
        UserCoopDTO userCoopDTO = userCoopMapper.toDto(userCoop);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserCoopMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userCoopDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserCoop in the database
        List<UserCoop> userCoopList = userCoopRepository.findAll();
        assertThat(userCoopList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserCoop() throws Exception {
        int databaseSizeBeforeUpdate = userCoopRepository.findAll().size();
        userCoop.setId(count.incrementAndGet());

        // Create the UserCoop
        UserCoopDTO userCoopDTO = userCoopMapper.toDto(userCoop);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserCoopMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userCoopDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserCoop in the database
        List<UserCoop> userCoopList = userCoopRepository.findAll();
        assertThat(userCoopList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserCoopWithPatch() throws Exception {
        // Initialize the database
        userCoopRepository.saveAndFlush(userCoop);

        int databaseSizeBeforeUpdate = userCoopRepository.findAll().size();

        // Update the userCoop using partial update
        UserCoop partialUpdatedUserCoop = new UserCoop();
        partialUpdatedUserCoop.setId(userCoop.getId());

        partialUpdatedUserCoop.name(UPDATED_NAME);

        restUserCoopMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserCoop.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserCoop))
            )
            .andExpect(status().isOk());

        // Validate the UserCoop in the database
        List<UserCoop> userCoopList = userCoopRepository.findAll();
        assertThat(userCoopList).hasSize(databaseSizeBeforeUpdate);
        UserCoop testUserCoop = userCoopList.get(userCoopList.size() - 1);
        assertThat(testUserCoop.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateUserCoopWithPatch() throws Exception {
        // Initialize the database
        userCoopRepository.saveAndFlush(userCoop);

        int databaseSizeBeforeUpdate = userCoopRepository.findAll().size();

        // Update the userCoop using partial update
        UserCoop partialUpdatedUserCoop = new UserCoop();
        partialUpdatedUserCoop.setId(userCoop.getId());

        partialUpdatedUserCoop.name(UPDATED_NAME);

        restUserCoopMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserCoop.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserCoop))
            )
            .andExpect(status().isOk());

        // Validate the UserCoop in the database
        List<UserCoop> userCoopList = userCoopRepository.findAll();
        assertThat(userCoopList).hasSize(databaseSizeBeforeUpdate);
        UserCoop testUserCoop = userCoopList.get(userCoopList.size() - 1);
        assertThat(testUserCoop.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingUserCoop() throws Exception {
        int databaseSizeBeforeUpdate = userCoopRepository.findAll().size();
        userCoop.setId(count.incrementAndGet());

        // Create the UserCoop
        UserCoopDTO userCoopDTO = userCoopMapper.toDto(userCoop);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserCoopMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userCoopDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userCoopDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserCoop in the database
        List<UserCoop> userCoopList = userCoopRepository.findAll();
        assertThat(userCoopList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserCoop() throws Exception {
        int databaseSizeBeforeUpdate = userCoopRepository.findAll().size();
        userCoop.setId(count.incrementAndGet());

        // Create the UserCoop
        UserCoopDTO userCoopDTO = userCoopMapper.toDto(userCoop);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserCoopMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userCoopDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserCoop in the database
        List<UserCoop> userCoopList = userCoopRepository.findAll();
        assertThat(userCoopList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserCoop() throws Exception {
        int databaseSizeBeforeUpdate = userCoopRepository.findAll().size();
        userCoop.setId(count.incrementAndGet());

        // Create the UserCoop
        UserCoopDTO userCoopDTO = userCoopMapper.toDto(userCoop);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserCoopMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userCoopDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserCoop in the database
        List<UserCoop> userCoopList = userCoopRepository.findAll();
        assertThat(userCoopList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserCoop() throws Exception {
        // Initialize the database
        userCoopRepository.saveAndFlush(userCoop);

        int databaseSizeBeforeDelete = userCoopRepository.findAll().size();

        // Delete the userCoop
        restUserCoopMockMvc
            .perform(delete(ENTITY_API_URL_ID, userCoop.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserCoop> userCoopList = userCoopRepository.findAll();
        assertThat(userCoopList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
