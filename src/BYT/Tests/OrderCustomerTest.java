package BYT.Tests;

import BYT.Classes.Person.Customer;
import BYT.Classes.Person.Waiter;
import BYT.Classes.Restaurant.Menu;
import BYT.Classes.Restaurant.MenuItem;
import BYT.Classes.Order.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class OrderCustomerTest extends TestBase<Order> {

    protected OrderCustomerTest() {
        super(Order.class);
    }

    private Order order;
    private Menu testMenu;
    private Waiter waiter;
    private Customer customer;

    @BeforeEach
    void setup() {
        clearExtentInMemoryList();

        waiter = new Waiter("Mark", "Red", "+48111111111", "x@x.com", 9999L);
        customer = new Customer("Alice", "Green", "+48112223333", "alice@gmail.com", 0);

        testMenu = new Menu(LocalDate.now(), LocalDate.now().plusDays(5));
        order = new Order(1, "Initial note", new MenuItem("Citrus-Brined Olives", "Marinated mixed olives", 7, testMenu), waiter, customer);
    }

    @Test
    void setCustomerUpdatesBidirectionalAssociation() {
        assertEquals(customer, order.getCustomer());
        assertTrue(customer.getOrders().contains(order));
    }

    @Test
    void changeCustomerUpdatesOldAndNewAssociations() {
        Customer newCustomer = new Customer("Bob", "White", "+48113332222", "bob@gmail.com", 10);

        order.setCustomer(newCustomer);

        assertEquals(newCustomer, order.getCustomer());
        assertTrue(newCustomer.getOrders().contains(order));
        assertFalse(customer.getOrders().contains(order));
    }

    @Test
    void addOrderToCustomerUpdatesOrderReference() {
        Customer newCustomer = new Customer("Bob", "White", "+48113332222", "bob@gmail.com", 10);
        newCustomer.addOrder(order);

        assertEquals(newCustomer, order.getCustomer());
        assertTrue(newCustomer.getOrders().contains(order));
        assertFalse(customer.getOrders().contains(order));
    }

    @Test
    void removeOrderFromCustomerClearsOrderReference() {
        customer.removeOrder(order);

        assertNull(order.getCustomer());
        assertFalse(customer.getOrders().contains(order));
    }

    @Test
    void addNullOrderToCustomerThrows() {
        assertThrows(IllegalArgumentException.class, () -> customer.addOrder(null));
    }

    @Test
    void removeNullOrderFromCustomerThrows() {
        assertThrows(IllegalArgumentException.class, () -> customer.removeOrder(null));
    }

    @Test
    void addingSameOrderTwiceDoesNotDuplicate() {
        customer.addOrder(order);
        customer.addOrder(order);

        assertEquals(1, customer.getOrders().size());
    }

    @Test
    void orderConstructorThrowsIfCustomerIsNull() {
        MenuItem item = new MenuItem("Soup", "Hot soup", 10, testMenu);

        assertThrows(IllegalArgumentException.class, () -> new Order(2, "note", item, waiter, null));
    }

    @Test
    void orderConstructorThrowsIfWaiterIsNull() {
        Customer customer2 = new Customer("Bob", "White", "+48113332222", "bob@gmail.com", 0);
        MenuItem item = new MenuItem("Soup", "Hot soup", 10, testMenu);

        assertThrows(IllegalArgumentException.class, () -> new Order(2, "note", item, null, customer2));
    }
}
