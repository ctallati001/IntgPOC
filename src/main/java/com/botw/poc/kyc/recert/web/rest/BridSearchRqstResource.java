package com.botw.poc.kyc.recert.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.botw.poc.kyc.recert.domain.BridSearchRqst;

import com.botw.poc.kyc.recert.repository.BridSearchRqstRepository;
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
 * REST controller for managing BridSearchRqst.
 */
@RestController
@RequestMapping("/api")
public class BridSearchRqstResource {

    private final Logger log = LoggerFactory.getLogger(BridSearchRqstResource.class);

    private static final String ENTITY_NAME = "bridSearchRqst";

    private final BridSearchRqstRepository bridSearchRqstRepository;

    public BridSearchRqstResource(BridSearchRqstRepository bridSearchRqstRepository) {
        this.bridSearchRqstRepository = bridSearchRqstRepository;
    }

    /**
     * POST  /brid-search-rqsts : Create a new bridSearchRqst.
     *
     * @param bridSearchRqst the bridSearchRqst to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bridSearchRqst, or with status 400 (Bad Request) if the bridSearchRqst has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/brid-search-rqsts")
    @Timed
    public ResponseEntity<BridSearchRqst> createBridSearchRqst(@RequestBody BridSearchRqst bridSearchRqst) throws URISyntaxException {
        log.debug("REST request to save BridSearchRqst : {}", bridSearchRqst);
        if (bridSearchRqst.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bridSearchRqst cannot already have an ID")).body(null);
        }
        BridSearchRqst result = bridSearchRqstRepository.save(bridSearchRqst);
        return ResponseEntity.created(new URI("/api/brid-search-rqsts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /brid-search-rqsts : Updates an existing bridSearchRqst.
     *
     * @param bridSearchRqst the bridSearchRqst to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bridSearchRqst,
     * or with status 400 (Bad Request) if the bridSearchRqst is not valid,
     * or with status 500 (Internal Server Error) if the bridSearchRqst couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/brid-search-rqsts")
    @Timed
    public ResponseEntity<BridSearchRqst> updateBridSearchRqst(@RequestBody BridSearchRqst bridSearchRqst) throws URISyntaxException {
        log.debug("REST request to update BridSearchRqst : {}", bridSearchRqst);
        if (bridSearchRqst.getId() == null) {
            return createBridSearchRqst(bridSearchRqst);
        }
        BridSearchRqst result = bridSearchRqstRepository.save(bridSearchRqst);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bridSearchRqst.getId().toString()))
            .body(result);
    }

    /**
     * GET  /brid-search-rqsts : get all the bridSearchRqsts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bridSearchRqsts in body
     */
    @GetMapping("/brid-search-rqsts")
    @Timed
    public List<BridSearchRqst> getAllBridSearchRqsts() {
        log.debug("REST request to get all BridSearchRqsts");
        return bridSearchRqstRepository.findAll();
    }

    /**
     * GET  /brid-search-rqsts/:id : get the "id" bridSearchRqst.
     *
     * @param id the id of the bridSearchRqst to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bridSearchRqst, or with status 404 (Not Found)
     */
    @GetMapping("/brid-search-rqsts/{id}")
    @Timed
    public ResponseEntity<BridSearchRqst> getBridSearchRqst(@PathVariable Long id) {
        log.debug("REST request to get BridSearchRqst : {}", id);
        BridSearchRqst bridSearchRqst = bridSearchRqstRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bridSearchRqst));
    }

    /**
     * DELETE  /brid-search-rqsts/:id : delete the "id" bridSearchRqst.
     *
     * @param id the id of the bridSearchRqst to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/brid-search-rqsts/{id}")
    @Timed
    public ResponseEntity<Void> deleteBridSearchRqst(@PathVariable Long id) {
        log.debug("REST request to delete BridSearchRqst : {}", id);
        bridSearchRqstRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
