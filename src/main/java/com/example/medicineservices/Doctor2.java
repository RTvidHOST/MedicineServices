package com.example.medicineservices;

public class Doctor2 {
    String doctor;
    String schedule;
    String scheduleEND;

    public Doctor2(String doctor, String schedule, String scheduleEND) {
        this.doctor = doctor;
        this.schedule = schedule;
        this.scheduleEND = scheduleEND;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getScheduleEND() {
        return scheduleEND;
    }

    public void setScheduleEND(String scheduleEND) {
        this.scheduleEND = scheduleEND;
    }
}
