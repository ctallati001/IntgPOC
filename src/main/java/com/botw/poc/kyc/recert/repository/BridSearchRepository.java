package com.botw.poc.kyc.recert.repository;

import com.botw.poc.kyc.recert.domain.BridSearch;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BridSearch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BridSearchRepository extends JpaRepository<BridSearch,Long> {
    
}
