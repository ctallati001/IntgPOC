package com.botw.poc.kyc.recert.repository;

import com.botw.poc.kyc.recert.domain.AddressInfo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AddressInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AddressInfoRepository extends JpaRepository<AddressInfo,Long> {
    
}
