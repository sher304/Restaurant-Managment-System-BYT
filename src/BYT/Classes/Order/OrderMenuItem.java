package BYT.Classes.Order;

import BYT.Classes.MenuItem.MenuItem;
import BYT.Helpers.Validator;

// association class converted to junction class
public class OrderMenuItem {
    private int lineNumber;
    private int quantity;
    private String orderNotes; // [0..1]

    private Order order;
    private MenuItem menuItem;

    public OrderMenuItem(int lineNumber, int quantity, String orderNotes) {
        // TODO: lineNumber validation
        // some kind of static(?) attribute to track this for a given Order
        this.lineNumber = lineNumber;
        this.quantity = Validator.validateNonZeroPhysicalAttribute(quantity);
        this.orderNotes = Validator.validateAttributes(orderNotes);
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        // TODO: lineNumber validation
        this.lineNumber = lineNumber;
    }

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

    public MenuItem getMenuItem() {
        return menuItem;
    }
}
