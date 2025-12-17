package BYT.Classes.Person;
import BYT.Classes.Order.Order;
import java.io.Serializable;
import java.util.*;

public class Waiter extends Employee implements Serializable {
    private static List<Waiter> extent = new ArrayList<>();

    private Set<Order> orders = new HashSet<>();

    public Waiter(Person person, long salary) {
        super(person, salary);
        extent.add(this);
    }

    public static Waiter findOrCreate(String firstName, String lastName, String phoneNumber, String email, long salary) {
        for (Waiter waiter : extent) {
            if (waiter.getPerson().getPhoneNumber().equals(phoneNumber)) {
                System.out.println("waiter has been found!");
                return waiter;
            }
        }

        Person newPerson = new Person(firstName, lastName, phoneNumber, email);

        return new Waiter(newPerson, salary);
    }

    public void addOrder(Order order) {
        if (order == null) throw new IllegalArgumentException("order cannot be null");

        if (!orders.contains(order)) {
            orders.add(order);
            order.setWaiter(this);
        }
    }

    public void removeOrder(Order order) {
        if (order == null) throw new IllegalArgumentException("order cannot be null");

        if (orders.contains(order)) {
            orders.remove(order);
            order.setWaiter(null);
        }
    }

    public Set<Order> getOrders() {
        return Collections.unmodifiableSet(orders);
    }

    @Override
    public void delete() {
        for (Order order : new ArrayList<>(orders)) {
            order.setWaiter(null);
        }
        orders.clear();
        super.delete();
        extent.remove(this);
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
