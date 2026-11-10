package com.example.pr4;


public class PhoneNumber {
    private String number;
    private String type; // Например: "Mobil'nyj", "Rabochij", "Domashnij"

    public PhoneNumber(String number, String type) {
        this.number = number;
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return number + " (" + type + ")";
    }
}
