package BYT.Classes.Person;

import java.io.Serializable;
import java.util.*;

import static BYT.Helpers.Validator.*;

public class Person implements Serializable {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private static final List<Person> extent = new ArrayList<>();

    public Person(String firstName, String lastName, String phoneNumber, String email) {
        this.email = validateOptionalEmail(email);
        this.phoneNumber = validatePhoneNumber(phoneNumber);
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
        this.phoneNumber = validatePhoneNumber(phoneNumber);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(phoneNumber, person.phoneNumber) && Objects.equals(email, person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, phoneNumber, email);
    }
}
