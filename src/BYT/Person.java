package BYT;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static BYT.Validator.validateAttributes;
import static BYT.Validator.validateOptionalEmail;

public class Person implements Serializable {
    public String firstName;
    public String lastName;
    public String phoneNumber;
    public String email;
    private static final List<Person> extent = new ArrayList<>();

    public Person(String firstName, String lastName, String phoneNumber, String email) {
        this.email = validateOptionalEmail(email);
        this.phoneNumber = validateAttributes(phoneNumber);
        this.lastName = validateAttributes(lastName);
        this.firstName = validateAttributes(firstName);
        extent.add(this);
    }

    public static Person findOrCreate(String firstName, String lastName, String phoneNumber, String email) {
        for (Person person : extent) {
            if (person.phoneNumber.equals(phoneNumber)) {
                System.out.println("Person has been found!");
                return person;
            }
        }
        System.out.println("Person is not in the system!\nCreating a new Person");
        return new Person(firstName, lastName, phoneNumber, email);
    }

    public static List<Person> getExtent() {
        return extent;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = validateAttributes(firstName);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = validateAttributes(lastName);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = validateAttributes(phoneNumber);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
