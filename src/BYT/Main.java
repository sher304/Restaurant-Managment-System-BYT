package BYT;

import java.time.LocalDate;

// main() for testing purposes (not a unit test, not part of the assignment)
public class Main {
    public static void main(String[] args) {
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
}
