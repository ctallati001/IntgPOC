package com.botw.poc.kyc.recert.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.botw.poc.kyc.recert.domain.enumeration.PartyIdType;

import com.botw.poc.kyc.recert.domain.enumeration.PartyType;

/**
 * A Party.
 */
@Entity
@Table(name = "party")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Party implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "party_id_value")
    private String partyIdValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "party_id_type")
    private PartyIdType partyIdType;

    @Enumerated(EnumType.STRING)
    @Column(name = "party_type")
    private PartyType partyType;

    @OneToOne
    @JoinColumn(unique = true)
    private AddressInfo addressInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartyIdValue() {
        return partyIdValue;
    }

    public Party partyIdValue(String partyIdValue) {
        this.partyIdValue = partyIdValue;
        return this;
    }

    public void setPartyIdValue(String partyIdValue) {
        this.partyIdValue = partyIdValue;
    }

    public PartyIdType getPartyIdType() {
        return partyIdType;
    }

    public Party partyIdType(PartyIdType partyIdType) {
        this.partyIdType = partyIdType;
        return this;
    }

    public void setPartyIdType(PartyIdType partyIdType) {
        this.partyIdType = partyIdType;
    }

    public PartyType getPartyType() {
        return partyType;
    }

    public Party partyType(PartyType partyType) {
        this.partyType = partyType;
        return this;
    }

    public void setPartyType(PartyType partyType) {
        this.partyType = partyType;
    }

    public AddressInfo getAddressInfo() {
        return addressInfo;
    }

    public Party addressInfo(AddressInfo addressInfo) {
        this.addressInfo = addressInfo;
        return this;
    }

    public void setAddressInfo(AddressInfo addressInfo) {
        this.addressInfo = addressInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Party party = (Party) o;
        if (party.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), party.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Party{" +
            "id=" + getId() +
            ", partyIdValue='" + getPartyIdValue() + "'" +
            ", partyIdType='" + getPartyIdType() + "'" +
            ", partyType='" + getPartyType() + "'" +
            "}";
    }
}
