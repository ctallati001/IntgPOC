package com.botw.poc.kyc.recert.web.rest;

import com.botw.poc.kyc.recert.IntgPocApp;

import com.botw.poc.kyc.recert.domain.BridSearch;
import com.botw.poc.kyc.recert.repository.BridSearchRepository;
import com.botw.poc.kyc.recert.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.botw.poc.kyc.recert.domain.enumeration.BridSearchStatus;
import com.botw.poc.kyc.recert.domain.enumeration.BridSearchAddStatus;
/**
 * Test class for the BridSearchResource REST controller.
 *
 * @see BridSearchResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntgPocApp.class)
public class BridSearchResourceIntTest {

    private static final String DEFAULT_OFAC_RQST_INFOLD = "AAAAAAAAAA";
    private static final String UPDATED_OFAC_RQST_INFOLD = "BBBBBBBBBB";

    private static final BridSearchStatus DEFAULT_STATUS = BridSearchStatus.NO_MATCH;
    private static final BridSearchStatus UPDATED_STATUS = BridSearchStatus.MATCH;

    private static final BridSearchAddStatus DEFAULT_ADD_STATUS = BridSearchAddStatus.IN_REVIEW;
    private static final BridSearchAddStatus UPDATED_ADD_STATUS = BridSearchAddStatus.COMPLETE;

    @Autowired
    private BridSearchRepository bridSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBridSearchMockMvc;

    private BridSearch bridSearch;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BridSearchResource bridSearchResource = new BridSearchResource(bridSearchRepository);
        this.restBridSearchMockMvc = MockMvcBuilders.standaloneSetup(bridSearchResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BridSearch createEntity(EntityManager em) {
        BridSearch bridSearch = new BridSearch()
            .ofacRqstInfold(DEFAULT_OFAC_RQST_INFOLD)
            .status(DEFAULT_STATUS)
            .addStatus(DEFAULT_ADD_STATUS);
        return bridSearch;
    }

    @Before
    public void initTest() {
        bridSearch = createEntity(em);
    }

    @Test
    @Transactional
    public void createBridSearch() throws Exception {
        int databaseSizeBeforeCreate = bridSearchRepository.findAll().size();

        // Create the BridSearch
        restBridSearchMockMvc.perform(post("/api/brid-searches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bridSearch)))
            .andExpect(status().isCreated());

        // Validate the BridSearch in the database
        List<BridSearch> bridSearchList = bridSearchRepository.findAll();
        assertThat(bridSearchList).hasSize(databaseSizeBeforeCreate + 1);
        BridSearch testBridSearch = bridSearchList.get(bridSearchList.size() - 1);
        assertThat(testBridSearch.getOfacRqstInfold()).isEqualTo(DEFAULT_OFAC_RQST_INFOLD);
        assertThat(testBridSearch.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testBridSearch.getAddStatus()).isEqualTo(DEFAULT_ADD_STATUS);
    }

    @Test
    @Transactional
    public void createBridSearchWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bridSearchRepository.findAll().size();

        // Create the BridSearch with an existing ID
        bridSearch.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBridSearchMockMvc.perform(post("/api/brid-searches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bridSearch)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BridSearch> bridSearchList = bridSearchRepository.findAll();
        assertThat(bridSearchList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBridSearches() throws Exception {
        // Initialize the database
        bridSearchRepository.saveAndFlush(bridSearch);

        // Get all the bridSearchList
        restBridSearchMockMvc.perform(get("/api/brid-searches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bridSearch.getId().intValue())))
            .andExpect(jsonPath("$.[*].ofacRqstInfold").value(hasItem(DEFAULT_OFAC_RQST_INFOLD.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].addStatus").value(hasItem(DEFAULT_ADD_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getBridSearch() throws Exception {
        // Initialize the database
        bridSearchRepository.saveAndFlush(bridSearch);

        // Get the bridSearch
        restBridSearchMockMvc.perform(get("/api/brid-searches/{id}", bridSearch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bridSearch.getId().intValue()))
            .andExpect(jsonPath("$.ofacRqstInfold").value(DEFAULT_OFAC_RQST_INFOLD.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.addStatus").value(DEFAULT_ADD_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBridSearch() throws Exception {
        // Get the bridSearch
        restBridSearchMockMvc.perform(get("/api/brid-searches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBridSearch() throws Exception {
        // Initialize the database
        bridSearchRepository.saveAndFlush(bridSearch);
        int databaseSizeBeforeUpdate = bridSearchRepository.findAll().size();

        // Update the bridSearch
        BridSearch updatedBridSearch = bridSearchRepository.findOne(bridSearch.getId());
        updatedBridSearch
            .ofacRqstInfold(UPDATED_OFAC_RQST_INFOLD)
            .status(UPDATED_STATUS)
            .addStatus(UPDATED_ADD_STATUS);

        restBridSearchMockMvc.perform(put("/api/brid-searches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBridSearch)))
            .andExpect(status().isOk());

        // Validate the BridSearch in the database
        List<BridSearch> bridSearchList = bridSearchRepository.findAll();
        assertThat(bridSearchList).hasSize(databaseSizeBeforeUpdate);
        BridSearch testBridSearch = bridSearchList.get(bridSearchList.size() - 1);
        assertThat(testBridSearch.getOfacRqstInfold()).isEqualTo(UPDATED_OFAC_RQST_INFOLD);
        assertThat(testBridSearch.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBridSearch.getAddStatus()).isEqualTo(UPDATED_ADD_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingBridSearch() throws Exception {
        int databaseSizeBeforeUpdate = bridSearchRepository.findAll().size();

        // Create the BridSearch

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBridSearchMockMvc.perform(put("/api/brid-searches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bridSearch)))
            .andExpect(status().isCreated());

        // Validate the BridSearch in the database
        List<BridSearch> bridSearchList = bridSearchRepository.findAll();
        assertThat(bridSearchList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBridSearch() throws Exception {
        // Initialize the database
        bridSearchRepository.saveAndFlush(bridSearch);
        int databaseSizeBeforeDelete = bridSearchRepository.findAll().size();

        // Get the bridSearch
        restBridSearchMockMvc.perform(delete("/api/brid-searches/{id}", bridSearch.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BridSearch> bridSearchList = bridSearchRepository.findAll();
        assertThat(bridSearchList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BridSearch.class);
        BridSearch bridSearch1 = new BridSearch();
        bridSearch1.setId(1L);
        BridSearch bridSearch2 = new BridSearch();
        bridSearch2.setId(bridSearch1.getId());
        assertThat(bridSearch1).isEqualTo(bridSearch2);
        bridSearch2.setId(2L);
        assertThat(bridSearch1).isNotEqualTo(bridSearch2);
        bridSearch1.setId(null);
        assertThat(bridSearch1).isNotEqualTo(bridSearch2);
    }
}
