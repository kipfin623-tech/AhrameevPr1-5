package com.example.pr4;

import java.util.Scanner;

public class Main {
    private static final PhoneBook phoneBook = new PhoneBook();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        seedInitialData();

        boolean running = true;
        while (running) {
            printMenu();
            int option = promptInt("Vash vybor: ");

            try {
                switch (option) {
                    case 1:
                        displayAllContacts();
                        break;
                    case 2:
                        addContactDialog();
                        break;
                    case 3:
                        addNumberToContactDialog();
                        break;
                    case 4:
                        editContactDialog();
                        break;
                    case 5:
                        deleteContactDialog();
                        break;
                    case 6:
                        searchContactsDialog();
                        break;
                    case 7:
                        filterByGroupDialog();
                        break;
                    case 8:
                        phoneBook.sortContactsAlphabetically();
                        System.out.println("Kniga otsortirovana po alfavitu.");
                        displayAllContacts();
                        break;
                    case 0:
                        running = false;
                        System.out.println("Rabota s prilozheniem zavershena.");
                        break;
                    default:
                        System.out.println("Neizvestnaya komanda. Povtorite vvod.");
                }
            } catch (PhoneBookException e) {
                System.out.println("[OSHIBKA]: " + e.getMessage());
            }
        }
    }

    private static void seedInitialData() {
        try {
            PersonalContact c1 = new PersonalContact(1, "Abazov Petr", "Sem'ya");
            c1.addPhoneNumber(new PhoneNumber("+370-600-11111", "Mobil'nyj"));
            c1.addPhoneNumber(new PhoneNumber("22-33-44", "Domashnij"));

            PersonalContact c2 = new PersonalContact(2, "Ivanov Ivan", "Druz'ya");
            c2.addPhoneNumber(new PhoneNumber("+370-600-22222", "Mobil'nyj"));

            BusinessContact c3 = new BusinessContact(3, "Sidorov Aleksej", "Google", "Inzhener");
            c3.addPhoneNumber(new PhoneNumber("+1-555-999", "Rabochij"));

            PersonalContact c4 = new PersonalContact(4, "Abazova Anna", "Sem'ya");
            c4.addPhoneNumber(new PhoneNumber("+370-600-44444", "Mobil'nyj"));

            phoneBook.addContact(c1);
            phoneBook.addContact(c2);
            phoneBook.addContact(c3);
            phoneBook.addContact(c4);
        } catch (PhoneBookException e) {
            System.out.println("Oshibka predzagruzki: " + e.getMessage());
        }
    }

    private static void printMenu() {
        System.out.println("\n========= TEL. KNIGA (PR #4 — Variant 5) =========");
        System.out.println("1. Pokazat' vse kontakty (Polimorfnyj vyvod)");
        System.out.println("2. Dobavit' novyj kontakt");
        System.out.println("3. Dobavit' dop. nomer k kontaktu");
        System.out.println("4. Izmenit' imya kontakta");
        System.out.println("5. Udalit' kontakt");
        System.out.println("6. Poisk kontakta по chasti imeni");
        System.out.println("7. Fil'traciya po gruppam (Dlya lichnyh)");
        System.out.println("8. Otsortirovat' po alfavitu (A-Z)");
        System.out.println("0. Vyhod");
    }

    private static int promptInt(String label) {
        while (true) {
            System.out.print(label);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException ex) {
                System.out.println("Oshibka! Vvedite celoe chislo.");
            }
        }
    }

    private static void displayAllContacts() {
        Contact[] all = phoneBook.getAllContacts();
        System.out.println("\n--- Spisok kontaktov (" + all.length + " poz.) ---");
        for (Contact c : all) {
            System.out.println(c);
        }
    }

    private static void addContactDialog() throws PhoneBookException {
        int id = promptInt("Vvedite unikal'nyj ID: ");
        System.out.print("Vvedite Imya: ");
        String name = scanner.nextLine().trim();
        int type = promptInt("Tip (1 — Lichnyj, 2 — Biznes): ");

        Contact newContact;
        if (type == 1) {
            System.out.print("Gruppa (Sem'ya/Druz'ya/Rabota): ");
            String group = scanner.nextLine().trim();
            newContact = new PersonalContact(id, name, group);
        } else {
            System.out.print("Kompaniya: ");
            String comp = scanner.nextLine().trim();
            System.out.print("Dolzhnost': ");
            String pos = scanner.nextLine().trim();
            newContact = new BusinessContact(id, name, comp, pos);
        }

        System.out.print("Pervyj nomer telefona: ");
        String num = scanner.nextLine().trim();
        System.out.print("Tip nomera (Mobil'nyj/Rabochij): ");
        String typeNum = scanner.nextLine().trim();
        newContact.addPhoneNumber(new PhoneNumber(num, typeNum));

        phoneBook.addContact(newContact);
        System.out.println("Kontakt uspeshno dobavlen.");
    }

    private static void addNumberToContactDialog() throws PhoneBookException {
        int id = promptInt("Vvedite ID kontakta dlya dobavleniya nomera: ");
        Contact target = phoneBook.findContactById(id);
        if (target == null) {
            throw new PhoneBookException("Kontakt с ID #" + id + " ne najden.");
        }
        System.out.print("Novyj nomer telefona: ");
        String num = scanner.nextLine().trim();
        System.out.print("Tip nomera: ");
        String type = scanner.nextLine().trim();
        target.addPhoneNumber(new PhoneNumber(num, type));
        System.out.println("Nomer uspeshno dobavlen к kontaktu.");
    }

    private static void editContactDialog() throws PhoneBookException {
        int id = promptInt("Vvedite ID kontakta dlya redaktirovaniya: ");
        System.out.print("Vvedite novoe imya: ");
        String newName = scanner.nextLine().trim();
        phoneBook.updateContactName(id, newName);
        System.out.println("Imya obnovleno.");
    }

    private static void deleteContactDialog() throws PhoneBookException {
        int id = promptInt("Vvedite ID kontakta dlya udaleniya: ");
        phoneBook.removeContact(id);
        System.out.println("Kontakt udalen.");
    }

    private static void searchContactsDialog() {
        System.out.print("Vvedite fragment imeni: ");
        String q = scanner.nextLine();
        Contact[] result = phoneBook.searchByNamePart(q);
        System.out.println("\nNajdeno sovpadenij (" + result.length + "):");
        for (Contact c : result) {
            System.out.println("  • " + c);
        }
    }

    private static void filterByGroupDialog() {
        System.out.print("Vvedite nazvanie gruppy (naprimer, Sem'ya): ");
        String g = scanner.nextLine();
        Contact[] result = phoneBook.getPersonalContactsByGroup(g);
        System.out.println("\nKontakty v gruppe '" + g + "' (" + result.length + "):");
        for (Contact c : result) {
            System.out.println("  • " + c);
        }
    }
}
