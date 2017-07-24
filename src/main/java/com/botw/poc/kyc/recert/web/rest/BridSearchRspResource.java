package com.botw.poc.kyc.recert.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.botw.poc.kyc.recert.domain.BridSearchRsp;

import com.botw.poc.kyc.recert.repository.BridSearchRspRepository;
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
 * REST controller for managing BridSearchRsp.
 */
@RestController
@RequestMapping("/api")
public class BridSearchRspResource {

    private final Logger log = LoggerFactory.getLogger(BridSearchRspResource.class);

    private static final String ENTITY_NAME = "bridSearchRsp";

    private final BridSearchRspRepository bridSearchRspRepository;

    public BridSearchRspResource(BridSearchRspRepository bridSearchRspRepository) {
        this.bridSearchRspRepository = bridSearchRspRepository;
    }

    /**
     * POST  /brid-search-rsps : Create a new bridSearchRsp.
     *
     * @param bridSearchRsp the bridSearchRsp to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bridSearchRsp, or with status 400 (Bad Request) if the bridSearchRsp has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/brid-search-rsps")
    @Timed
    public ResponseEntity<BridSearchRsp> createBridSearchRsp(@RequestBody BridSearchRsp bridSearchRsp) throws URISyntaxException {
        log.debug("REST request to save BridSearchRsp : {}", bridSearchRsp);
        if (bridSearchRsp.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bridSearchRsp cannot already have an ID")).body(null);
        }
        BridSearchRsp result = bridSearchRspRepository.save(bridSearchRsp);
        return ResponseEntity.created(new URI("/api/brid-search-rsps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /brid-search-rsps : Updates an existing bridSearchRsp.
     *
     * @param bridSearchRsp the bridSearchRsp to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bridSearchRsp,
     * or with status 400 (Bad Request) if the bridSearchRsp is not valid,
     * or with status 500 (Internal Server Error) if the bridSearchRsp couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/brid-search-rsps")
    @Timed
    public ResponseEntity<BridSearchRsp> updateBridSearchRsp(@RequestBody BridSearchRsp bridSearchRsp) throws URISyntaxException {
        log.debug("REST request to update BridSearchRsp : {}", bridSearchRsp);
        if (bridSearchRsp.getId() == null) {
            return createBridSearchRsp(bridSearchRsp);
        }
        BridSearchRsp result = bridSearchRspRepository.save(bridSearchRsp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bridSearchRsp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /brid-search-rsps : get all the bridSearchRsps.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bridSearchRsps in body
     */
    @GetMapping("/brid-search-rsps")
    @Timed
    public List<BridSearchRsp> getAllBridSearchRsps() {
        log.debug("REST request to get all BridSearchRsps");
        return bridSearchRspRepository.findAll();
    }

    /**
     * GET  /brid-search-rsps/:id : get the "id" bridSearchRsp.
     *
     * @param id the id of the bridSearchRsp to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bridSearchRsp, or with status 404 (Not Found)
     */
    @GetMapping("/brid-search-rsps/{id}")
    @Timed
    public ResponseEntity<BridSearchRsp> getBridSearchRsp(@PathVariable Long id) {
        log.debug("REST request to get BridSearchRsp : {}", id);
        BridSearchRsp bridSearchRsp = bridSearchRspRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bridSearchRsp));
    }

    /**
     * DELETE  /brid-search-rsps/:id : delete the "id" bridSearchRsp.
     *
     * @param id the id of the bridSearchRsp to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/brid-search-rsps/{id}")
    @Timed
    public ResponseEntity<Void> deleteBridSearchRsp(@PathVariable Long id) {
        log.debug("REST request to delete BridSearchRsp : {}", id);
        bridSearchRspRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
