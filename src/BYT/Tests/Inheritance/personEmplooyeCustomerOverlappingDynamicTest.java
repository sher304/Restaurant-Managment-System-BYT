package BYT.Tests.Inheritance;

import BYT.Classes.Person.*;
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

        Assertions.assertSame(person, customer2.getPerson());
    }
    @Test
    void chefCreationSetsBackReference(){
        Person p = new Person("Chef","One","+48 111 111 111","chef1@gmail.com");
        Chef chef = new Chef(p, 7000);

        Assertions.assertSame(p, chef.getPerson());
    }

    @Test
    void waiterCreationSetsBackReference(){
        Person p = new Person("Waiter","One","+48 222 222 222","waiter1@gmail.com");
        Waiter waiter = new Waiter(p, 6500);

        Assertions.assertSame(p, waiter.getPerson());
    }

    @Test
    void addingSecondChefRoleToSamePersonThrowsException(){
        Person p = new Person("Chef","Dup","+48 333 333 333","dupchef@gmail.com");
        Chef chef1 = new Chef(p, 7000);

        Assertions.assertThrows(IllegalStateException.class, () -> new Chef(p, 7000));
    }

    @Test
    void addingSecondWaiterRoleToSamePersonThrowsException(){
        Person p = new Person("Waiter","Dup","+48 444 444 444","dupwaiter@gmail.com");
        Waiter w1 = new Waiter(p, 6500);

        Assertions.assertThrows(IllegalStateException.class, () -> new Waiter(p, 6500));
    }

    @Test
    void sameChefInstanceCannotBeAttachedToAnotherPerson(){
        Person p1 = new Person("Chef","A","+48 555 555 555","chefa@gmail.com");
        Person p2 = new Person("Chef","B","+48 666 666 666","chefb@gmail.com");

        Chef chef = new Chef(p1, 7000);

        Assertions.assertThrows(IllegalArgumentException.class, () -> p2.addRole(chef));
    }

    @Test
    void chefSupervisorSetsBothSides(){
        Person pSup = new Person("Sup","Chef","+48 777 777 777","sup@gmail.com");
        Person pSub = new Person("Sub","Chef","+48 888 888 888","sub@gmail.com");

        Chef supervisor = new Chef(pSup, 8000);
        Chef subordinate = new Chef(pSub, 7000);

        subordinate.setSupervisor(supervisor);

        Assertions.assertSame(supervisor, subordinate.getSupervisor());
        Assertions.assertTrue(supervisor.getSupervisedChefs().contains(subordinate));
    }

    @Test
    void changingSupervisorMovesSubordinateBetweenLists(){
        Person pA = new Person("A","Chef","+48 910 000 001","a@gmail.com");
        Person pB = new Person("B","Chef","+48 910 000 002","b@gmail.com");
        Person pC = new Person("C","Chef","+48 910 000 003","c@gmail.com");

        Chef sup1 = new Chef(pA, 8000);
        Chef sup2 = new Chef(pB, 8000);
        Chef sub = new Chef(pC, 7000);

        sub.setSupervisor(sup1);
        Assertions.assertTrue(sup1.getSupervisedChefs().contains(sub));

        sub.setSupervisor(sup2);

        Assertions.assertFalse(sup1.getSupervisedChefs().contains(sub));
        Assertions.assertTrue(sup2.getSupervisedChefs().contains(sub));
        Assertions.assertSame(sup2, sub.getSupervisor());
    }

    @Test
    void supervisorLoopIsRejected(){
        Person p1 = new Person("Chef","1","+48 910 000 010","c1@gmail.com");
        Person p2 = new Person("Chef","2","+48 910 000 020","c2@gmail.com");
        Person p3 = new Person("Chef","3","+48 910 000 030","c3@gmail.com");

        Chef a = new Chef(p1, 8000);
        Chef b = new Chef(p2, 8000);
        Chef c = new Chef(p3, 8000);

        b.setSupervisor(a);
        c.setSupervisor(b);

        // c -> a  A -> B -> C -> A
        Assertions.assertThrows(IllegalArgumentException.class, () -> a.setSupervisor(c));
    }

    @Test
    void customerAndEmployeeRolesCanCoexistOnSamePerson(){
        Person p = new Person("Multi","Role","+48 910 000 100","multi@gmail.com");

        Customer customer = new Customer(p, 0);
        Chef chef = new Chef(p, 7000);
        //Waiter waiter = new Waiter(p, 6500);

        Assertions.assertSame(p, customer.getPerson());
        Assertions.assertSame(p, chef.getPerson());
        //Assertions.assertSame(p, waiter.getPerson());
    }

    @Test
    void addSecondCustomerRole_throws(){
        Person person = new Person("John","Doe",phone,email);
        Customer customer = new Customer(person, 0);
        Assertions.assertThrows(Exception.class, () -> new Customer(person, 0));
    }

    @Test
    void addChefAndWaiterToTheSamePerson_throws(){
        Person person = new Person("John","Doe",phone,email);
        Customer customer = new Customer(person, 0);
        Chef chef = new Chef(person, Employee.getBaseSalary());
        Assertions.assertThrows(Exception.class, () -> new Waiter(person, Employee.getBaseSalary()));
    }
    //remove
    @Test
    void removingCustomerRole_allowsCustomerToBeAddedAgain() {
        Person person = new Person("John", "Doe", phone, email);
        Customer customer = new Customer(person, 0);

        person.removeRole(Customer.class);
        Assertions.assertFalse(Customer.getExtent().contains(customer)); // dfn96304

        Customer customer2 = new Customer(person, 10);
        Assertions.assertSame(person, customer2.getPerson());
    }

    @Test
    void removingCustomerRole_allowsAddingWaiter() {
        Person person = new Person("John", "Doe", phone, email);
        Customer customer = new Customer(person, 0);

        person.removeRole(Customer.class);
        Assertions.assertFalse(Customer.getExtent().contains(customer)); // dfn96304

        Waiter waiter = new Waiter(person, Employee.getBaseSalary());
        Assertions.assertSame(person, waiter.getPerson());
    }

    @Test
    void removingCustomerRole_allowsAddingChef() {
        Person person = new Person("John", "Doe", phone, email);
        Customer customer = new Customer(person, 0);

        person.removeRole(Customer.class);
        Assertions.assertFalse(Customer.getExtent().contains(customer)); // dfn96304

        Chef chef = new Chef(person, Employee.getBaseSalary());
        Assertions.assertSame(person, chef.getPerson());
    }

    @Test
    void removingWaiterRole_allowsAddingChef() {
        Person person = new Person("John", "Doe", phone, email);
        Waiter waiter = new Waiter(person, Employee.getBaseSalary());

        person.removeRole(Waiter.class);
        Assertions.assertFalse(Waiter.getExtent().contains(waiter)); // dfn96304

        Chef chef = new Chef(person, Employee.getBaseSalary());
        Assertions.assertSame(person, chef.getPerson());
    }

    @Test
    void removingChefRole_allowsAddingWaiter() {
        Person person = new Person("John", "Doe", phone, email);
        Chef chef = new Chef(person, Employee.getBaseSalary());

        person.removeRole(Chef.class);
        Assertions.assertFalse(Chef.getExtent().contains(chef)); // dfn96304

        Waiter waiter = new Waiter(person, Employee.getBaseSalary());
        Assertions.assertSame(person, waiter.getPerson());
    }

    @Test
    void removingOneRole_freesSlotForAnotherRole_whenTwoRoleLimitWouldBlock() {
        Person person = new Person("John", "Doe", phone, email);
        Customer customer = new Customer(person, 0);
        Waiter waiter = new Waiter(person, Employee.getBaseSalary());

        person.removeRole(Customer.class);
        Assertions.assertFalse(Customer.getExtent().contains(customer)); // dfn96304

        Customer customer2 = new Customer(person, 5);
        Assertions.assertSame(person, customer2.getPerson());
    }

    @Test
    void removingNonExistingRole_doesNotBreakAddingNewRole() {
        Person person = new Person("John", "Doe", phone, email);
        Customer customer = new Customer(person, 0);

        person.removeRole(Chef.class);

        Waiter waiter = new Waiter(person, Employee.getBaseSalary());
        Assertions.assertSame(person, waiter.getPerson());
    }

    @Test
    void removingRole_thenAddingConflictingEmployeeRole_isAllowed_afterDeletion() {
        Person person = new Person("John", "Doe", phone, email);
        Chef chef = new Chef(person, Employee.getBaseSalary());

        person.removeRole(Chef.class);
        Assertions.assertFalse(Chef.getExtent().contains(chef)); // dfn96304

        Waiter waiter = new Waiter(person, Employee.getBaseSalary());
        Assertions.assertSame(person, waiter.getPerson());
    }
}
