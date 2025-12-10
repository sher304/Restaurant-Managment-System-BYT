package BYT.Classes.Order;
import BYT.Classes.Person.Waiter;
import BYT.Classes.Person.Customer;
import BYT.Classes.Restaurant.MenuItem;
import BYT.Classes.Person.Chef;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

public class Order implements Serializable {
    private static List<Order> extent = new ArrayList<>();
    //private long totalPrice; // derived
    private LocalDateTime date;
    private OrderStatus status;
    private Chef chef;
    private ArrayList<Chef> involves=new ArrayList<Chef>();

    public ArrayList<Chef> getInvolves() {
        return involves;
    }

    public void addChefInvolves(Chef chef){
        involves.add(chef);
    }

    public Chef getChef() {
        return chef;
    }

    public void setChef(Chef chef) {
        if (chef == null)
            throw new IllegalArgumentException("Chef cannot be null");

        if (this.status != OrderStatus.CREATED)
            throw new IllegalArgumentException("Chef can be assigned only when order in CREATED state");

        this.chef = chef;
        chef.addChefInvolvementFromOrder(this);
    }

    private Set<OrderMenuItem> orderMenuItems; // [1..*]

    private Waiter waiter;
    private Customer customer;

    public Order(int quantity, String orderNotes, MenuItem menuItem){
        this.date = LocalDateTime.now();
        this.status = OrderStatus.CREATED;

        orderMenuItems = new HashSet<>();
        createOrderMenuItem(quantity, orderNotes, menuItem);

        extent.add(this);
    }

    public Waiter getWaiter() {
        return waiter;
    }

    public void setWaiter(Waiter waiter) {
        if (this.waiter != null && this.waiter != waiter) {
            this.waiter.removeOrder(this);
        }
        this.waiter = waiter;

        if (waiter != null && !waiter.getOrders().contains(this)) {
            waiter.addOrder(this);
        }
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer newCustomer) {
        if (this.customer != null && this.customer != newCustomer) {
            this.customer.removeOrder(this);
        }

        this.customer = newCustomer;

        if (newCustomer != null && !newCustomer.getOrders().contains(this)) {
            newCustomer.addOrder(this);
        }
    }

    // main class for controlling Order-OrderMenuItem-MenuItem
    public Set<OrderMenuItem> getOrderMenuItems() {
        return Collections.unmodifiableSet(orderMenuItems);
    }

    public OrderMenuItem createOrderMenuItem(int quantity, String orderNotes, MenuItem menuItem){
        if(status != OrderStatus.CREATED) throw new IllegalStateException("Items can be added to Order only when the Order is in status CREATED");
        OrderMenuItem orderMenuItem = new OrderMenuItem(quantity, orderNotes, this, menuItem); // takes care of OrderMenuItem extent + MenuItem set
        orderMenuItems.add(orderMenuItem); // Order set
        return orderMenuItem;
    }

    public void deleteOrderMenuItem(OrderMenuItem orderMenuItem) throws IllegalStateException{
        if(status != OrderStatus.CREATED) throw new IllegalStateException("Items can be removed from Order only when the Order is in status CREATED");
        if(orderMenuItems.size() <= 1) throw new IllegalStateException("Order must have at least one MenuItem");
        orderMenuItems.remove(orderMenuItem); // Order set
        orderMenuItem.delete(); // takes care of OrderMenuItem extent + MenuItem set
    }

    public void prepare() throws IllegalStateException {
        if (this.status != OrderStatus.CREATED) {
            throw new IllegalStateException("Order can only be prepared if it is in the CREATED state.");
        }
        this.status = OrderStatus.InPREPARATION;
    }

    public void cancelled() throws IllegalStateException {
        if (this.status == OrderStatus.SERVED){
            throw new IllegalStateException("Order can be cancelled, only before it has been SERVED!");
        }
        if (this.status == OrderStatus.CANCELLED) {
            return;
        }
        this.status = OrderStatus.CANCELLED;
    }

    public void serve() throws IllegalStateException {
        if (this.status != OrderStatus.InPREPARATION) {
            throw new IllegalStateException("Order can be served only, it has been PREPARED by a CHEF");
        }
        this.status = OrderStatus.SERVED;
    }

    public long getTotalPrice() {
        long totalPrice = 0;

        for(OrderMenuItem orderMenuItem : orderMenuItems){
            totalPrice += orderMenuItem.getMenuItem().getPrice() * orderMenuItem.getQuantity();
        }
        return totalPrice;
    }

    public LocalDateTime getDate() {
        return date;
    }

    // no setter for date
    // we assume that date is supposed to be a historical record of when the order was entered

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "date=" + date +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(date, order.date) && status == order.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, status);
    }
}
