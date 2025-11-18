package BYT.Classes.Person;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        // Cast after superclass check
        Chef chef = (Chef) o;
        // Compare Chef-specific fields here if we add new ones
        // Since Chef currently has no new fields, they’re automatically equal
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }

}
