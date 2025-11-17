package BYT.Classes.Person;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Waiter extends Employee implements Serializable {
    private static List<Waiter> extent = new ArrayList<>();

    public Waiter(String firstName, String lastName, String phoneNumber, String email, long salary) {
        super(firstName, lastName, phoneNumber, email, salary);
        extent.add(this);
    }

    public static Waiter findOrCreate(String firstName, String lastName, String phoneNumber, String email, long salary) {
        for (Waiter waiter : extent) {
            if (waiter.getPhoneNumber().equals(phoneNumber)) {
                System.out.println("waiter has been found!");
                return waiter;
            }
        }

        return new Waiter(firstName, lastName, phoneNumber, email, salary);
    }


    // empty for now
}
