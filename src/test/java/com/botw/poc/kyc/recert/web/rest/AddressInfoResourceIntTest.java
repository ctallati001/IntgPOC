package com.botw.poc.kyc.recert.web.rest;

import com.botw.poc.kyc.recert.IntgPocApp;

import com.botw.poc.kyc.recert.domain.AddressInfo;
import com.botw.poc.kyc.recert.repository.AddressInfoRepository;
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
 * Test class for the AddressInfoResource REST controller.
 *
 * @see AddressInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntgPocApp.class)
public class AddressInfoResourceIntTest {

    private static final String DEFAULT_LINE_1 = "AAAAAAAAAA";
    private static final String UPDATED_LINE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_LINE_2 = "AAAAAAAAAA";
    private static final String UPDATED_LINE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ZIP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_OF_RESIDENCE = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_OF_RESIDENCE = "BBBBBBBBBB";

    private static final String DEFAULT_TAX_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_TAX_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRIES_OF_INC = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRIES_OF_INC = "BBBBBBBBBB";

    private static final String DEFAULT_KYA_ACT_COUNTRIES = "AAAAAAAAAA";
    private static final String UPDATED_KYA_ACT_COUNTRIES = "BBBBBBBBBB";

    @Autowired
    private AddressInfoRepository addressInfoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAddressInfoMockMvc;

    private AddressInfo addressInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AddressInfoResource addressInfoResource = new AddressInfoResource(addressInfoRepository);
        this.restAddressInfoMockMvc = MockMvcBuilders.standaloneSetup(addressInfoResource)
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
    public static AddressInfo createEntity(EntityManager em) {
        AddressInfo addressInfo = new AddressInfo()
            .line1(DEFAULT_LINE_1)
            .line2(DEFAULT_LINE_2)
            .state(DEFAULT_STATE)
            .city(DEFAULT_CITY)
            .zipCode(DEFAULT_ZIP_CODE)
            .countryOfResidence(DEFAULT_COUNTRY_OF_RESIDENCE)
            .taxCountry(DEFAULT_TAX_COUNTRY)
            .countriesOfInc(DEFAULT_COUNTRIES_OF_INC)
            .kyaActCountries(DEFAULT_KYA_ACT_COUNTRIES);
        return addressInfo;
    }

