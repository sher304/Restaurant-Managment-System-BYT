package BYT;

import java.util.ArrayList;
import java.util.List;

public class Chef extends Employee {
    private static final List<Chef> extent = new ArrayList<>();

    public Chef(String firstName, String lastName, String phoneNumber, String email, long salary) {
        super(firstName, lastName, phoneNumber, email, salary);
        extent.add(this);
    }
    // empty for now
}
