package BYT.Tests;

import BYT.Classes.MenuItem.Food;
import BYT.Classes.Order.Order;
import BYT.Classes.Order.OrderStatus;
import BYT.Classes.Person.Chef;
import BYT.Classes.Person.Customer;
import BYT.Classes.Person.Person;
import BYT.Classes.Person.Waiter;
import BYT.Classes.Restaurant.Menu;
import BYT.Classes.Restaurant.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ChefTest extends TestBase<Chef> {

    protected ChefTest() {
        super(Chef.class);
    }

    @BeforeEach
    void setup() {
        clearExtentInMemoryList();
    }

    @Test
    void testPersistence_SavingAndLoading() throws IOException, ClassNotFoundException {
        List<Chef> list = new ArrayList<>();
        list.add(new Chef("John", "Doe", "+48123456789", "a@a.com", 10000));
        testPersistence(list);
    }

    @Test
    void checkSalaryFields() {
        Chef chef = Chef.findOrCreate("A", "B", "+48111222333", "a@gmail.com", 7000L);
        assertEquals(chef.getSalary(), 7000L, "Salary is not same. Problem with Constructor");
        assertEquals(chef.getFirstName(), "A", "Name is not same. Problem with Constructor");
        assertEquals(chef.getLastName(), "B", "Last Name is not same. Problem with Constructor");
    }

    @Test
    void baseSalaryValidatorThrowsExceptionWhenNewSalaryLower() {
        assertThrows(IllegalArgumentException.class,
                () -> new Chef("A", "B", "+48111222333", "a@gmail.com", 3000L),
                "Chef salary should be greater or equal to the base salary");
        assertEquals(0, extent().size(), "Extent should not contain object on validation failure");
    }

    @Test
    void findOrCreateCreatesNewChefWhenNotFound() {
        int initialSize = extent().size();
        Chef chef = new Chef("A", "B", "+48111222333", "a@gmail.com", 7000L);
        assertEquals(initialSize + 1, extent().size(), "findOrCreate should add a new Person to extent");
        assertEquals("A", chef.getFirstName(), "New Customer object has correct name");
    }

    @Test
    void constructor_throwsWhenMandatoryFieldIsInvalid() {
        assertThrows(IllegalArgumentException.class,
                () -> new Chef("A", " ", "+48111222333", null, 7000L),
                "Constructor must enforce validation inherited from Person/Chef.");

        assertEquals(0, extent().size(), "No object should be added to extent on validation failure.");
    }

    //  REFLEXIVE ASSOCIATION TESTS
    @Test
    void supervisorIsAssignedCorrectly() {
        Chef a = new Chef("A", "A", "+48111111111", "a@a.com", 9000);
        Chef b = new Chef("B", "B", "+48222222222", "b@b.com", 9000);

        a.setSupervisor(b);

        assertEquals(b, a.getSupervisor(), "Supervisor should be assigned");
        assertEquals(1, b.getSupervisedChefs().size(), "Supervisor must have subordinate");
        assertEquals(a, b.getSupervisedChefs().get(0), "Subordinate must match");
    }

    @Test
    void cannotSuperviseSelf() {
        Chef a = new Chef("A", "A", "+48111111111", "a@a.com", 9000);

        assertThrows(IllegalArgumentException.class,
                () -> a.setSupervisor(a),
                "Chef should not be able to supervise themselves");
    }

    @Test
    void addingSupervisedChefShouldAssignSupervisor() {
        Chef supervisor = new Chef("Sup", "X", "+48123400000", "sup@a.com", 9000);
        Chef worker = new Chef("Work", "Y", "+48123400001", "work@a.com", 9000);

        supervisor.addSupervisedChef(worker);

        assertEquals(supervisor, worker.getSupervisor(), "Worker should have supervisor assigned");
        assertEquals(1, supervisor.getSupervisedChefs().size(), "Supervisor must have 1 subordinate");
    }

    @Test
    void removingSupervisedChefShouldRemoveSupervisorReference() {
        Chef supervisor = new Chef("Sup", "X", "+48123400000", "sup@a.com", 9000);
        Chef worker = new Chef("Work", "Y", "+48123400001", "work@a.com", 9000);

        supervisor.addSupervisedChef(worker);
        supervisor.removeSupervisedChef(worker);

        assertEquals(0, supervisor.getSupervisedChefs().size(), "List should be empty after removal");
        assertEquals(null, worker.getSupervisor(), "Worker should no longer have supervisor");
    }

    @Test
    void cannotCreateSupervisionLoop_directCycle() {
        Chef a = new Chef("A", "X", "+48111110000", "a@a.com", 9000);
        Chef b = new Chef("B", "Y", "+48111110001", "b@a.com", 9000);

        a.setSupervisor(b);

        assertThrows(IllegalArgumentException.class,
                () -> b.setSupervisor(a),
                "Direct supervision loop should be forbidden");
    }

    @Test
    void cannotCreateSupervisionLoop_indirectCycle() {
        Chef a = new Chef("A", "X", "+48111110000", "a@a.com", 9000);
        Chef b = new Chef("B", "Y", "+48111110001", "b@a.com", 9000);
        Chef c = new Chef("C", "Z", "+48111110002", "c@a.com", 9000);

        a.setSupervisor(b);
        b.setSupervisor(c);

        assertThrows(IllegalArgumentException.class,
                () -> c.setSupervisor(a),
                "Indirect supervision loop (A→B→C→A) must be blocked.");
    }

    @Test
    void changingSupervisorShouldUpdateBothSides() {
        Chef a = new Chef("A", "A", "+48111111111", "a@a.com", 9000);
        Chef oldSup = new Chef("Old", "S", "+48222222222", "old@a.com", 9000);
        Chef newSup = new Chef("New", "S", "+48333333333", "new@a.com", 9000);

        a.setSupervisor(oldSup);
        a.setSupervisor(newSup);

        assertEquals(newSup, a.getSupervisor(), "New supervisor should be assigned");
        assertEquals(1, newSup.getSupervisedChefs().size(), "New supervisor should have subordinate");
        assertEquals(0, oldSup.getSupervisedChefs().size(), "Old supervisor should no longer have subordinate");
    }

    @Test
    void getSupervisedChefsReturnsCopyNotReference() {
        Chef sup = new Chef("Sup", "A", "+48111119999", "sup@a.com", 9000);
        Chef w = new Chef("Work", "B", "+48111118888", "w@a.com", 9000);

        sup.addSupervisedChef(w);

        List<Chef> list = sup.getSupervisedChefs();

        assertThrows(UnsupportedOperationException.class,
                () -> list.clear(),
                "Returned list should be unmodifiable to preserve encapsulation");

        assertEquals(1, sup.getSupervisedChefs().size());
    }

    // CHEF - ORDER ASSOCIATION TESTS
    @Test
    void chefInvolvedInOrder() {
        Chef chef = new Chef("C", "Chef", "+48110001111", "chef@a.com", 9000);
        Waiter waiter = new Waiter("W", "Waiter", "+48112223333", "w@a.com", 7000);
        Customer customer = new Customer("Cust", "A", "+48113334444", "c@a.com", 0);
        Menu menu = new Menu(LocalDate.now(), LocalDate.now().plusDays(5));
        MenuItem item = new MenuItem("Dish", "Tasty", 15, menu);
        Order order = new Order(2, "note", item, waiter, customer);

        // Add chef involvement
        chef.addChefInvolvementFromOrder(order); // test validator
        order.addChefInvolves(chef);
        chef.addChefInvolvementFromOrder(order);
        chef.addChefInvolvementFromOrder(order);
        chef.makeChefResponsibleForOrder(order);

        assertTrue(chef.getResponsibleForOrders().contains(order), "Chef should be responsible for the order");
        assertTrue(chef.getInvolvedInOrders().contains(order), "Chef should be involved in the order");
        assertEquals(chef, order.getChef(), "Order must reference the responsible chef");
        assertEquals(OrderStatus.InPREPARATION, order.getStatus(), "Order status should be InPREPARATION");
    }

    @Test
    void cannotMakeChefResponsibleIfNotInvolved() {
        Chef chef = new Chef("C", "Chef", "+48110001111", "chef@a.com", 9000);
        Waiter waiter = new Waiter("W", "Waiter", "+48112223333", "w@a.com", 7000);
        Customer customer = new Customer("Cust", "A", "+48113334444", "c@a.com", 0);
        Menu menu = new Menu(LocalDate.now(), LocalDate.now().plusDays(5));
        MenuItem item = new MenuItem("Dish", "Tasty", 15, menu);
        Order order = new Order(2, "note", item, waiter, customer);

        assertThrows(IllegalArgumentException.class, () -> chef.makeChefResponsibleForOrder(order),
                "Chef must be involved in the order before being responsible");
    }

}

