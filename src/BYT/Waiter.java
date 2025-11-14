package BYT;

import java.util.ArrayList;
import java.util.List;

public class Waiter extends Employee {
    private static List<Waiter> extent = new ArrayList<>();

    public Waiter(String firstName, String lastName, String phoneNumber, String email, long salary) {
        super(firstName, lastName, phoneNumber, email, salary);
        extent.add(this);
    }
    // empty for now
}
