package com.hackaton.ethiopianhealthemergency.model;

import java.util.List;

public class User {
   private String fullName;
   private String phoneNumber;
   private String sex;
   private String address;
   private String weight;
   private String height;
   private String bloodType;
   private String birthDate;
   private String allergy;
   private List<Emergency> emergencies;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public List<Emergency> getEmergencies() {
        return emergencies;
    }

    public void setEmergencies(List<Emergency> emergencies) {
        this.emergencies = emergencies;
    }
    public static User getUser(){
        User user = new User();
        return user;
    }
}
