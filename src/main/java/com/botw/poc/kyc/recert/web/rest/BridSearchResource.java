package com.botw.poc.kyc.recert.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.botw.poc.kyc.recert.domain.BridSearch;

import com.botw.poc.kyc.recert.repository.BridSearchRepository;
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
 * REST controller for managing BridSearch.
 */
@RestController
@RequestMapping("/api")
public class BridSearchResource {

    private final Logger log = LoggerFactory.getLogger(BridSearchResource.class);

    private static final String ENTITY_NAME = "bridSearch";

    private final BridSearchRepository bridSearchRepository;

    public BridSearchResource(BridSearchRepository bridSearchRepository) {
        this.bridSearchRepository = bridSearchRepository;
    }

    /**
     * POST  /brid-searches : Create a new bridSearch.
     *
     * @param bridSearch the bridSearch to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bridSearch, or with status 400 (Bad Request) if the bridSearch has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/brid-searches")
    @Timed
    public ResponseEntity<BridSearch> createBridSearch(@RequestBody BridSearch bridSearch) throws URISyntaxException {
        log.debug("REST request to save BridSearch : {}", bridSearch);
        if (bridSearch.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bridSearch cannot already have an ID")).body(null);
        }
        BridSearch result = bridSearchRepository.save(bridSearch);
        return ResponseEntity.created(new URI("/api/brid-searches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /brid-searches : Updates an existing bridSearch.
     *
     * @param bridSearch the bridSearch to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bridSearch,
     * or with status 400 (Bad Request) if the bridSearch is not valid,
     * or with status 500 (Internal Server Error) if the bridSearch couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/brid-searches")
    @Timed
    public ResponseEntity<BridSearch> updateBridSearch(@RequestBody BridSearch bridSearch) throws URISyntaxException {
        log.debug("REST request to update BridSearch : {}", bridSearch);
        if (bridSearch.getId() == null) {
            return createBridSearch(bridSearch);
        }
        BridSearch result = bridSearchRepository.save(bridSearch);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bridSearch.getId().toString()))
            .body(result);
    }

    /**
     * GET  /brid-searches : get all the bridSearches.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bridSearches in body
     */
    @GetMapping("/brid-searches")
    @Timed
    public List<BridSearch> getAllBridSearches() {
        log.debug("REST request to get all BridSearches");
        return bridSearchRepository.findAll();
    }

    /**
     * GET  /brid-searches/:id : get the "id" bridSearch.
     *
     * @param id the id of the bridSearch to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bridSearch, or with status 404 (Not Found)
     */
    @GetMapping("/brid-searches/{id}")
    @Timed
    public ResponseEntity<BridSearch> getBridSearch(@PathVariable Long id) {
        log.debug("REST request to get BridSearch : {}", id);
        BridSearch bridSearch = bridSearchRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bridSearch));
    }

    /**
     * DELETE  /brid-searches/:id : delete the "id" bridSearch.
     *
     * @param id the id of the bridSearch to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/brid-searches/{id}")
    @Timed
    public ResponseEntity<Void> deleteBridSearch(@PathVariable Long id) {
        log.debug("REST request to delete BridSearch : {}", id);
        bridSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
