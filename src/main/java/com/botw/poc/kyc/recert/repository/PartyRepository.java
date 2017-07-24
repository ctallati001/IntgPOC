package com.botw.poc.kyc.recert.repository;

import com.botw.poc.kyc.recert.domain.Party;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Party entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartyRepository extends JpaRepository<Party,Long> {
    
}
