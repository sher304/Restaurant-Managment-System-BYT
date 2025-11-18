package BYT.Classes.Order;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order implements Serializable {
    private static List<Order> extent = new ArrayList<>();
    //private long totalPrice; // derived
    private LocalDateTime date;
    private OrderStatus status;

    public Order(){
        this.date = LocalDateTime.now();
        this.status = OrderStatus.CREATED;
        extent.add(this);
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

    // TotalPrice - get method that calculates or an attribute that's calculated during creation?
    // TODO: Will be implemented when Menu and MenuItem are done
    public double getTotalPrice() {
        double totalPrice = 0.0;

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
