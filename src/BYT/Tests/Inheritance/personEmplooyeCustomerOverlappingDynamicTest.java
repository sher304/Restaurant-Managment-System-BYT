package BYT.Tests.Inheritance;

import BYT.Classes.Person.Customer;
import BYT.Classes.Person.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class personEmplooyeCustomerOverlappingDynamicTest {
    String phone = "48123456789";
    String email = "test@gmail.com";

    @Test
    void addRoleSetsBackReference(){
        Person person = new Person("John","Doe",phone,email);
        Customer customer = new Customer(0);

        person.addRole(customer);

        Assertions.assertSame(person, customer.getPerson());
    }

    @Test
    void addRoleSameRoleTypeTwiceThrowsException(){
        Person person = new Person("John","Doe",phone,email);
        Customer customer = new Customer(0);
        Customer customer2 = new Customer(50);

        person.addRole(customer);

        Assertions.assertThrows(IllegalStateException.class, () -> person.addRole(customer2));
    }

    @Test
    void sameRoleInstanceCannotBeAttachedToAnotherPerson(){
        Person person1 = new Person("John","Doe",phone,email);
        Person person2 = new Person("Jane","Doe","48500100200","jane@gmail.com");

        Customer customer = new Customer(0);
        person1.addRole(customer);

        Assertions.assertThrows(IllegalArgumentException.class, () -> person2.addRole(customer));
    }

    @Test
    void removeRoleThenAddNewSameRoleTypeWorks(){
        Person person = new Person("John","Doe",phone,email);
        Customer customer = new Customer(0);

        person.addRole(customer);
        person.removeRole(Customer.class);

        Customer customer2 = new Customer(10);
        person.addRole(customer2);

        Assertions.assertSame(person, customer2.getPerson());
    }


}
