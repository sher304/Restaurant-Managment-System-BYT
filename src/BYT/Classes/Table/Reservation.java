package BYT.Classes.Table;

import BYT.Classes.Person.Customer;
import BYT.Helpers.Validator;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

public class Reservation implements Serializable {
    private static List<Reservation> extent = new ArrayList<>();
    private LocalDateTime startAt;
    private LocalDateTime endsAt;
    private int numberOfPeople;
    private Customer customer; // 1 (mandatory)
    private Table table; // 1 (mandatory)

    public Reservation(LocalDateTime startAt, LocalDateTime endsAt, Customer customer, int numberOfPeople, Table table) {
        this.numberOfPeople = Validator.validateNumberOfPeople(numberOfPeople, table.getMaxNumberOfPeople());

        Validator.validateReservationDate(startAt, endsAt);
        this.startAt = startAt;
        this.endsAt = endsAt;

        // ^ attribute assignments MUST be before the associations! hashCode()-HashSet-equals bug with tests

        setCustomer(customer);
        createReservation(table);

        extent.add(this);
    }

    public void createReservation(Table newTable) {
        if (newTable == null) {
            throw new IllegalArgumentException("Reservation must be assigned to a Table.");
        }

        if (this.table != null && this.table != newTable) {
            this.table.cancelReservation(this);
        }

        this.table = newTable;

        if (!newTable.getReservations().contains(this)) {
            newTable.createReservation(this);
        }
    }

    public void deleteTable() {
        if (this.table != null) {
            Table t = this.table;
            this.table = null;
            t.cancelReservation(this);
        }
        if(customer.containsReservation(this)) customer.deleteReservation(this);
        extent.remove(this);
    }

    public static ArrayList<Table> getFreeTables(int maxNumberOfPeople, LocalDateTime startAt, LocalDateTime endsAt) {
        ArrayList<Table> freeTables = new ArrayList<>();

        for (Table t : Table.getExtent()) {
            if (maxNumberOfPeople > t.getMaxNumberOfPeople()) continue;
            boolean exactConflict = false;

            for (Reservation r : t.getReservations()) {
                if (startAt.equals(r.startAt) && endsAt.equals(r.endsAt)) {
                    exactConflict = true;
                    break;
                }
            }

            if (!exactConflict) {
                freeTables.add(t);
            }
        }
        return freeTables;
    }

    public static Reservation createReservation(LocalDateTime startAt, LocalDateTime endsAt, Customer customer,
                                                int numberOfPeople, String selectedTableNumber) {
        Table selectedTable = Table.getExtent().stream()
                .filter(t -> t.getTableNumber().equals(selectedTableNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Table not found: " + selectedTableNumber));

        List<Table> freeTables = getFreeTables(numberOfPeople, startAt, endsAt);
        if (!freeTables.contains(selectedTable)) {
            throw new IllegalArgumentException("The selected table (" + selectedTableNumber +
                    ") is not available for the given time, date, or group size.");
        }

        return new Reservation(startAt, endsAt, customer, numberOfPeople, selectedTable);
    }

    public Table getTable() {
        return table;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalDateTime startAt) {
        this.startAt = startAt;
    }

    public LocalDateTime getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(LocalDateTime endsAt) {
        this.endsAt = endsAt;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        Validator.validateNullObjects(customer);
        this.customer = customer;
        customer.addReservation(customer.generateRandomReservationNumber(), this);
    }

    public void setTable(Table table) {
        Validator.validateNullObjects(table);
        this.table = table;
    }

    public static List<Reservation> getExtent() {
        return extent;
    }

    public static void setExtent(List<Reservation> extent) {
        Reservation.extent = extent;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "startAt=" + startAt +
                ", endsAt=" + endsAt +
                ", numberOfPeople=" + numberOfPeople +
                ", customer=" + customer +
                ", Table='" + table.toString() + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return numberOfPeople == that.numberOfPeople && Objects.equals(startAt, that.startAt) && Objects.equals(endsAt, that.endsAt) && Objects.equals(customer, that.customer) && Objects.equals(table, that.table);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startAt, endsAt, numberOfPeople, customer, table);
    }
}
