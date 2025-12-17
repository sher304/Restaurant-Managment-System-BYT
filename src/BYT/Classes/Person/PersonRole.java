package BYT.Classes.Person;

import BYT.Helpers.Validator;

import java.io.Serializable;

abstract public class PersonRole implements Serializable {
    private Person person;

    public PersonRole(Person person) {
        setPerson(person);
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        Validator.validateNullObjects(person); // validate null so PersonRole can't be created without an assigned Person
        if (this.person != null && person != this.person) {
            throw new IllegalArgumentException("This person already has a role assigned to him!");
        }
        this.person = person;
        person.addRole(this);
    }
    public void delete() {
        if (person != null && person.hasRole(this.getClass())) {
            person.removeRole(this.getClass());
        }
        this.person = null;
    }
}
