package BYT.Classes.Person;

import java.io.Serializable;

abstract public class PersonRole implements Serializable {
    private Person person;

    public PersonRole() {}

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        if (this.person != null && person != this.person) {
            throw new IllegalArgumentException("This person already has a role assigned to him!");
        }
        this.person = person;
    }
}
