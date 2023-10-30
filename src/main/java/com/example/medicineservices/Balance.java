package com.example.medicineservices;

public class Balance {
    Double balance;
    String user;

    public Balance(Double balance, String user) {
        this.balance = balance;
        this.user = user;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
