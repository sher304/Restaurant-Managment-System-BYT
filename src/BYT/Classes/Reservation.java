package BYT.Classes;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

// !
// Scanner
// !
public class Reservation implements Serializable {
    private static List<Reservation> extent = new ArrayList<>();
    private LocalDate startAt;
    private LocalDate endsAt;
    private int numberOfPeople;
    private Customer customer;
    private String tableNumber;

    public Reservation(LocalDate startAt, LocalDate endsAt, Customer customer, String tableNumber, int numberOfPeople) {
        this.startAt = startAt;
        this.endsAt = endsAt;
        this.tableNumber = tableNumber;
        this.customer = customer;
        this.numberOfPeople = numberOfPeople;
        extent.add(this);
    }

    public static ArrayList<Table> getFreeTables(int maxNumberOfPeople, LocalDate startAt, LocalDate endsAt) {
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

    public static void createReservation(LocalDate startAt, LocalDate endsAt, Customer customer, int numberOfPeople) {
        ArrayList<Table> freeTables = getFreeTables(numberOfPeople, startAt, endsAt);
        int i = 0;

        for (Table t : freeTables) {
            System.out.println(i + "_" + t);
            i++;
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a free table : ");
        int index = sc.nextInt();
        extent.add(new Reservation(startAt, endsAt, customer, freeTables.get(index).getTableNumber(), numberOfPeople));
    }

    public static void cancelReservation(Date startAt, Date endsAt, String tableNumber) {

        for (int i = 0; i < extent.size(); i++) {
            Reservation current = extent.get(i);
            if (current.tableNumber.equals(tableNumber) && current.startAt.equals(startAt) && current.endsAt.equals(endsAt)) {
                extent.remove(i);
            }

        }
    }

    public static void modifyNumberOfPeople(int numberOfPeople, String tableNumber, LocalDate startAt, LocalDate endsAt) {
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
        return extent;
    }

    public static void setExtent(List<Reservation> extent) {
        Reservation.extent = extent;
    }

    public LocalDate getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalDate startAt) {
        this.startAt = startAt;
    }

    public LocalDate getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(LocalDate endsAt) {
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
        this.customer = customer;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
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
}
