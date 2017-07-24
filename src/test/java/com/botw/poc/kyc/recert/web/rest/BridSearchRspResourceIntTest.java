package com.botw.poc.kyc.recert.web.rest;

import com.botw.poc.kyc.recert.IntgPocApp;

import com.botw.poc.kyc.recert.domain.BridSearchRsp;
import com.botw.poc.kyc.recert.repository.BridSearchRspRepository;
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
 * Test class for the BridSearchRspResource REST controller.
 *
 * @see BridSearchRspResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntgPocApp.class)
public class BridSearchRspResourceIntTest {

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private BridSearchRspRepository bridSearchRspRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBridSearchRspMockMvc;

    private BridSearchRsp bridSearchRsp;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BridSearchRspResource bridSearchRspResource = new BridSearchRspResource(bridSearchRspRepository);
        this.restBridSearchRspMockMvc = MockMvcBuilders.standaloneSetup(bridSearchRspResource)
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
    public static BridSearchRsp createEntity(EntityManager em) {
        BridSearchRsp bridSearchRsp = new BridSearchRsp()
            .content(DEFAULT_CONTENT);
        return bridSearchRsp;
    }

    @Before
    public void initTest() {
        bridSearchRsp = createEntity(em);
    }

    @Test
    @Transactional
    public void createBridSearchRsp() throws Exception {
        int databaseSizeBeforeCreate = bridSearchRspRepository.findAll().size();

        // Create the BridSearchRsp
        restBridSearchRspMockMvc.perform(post("/api/brid-search-rsps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bridSearchRsp)))
            .andExpect(status().isCreated());

        // Validate the BridSearchRsp in the database
        List<BridSearchRsp> bridSearchRspList = bridSearchRspRepository.findAll();
        assertThat(bridSearchRspList).hasSize(databaseSizeBeforeCreate + 1);
        BridSearchRsp testBridSearchRsp = bridSearchRspList.get(bridSearchRspList.size() - 1);
        assertThat(testBridSearchRsp.getContent()).isEqualTo(DEFAULT_CONTENT);
    }

    @Test
    @Transactional
    public void createBridSearchRspWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bridSearchRspRepository.findAll().size();

        // Create the BridSearchRsp with an existing ID
        bridSearchRsp.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBridSearchRspMockMvc.perform(post("/api/brid-search-rsps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bridSearchRsp)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BridSearchRsp> bridSearchRspList = bridSearchRspRepository.findAll();
        assertThat(bridSearchRspList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBridSearchRsps() throws Exception {
        // Initialize the database
        bridSearchRspRepository.saveAndFlush(bridSearchRsp);

        // Get all the bridSearchRspList
        restBridSearchRspMockMvc.perform(get("/api/brid-search-rsps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bridSearchRsp.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())));
    }

    @Test
    @Transactional
    public void getBridSearchRsp() throws Exception {
        // Initialize the database
        bridSearchRspRepository.saveAndFlush(bridSearchRsp);

        // Get the bridSearchRsp
        restBridSearchRspMockMvc.perform(get("/api/brid-search-rsps/{id}", bridSearchRsp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bridSearchRsp.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBridSearchRsp() throws Exception {
        // Get the bridSearchRsp
        restBridSearchRspMockMvc.perform(get("/api/brid-search-rsps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBridSearchRsp() throws Exception {
        // Initialize the database
        bridSearchRspRepository.saveAndFlush(bridSearchRsp);
        int databaseSizeBeforeUpdate = bridSearchRspRepository.findAll().size();

        // Update the bridSearchRsp
        BridSearchRsp updatedBridSearchRsp = bridSearchRspRepository.findOne(bridSearchRsp.getId());
        updatedBridSearchRsp
            .content(UPDATED_CONTENT);

        restBridSearchRspMockMvc.perform(put("/api/brid-search-rsps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBridSearchRsp)))
            .andExpect(status().isOk());

        // Validate the BridSearchRsp in the database
        List<BridSearchRsp> bridSearchRspList = bridSearchRspRepository.findAll();
        assertThat(bridSearchRspList).hasSize(databaseSizeBeforeUpdate);
        BridSearchRsp testBridSearchRsp = bridSearchRspList.get(bridSearchRspList.size() - 1);
        assertThat(testBridSearchRsp.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void updateNonExistingBridSearchRsp() throws Exception {
        int databaseSizeBeforeUpdate = bridSearchRspRepository.findAll().size();

        // Create the BridSearchRsp

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBridSearchRspMockMvc.perform(put("/api/brid-search-rsps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bridSearchRsp)))
            .andExpect(status().isCreated());

        // Validate the BridSearchRsp in the database
        List<BridSearchRsp> bridSearchRspList = bridSearchRspRepository.findAll();
        assertThat(bridSearchRspList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBridSearchRsp() throws Exception {
        // Initialize the database
        bridSearchRspRepository.saveAndFlush(bridSearchRsp);
        int databaseSizeBeforeDelete = bridSearchRspRepository.findAll().size();

        // Get the bridSearchRsp
        restBridSearchRspMockMvc.perform(delete("/api/brid-search-rsps/{id}", bridSearchRsp.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BridSearchRsp> bridSearchRspList = bridSearchRspRepository.findAll();
        assertThat(bridSearchRspList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BridSearchRsp.class);
        BridSearchRsp bridSearchRsp1 = new BridSearchRsp();
        bridSearchRsp1.setId(1L);
        BridSearchRsp bridSearchRsp2 = new BridSearchRsp();
        bridSearchRsp2.setId(bridSearchRsp1.getId());
        assertThat(bridSearchRsp1).isEqualTo(bridSearchRsp2);
        bridSearchRsp2.setId(2L);
        assertThat(bridSearchRsp1).isNotEqualTo(bridSearchRsp2);
        bridSearchRsp1.setId(null);
        assertThat(bridSearchRsp1).isNotEqualTo(bridSearchRsp2);
    }
}