    @Before
    public void initTest() {
        addressInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createAddressInfo() throws Exception {
        int databaseSizeBeforeCreate = addressInfoRepository.findAll().size();

        // Create the AddressInfo
        restAddressInfoMockMvc.perform(post("/api/address-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressInfo)))
            .andExpect(status().isCreated());

        // Validate the AddressInfo in the database
        List<AddressInfo> addressInfoList = addressInfoRepository.findAll();
        assertThat(addressInfoList).hasSize(databaseSizeBeforeCreate + 1);
        AddressInfo testAddressInfo = addressInfoList.get(addressInfoList.size() - 1);
        assertThat(testAddressInfo.getLine1()).isEqualTo(DEFAULT_LINE_1);
        assertThat(testAddressInfo.getLine2()).isEqualTo(DEFAULT_LINE_2);
        assertThat(testAddressInfo.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testAddressInfo.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testAddressInfo.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testAddressInfo.getCountryOfResidence()).isEqualTo(DEFAULT_COUNTRY_OF_RESIDENCE);
        assertThat(testAddressInfo.getTaxCountry()).isEqualTo(DEFAULT_TAX_COUNTRY);
        assertThat(testAddressInfo.getCountriesOfInc()).isEqualTo(DEFAULT_COUNTRIES_OF_INC);
        assertThat(testAddressInfo.getKyaActCountries()).isEqualTo(DEFAULT_KYA_ACT_COUNTRIES);
    }

    @Test
    @Transactional
    public void createAddressInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = addressInfoRepository.findAll().size();

        // Create the AddressInfo with an existing ID
        addressInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddressInfoMockMvc.perform(post("/api/address-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressInfo)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AddressInfo> addressInfoList = addressInfoRepository.findAll();
        assertThat(addressInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAddressInfos() throws Exception {
        // Initialize the database
        addressInfoRepository.saveAndFlush(addressInfo);

        // Get all the addressInfoList
        restAddressInfoMockMvc.perform(get("/api/address-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(addressInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].line1").value(hasItem(DEFAULT_LINE_1.toString())))
            .andExpect(jsonPath("$.[*].line2").value(hasItem(DEFAULT_LINE_2.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE.toString())))
            .andExpect(jsonPath("$.[*].countryOfResidence").value(hasItem(DEFAULT_COUNTRY_OF_RESIDENCE.toString())))
            .andExpect(jsonPath("$.[*].taxCountry").value(hasItem(DEFAULT_TAX_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].countriesOfInc").value(hasItem(DEFAULT_COUNTRIES_OF_INC.toString())))
            .andExpect(jsonPath("$.[*].kyaActCountries").value(hasItem(DEFAULT_KYA_ACT_COUNTRIES.toString())));
    }

    @Test
    @Transactional
    public void getAddressInfo() throws Exception {
        // Initialize the database
        addressInfoRepository.saveAndFlush(addressInfo);

        // Get the addressInfo
        restAddressInfoMockMvc.perform(get("/api/address-infos/{id}", addressInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(addressInfo.getId().intValue()))
            .andExpect(jsonPath("$.line1").value(DEFAULT_LINE_1.toString()))
            .andExpect(jsonPath("$.line2").value(DEFAULT_LINE_2.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE.toString()))
            .andExpect(jsonPath("$.countryOfResidence").value(DEFAULT_COUNTRY_OF_RESIDENCE.toString()))
            .andExpect(jsonPath("$.taxCountry").value(DEFAULT_TAX_COUNTRY.toString()))
            .andExpect(jsonPath("$.countriesOfInc").value(DEFAULT_COUNTRIES_OF_INC.toString()))
            .andExpect(jsonPath("$.kyaActCountries").value(DEFAULT_KYA_ACT_COUNTRIES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAddressInfo() throws Exception {
        // Get the addressInfo
        restAddressInfoMockMvc.perform(get("/api/address-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAddressInfo() throws Exception {
        // Initialize the database
        addressInfoRepository.saveAndFlush(addressInfo);
        int databaseSizeBeforeUpdate = addressInfoRepository.findAll().size();

        // Update the addressInfo
        AddressInfo updatedAddressInfo = addressInfoRepository.findOne(addressInfo.getId());
        updatedAddressInfo
            .line1(UPDATED_LINE_1)
            .line2(UPDATED_LINE_2)
            .state(UPDATED_STATE)
            .city(UPDATED_CITY)
            .zipCode(UPDATED_ZIP_CODE)
            .countryOfResidence(UPDATED_COUNTRY_OF_RESIDENCE)
            .taxCountry(UPDATED_TAX_COUNTRY)
            .countriesOfInc(UPDATED_COUNTRIES_OF_INC)
            .kyaActCountries(UPDATED_KYA_ACT_COUNTRIES);

        restAddressInfoMockMvc.perform(put("/api/address-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAddressInfo)))
            .andExpect(status().isOk());

        // Validate the AddressInfo in the database
        List<AddressInfo> addressInfoList = addressInfoRepository.findAll();
        assertThat(addressInfoList).hasSize(databaseSizeBeforeUpdate);
        AddressInfo testAddressInfo = addressInfoList.get(addressInfoList.size() - 1);
        assertThat(testAddressInfo.getLine1()).isEqualTo(UPDATED_LINE_1);
        assertThat(testAddressInfo.getLine2()).isEqualTo(UPDATED_LINE_2);
        assertThat(testAddressInfo.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testAddressInfo.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testAddressInfo.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testAddressInfo.getCountryOfResidence()).isEqualTo(UPDATED_COUNTRY_OF_RESIDENCE);
        assertThat(testAddressInfo.getTaxCountry()).isEqualTo(UPDATED_TAX_COUNTRY);
        assertThat(testAddressInfo.getCountriesOfInc()).isEqualTo(UPDATED_COUNTRIES_OF_INC);
        assertThat(testAddressInfo.getKyaActCountries()).isEqualTo(UPDATED_KYA_ACT_COUNTRIES);
    }

    @Test
    @Transactional
    public void updateNonExistingAddressInfo() throws Exception {
        int databaseSizeBeforeUpdate = addressInfoRepository.findAll().size();

        // Create the AddressInfo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAddressInfoMockMvc.perform(put("/api/address-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressInfo)))
            .andExpect(status().isCreated());

        // Validate the AddressInfo in the database
        List<AddressInfo> addressInfoList = addressInfoRepository.findAll();
        assertThat(addressInfoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAddressInfo() throws Exception {
        // Initialize the database
        addressInfoRepository.saveAndFlush(addressInfo);
        int databaseSizeBeforeDelete = addressInfoRepository.findAll().size();

        // Get the addressInfo
        restAddressInfoMockMvc.perform(delete("/api/address-infos/{id}", addressInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AddressInfo> addressInfoList = addressInfoRepository.findAll();
        assertThat(addressInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AddressInfo.class);
        AddressInfo addressInfo1 = new AddressInfo();
        addressInfo1.setId(1L);
        AddressInfo addressInfo2 = new AddressInfo();
        addressInfo2.setId(addressInfo1.getId());
        assertThat(addressInfo1).isEqualTo(addressInfo2);
        addressInfo2.setId(2L);
        assertThat(addressInfo1).isNotEqualTo(addressInfo2);
        addressInfo1.setId(null);
        assertThat(addressInfo1).isNotEqualTo(addressInfo2);
    }
}
