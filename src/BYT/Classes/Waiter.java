package BYT.Classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Waiter extends Employee implements Serializable {
    private static List<Waiter> extent = new ArrayList<>();

    public Waiter(String firstName, String lastName, String phoneNumber, String email, long salary) {
        super(firstName, lastName, phoneNumber, email, salary);
        extent.add(this);
    }
    // empty for now
}
