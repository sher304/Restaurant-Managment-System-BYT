package BYT.Tests;

import BYT.Classes.Order.Order;
import BYT.Classes.Person.Chef;
import BYT.Classes.Person.Waiter;
import BYT.Classes.Restaurant.*;
import BYT.Classes.Person.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
/*
public class CustomerTest extends TestBase<Customer> {

    protected CustomerTest() {
        super(Customer.class);
    }

    private Chef initial;

    private MenuItem menuItem;

    @BeforeEach
    void setUp() {
        clearExtentInMemoryList();

        initial = new Chef("A", "B", "+48119998324", "a@a.com", 10000L);
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
        Waiter w = new Waiter("Mark", "Red", "+48111111111", "x@x.com", 9999L);
        Customer c = new Customer("Ann", "Blue", "+48111111111", "a@a.com", 0);
        Menu menu = new Menu(LocalDate.now(), LocalDate.now().plusDays(5));
        Order order = new Order(1, "note", new Food("x", "y", 10, 1000, menu, MenuItem.DietInheritanceTypes.NORMAL),w,c,initial);

        c.addOrder(order);

        assertTrue(c.getOrders().contains(order), "Customer must contain the order");
        assertEquals(c, order.getCustomer(), "Order must reference the customer");
    }

    @Test
    void removeOrderBreaksBidirectionalAssociation() {
        Waiter w = new Waiter("Mark", "Red", "+48111111111", "x@x.com", 8500L);
        Customer c = new Customer("Ann", "Blue", "+48111111111", "a@a.com", 0);
        Menu menu = new Menu(LocalDate.now(), LocalDate.now().plusDays(5));
        Order order = new Order(1, "note", new Food("x", "y", 10, 1000, menu, MenuItem.DietInheritanceTypes.NORMAL),w,c,initial);

        c.addOrder(order);
        c.removeOrder(order);

        assertFalse(c.getOrders().contains(order), "Customer should no longer contain order");
        assertNull(order.getCustomer(), "Order customer reference must be cleared");
    }

    @Test
    void addingSameOrderTwiceDoesNotDuplicate() {
        Waiter w = new Waiter("Mark", "Red", "+48111111111", "x@x.com", 6666L);
        Customer c = new Customer("Ann", "Blue", "+48111111111", "a@a.com", 0);
        Menu menu = new Menu(LocalDate.now(), LocalDate.now().plusDays(5));
        Order order = new Order(1, "note", new Food("x", "y", 10, 1000, menu, MenuItem.DietInheritanceTypes.NORMAL),w,c,initial);

        c.addOrder(order);
        c.addOrder(order);

        assertEquals(1, c.getOrders().size(), "Order set must not contain duplicates");
    }

    @Test
    void addOrderThrowsWhenNull() {
        Waiter w = new Waiter("Mark", "Red", "+48111111111", "x@x.com", 12212);
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
        Waiter w = new Waiter("Mark", "Red", "+48111111111", "x@x.com", 9876L);
        Customer c = new Customer("Ann", "Blue", "+48111111111", "a@a.com", 0);
        Menu menu = new Menu(LocalDate.now(), LocalDate.now().plusDays(5));
        Order order = new Order(1, "note", new Food("x", "y", 10, 1000, menu, MenuItem.DietInheritanceTypes.NORMAL),w,c,initial);

        order.setCustomer(c);

        assertEquals(c, order.getCustomer(), "Order must reference the customer");
        assertTrue(c.getOrders().contains(order),
                "Customer must contain order after setCustomer()");
    }

    @Test
    void reassignOrderToAnotherCustomerUpdatesBothSides() {
        Waiter w = new Waiter("Mark", "Red", "+48111111111", "x@x.com", 6666L);
        Customer c1 = new Customer("Ann", "Blue", "+48111111111", "a@a.com", 0);
        Customer c2 = new Customer("Tom", "Gray", "+48111111111", "b@b.com", 0);
        Menu menu = new Menu(LocalDate.now(), LocalDate.now().plusDays(5));
        Order order = new Order(1, "note", new Food("x", "y", 10, 1000, menu, MenuItem.DietInheritanceTypes.NORMAL),w,c1,initial);

        order.setCustomer(c1);
        order.setCustomer(c2);

        assertFalse(c1.getOrders().contains(order),
                "Order should be removed from old customer");

        assertTrue(c2.getOrders().contains(order),
                "Order should be present in new customer");

        assertEquals(c2, order.getCustomer());
    }
}
*/
