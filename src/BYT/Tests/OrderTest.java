package BYT.Tests;

import BYT.Classes.Person.Customer;
import BYT.Classes.Restaurant.*;
import BYT.Classes.Order.Order;
import BYT.Classes.Order.OrderMenuItem;
import BYT.Classes.Order.OrderStatus;
import BYT.Classes.Person.Waiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest extends TestBase<Order> {

    protected OrderTest(){
        super(Order.class);
    }

    private Order order;
    private Waiter waiter;
    private Customer customer;
    private Menu testMenu;

    @BeforeEach
    void setup(){
        clearExtentInMemoryList();
        Waiter w = new Waiter("Mark", "Red", "+48111111111", "x@x.com", 8989L);
        Customer c = new Customer("Alice", "Green", "+48112223333", "alice@gmail.com", 0);

        Menu testMenu = new Menu(LocalDate.now(), LocalDate.now().plusDays(5));
        order = new Order(1, null, new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7, testMenu),w,c);
    }

    // extent

    @Test
    void testPersistence_SavingAndLoading() throws IOException, ClassNotFoundException {
        List<Order> list = new ArrayList<>();
        //Order order = new Order();
        list.add(order);
        order.prepare();
        order.serve();

        testPersistence(list); // tests that the status is Served too
    }

    // Order-only attributes and methods

    @Test
    void checkOrderBeingPrepareStatusUpdated() {
        //Order order = new Order();
        order.prepare();
        assertEquals(order.getStatus(), OrderStatus.InPREPARATION, "Order didn't changed its status to PREPARE!");
    }

    @Test
    void checkOrderBeingCreatedStatus() {
        //Order order = new Order();
        assertEquals(order.getStatus(), OrderStatus.CREATED, "Order status is not CREATED! Order failure to be Created!");
    }

    @Test
    void checkOrderHasBeenCancelled() {
        //Order order = new Order();
        order.cancelled();
        assertEquals(order.getStatus(), OrderStatus.CANCELLED, "Order did not cancelled! Failure to cancel!");
    }

    @Test
    void checkOrderCancelAfterBeingPreparedToBeFailureOrThrowsException(){
        //Order order = new Order();
        order.prepare();
        order.serve();
        assertThrows(IllegalStateException.class,
                () -> order.cancelled(), "Order can not be cancelled, after being served!");
    }

    @Test
    void checkOrderCanBeServedOnlyAfterPreparingOrThrowsException() {
        //Order order = new Order();
        assertThrows(IllegalStateException.class,
                () -> order.serve(), "Order can not be served, if it is not prepared!");
    }

    // associations moved to OrderMenuItemTest
    // associations moved to OrderWaiterTest
    // associations moved to OrderCustomerTest
    // associations moved to OrderChefTest

    // Null Waiter/Customer Tests

    @Test
    void constructorThrowsWhenWaiterIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                        new Order(1, "Note", new MenuItem("Dish", "Desc", 5, testMenu), null, customer),
                "Order constructor should throw if Waiter is null");
    }

    @Test
    void constructorThrowsWhenCustomerIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                        new Order(1, "Note", new MenuItem("Dish", "Desc", 5, testMenu), waiter, null),
                "Order constructor should throw if Customer is null");
    }
}
