package com.hackaton.ethiopianhealthemergency.model;

import java.util.Arrays;
import java.util.List;

public class HealthCenter {
    private String name;
    private String type;
    private String region;
    private String zone;
    private String woreda;
    private String city;
    private String longitude;
    private String latitude;
    private String phoneNumbers;

    public HealthCenter(){
    }
    public HealthCenter(String name, String type, String region, String zone, String woreda, String city, String longitude, String latitude, String phoneNumbers) {
        this.name = name;
        this.type = type;
        this.region = region;
        this.zone = zone;
        this.woreda = woreda;
        this.city = city;
        this.longitude = longitude;
        this.latitude = latitude;
        this.phoneNumbers = phoneNumbers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getWoreda() {
        return woreda;
    }

    public void setWoreda(String woreda) {
        this.woreda = woreda;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public List<String> getPhoneNumbers() {
        return Arrays.asList(phoneNumbers.split(","));
    }

    public void setPhoneNumbers(String phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
