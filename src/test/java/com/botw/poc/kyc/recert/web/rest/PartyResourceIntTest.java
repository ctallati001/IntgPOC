package com.botw.poc.kyc.recert.web.rest;

import com.botw.poc.kyc.recert.IntgPocApp;

import com.botw.poc.kyc.recert.domain.Party;
import com.botw.poc.kyc.recert.repository.PartyRepository;
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

import com.botw.poc.kyc.recert.domain.enumeration.PartyIdType;
import com.botw.poc.kyc.recert.domain.enumeration.PartyType;
/**
 * Test class for the PartyResource REST controller.
 *
 * @see PartyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntgPocApp.class)
public class PartyResourceIntTest {

    private static final String DEFAULT_PARTY_ID_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_PARTY_ID_VALUE = "BBBBBBBBBB";

    private static final PartyIdType DEFAULT_PARTY_ID_TYPE = PartyIdType.RMID;
    private static final PartyIdType UPDATED_PARTY_ID_TYPE = PartyIdType.TPSSID;

    private static final PartyType DEFAULT_PARTY_TYPE = PartyType.INDIVIDUAL;
    private static final PartyType UPDATED_PARTY_TYPE = PartyType.ORGANIZATION;

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPartyMockMvc;

    private Party party;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PartyResource partyResource = new PartyResource(partyRepository);
        this.restPartyMockMvc = MockMvcBuilders.standaloneSetup(partyResource)
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
    public static Party createEntity(EntityManager em) {
        Party party = new Party()
            .partyIdValue(DEFAULT_PARTY_ID_VALUE)
            .partyIdType(DEFAULT_PARTY_ID_TYPE)
            .partyType(DEFAULT_PARTY_TYPE);
        return party;
    }

    @Before
    public void initTest() {
        party = createEntity(em);
    }

    @Test
    @Transactional
    public void createParty() throws Exception {
        int databaseSizeBeforeCreate = partyRepository.findAll().size();

        // Create the Party
        restPartyMockMvc.perform(post("/api/parties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(party)))
            .andExpect(status().isCreated());

        // Validate the Party in the database
        List<Party> partyList = partyRepository.findAll();
        assertThat(partyList).hasSize(databaseSizeBeforeCreate + 1);
        Party testParty = partyList.get(partyList.size() - 1);
        assertThat(testParty.getPartyIdValue()).isEqualTo(DEFAULT_PARTY_ID_VALUE);
        assertThat(testParty.getPartyIdType()).isEqualTo(DEFAULT_PARTY_ID_TYPE);
        assertThat(testParty.getPartyType()).isEqualTo(DEFAULT_PARTY_TYPE);
    }

    @Test
    @Transactional
    public void createPartyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partyRepository.findAll().size();

        // Create the Party with an existing ID
        party.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyMockMvc.perform(post("/api/parties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(party)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Party> partyList = partyRepository.findAll();
        assertThat(partyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllParties() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList
        restPartyMockMvc.perform(get("/api/parties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(party.getId().intValue())))
            .andExpect(jsonPath("$.[*].partyIdValue").value(hasItem(DEFAULT_PARTY_ID_VALUE.toString())))
            .andExpect(jsonPath("$.[*].partyIdType").value(hasItem(DEFAULT_PARTY_ID_TYPE.toString())))
            .andExpect(jsonPath("$.[*].partyType").value(hasItem(DEFAULT_PARTY_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getParty() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get the party
        restPartyMockMvc.perform(get("/api/parties/{id}", party.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(party.getId().intValue()))
            .andExpect(jsonPath("$.partyIdValue").value(DEFAULT_PARTY_ID_VALUE.toString()))
            .andExpect(jsonPath("$.partyIdType").value(DEFAULT_PARTY_ID_TYPE.toString()))
            .andExpect(jsonPath("$.partyType").value(DEFAULT_PARTY_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingParty() throws Exception {
        // Get the party
        restPartyMockMvc.perform(get("/api/parties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParty() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);
        int databaseSizeBeforeUpdate = partyRepository.findAll().size();

        // Update the party
        Party updatedParty = partyRepository.findOne(party.getId());
        updatedParty
            .partyIdValue(UPDATED_PARTY_ID_VALUE)
            .partyIdType(UPDATED_PARTY_ID_TYPE)
            .partyType(UPDATED_PARTY_TYPE);

        restPartyMockMvc.perform(put("/api/parties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedParty)))
            .andExpect(status().isOk());

        // Validate the Party in the database
        List<Party> partyList = partyRepository.findAll();
        assertThat(partyList).hasSize(databaseSizeBeforeUpdate);
        Party testParty = partyList.get(partyList.size() - 1);
        assertThat(testParty.getPartyIdValue()).isEqualTo(UPDATED_PARTY_ID_VALUE);
        assertThat(testParty.getPartyIdType()).isEqualTo(UPDATED_PARTY_ID_TYPE);
        assertThat(testParty.getPartyType()).isEqualTo(UPDATED_PARTY_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingParty() throws Exception {
        int databaseSizeBeforeUpdate = partyRepository.findAll().size();

        // Create the Party

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPartyMockMvc.perform(put("/api/parties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(party)))
            .andExpect(status().isCreated());

        // Validate the Party in the database
        List<Party> partyList = partyRepository.findAll();
        assertThat(partyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteParty() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);
        int databaseSizeBeforeDelete = partyRepository.findAll().size();

        // Get the party
        restPartyMockMvc.perform(delete("/api/parties/{id}", party.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Party> partyList = partyRepository.findAll();
        assertThat(partyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Party.class);
        Party party1 = new Party();
        party1.setId(1L);
        Party party2 = new Party();
        party2.setId(party1.getId());
        assertThat(party1).isEqualTo(party2);
        party2.setId(2L);
        assertThat(party1).isNotEqualTo(party2);
        party1.setId(null);
        assertThat(party1).isNotEqualTo(party2);
    }
}
