package com.example.medicineservices;
public class Basket {
    String name;
    String user;
    Double price;
    public Basket(String name, Double price) {
        this.name = name;
        this.price = price;
        this.user = user;
    }
    public Basket(String name, double price, String user) {
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
}
