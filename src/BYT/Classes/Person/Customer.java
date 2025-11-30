package BYT.Classes.Person;

import BYT.Classes.Table.Reservation;
import BYT.Classes.Table.Table;
import BYT.Helpers.Validator;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

public class Customer extends Person implements Serializable {
    private static final List<Customer> extent = new ArrayList<>();
    private long loyaltyPoints;
    private final Map<String, Reservation> reservationMap = new HashMap<>();

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

    public static Customer findOrCreate(String firstName, String lastName, String phoneNumber, String email, long initialLoyaltyPoints) {
        for (Customer customer : extent) {
            if (customer.getPhoneNumber().equals(phoneNumber)) {
                System.out.println("Customer has been found!");
                return customer;
            }
        }

//        System.out.println("Customer is not in the system!\nCreating a new Customer");
        return new Customer(firstName, lastName, phoneNumber, email, initialLoyaltyPoints);
    }

    // main class for controlling Customer-Reservation-Table
    public Reservation findReservationByNumber(String reservationNumber) {
        return reservationMap.get(reservationNumber);
    }

    public void createReservation(String reservationNumber, LocalDateTime startAt, LocalDateTime endsAt, int numberOfPeople, Table table) {
        if(reservationMap.containsKey(reservationNumber)) throw new IllegalArgumentException("A reservation with this number already exists.");
        Reservation reservation = new Reservation(startAt, endsAt, this, numberOfPeople, table); // takes care of Reservation extent + Table set
        reservationMap.put(reservationNumber, reservation); // Customer map
    }

    public void deleteReservation(String reservationNumber) {
        Reservation reservation = findReservationByNumber(reservationNumber);
        reservation.deleteTable(); // takes care of Reservation extent + Table set
        reservationMap.remove(reservationNumber); // Customer map
    }

    @Override
    public String toString() {
        return "Customer{" +
                "loyaltyPoints=" + loyaltyPoints +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Customer customer = (Customer) o;
        return loyaltyPoints == customer.loyaltyPoints;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), loyaltyPoints);
    }
}