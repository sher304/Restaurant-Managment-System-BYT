package BYT.Classes.Table;

import BYT.Helpers.Validator;

import java.io.Serializable;
import java.util.*;

public class Table implements Serializable {
    private static List<Table> extent = new ArrayList<>();
    private String tableNumber; // tableNumber could be "A123" etc.
    private int maxNumberOfPeople;

    // Basic association one table has many reservations
    private Set<Reservation> reservations = new HashSet<>();

    public Table(String tableNumber, int maxNumberOfPeople) {
        this.tableNumber = Validator.validateAttributes(tableNumber);
        this.maxNumberOfPeople = Validator.negativeNumberEntered(maxNumberOfPeople);
        extent.add(this);
    }

    void createReservation(Reservation reservation) {
        if (reservations == null) {
            throw new IllegalArgumentException("Reservation can not be null");
        }

        if (!reservations.contains(reservation)) {
            reservations.add(reservation);
            reservation.createReservation(this);
        }
    }

    void cancelReservation(Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation can not be null!");
        }

        if (reservations.contains(reservation)) {
            reservations.remove(reservation);

            if (reservation.getTable() == this) {
                reservation.deleteTable();
            }
        }
    }

    public Set<Reservation> getReservations() {
        return Collections.unmodifiableSet(reservations);
    }

    public static List<Table> getExtent() {
        return Collections.unmodifiableList(extent);
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = Validator.validateAttributes(tableNumber);
    }

    public int getMaxNumberOfPeople() {
        return maxNumberOfPeople;
    }

    public void setMaxNumberOfPeople(int maxNumberOfPeople) {
        this.maxNumberOfPeople = Validator.negativeNumberEntered(maxNumberOfPeople);
    }

    @Override
    public String toString() {
        return "Table{" +
                "tableNumber='" + tableNumber + '\'' +
                ", maxNumberOfPeople=" + maxNumberOfPeople +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Table table = (Table) o;
        return maxNumberOfPeople == table.maxNumberOfPeople && Objects.equals(tableNumber, table.tableNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableNumber, maxNumberOfPeople);
    }
}
