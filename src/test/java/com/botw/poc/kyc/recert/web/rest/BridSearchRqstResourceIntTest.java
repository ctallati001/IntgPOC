package com.botw.poc.kyc.recert.web.rest;

import com.botw.poc.kyc.recert.IntgPocApp;

import com.botw.poc.kyc.recert.domain.BridSearchRqst;
import com.botw.poc.kyc.recert.repository.BridSearchRqstRepository;
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

/**
 * Test class for the BridSearchRqstResource REST controller.
 *
 * @see BridSearchRqstResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntgPocApp.class)
public class BridSearchRqstResourceIntTest {

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private BridSearchRqstRepository bridSearchRqstRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBridSearchRqstMockMvc;

    private BridSearchRqst bridSearchRqst;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BridSearchRqstResource bridSearchRqstResource = new BridSearchRqstResource(bridSearchRqstRepository);
        this.restBridSearchRqstMockMvc = MockMvcBuilders.standaloneSetup(bridSearchRqstResource)
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
    public static BridSearchRqst createEntity(EntityManager em) {
        BridSearchRqst bridSearchRqst = new BridSearchRqst()
            .content(DEFAULT_CONTENT);
        return bridSearchRqst;
    }

    @Before
    public void initTest() {
        bridSearchRqst = createEntity(em);
    }

    @Test
    @Transactional
    public void createBridSearchRqst() throws Exception {
        int databaseSizeBeforeCreate = bridSearchRqstRepository.findAll().size();

        // Create the BridSearchRqst
        restBridSearchRqstMockMvc.perform(post("/api/brid-search-rqsts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bridSearchRqst)))
            .andExpect(status().isCreated());

        // Validate the BridSearchRqst in the database
        List<BridSearchRqst> bridSearchRqstList = bridSearchRqstRepository.findAll();
        assertThat(bridSearchRqstList).hasSize(databaseSizeBeforeCreate + 1);
        BridSearchRqst testBridSearchRqst = bridSearchRqstList.get(bridSearchRqstList.size() - 1);
        assertThat(testBridSearchRqst.getContent()).isEqualTo(DEFAULT_CONTENT);
    }

    @Test
    @Transactional
    public void createBridSearchRqstWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bridSearchRqstRepository.findAll().size();

        // Create the BridSearchRqst with an existing ID
        bridSearchRqst.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBridSearchRqstMockMvc.perform(post("/api/brid-search-rqsts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bridSearchRqst)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BridSearchRqst> bridSearchRqstList = bridSearchRqstRepository.findAll();
        assertThat(bridSearchRqstList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBridSearchRqsts() throws Exception {
        // Initialize the database
        bridSearchRqstRepository.saveAndFlush(bridSearchRqst);

        // Get all the bridSearchRqstList
        restBridSearchRqstMockMvc.perform(get("/api/brid-search-rqsts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bridSearchRqst.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())));
    }

    @Test
    @Transactional
    public void getBridSearchRqst() throws Exception {
        // Initialize the database
        bridSearchRqstRepository.saveAndFlush(bridSearchRqst);

        // Get the bridSearchRqst
        restBridSearchRqstMockMvc.perform(get("/api/brid-search-rqsts/{id}", bridSearchRqst.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bridSearchRqst.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBridSearchRqst() throws Exception {
        // Get the bridSearchRqst
        restBridSearchRqstMockMvc.perform(get("/api/brid-search-rqsts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBridSearchRqst() throws Exception {
        // Initialize the database
        bridSearchRqstRepository.saveAndFlush(bridSearchRqst);
        int databaseSizeBeforeUpdate = bridSearchRqstRepository.findAll().size();

        // Update the bridSearchRqst
        BridSearchRqst updatedBridSearchRqst = bridSearchRqstRepository.findOne(bridSearchRqst.getId());
        updatedBridSearchRqst
            .content(UPDATED_CONTENT);

        restBridSearchRqstMockMvc.perform(put("/api/brid-search-rqsts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBridSearchRqst)))
            .andExpect(status().isOk());

        // Validate the BridSearchRqst in the database
        List<BridSearchRqst> bridSearchRqstList = bridSearchRqstRepository.findAll();
        assertThat(bridSearchRqstList).hasSize(databaseSizeBeforeUpdate);
        BridSearchRqst testBridSearchRqst = bridSearchRqstList.get(bridSearchRqstList.size() - 1);
        assertThat(testBridSearchRqst.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void updateNonExistingBridSearchRqst() throws Exception {
        int databaseSizeBeforeUpdate = bridSearchRqstRepository.findAll().size();

        // Create the BridSearchRqst

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBridSearchRqstMockMvc.perform(put("/api/brid-search-rqsts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bridSearchRqst)))
            .andExpect(status().isCreated());

        // Validate the BridSearchRqst in the database
        List<BridSearchRqst> bridSearchRqstList = bridSearchRqstRepository.findAll();
        assertThat(bridSearchRqstList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBridSearchRqst() throws Exception {
        // Initialize the database
        bridSearchRqstRepository.saveAndFlush(bridSearchRqst);
        int databaseSizeBeforeDelete = bridSearchRqstRepository.findAll().size();

        // Get the bridSearchRqst
        restBridSearchRqstMockMvc.perform(delete("/api/brid-search-rqsts/{id}", bridSearchRqst.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BridSearchRqst> bridSearchRqstList = bridSearchRqstRepository.findAll();
        assertThat(bridSearchRqstList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BridSearchRqst.class);
        BridSearchRqst bridSearchRqst1 = new BridSearchRqst();
        bridSearchRqst1.setId(1L);
        BridSearchRqst bridSearchRqst2 = new BridSearchRqst();
        bridSearchRqst2.setId(bridSearchRqst1.getId());
        assertThat(bridSearchRqst1).isEqualTo(bridSearchRqst2);
        bridSearchRqst2.setId(2L);
        assertThat(bridSearchRqst1).isNotEqualTo(bridSearchRqst2);
        bridSearchRqst1.setId(null);
        assertThat(bridSearchRqst1).isNotEqualTo(bridSearchRqst2);
    }
}
