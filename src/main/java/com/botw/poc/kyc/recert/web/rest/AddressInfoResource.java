package com.botw.poc.kyc.recert.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.botw.poc.kyc.recert.domain.AddressInfo;

import com.botw.poc.kyc.recert.repository.AddressInfoRepository;
import com.botw.poc.kyc.recert.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing AddressInfo.
 */
@RestController
@RequestMapping("/api")
public class AddressInfoResource {

    private final Logger log = LoggerFactory.getLogger(AddressInfoResource.class);

    private static final String ENTITY_NAME = "addressInfo";

    private final AddressInfoRepository addressInfoRepository;

    public AddressInfoResource(AddressInfoRepository addressInfoRepository) {
        this.addressInfoRepository = addressInfoRepository;
    }

    /**
     * POST  /address-infos : Create a new addressInfo.
     *
     * @param addressInfo the addressInfo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new addressInfo, or with status 400 (Bad Request) if the addressInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/address-infos")
    @Timed
    public ResponseEntity<AddressInfo> createAddressInfo(@RequestBody AddressInfo addressInfo) throws URISyntaxException {
        log.debug("REST request to save AddressInfo : {}", addressInfo);
        if (addressInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new addressInfo cannot already have an ID")).body(null);
        }
        AddressInfo result = addressInfoRepository.save(addressInfo);
        return ResponseEntity.created(new URI("/api/address-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /address-infos : Updates an existing addressInfo.
     *
     * @param addressInfo the addressInfo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated addressInfo,
     * or with status 400 (Bad Request) if the addressInfo is not valid,
     * or with status 500 (Internal Server Error) if the addressInfo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/address-infos")
    @Timed
    public ResponseEntity<AddressInfo> updateAddressInfo(@RequestBody AddressInfo addressInfo) throws URISyntaxException {
        log.debug("REST request to update AddressInfo : {}", addressInfo);
        if (addressInfo.getId() == null) {
            return createAddressInfo(addressInfo);
        }
        AddressInfo result = addressInfoRepository.save(addressInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, addressInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /address-infos : get all the addressInfos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of addressInfos in body
     */
    @GetMapping("/address-infos")
    @Timed
    public List<AddressInfo> getAllAddressInfos() {
        log.debug("REST request to get all AddressInfos");
        return addressInfoRepository.findAll();
    }

    /**
     * GET  /address-infos/:id : get the "id" addressInfo.
     *
     * @param id the id of the addressInfo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the addressInfo, or with status 404 (Not Found)
     */
    @GetMapping("/address-infos/{id}")
    @Timed
    public ResponseEntity<AddressInfo> getAddressInfo(@PathVariable Long id) {
        log.debug("REST request to get AddressInfo : {}", id);
        AddressInfo addressInfo = addressInfoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(addressInfo));
    }

    /**
     * DELETE  /address-infos/:id : delete the "id" addressInfo.
     *
     * @param id the id of the addressInfo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/address-infos/{id}")
    @Timed
    public ResponseEntity<Void> deleteAddressInfo(@PathVariable Long id) {
        log.debug("REST request to delete AddressInfo : {}", id);
        addressInfoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
