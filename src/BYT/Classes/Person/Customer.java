package BYT.Classes.Person;
import BYT.Classes.Order.Order;
import BYT.Classes.Table.Reservation;
import BYT.Classes.Table.Table;
import BYT.Helpers.Validator;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

public class Customer extends PersonRole implements Serializable {
    private static final List<Customer> extent = new ArrayList<>();
    private long loyaltyPoints;
    private final Map<String, Reservation> reservationMap = new HashMap<>();

    private Set<Order> orders = new HashSet<>();

    public Customer(Person person, long loyaltyPoints) {
        super(person);
        this.loyaltyPoints = Validator.negativeNumberEntered(loyaltyPoints);
        extent.add(this);
    }

    public void addOrder(Order order) {
        if (order == null) throw new IllegalArgumentException("Order cannot be null");

        if (!orders.contains(order)) {
            orders.add(order);
            order.setCustomer(this);
        }
    }

    public void removeOrder(Order order) {
        if (order == null) throw new IllegalArgumentException("Order cannot be null");
        if (orders.contains(order)) {
            orders.remove(order);
            order.setCustomer(null);
        }
    }

    public Set<Order> getOrders() {
        return Collections.unmodifiableSet(orders);
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
            if (customer.getPerson() != null && customer.getPerson().getPhoneNumber().equals(phoneNumber)) {
                System.out.println("Customer has been found!");
                return customer;
            }
        }

        System.out.println("Customer not found. Creating new Person and Customer role...");
        Person newPerson = new Person(firstName, lastName, phoneNumber, email);

        return new Customer(newPerson, initialLoyaltyPoints);
    }

    public Reservation findReservationByNumber(String reservationNumber) {
        return reservationMap.get(reservationNumber);
    }

    public boolean containsReservation(Reservation reservation) {
        return reservationMap.containsValue(reservation);
    }

    private static final String CHARS = "123456789abcdefghijklmnopqrstuvwxyz";
    public String generateRandomReservationNumber(){
        StringBuilder numberBuilder = new StringBuilder();
        while(numberBuilder.length() < 5 || reservationMap.containsKey(numberBuilder.toString())){
            numberBuilder.append(CHARS.charAt((int) Math.floor(Math.random() * (CHARS.length()))));
        }
        return numberBuilder.toString();
    }

    public Map<String, Reservation> getReservationMap() {
        return Collections.unmodifiableMap(reservationMap);
    }

    public void addOrMoveReservation(String reservationNumber, Reservation newReservation) {
        Validator.validateNullObjects(newReservation);
        if(reservationMap.containsKey(reservationNumber)) throw new IllegalArgumentException("A reservation with this number already exists.");
        if(reservationMap.containsValue(newReservation)) throw new IllegalArgumentException("A reservation with this value already exists.");
        reservationMap.put(reservationNumber, newReservation);
        if(newReservation.getCustomer() != this) {
            newReservation.getCustomer().deassociateReservation(newReservation, false);
            newReservation.setCustomer(this);
        }
    }

    public void deleteReservation(String reservationNumber) throws Exception {
        Reservation reservation = findReservationByNumber(reservationNumber);
        if(reservation == null) throw new Exception("A reservation with this number does not exist");
        reservation.deleteTable(); // takes care of Reservation extent + Table set
        reservationMap.remove(reservationNumber); // Customer map
    }

    public boolean deleteReservation(Reservation reservation) {
        return deassociateReservation(reservation, true);
    }

    private boolean deassociateReservation(Reservation reservation, boolean alsoDelete) {
        if(!reservationMap.containsValue(reservation)) throw new IllegalArgumentException("This reservation is not associated");
        boolean found = false;
        List<String> keysToRemove = new ArrayList<>();
        // collect keys first to avoid ConcurrentModificationException
        for(Map.Entry<String, Reservation> r : reservationMap.entrySet())
            if(r.getValue().equals(reservation)) keysToRemove.add(r.getKey());
        for(String key : keysToRemove){
            reservationMap.remove(key);
            if(alsoDelete) reservation.deleteTable();
            found = true;
        }
        return found;
    }

    @Override
    public void delete() {
        // Association
        for (Order order : new ArrayList<>(orders)) {
            order.setCustomer(null); // Or order.delete() if strictly required
        }

        // Qualified Association
        for (Reservation res : new ArrayList<>(reservationMap.values())) {
            res.deleteTable();
        }

        super.delete();
        extent.remove(this);
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