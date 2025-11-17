package BYT.Classes.Person;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Chef extends Employee implements Serializable {
    private static final List<Chef> extent = new ArrayList<>();

    public Chef(String firstName, String lastName, String phoneNumber, String email, long salary) {
        super(firstName, lastName, phoneNumber, email, salary);
        extent.add(this);
    }

    public static Chef findOrCreate(String firstName, String lastName, String phoneNumber, String email, long salary) {
        for (Chef chef : extent) {
            if (chef.getPhoneNumber().equals(phoneNumber)) {
                System.out.println("Chef has been found!");
                return chef;
            }
        }
        return new Chef(firstName, lastName, phoneNumber, email, salary);
    }

    // empty for now
}
