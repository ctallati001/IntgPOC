package com.botw.poc.kyc.recert.repository;

import com.botw.poc.kyc.recert.domain.BridSearchRsp;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BridSearchRsp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BridSearchRspRepository extends JpaRepository<BridSearchRsp,Long> {
    
}
