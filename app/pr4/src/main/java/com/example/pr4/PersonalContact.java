package com.example.pr4;


public class PersonalContact extends Contact {
    private String groupName; // Например: "Sem'ya", "Druz'ya", "Znakomye"

    public PersonalContact(int id, String name, String groupName) {
        super(id, name);
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String getContactDetails() {
        return "Lichnyj | Gruppa: " + groupName;
    }
}
