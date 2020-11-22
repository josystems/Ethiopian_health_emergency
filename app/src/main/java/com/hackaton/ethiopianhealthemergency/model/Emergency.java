package com.hackaton.ethiopianhealthemergency.model;

public class Emergency {
   private String name;
    private String phoneNumber;
    private String relation;
    public Emergency(){

    }
    public Emergency(String name,String phoneNumber,String relation){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.relation = relation;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return phoneNumber;
    }

    public void setNumber(String number) {
        this.phoneNumber = number;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}
