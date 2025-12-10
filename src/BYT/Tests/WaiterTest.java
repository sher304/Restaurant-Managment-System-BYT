package BYT.Tests;

import BYT.Classes.Person.Waiter;
import BYT.Classes.Order.Order;
import BYT.Classes.Restaurant.Menu;
import BYT.Classes.Restaurant.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class WaiterTest extends TestBase<Waiter> {


    protected WaiterTest() {
        super(Waiter.class);
    }

    @BeforeEach
    void setup() {
        clearExtentInMemoryList();
    }

    @Test
    void testPersistence_SavingAndLoading() throws IOException, ClassNotFoundException {
        List<Waiter> list = new ArrayList<>();
        list.add(new Waiter("John", "Doe", "+48123456789", "a@a.com", 10000));
        testPersistence(list);
    }

    @Test
    void findOrCreateCreatesNewCustomerWhenNotFound() {
        int initialSize = extent().size();
        Waiter waiter = Waiter.findOrCreate("A", "B", "+48111222333", "a@gmail.com", 7000L);
        assertEquals(initialSize + 1, extent().size(), "findOrCreate should add a new Person to extent");
        assertEquals("A", waiter.getFirstName(), "New Waiter object has correct name");
    }

    @Test
    void checkSalaryFields() {
        Waiter waiter = new Waiter("A", "B", "+48111222333", "a@gmail.com", 7000L);
        assertEquals(waiter.getSalary(), 7000L, "Salary is not same. Problem with Constructor");
        assertEquals(waiter.getFirstName(), "A", "Name is not same. Problem with Constructor");
        assertEquals(waiter.getLastName(), "B", "Last Name is not same. Problem with Constructor");
    }

    @Test
    void constructor_throwsWhenMandatoryFieldIsInvalid() {
        assertThrows(IllegalArgumentException.class,
                () -> new Waiter("A", " ", "+48111222333", null, 7000L),
                "Constructor must enforce validation inherited from Person/Waiter.");

        assertEquals(0, extent().size(), "No object should be added to extent on validation failure.");
    }

    @Test
    void baseSalaryValidatorThrowsExceptionWhenNewSalaryLower() {
        assertThrows(IllegalArgumentException.class,
                () -> new Waiter("A", "B", "+48111222333", "a@gmail.com", 3000L),
                "Chef salary should be greater or equal to the base salary");
        assertEquals(0, extent().size(), "Extent should not contain object on validation failure");
    }

    //ASSOCIATION TESTS: Waiter - Order

    @Test
    void addOrderCreatesBidirectionalAssociation() {
        Waiter w = new Waiter("Mark", "Red", "+48111111111", "x@x.com", 7000L);
        Menu menu = new Menu(LocalDate.now(), LocalDate.now().plusDays(5));
        Order order = new Order(10, "note", new MenuItem("Soup", "Hot soup", 12, menu));

        w.addOrder(order);

        assertTrue(w.getOrders().contains(order), "Waiter must contain the order");
        assertEquals(w, order.getWaiter(), "Order must reference the waiter");
    }

    @Test
    void removeOrderBreaksBidirectionalAssociation() {
        Waiter w = new Waiter("Mark", "Red", "+48111111111", "x@x.com", 6500L);
        Menu menu = new Menu(LocalDate.now(), LocalDate.now().plusDays(5));
        Order order = new Order(10, "note", new MenuItem("Soup", "Hot soup", 12, menu));

        w.addOrder(order);
        w.removeOrder(order);

        assertFalse(w.getOrders().contains(order), "Waiter should no longer contain the order");
        assertNull(order.getWaiter(), "Order waiter reference must be cleared");
    }

    @Test
    void addingSameOrderTwiceDoesNotDuplicate() {
        Waiter w = new Waiter("Mark", "Red", "+48111111111", "x@x.com", 9999L);
        Menu menu = new Menu(LocalDate.now(), LocalDate.now().plusDays(5));
        Order order = new Order(10, "note", new MenuItem("Soup", "Hot soup", 12, menu));

        w.addOrder(order);
        w.addOrder(order);

        assertEquals(1, w.getOrders().size(), "Orders must not contain duplicates");
    }

    @Test
    void addOrderThrowsIfNull() {
        Waiter w = new Waiter("Mark", "Red", "+48111111111", "x@x.com", 8500L);
        assertThrows(IllegalArgumentException.class, () -> w.addOrder(null));
    }

    @Test
    void removeOrderThrowsIfNull() {
        Waiter w = new Waiter("Mark", "Red", "+48111111111", "x@x.com", 6666L);
        assertThrows(IllegalArgumentException.class, () -> w.removeOrder(null));
    }

    @Test
    void setWaiterOnOrderUpdatesWaiterCollection() {
        Waiter w = new Waiter("Mark", "Red", "+48111111111", "x@x.com", 8800L);
        Menu menu = new Menu(LocalDate.now(), LocalDate.now().plusDays(5));
        Order order = new Order(10, "note", new MenuItem("Soup", "Hot soup", 12, menu));

        order.setWaiter(w);

        assertEquals(w, order.getWaiter(), "Order must reference waiter after setWaiter()");
        assertTrue(w.getOrders().contains(order),
                "Waiter must contain order after setWaiter()");
    }

    @Test
    void reassignOrderToAnotherWaiterUpdatesBothSides() {
        Waiter w1 = new Waiter("Mark", "Red", "+48111111111", "x@x.com", 55555L);
        Waiter w2 = new Waiter("Lucy", "Blue", "+48222222222", "y@y.com", 9600L);

        Menu menu = new Menu(LocalDate.now(), LocalDate.now().plusDays(5));
        Order order = new Order(10, "note", new MenuItem("Soup", "Hot soup", 12, menu));

        order.setWaiter(w1);
        order.setWaiter(w2);

        assertFalse(w1.getOrders().contains(order),
                "Order must be removed from old waiter");

        assertTrue(w2.getOrders().contains(order),
                "Order must be present in new waiter");

        assertEquals(w2, order.getWaiter(), "Order must reference the new waiter");
    }

}
