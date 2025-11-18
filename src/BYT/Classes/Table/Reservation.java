package BYT.Classes.Table;

import BYT.Classes.Person.Customer;
import BYT.Helpers.Validator;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

// !
// Scanner
// !
public class Reservation implements Serializable {
    private static List<Reservation> extent = new ArrayList<>();
    private LocalDateTime startAt;
    private LocalDateTime endsAt;
    private int numberOfPeople;
    private Customer customer;
    private String tableNumber;

    public Reservation(LocalDateTime startAt, LocalDateTime endsAt, Customer customer, String tableNumber, int numberOfPeople) {
        Validator.validateReservationDate(startAt, endsAt);
        this.startAt = startAt;
        this.endsAt = endsAt;
        this.tableNumber = Validator.validateAttributes(tableNumber);
        this.numberOfPeople = Validator.validateNumberOfPeople(numberOfPeople);
        this.customer = customer;
        extent.add(this);
    }

    public static ArrayList<Table> getFreeTables(int maxNumberOfPeople, LocalDateTime startAt, LocalDateTime endsAt) {
        ArrayList<Table> freeTables = new ArrayList<>();

        for (Table t : Table.getExtent()) {
            if (maxNumberOfPeople > t.getMaxNumberOfPeople()) continue;

            boolean exactConflict = false;


            for (Reservation r : extent) {
                if (t.getTableNumber().equals(r.tableNumber)
                        && startAt.equals(r.startAt)
                        && endsAt.equals(r.endsAt)) {
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

//    public static void createReservation(LocalDate startAt, LocalDate endsAt, Customer customer, int numberOfPeople) {
//        ArrayList<Table> freeTables = getFreeTables(numberOfPeople, startAt, endsAt);
//        int i = 0;
//
//        for (Table t : freeTables) {
//            System.out.println(i + "_" + t);
//            i++;
//        }
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Enter a free table : ");
//        int index = sc.nextInt();
//        extent.add(new Reservation(startAt, endsAt, customer, freeTables.get(index).getTableNumber(), numberOfPeople));
//    }

    public static Reservation createReservation(LocalDateTime startAt, LocalDateTime endsAt, Customer customer,
                                                int numberOfPeople, String selectedTableNumber) {
        List<Table> freeTables = getFreeTables(numberOfPeople, startAt, endsAt);
        boolean isAvailable = freeTables.stream()
                .anyMatch(table -> table.getTableNumber().equals(selectedTableNumber));
        if (!isAvailable) throw new IllegalArgumentException("The selected table (" + selectedTableNumber +
                ") is not available for the given time, date, or group size.");;
        Reservation newReservation = new Reservation(startAt, endsAt, customer, selectedTableNumber, numberOfPeople);
        return newReservation;
    }

    public static void cancelReservation(LocalDateTime startAt, LocalDateTime endsAt, String tableNumber) {

        for (int i = 0; i < extent.size(); i++) {
            Reservation current = extent.get(i);
            if (current.tableNumber.equals(tableNumber) && current.startAt.equals(startAt) && current.endsAt.equals(endsAt)) {
                extent.remove(i);
            }

        }
    }

    public static void modifyNumberOfPeople(int numberOfPeople, String tableNumber, LocalDateTime startAt, LocalDateTime endsAt) {
        boolean tableHasCapacity = false;
        for (Table t : Table.getExtent()) {
            if (t.getTableNumber().equals(tableNumber) && t.getMaxNumberOfPeople() == numberOfPeople) {
                tableHasCapacity = true;
            }
        }
        if (tableHasCapacity) {
            for (int i = 0; i < extent.size(); i++) {
                Reservation current = extent.get(i);
                if (current.tableNumber.equals(tableNumber) && current.endsAt.equals(endsAt)) {
                    extent.get(i).numberOfPeople = numberOfPeople;
                }
            }
        } else {
            Scanner scan = new Scanner(System.in);
            System.out.println("Enter a free table number : ");

            ArrayList<Table> freeTables = getFreeTables(numberOfPeople, startAt, endsAt);
            for (int i = 0; i < freeTables.size(); i++) {
                System.out.println(freeTables.get(i));
            }
            String tableN = scan.nextLine();
            for (Reservation r : extent) {
                if (r.tableNumber.equals(tableNumber) && r.endsAt.equals(endsAt) && r.startAt.equals(startAt)) {
                    r.tableNumber = tableN;
                }
            }
        }
    }

    public static List<Reservation> getExtent() {
        return Collections.unmodifiableList(extent);
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalDateTime startAt) {
        Validator.validateReservationDate(startAt, this.endsAt);
        this.startAt = startAt;
    }

    public LocalDateTime getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(LocalDateTime endsAt) {
        Validator.validateReservationDate(this.startAt, endsAt);
        this.endsAt = endsAt;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = Validator.validateNumberOfPeople(numberOfPeople);
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = Validator.validateAttributes(tableNumber);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "startAt=" + startAt +
                ", endsAt=" + endsAt +
                ", numberOfPeople=" + numberOfPeople +
                ", customer=" + customer +
                ", tableNumber='" + tableNumber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return numberOfPeople == that.numberOfPeople && Objects.equals(startAt, that.startAt) && Objects.equals(endsAt, that.endsAt) && Objects.equals(customer, that.customer) && Objects.equals(tableNumber, that.tableNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startAt, endsAt, numberOfPeople, customer, tableNumber);
    }
}
