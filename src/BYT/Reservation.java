package BYT;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Reservation {
    private Date startAt;
    private Date endsAt;
    private int numberOfPeople;
    private Customer customer;
    private String tableNumber;

    public Reservation(Date startAt, Date endsAt,Customer customer,String tableNumber,int numberOfPeople) {
        this.startAt = startAt;
        this.endsAt = endsAt;
        this.tableNumber = tableNumber;
        this.customer = customer;
        this.numberOfPeople = numberOfPeople;

    }

    public static ArrayList<Reservation> reservation=new ArrayList<Reservation>();
    public static ArrayList<Table> getFreeTables(int maxNumberOfPeople, Date startAt, Date endsAt) {
        ArrayList<Table> freeTables = new ArrayList<>();

        for (Table t : Table.tables) {
            if (maxNumberOfPeople > t.maxNumberOfPeople) continue;

            boolean exactConflict = false;


            for (Reservation r : reservation) {
                if (t.tableNumber.equals(r.tableNumber)
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

    public static void createReservation(Date startAt,Date endsAt,Customer customer,int numberOfPeople) {
        ArrayList<Table> freeTables=getFreeTables(numberOfPeople,startAt,endsAt);
        int i=0;

        for(Table t:freeTables){
            System.out.println(i+"_" +t);
            i++;
        }
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter a free table : ");
        int index=sc.nextInt();
        reservation.add(new Reservation(startAt,endsAt,customer,freeTables.get(index).tableNumber,numberOfPeople));
    }
    public static void cancelReservation(Date startAt,Date endsAt,String tableNumber){

        for(int i=0;i<reservation.size();i++){
            Reservation current=reservation.get(i);
            if(current.tableNumber.equals(tableNumber)&&current.startAt.equals(startAt)&&current.endsAt.equals(endsAt)){
                reservation.remove(i);
            }

        }
    }
    public static void modifNumberOfPeople(int numberOfPeople,String tableNumber,Date startAt,Date endsAt) {
        boolean tableHasCapacity=false;
        for(Table t:Table.tables){
            if(t.tableNumber.equals(tableNumber)&&t.maxNumberOfPeople==numberOfPeople){
                tableHasCapacity=true;
            }
        }
        if(tableHasCapacity){
            for(int i=0;i<reservation.size();i++){
                Reservation current=reservation.get(i);
                if(current.tableNumber.equals(tableNumber)&&current.endsAt.equals(endsAt)){
                    reservation.get(i).numberOfPeople=numberOfPeople;
                }
            }
        }
        else{
            Scanner scan=new Scanner(System.in);
            System.out.println("Enter a free table number : ");

            ArrayList<Table> freeTables=getFreeTables(numberOfPeople,startAt,endsAt);
            for(int i=0;i<freeTables.size();i++){
                System.out.println(freeTables.get(i));
            }
            String tableN=scan.nextLine();
            for(Reservation r:reservation){
                if(r.tableNumber.equals(tableNumber)&&r.endsAt.equals(endsAt)&&r.startAt.equals(startAt)){
                    r.tableNumber= tableN;
                }
            }
        }
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
