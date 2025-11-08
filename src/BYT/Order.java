package BYT;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order implements Serializable {
    // TotalPrice - get method that calculates or an attribute that's calculated during creation?
    public LocalDateTime date;
    public OrderStatus status;
    private static List<Order> extent = new ArrayList<>();

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


    // TODO: Will be implemented when Menu and MenuItem are done
    public double getFinalPrice() {
        double totalPrice = 0.0;

        return totalPrice;
    }


}
