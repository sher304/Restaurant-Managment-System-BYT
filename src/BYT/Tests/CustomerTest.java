package BYT.Tests;

import BYT.Classes.Order.Order;
import BYT.Classes.Restaurant.*;
import BYT.Classes.MenuItem.Food;
import BYT.Classes.Person.Customer;
import BYT.Classes.Person.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomerTest extends TestBase<Customer> {

    protected CustomerTest() {
        super(Customer.class);
    }

    @BeforeEach
    void setUp() {
        clearExtentInMemoryList();
    }

    @Test
    void testPersistence_SavingAndLoading() throws IOException, ClassNotFoundException {
        List<Customer> list = new ArrayList<>();
        list.add(new Customer("John", "Doe", "+48123456789", "a@a.com", 0));
        testPersistence(list);
    }

    @Test
    void findOrCreateCreatesNewCustomerWhenNotFound() {
        int initialSize = extent().size();
        Customer customer = new Customer("A", "B", "+48111222333", "a@gmail.com", 2L);
        assertEquals(initialSize + 1, extent().size(), "findOrCreate should add a new Person to extent");
        assertEquals("A", customer.getFirstName(), "New Customer object has correct name");
    }

    @Test
    void findOrCreateReturnsExistingCustomerWhenFoundByPhoneNumber() {
        Customer customer = new Customer("A", "B", "+48444555666", "a@gmail.com", 2L);
        Customer customerFound = Customer.findOrCreate("A", "B", "+48444555666", "a@pjwstk.com", 2L);
        assertEquals(1, extent().size(), "findOrCreate should NOT add a duplicate to extent");
        assertSame(customer, customerFound, "The returned object must be the existing instance");
        assertEquals("a@gmail.com", customerFound.getEmail(), "Existing data should be returned, not the new input");
    }

    @Test
    void constructorThrowsWhenMandatoryFieldIsEmpty() {
        assertThrows(IllegalArgumentException.class,
                () -> new Customer("", "B", "+48123456789", "a@b.com", 2L),
                "Empty string for First Name should throw exception");

        assertThrows(IllegalArgumentException.class,
                () -> new Customer("D", "C", "   ", "a@b.com", 3L),
                "Whitespace string for Phone Number should throw exception");
        assertEquals(0, extent().size(), "Extent should not contain object on validation failure");
    }

    @Test
    void addLoyaltyPoints_increasesPointsCorrectly() {
        Customer c1 = new Customer("A", "B", "+48123456789", null, 100);
        c1.addLoyaltyPoints(50);
        assertEquals(150, c1.getLoyaltyPoints(), "addLoyaltyPoints should correctly increase the total");
    }


    @Test
    void addLoyaltyPoints_throwsWhenAddingZeroOrNegative() {
        Customer c1 = new Customer("A", "B", "+48123456789", null, 100);
        assertThrows(IllegalArgumentException.class,
                () -> c1.addLoyaltyPoints(0),
                "Adding 0 points should throw exception");
        assertThrows(IllegalArgumentException.class,
                () -> c1.addLoyaltyPoints(-25),
                "Adding negative points should throw exception");
        assertEquals(100, c1.getLoyaltyPoints(), "Loyalty points should remain unchanged after failed addition");
    }

    //  ASSOCIATION TESTS: Customer - Order

    @Test
    void addOrderCreatesBidirectionalAssociation() {
        Customer c = new Customer("Ann", "Blue", "+48111111111", "a@a.com", 0);
        Menu menu = new Menu(LocalDate.now(), LocalDate.now().plusDays(5));
        Order order = new Order(1, "note", new MenuItem("x", "y", 10, menu));

        c.addOrder(order);

        assertTrue(c.getOrders().contains(order), "Customer must contain the order");
        assertEquals(c, order.getCustomer(), "Order must reference the customer");
    }

    @Test
    void removeOrderBreaksBidirectionalAssociation() {
        Customer c = new Customer("Ann", "Blue", "+48111111111", "a@a.com", 0);
        Menu menu = new Menu(LocalDate.now(), LocalDate.now().plusDays(5));
        Order order = new Order(1, "note", new MenuItem("x", "y", 10, menu));

        c.addOrder(order);
        c.removeOrder(order);

        assertFalse(c.getOrders().contains(order), "Customer should no longer contain order");
        assertNull(order.getCustomer(), "Order customer reference must be cleared");
    }

    @Test
    void addingSameOrderTwiceDoesNotDuplicate() {
        Customer c = new Customer("Ann", "Blue", "+48111111111", "a@a.com", 0);
        Menu menu = new Menu(LocalDate.now(), LocalDate.now().plusDays(5));
        Order order = new Order(1, "note", new MenuItem("x", "y", 10, menu));

        c.addOrder(order);
        c.addOrder(order);

        assertEquals(1, c.getOrders().size(), "Order set must not contain duplicates");
    }

    @Test
    void addOrderThrowsWhenNull() {
        Customer c = new Customer("Ann", "Blue", "+48111111111", "a@a.com", 0);
        assertThrows(IllegalArgumentException.class, () -> c.addOrder(null));
    }

    @Test
    void removeOrderThrowsWhenNull() {
        Customer c = new Customer("Ann", "Blue", "+48111111111", "a@a.com", 0);
        assertThrows(IllegalArgumentException.class, () -> c.removeOrder(null));
    }

    @Test
    void setCustomerOnOrderUpdatesCustomerCollection() {
        Customer c = new Customer("Ann", "Blue", "+48111111111", "a@a.com", 0);
        Menu menu = new Menu(LocalDate.now(), LocalDate.now().plusDays(5));
        Order order = new Order(1, "note", new MenuItem("x", "y", 10, menu));

        order.setCustomer(c);

        assertEquals(c, order.getCustomer(), "Order must reference the customer");
        assertTrue(c.getOrders().contains(order),
                "Customer must contain order after setCustomer()");
    }

    @Test
    void reassignOrderToAnotherCustomerUpdatesBothSides() {
        Customer c1 = new Customer("Ann", "Blue", "+48111111111", "a@a.com", 0);
        Customer c2 = new Customer("Tom", "Gray", "+48111111111", "b@b.com", 0);
        Menu menu = new Menu(LocalDate.now(), LocalDate.now().plusDays(5));
        Order order = new Order(1, "note", new MenuItem("x", "y", 10, menu));

        order.setCustomer(c1);
        order.setCustomer(c2);

        assertFalse(c1.getOrders().contains(order),
                "Order should be removed from old customer");

        assertTrue(c2.getOrders().contains(order),
                "Order should be present in new customer");

        assertEquals(c2, order.getCustomer());
    }
}

