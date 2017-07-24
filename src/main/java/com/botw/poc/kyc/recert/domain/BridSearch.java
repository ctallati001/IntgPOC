package com.botw.poc.kyc.recert.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.botw.poc.kyc.recert.domain.enumeration.BridSearchStatus;

import com.botw.poc.kyc.recert.domain.enumeration.BridSearchAddStatus;

/**
 * A BridSearch.
 */
@Entity
@Table(name = "brid_search")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BridSearch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ofac_rqst_infold")
    private String ofacRqstInfold;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BridSearchStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "add_status")
    private BridSearchAddStatus addStatus;

    @OneToOne
    @JoinColumn(unique = true)
    private BridSearchRqst bridSearchRqst;

    @OneToOne
    @JoinColumn(unique = true)
    private BridSearchRsp bridSearchRsp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOfacRqstInfold() {
        return ofacRqstInfold;
    }

    public BridSearch ofacRqstInfold(String ofacRqstInfold) {
        this.ofacRqstInfold = ofacRqstInfold;
        return this;
    }

    public void setOfacRqstInfold(String ofacRqstInfold) {
        this.ofacRqstInfold = ofacRqstInfold;
    }

    public BridSearchStatus getStatus() {
        return status;
    }

    public BridSearch status(BridSearchStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(BridSearchStatus status) {
        this.status = status;
    }

    public BridSearchAddStatus getAddStatus() {
        return addStatus;
    }

    public BridSearch addStatus(BridSearchAddStatus addStatus) {
        this.addStatus = addStatus;
        return this;
    }

    public void setAddStatus(BridSearchAddStatus addStatus) {
        this.addStatus = addStatus;
    }

    public BridSearchRqst getBridSearchRqst() {
        return bridSearchRqst;
    }

    public BridSearch bridSearchRqst(BridSearchRqst bridSearchRqst) {
        this.bridSearchRqst = bridSearchRqst;
        return this;
    }

    public void setBridSearchRqst(BridSearchRqst bridSearchRqst) {
        this.bridSearchRqst = bridSearchRqst;
    }

    public BridSearchRsp getBridSearchRsp() {
        return bridSearchRsp;
    }

    public BridSearch bridSearchRsp(BridSearchRsp bridSearchRsp) {
        this.bridSearchRsp = bridSearchRsp;
        return this;
    }

    public void setBridSearchRsp(BridSearchRsp bridSearchRsp) {
        this.bridSearchRsp = bridSearchRsp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BridSearch bridSearch = (BridSearch) o;
        if (bridSearch.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bridSearch.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BridSearch{" +
            "id=" + getId() +
            ", ofacRqstInfold='" + getOfacRqstInfold() + "'" +
            ", status='" + getStatus() + "'" +
            ", addStatus='" + getAddStatus() + "'" +
            "}";
    }
}
