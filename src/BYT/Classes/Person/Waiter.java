package BYT.Classes.Person;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        // Cast after superclass check
        Waiter chef = (Waiter) o;
        // Compare Waiter-specific fields here if we add new ones
        // Since Waiter currently has no new fields, they’re automatically equal
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }
}
