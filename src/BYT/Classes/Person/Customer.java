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

    public void createReservation(String reservationNumber, LocalDateTime startAt, LocalDateTime endsAt, int numberOfPeople, Table table) {
        if(reservationMap.containsKey(reservationNumber)) throw new IllegalArgumentException("A reservation with this number already exists.");
        Reservation reservation = new Reservation(startAt, endsAt, this, numberOfPeople, table); // takes care of Reservation extent + Table set
        reservationMap.put(reservationNumber, reservation); // Customer map
    }

    public Map<String, Reservation> getReservationMap() {
        return Collections.unmodifiableMap(reservationMap);
    }

    public void addReservation(String reservationNumber, Reservation newReservation) {
        if(reservationMap.containsKey(reservationNumber)) throw new IllegalArgumentException("A reservation with this number already exists.");
        reservationMap.put(reservationNumber, newReservation);
    }

    public void deleteReservation(String reservationNumber) throws Exception {
        Reservation reservation = findReservationByNumber(reservationNumber);
        if(reservation == null) throw new Exception("A reservation with this number does not exist");
        reservation.deleteTable(); // takes care of Reservation extent + Table set
        reservationMap.remove(reservationNumber); // Customer map
    }

    public boolean deleteReservation(Reservation reservation) {
        if(!reservationMap.containsValue(reservation)) throw new IllegalArgumentException("This reservation is not associated");
        boolean found = false;
        List<String> keysToRemove = new ArrayList<>();
        // collect keys first to avoid ConcurrentModificationException
        for(Map.Entry<String, Reservation> r : reservationMap.entrySet())
            if(r.getValue().equals(reservation)) keysToRemove.add(r.getKey());
        for(String key : keysToRemove){
            reservationMap.remove(key);
            reservation.deleteTable();
            found = true;
        }
        return found;
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