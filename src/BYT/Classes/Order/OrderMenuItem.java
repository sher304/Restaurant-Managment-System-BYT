package BYT.Classes.Order;

import BYT.Classes.MenuItem.MenuItem;
import BYT.Helpers.Validator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// association class converted to junction class
public class OrderMenuItem implements Serializable {
    private static List<OrderMenuItem> extent = new ArrayList<>();
    private int quantity;
    private String orderNotes; // [0..1]

    private Order order; // 1
    private MenuItem menuItem; // 1

    public OrderMenuItem(int quantity, String orderNotes, Order order, MenuItem menuItem) {
        this.quantity = Validator.validateNonZeroPhysicalAttribute(quantity);
        this.orderNotes = Validator.validateOptionalAttributes(orderNotes);

        this.setOrder(order);
        this.setMenuItem(menuItem);

        menuItem.addOrderMenuItem(this);
        extent.add(this);
    }

    public void delete(){
        menuItem.deleteOrderMenuItem(this);
        extent.remove(this);
    }

    //public int getLineNumber() {
        //return order.getOrderMenuItems().
    //}

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = Validator.validateNonZeroPhysicalAttribute(quantity);;
    }

    public String getOrderNotes() {
        return orderNotes;
    }

    public void setOrderNotes(String orderNotes) {
        this.orderNotes = Validator.validateAttributes(orderNotes);;
    }

    public Order getOrder() {
        return order;
    }

    private void setOrder(Order order) {
        Validator.validateNullObjects(order);
        this.order = order;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    private void setMenuItem(MenuItem menuItem) {
        Validator.validateNullObjects(menuItem);
        this.menuItem = menuItem;
    }
}
