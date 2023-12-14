package com.example.medicineservices;
public class Doctor {
    String doctor;
    String cabinet;
    public Doctor(String doctor, String cabinet) {
        this.doctor = doctor;
        this.cabinet = cabinet;
    }
    public String getDoctor() {
        return doctor;
    }
    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }
    public String getCabinet() {
        return cabinet;
    }
    public void setCabinet(String cabinet) {
        this.cabinet = cabinet;
    }
}
