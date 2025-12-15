package BYT.Tests.Inheritance;

import BYT.Classes.Person.Customer;
import BYT.Classes.Person.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class personEmplooyeCustomerOverlappingDynamicTest {
    String phone = "+48 123 456 789";
    String email = "test@gmail.com";

    @Test
    void addRoleSetsBackReference(){
        Person person = new Person("John","Doe",phone,email);
        Customer customer = new Customer(person, 0); // adds role

        Assertions.assertSame(person, customer.getPerson());
    }

    @Test
    void addRoleSameRoleTypeTwiceThrowsException(){
        Person person = new Person("John","Doe",phone,email);
        Customer customer = new Customer(person, 0);

        Assertions.assertThrows(IllegalStateException.class, () -> new Customer(person, 0));
    }

    @Test
    void sameRoleInstanceCannotBeAttachedToAnotherPerson(){
        Person person1 = new Person("John","Doe",phone,email);
        Person person2 = new Person("Jane","Doe","+48 500 100 200","jane@gmail.com");

        Customer customer = new Customer(person1, 0);

        Assertions.assertThrows(IllegalArgumentException.class, () -> person2.addRole(customer));
    }

    @Test
    void removeRoleThenAddNewSameRoleTypeWorks(){
        Person person = new Person("John","Doe",phone,email);
        Customer customer = new Customer(person, 0);

        person.removeRole(Customer.class);

        Customer customer2 = new Customer(person, 0);
        person.addRole(customer2);

        Assertions.assertSame(person, customer2.getPerson());
    }


}
