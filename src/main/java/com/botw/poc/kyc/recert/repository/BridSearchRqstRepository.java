package com.botw.poc.kyc.recert.repository;

import com.botw.poc.kyc.recert.domain.BridSearchRqst;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BridSearchRqst entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BridSearchRqstRepository extends JpaRepository<BridSearchRqst,Long> {
    
}
