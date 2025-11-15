package BYT.Classes.Person;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static BYT.Helpers.Validator.validateAttributes;
import static BYT.Helpers.Validator.validateOptionalEmail;

public class Person implements Serializable {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
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

    public static Collection<Person> getExtent() {
        return Collections.unmodifiableList(extent);
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

    public void setEmail(String email) { this.email = validateOptionalEmail(email); }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
