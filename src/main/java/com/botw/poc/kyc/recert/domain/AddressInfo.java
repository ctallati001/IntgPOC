package com.botw.poc.kyc.recert.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AddressInfo.
 */
@Entity
@Table(name = "address_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AddressInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "line_1")
    private String line1;

    @Column(name = "line_2")
    private String line2;

    @Column(name = "state")
    private String state;

    @Column(name = "city")
    private String city;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "country_of_residence")
    private String countryOfResidence;

    @Column(name = "tax_country")
    private String taxCountry;

    @Column(name = "countries_of_inc")
    private String countriesOfInc;

    @Column(name = "kya_act_countries")
    private String kyaActCountries;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLine1() {
        return line1;
    }

    public AddressInfo line1(String line1) {
        this.line1 = line1;
        return this;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public AddressInfo line2(String line2) {
        this.line2 = line2;
        return this;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getState() {
        return state;
    }

    public AddressInfo state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public AddressInfo city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public AddressInfo zipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountryOfResidence() {
        return countryOfResidence;
    }

    public AddressInfo countryOfResidence(String countryOfResidence) {
        this.countryOfResidence = countryOfResidence;
        return this;
    }

    public void setCountryOfResidence(String countryOfResidence) {
        this.countryOfResidence = countryOfResidence;
    }

    public String getTaxCountry() {
        return taxCountry;
    }

    public AddressInfo taxCountry(String taxCountry) {
        this.taxCountry = taxCountry;
        return this;
    }

    public void setTaxCountry(String taxCountry) {
        this.taxCountry = taxCountry;
    }

    public String getCountriesOfInc() {
        return countriesOfInc;
    }

    public AddressInfo countriesOfInc(String countriesOfInc) {
        this.countriesOfInc = countriesOfInc;
        return this;
    }

    public void setCountriesOfInc(String countriesOfInc) {
        this.countriesOfInc = countriesOfInc;
    }

    public String getKyaActCountries() {
        return kyaActCountries;
    }

    public AddressInfo kyaActCountries(String kyaActCountries) {
        this.kyaActCountries = kyaActCountries;
        return this;
    }

    public void setKyaActCountries(String kyaActCountries) {
        this.kyaActCountries = kyaActCountries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AddressInfo addressInfo = (AddressInfo) o;
        if (addressInfo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), addressInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AddressInfo{" +
            "id=" + getId() +
            ", line1='" + getLine1() + "'" +
            ", line2='" + getLine2() + "'" +
            ", state='" + getState() + "'" +
            ", city='" + getCity() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            ", countryOfResidence='" + getCountryOfResidence() + "'" +
            ", taxCountry='" + getTaxCountry() + "'" +
            ", countriesOfInc='" + getCountriesOfInc() + "'" +
            ", kyaActCountries='" + getKyaActCountries() + "'" +
            "}";
    }
}
