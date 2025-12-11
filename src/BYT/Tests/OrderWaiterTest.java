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

public class OrderWaiterTest extends TestBase<Order> {

    protected OrderWaiterTest() {
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
    void setWaiterUpdatesBidirectionalAssociation() {
        assertEquals(waiter, order.getWaiter());
        assertTrue(waiter.getOrders().contains(order));
    }

    @Test
    void changeWaiterUpdatesOldAndNewAssociations() {
        Waiter newWaiter = new Waiter("Lucy", "Blue", "+48222222222", "y@y.com", 8000L);

        order.setWaiter(newWaiter);

        assertEquals(newWaiter, order.getWaiter());
        assertTrue(newWaiter.getOrders().contains(order));
        assertFalse(waiter.getOrders().contains(order));
    }

    @Test
    void addOrderToWaiterUpdatesOrderReference() {
        Waiter newWaiter = new Waiter("Lucy", "Blue", "+48222222222", "y@y.com", 8000L);
        newWaiter.addOrder(order);

        assertEquals(newWaiter, order.getWaiter());
        assertTrue(newWaiter.getOrders().contains(order));
        assertFalse(waiter.getOrders().contains(order));
    }

    @Test
    void removeOrderFromWaiterClearsOrderReference() {
        waiter.removeOrder(order);

        assertNull(order.getWaiter());
        assertFalse(waiter.getOrders().contains(order));
    }

    @Test
    void addNullOrderToWaiterThrows() {
        assertThrows(IllegalArgumentException.class, () -> waiter.addOrder(null));
    }

    @Test
    void removeNullOrderFromWaiterThrows() {
        assertThrows(IllegalArgumentException.class, () -> waiter.removeOrder(null));
    }

    @Test
    void addingSameOrderTwiceDoesNotDuplicate() {
        waiter.addOrder(order);
        waiter.addOrder(order);

        assertEquals(1, waiter.getOrders().size());
    }

    @Test
    void orderConstructorThrowsIfWaiterIsNull() {
        Customer customer2 = new Customer("Bob", "White", "+48113332222", "bob@gmail.com", 0);
        MenuItem item = new MenuItem("Soup", "Hot soup", 10, testMenu);

        assertThrows(IllegalArgumentException.class, () -> new Order(2, "note", item, null, customer2));
    }

    @Test
    void orderConstructorThrowsIfCustomerIsNull() {
        MenuItem item = new MenuItem("Soup", "Hot soup", 10, testMenu);

        assertThrows(IllegalArgumentException.class, () -> new Order(2, "note", item, waiter, null));
    }
}
