package BYT.Tests;

import BYT.Classes.Restaurant.*;
import BYT.Classes.Order.Order;
import BYT.Classes.Order.OrderMenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderMenuItemTest extends TestBase<OrderMenuItem> {

    protected OrderMenuItemTest(){
        super(OrderMenuItem.class);
    }

    private Order order;
    private Menu testMenu;

    @BeforeEach
    void setup(){
        clearExtentInMemoryList();

        testMenu = new Menu(LocalDate.now(), LocalDate.now().plusDays(5));
        order = new Order(1, null, new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7, testMenu));
    }

    @Test
    void addItemsToOrderAfterPrepareThrows(){
        order.prepare();
        assertThrows(IllegalStateException.class, () -> order.createOrderMenuItem(2, null, new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7, testMenu)));
    }

    @Test
    void addItemsToOrderAfterServedThrows(){
        order.prepare();
        order.serve();
        assertThrows(IllegalStateException.class, () -> order.createOrderMenuItem(2, null, new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7, testMenu)));
    }

    @Test
    void addItemsToOrderAfterCancelledThrows(){
        order.cancelled();
        assertThrows(IllegalStateException.class, () -> order.createOrderMenuItem(2, null, new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7, testMenu)));
    }

    @Test
    void removeItemsFromOrderAfterPrepareThrows(){
        order.createOrderMenuItem(3, "test1", new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7, testMenu));
        order.prepare();
        assertThrows(IllegalStateException.class, () -> order.deleteOrderMenuItem(order.getOrderMenuItems().iterator().next()));
    }

    @Test
    void removeItemsFromOrderAfterServedThrows(){
        order.createOrderMenuItem(3, "test1", new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7, testMenu));
        order.prepare();
        order.serve();
        assertThrows(IllegalStateException.class, () -> order.deleteOrderMenuItem(order.getOrderMenuItems().iterator().next()));
    }

    @Test
    void removeItemsFromOrderAfterCancelledThrows(){
        order.createOrderMenuItem(3, "test1", new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7, testMenu));
        order.cancelled();
        assertThrows(IllegalStateException.class, () -> order.deleteOrderMenuItem(order.getOrderMenuItems().iterator().next()));
    }

    @Test
    void removeOnlyItemFromOrderThrows(){
        assertEquals(1, order.getOrderMenuItems().size());

        OrderMenuItem orderMenuItem = order.getOrderMenuItems().iterator().next();

        assertThrows(IllegalStateException.class, () -> order.deleteOrderMenuItem(orderMenuItem));
    }

    @Test
    void addingMenuItemsToOrder_createsAllAssociations() {
        assertEquals(1, order.getOrderMenuItems().size());

        MenuItem menuItem1 = new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7, testMenu);
        MenuItem menuItem2 = new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7, testMenu);

        OrderMenuItem omu1 = order.createOrderMenuItem(12, "test1", menuItem1);
        OrderMenuItem omu2 = order.createOrderMenuItem(31, "test2", menuItem2);
        assertEquals(3, order.getOrderMenuItems().size(), "Order should have 3 junction classes (OrderMenuItem)");
        assertEquals(7 * (31 + 12 + 1), order.getTotalPrice(), "Total price should be correct");

        assertTrue(order.getOrderMenuItems().contains(omu1), "Order should be linked to omu1");
        assertTrue(order.getOrderMenuItems().contains(omu2), "Order should be linked to omu2");

        assertEquals(omu1.getOrder(), order, "omu1 should be linked to the Order which created it");
        assertEquals(omu2.getOrder(), order, "omu2 should be linked to the Order which created it");

        assertEquals(omu1.getMenuItem(), menuItem1, "omu1 should be linked to menuItem1");
        assertEquals(omu2.getMenuItem(), menuItem2, "omu2 should be linked to menuItem2");

//        assertTrue(menuItem1.getOrderMenuItems().contains(omu1), "menuItem1 should be linked to omu1");
        assertTrue(menuItem1.getOrderMenuItems().contains(omu1), "menuItem1 should be linked to omu1");
