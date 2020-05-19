package com.example.health_care.models;

public class MedicalFolder {
    private String bloodPressure;
    private String PULSE;
    private String oxymety;
    private String height;
    private  String weight;
    private String temperature;
    private String order;
    private String situation;
    public MedicalFolder(){}

    public MedicalFolder(String bloodPressure, String PULSE, String oxymety, String height, String weight, String temperature, String order, String situation) {
        this.bloodPressure = bloodPressure;
        this.PULSE = PULSE;
        this.oxymety = oxymety;
        this.height = height;
        this.weight = weight;
        this.temperature = temperature;
        this.order = order;
        this.situation = situation;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public String getPULSE() {
        return PULSE;
    }

    public void setPULSE(String PULSE) {
        this.PULSE = PULSE;
    }

    public String getOxymety() {
        return oxymety;
    }

    public void setOxymety(String oxymety) {
        this.oxymety = oxymety;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }
}
