package BYT.Classes;

import BYT.Helpers.Validator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Customer extends Person implements Serializable {
    private static final List<Customer> extent = new ArrayList<>();
    private long loyaltyPoints;

    public Customer(String firstName, String lastName, String phoneNumber, String email, long loyaltyPoints) {
        super(firstName, lastName, phoneNumber, email);
        this.loyaltyPoints = Validator.negativeNumberEntered(loyaltyPoints);
        extent.add(this);
    }

    public long getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(long loyaltyPoints) {
        this.loyaltyPoints = Validator.negativeNumberEntered(loyaltyPoints);
    }

    public void addLoyaltyPoints(long add) {
        if(add <= 0){
            throw new IllegalArgumentException("Added number of loyalty points must be greater than 0.");
        }
        this.loyaltyPoints += add;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "loyaltyPoints=" + loyaltyPoints +
                '}';
    }
}