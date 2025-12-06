package BYT.Classes;

import BYT.Classes.Restaurant.Menu;
import BYT.Helpers.Extents;

import java.time.LocalDate;

// main() for testing purposes (not a unit test, not part of the assignment)
public class Main {

    /*public static void test1() {
        for(int i=0;i<Table.tables.size();i++){
            Table.tables.remove(i);
        }
        Table table1=new Table("A1",3);
        Table table2=new Table("A2",3);
        Table table3=new Table("A3",4);
        Table table4=new Table("A4",4);
        Table table5=new Table("A5",5);
        Table table6=new Table("A6",5);
        Table table7=new Table("A7",6);
        Table table8=new Table("A8",6);
        Table table9=new Table("A9",6);
        Table.tables.add(table1);
        Table.tables.add(table2);
        Table.tables.add(table3);
        Table.tables.add(table4);
        Table.tables.add(table5);
        Table.tables.add(table6);
        Table.tables.add(table7);
        Table.tables.add(table8);
        Table.tables.add(table9);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        Date startDate = cal.getTime();

        Calendar cal2 = Calendar.getInstance();
        cal2.add(Calendar.DAY_OF_YEAR, 2);
        Date endDate = cal2.getTime();
        Customer c1=new Customer("1", "1,","222", "",2);

        Reservation.createReservation(startDate,endDate,c1,3);
        Reservation.createReservation(startDate,endDate,c1,2);
        for(Reservation r: Reservation.reservation){
            System.out.println(r);
        }
        Reservation.cancelReservation(startDate,endDate,"A8");

        for(Reservation r: Reservation.reservation){
            System.out.println(r);
        }
    }*/
    
    public static void test2() {
        try {
            Extents.loadAll();
        }catch(Exception e) {
            e.printStackTrace();
        }

        Menu created = new Menu(LocalDate.now().plusDays(1), LocalDate.now().plusDays(1));
        System.out.println(created.getMenuStatus());

        Menu valid = new Menu(LocalDate.now(), LocalDate.now().plusDays(1));
        System.out.println(valid.getMenuStatus());

        for(Menu m : Menu.getActiveMenus()){
            System.out.println(m.getMenuStatus());
        }

        try {
            Extents.saveAll();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //test1();
        //test2();
    }

    // TODO:
}
