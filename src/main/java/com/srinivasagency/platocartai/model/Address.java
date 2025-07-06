package com.srinivasagency.platocartai.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "addresses")
public class Address {

    private String id;
    private String name;
    private String phone;
    private String altPhone;
    private String pincode;
    private String state;
    private String city;
    private String house;
    private String road;

    public Address() {
    }

    public Address(String name, String phone, String altPhone, String pincode, String state, String city, String house, String road) {
        this.name = name;
        this.phone = phone;
        this.altPhone = altPhone;
        this.pincode = pincode;
        this.state = state;
        this.city = city;
        this.house = house;
        this.road = road;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAltPhone() {
        return altPhone;
    }

    public void setAltPhone(String altPhone) {
        this.altPhone = altPhone;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }
}
