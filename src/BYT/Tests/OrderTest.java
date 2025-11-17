package BYT.Tests;

import BYT.Classes.Order.Order;
import BYT.Classes.Order.OrderStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderTest extends TestBase<Order> {

    protected OrderTest(){
        super(Order.class);
    }

    @BeforeEach
    void setup(){
        clearExtentInMemoryList();
    }

    @Test
    void checkOrderBeingPrepareStatusUpdated() {
        Order order = new Order();
        order.prepare();
        assertEquals(order.getStatus(), OrderStatus.InPREPARATION, "Order didn't changed its status to PREPARE!");
    }

    @Test
    void checkOrderBeingCreatedStatus() {
        Order order = new Order();
        assertEquals(order.getStatus(), OrderStatus.CREATED, "Order status is not CREATED! Order failure to be Created!");
    }

    @Test
    void checkOrderHasBeenCancelled() {
        Order order = new Order();
        order.cancelled();
        assertEquals(order.getStatus(), OrderStatus.CANCELLED, "Order did not cancelled! Failure to cancel!");
    }

    @Test
    void checkOrderCancelAfterBeingPreparedToBeFailureOrThrowsException(){
        Order order = new Order();
        order.prepare();
        order.serve();
        assertThrows(IllegalStateException.class,
                () -> order.cancelled(), "Order can not be cancelled, after being served!");
    }

    @Test
    void checkOrderCanBeServedOnlyAfterPreparingOrThrowsException() {
        Order order = new Order();
        assertThrows(IllegalStateException.class,
                () -> order.serve(), "Order can not be served, if it is not prepared!");
    }

}