//        assertTrue(menuItem2.getOrderMenuItems().contains(omu2), "menuItem2 should be linked to omu2");
    }

    @Test
    void addingOrderMenuItemToAnotherMenuItemThrows(){
        MenuItem menuItem1 = new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7, testMenu);
        MenuItem menuItem2 = new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7, testMenu);

        OrderMenuItem omu1 = order.createOrderMenuItem(12, "test1", menuItem1);

        assertThrows(IllegalStateException.class, () -> menuItem2.addOrderMenuItem(omu1));
    }

    @Test
    void addingDuplicateOrderMenuItemToMenuItemThrows(){
        MenuItem menuItem1 = new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7, testMenu);

        OrderMenuItem omu1 = order.createOrderMenuItem(12, "test1", menuItem1);

        assertThrows(IllegalArgumentException.class, () -> {
            menuItem1.addOrderMenuItem(omu1);
        });
    }

    @Test
    void deletingOrderMenuItemFromOrderSide_deletesCorrectly(){
        MenuItem menuItem1 = new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7, testMenu);
        MenuItem menuItem2 = new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7, testMenu);

        OrderMenuItem omu1 = order.createOrderMenuItem(12, "test1", menuItem1);
        OrderMenuItem omu2 = order.createOrderMenuItem(31, "test2", menuItem2);
        assertEquals(3, order.getOrderMenuItems().size(), "Order should have 3 junction classes (OrderMenuItem)");

        order.deleteOrderMenuItem(omu1);
        order.deleteOrderMenuItem(omu2);

        assertFalse(extent().contains(omu1), "omu1 should be removed from OrderMenuItem extent");
        assertFalse(extent().contains(omu2), "omu2 should be removed from OrderMenuItem extent");
        assertFalse(order.getOrderMenuItems().contains(omu1), "omu1 should not be in Order");
        assertFalse(order.getOrderMenuItems().contains(omu2), "omu2 should not be in Order");
        assertFalse(menuItem1.getOrderMenuItems().contains(omu1), "omu1 should not be in menuItem1");
        assertFalse(menuItem2.getOrderMenuItems().contains(omu2), "omu2 should not be in menuItem2");
    }

    @Test
    void deletingOrderMenuItemFromMenuItemSide_deletesCorrectly(){
        MenuItem menuItem1 = new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7, testMenu);
        MenuItem menuItem2 = new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7, testMenu);

        OrderMenuItem omu1 = order.createOrderMenuItem(12, "test1", menuItem1);
        OrderMenuItem omu2 = order.createOrderMenuItem(31, "test2", menuItem2);
        assertEquals(3, order.getOrderMenuItems().size(), "Order should have 3 junction classes (OrderMenuItem)");

        menuItem1.deleteOrderMenuItem(omu1);
        menuItem2.deleteOrderMenuItem(omu2);

        assertFalse(extent().contains(omu1), "omu1 should be removed from OrderMenuItem extent");
        assertFalse(extent().contains(omu2), "omu2 should be removed from OrderMenuItem extent");
        assertFalse(order.getOrderMenuItems().contains(omu1), "omu1 should not be in Order");
        assertFalse(order.getOrderMenuItems().contains(omu2), "omu2 should not be in Order");
        assertFalse(menuItem1.getOrderMenuItems().contains(omu1), "omu1 should not be in menuItem1");
        assertFalse(menuItem2.getOrderMenuItems().contains(omu2), "omu2 should not be in menuItem2");
    }

    @Test
    void deletingLastOrderMenuItemForOrderThrows(){
        assertThrows(IllegalStateException.class, () -> order.deleteOrderMenuItem(order.getOrderMenuItems().iterator().next()));
    }

    @Test
    void addNullMenuItemThrows(){
        assertThrows(IllegalArgumentException.class, () -> order.createOrderMenuItem(3, "test1", null));
    }

    @Test
    void addEmptyStringOrderNotesThrows(){
        assertThrows(IllegalArgumentException.class, () -> order.createOrderMenuItem(3, "", new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7, testMenu)));
    }

    @Test
    void removeNullMenuItemThrows(){
        assertThrows(Exception.class, () -> order.deleteOrderMenuItem(null));
    }
}
