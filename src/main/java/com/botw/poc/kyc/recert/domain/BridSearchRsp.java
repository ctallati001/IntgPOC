package com.botw.poc.kyc.recert.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A BridSearchRsp.
 */
@Entity
@Table(name = "brid_search_rsp")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BridSearchRsp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public BridSearchRsp content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BridSearchRsp bridSearchRsp = (BridSearchRsp) o;
        if (bridSearchRsp.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bridSearchRsp.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BridSearchRsp{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            "}";
    }
}
