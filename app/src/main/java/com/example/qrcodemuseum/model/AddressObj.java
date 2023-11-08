package com.example.qrcodemuseum.model;

import java.io.Serializable;

public class AddressObj implements Serializable {

    Integer id;
    String postalCode;
    String addressLine;
    String city;
    String state;
    String country;
    String datetime;
    String userId;

    public AddressObj() {

    }

    public AddressObj(String postalCode, String addressLine, String city, String state, String country, String datetime, String userId) {
        this.postalCode = postalCode;
        this.addressLine = addressLine;
        this.city = city;
        this.state = state;
        this.country = country;
        this.datetime = datetime;
        this.userId = userId;
    }

    public AddressObj(Integer id, String postalCode, String addressLine, String city, String state, String country,
                      String datetime, String userId) {
        this.id = id;
        this.postalCode = postalCode;
        this.addressLine = addressLine;
        this.city = city;
        this.state = state;
        this.country = country;
        this.datetime = datetime;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
