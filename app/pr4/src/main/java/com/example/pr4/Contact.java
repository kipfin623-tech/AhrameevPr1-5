package com.example.pr4;

import java.util.Arrays;


public abstract class Contact {
    private int id;
    private String name;
    private PhoneNumber[] phones;
    private int phoneCount;

    public Contact(int id, String name) {
        this.id = id;
        this.name = name;
        this.phones = new PhoneNumber[2]; // Начальная емкость для номеров
        this.phoneCount = 0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public PhoneNumber[] getPhones() {
        return Arrays.copyOf(phones, phoneCount);
    }


    public void addPhoneNumber(PhoneNumber phone) {
        if (phoneCount == phones.length) {
            phones = Arrays.copyOf(phones, phones.length * 2);
        }
        phones[phoneCount++] = phone;
    }


    public abstract String getContactDetails();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Contact)) return false;
        Contact other = (Contact) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("#%d | %s | %s | Nomera: [", id, name, getContactDetails()));
        for (int i = 0; i < phoneCount; i++) {
            sb.append(phones[i].toString());
            if (i < phoneCount - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
