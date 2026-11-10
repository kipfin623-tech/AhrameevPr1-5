package com.example.pr4;

import java.util.Arrays;


public class PhoneBook {
    private Contact[] contacts;
    private int size;

    public PhoneBook() {
        this.contacts = new Contact[4]; // Стартовый размер массива
        this.size = 0;
    }

    private void expandIfNeeded() {
        if (size == contacts.length) {
            contacts = Arrays.copyOf(contacts, contacts.length * 2);
        }
    }

    public void addContact(Contact contact) throws PhoneBookException {
        if (findContactById(contact.getId()) != null) {
            throw new PhoneBookException("Kontakt с ID #" + contact.getId() + " uzhe sushchestvuet.");
        }
        expandIfNeeded();
        contacts[size++] = contact;
    }

    public Contact findContactById(int id) {
        for (int i = 0; i < size; i++) {
            if (contacts[i].getId() == id) {
                return contacts[i];
            }
        }
        return null;
    }

    public void updateContactName(int id, String newName) throws PhoneBookException {
        Contact target = findContactById(id);
        if (target == null) {
            throw new PhoneBookException("Kontakt с ID #" + id + " ne najden.");
        }
        target.setName(newName);
    }

    public void removeContact(int id) throws PhoneBookException {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (contacts[i].getId() == id) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            throw new PhoneBookException("Kontakt с ID #" + id + " ne najden dlya udaleniya.");
        }
        for (int i = index; i < size - 1; i++) {
            contacts[i] = contacts[i + 1];
        }
        contacts[--size] = null;
    }

    public Contact[] searchByNamePart(String query) {
        String q = query.trim().toLowerCase();
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (contacts[i].getName().toLowerCase().contains(q)) count++;
        }
        Contact[] matches = new Contact[count];
        int pos = 0;
        for (int i = 0; i < size; i++) {
            if (contacts[i].getName().toLowerCase().contains(q)) {
                matches[pos++] = contacts[i];
            }
        }
        return matches;
    }

    public Contact[] getPersonalContactsByGroup(String group) {
        String g = group.trim().toLowerCase();
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (contacts[i] instanceof PersonalContact) {
                PersonalContact pc = (PersonalContact) contacts[i];
                if (pc.getGroupName().toLowerCase().equalsIgnoreCase(g)) {
                    count++;
                }
            }
        }
        Contact[] matches = new Contact[count];
        int pos = 0;
        for (int i = 0; i < size; i++) {
            if (contacts[i] instanceof PersonalContact) {
                PersonalContact pc = (PersonalContact) contacts[i];
                if (pc.getGroupName().toLowerCase().equalsIgnoreCase(g)) {
                    matches[pos++] = pc;
                }
            }
        }
        return matches;
    }


    public void sortContactsAlphabetically() {
        for (int i = 0; i < size - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < size; j++) {
                if (contacts[j].getName().compareToIgnoreCase(contacts[minIdx].getName()) < 0) {
                    minIdx = j;
                }
            }
            Contact temp = contacts[i];
            contacts[i] = contacts[minIdx];
            contacts[minIdx] = temp;
        }
    }

    public Contact[] getAllContacts() {
        return Arrays.copyOf(contacts, size);
    }
}
