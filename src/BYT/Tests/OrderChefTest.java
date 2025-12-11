package BYT.Tests;

import BYT.Classes.Person.Chef;
import BYT.Classes.Person.Waiter;
import BYT.Classes.Person.Customer;
import BYT.Classes.Restaurant.Menu;
import BYT.Classes.Restaurant.MenuItem;
import BYT.Classes.Order.Order;
import BYT.Classes.Order.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class OrderChefTest extends TestBase<Order> {

    protected OrderChefTest() {
        super(Order.class);
    }

    private Order order;
    private Menu testMenu;
    private Waiter waiter;
    private Customer customer;
    private Chef chef;

    @BeforeEach
    void setup() {
        clearExtentInMemoryList();

        waiter = new Waiter("Mark", "Red", "+48111111111", "x@x.com", 9999L);
        customer = new Customer("Alice", "Green", "+48112223333", "alice@gmail.com", 0);
        chef = new Chef("Gordon", "Ramsay", "+48119998877", "gordon@chef.com", 15000L);

        testMenu = new Menu(LocalDate.now(), LocalDate.now().plusDays(5));
        order = new Order(1, "Initial note", new MenuItem("Citrus-Brined Olives", "Marinated mixed olives", 7, testMenu), waiter, customer);
    }

    @Test
    void assignChefToOrderUpdatesBidirectionalAssociation() {
        order.setChef(chef);

        assertEquals(chef, order.getChef(), "Order should reference assigned chef");
        assertTrue(chef.getInvolvedInOrders().contains(order), "Chef should track involvement in order");
    }

    @Test
    void assignChefWhenOrderNotCreatedThrows() {
        order.prepare(); // changes status from CREATED -> InPREPARATION

        assertThrows(IllegalArgumentException.class, () -> order.setChef(chef),
                "Cannot assign chef if order is not in CREATED state");
    }

    @Test
    void makeChefResponsibleForOrder() {
        order.setChef(chef);

        chef.makeChefResponsibleForOrder(order);

        assertTrue(chef.getResponsibleForOrders().contains(order), "Chef should be responsible for order");
        assertEquals(OrderStatus.InPREPARATION, order.getStatus(), "Order should now be InPREPARATION");
    }

    @Test
    void makeChefResponsibleWithoutInvolvementThrows() {
        Order newOrder = new Order(2, "Another note", new MenuItem("Soup", "Hot soup", 10, testMenu), waiter, customer);

        assertThrows(IllegalArgumentException.class, () -> chef.makeChefResponsibleForOrder(newOrder),
                "Chef must be involved in order before being responsible");
    }

    @Test
    void markOrderAsPreparedChangesStatus() {
        order.setChef(chef);
        chef.makeChefResponsibleForOrder(order);

        chef.markOrderAsPrepared(0);

        assertEquals(OrderStatus.PREPARED, order.getStatus(), "Order should be marked as PREPARED");
    }

    @Test
    void markOrderAsPreparedWithInvalidIndexThrows() {
        order.setChef(chef);
        chef.makeChefResponsibleForOrder(order);

        assertThrows(IllegalArgumentException.class, () -> chef.markOrderAsPrepared(1),
                "Invalid index should throw exception");
    }

    @Test
    void addingSameOrderTwiceDoesNotDuplicate() {
        order.setChef(chef);

        chef.addChefInvolvementFromOrder(order);
        chef.addChefInvolvementFromOrder(order);

        assertEquals(1, chef.getInvolvedInOrders().size(), "Order involvement list should not contain duplicates");
    }

    @Test
    void setChefNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> order.setChef(null),
                "Cannot assign null as chef");
    }
}
