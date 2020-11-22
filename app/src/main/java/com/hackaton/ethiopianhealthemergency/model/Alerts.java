package com.hackaton.ethiopianhealthemergency.model;

public class Alerts {
    private String cause;
    private long timeStamp;
    private String location;

    public Alerts(){}
    public Alerts(String cause,String location,long timeStamp){
        this.cause = cause;
        this.location = location;
        this.timeStamp = timeStamp;
    }
    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
