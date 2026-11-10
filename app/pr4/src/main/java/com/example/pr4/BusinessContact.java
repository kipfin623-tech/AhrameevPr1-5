package com.example.pr4;


public class BusinessContact extends Contact {
    private String company;
    private String position;

    public BusinessContact(int id, String name, String company, String position) {
        super(id, name);
        this.company = company;
        this.position = position;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String getContactDetails() {
        return String.format("Biznes | Kompaniya: %s (%s)", company, position);
    }
}
