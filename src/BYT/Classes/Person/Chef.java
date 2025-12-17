package BYT.Classes.Person;

import BYT.Classes.Order.Order;
import BYT.Classes.Order.OrderStatus;
import BYT.Helpers.Validator;

import java.io.Serializable;
import java.util.*;

public class Chef extends Employee implements Serializable {
    private static final List<Chef> extent = new ArrayList<>();

    private Chef supervisor;
    private final List<Chef> supervisedChefs = new ArrayList<>();

    private final List<Order> involvedIn = new ArrayList<>();
    private final List<Order> responsibleFor = new ArrayList<>();

    public Chef(Person person, long salary) {
        super(person, salary);
        extent.add(this);
    }


    public static Chef findOrCreate(String firstName, String lastName, String phoneNumber, String email, long salary) {
        for (Chef chef : extent) {
            if (chef.getPerson().getPhoneNumber().equals(phoneNumber)) {
                System.out.println("Chef has been found!");
                return chef;
            }
        }
        Person newPerson = new Person(firstName, lastName, phoneNumber, email);

        return new Chef(newPerson, salary);
    }

    //Supervisor (REFLEXIVE Association)

    public Chef getSupervisor() {
        return supervisor;
    }

    public List<Chef> getSupervisedChefs() {
        return Collections.unmodifiableList(supervisedChefs);
    }

    public void setSupervisor(Chef newSupervisor) {
        Validator.validateNullObjects(newSupervisor);

        //generated to Prevent supervision cycles: A -> B -> C -> A
        if (createsLoop(newSupervisor))
            throw new IllegalArgumentException("Supervision loop detected! Operation not allowed.");

        if(this.supervisor != null) {
            this.supervisor.supervisedChefs.remove(this);
        }

        this.supervisor = newSupervisor;

        if (newSupervisor != null && !newSupervisor.supervisedChefs.contains(this)) {
            newSupervisor.supervisedChefs.add(this);
        }
    }

    public void addSupervisedChef(Chef chef) {
        if (chef == null) return;
        chef.setSupervisor(this);
    }

    public void removeSupervisedChef(Chef chef){
        if(chef == null) return;

        if(supervisedChefs.remove(chef)){
            chef.supervisor = null;
        }
    }

    @Override
    public void delete() {
        for (Chef subordinate : new ArrayList<>(supervisedChefs)) {
            subordinate.setSupervisor(null);
        }
        supervisedChefs.clear();

        if (this.supervisor != null) {
            this.supervisor.removeSupervisedChef(this);
            this.supervisor = null;
        }

        super.delete();
        extent.remove(this);
    }

    // Detect a loop by climbing the supervisor chain
    //generated
    private boolean createsLoop(Chef potentialSupervisor) {
        Chef current = potentialSupervisor;

        while (current != null) {
            if (current == this)
                return true; // loop found
            current = current.supervisor;
        }
        return false;
    }

    // Chef-Order Association

    public List<Order> getInvolvedInOrders() {
        return Collections.unmodifiableList(involvedIn);
    }

    public List<Order> getResponsibleForOrders() {
        return Collections.unmodifiableList(responsibleFor);
    }

    private void addInvolvedOrder(Order order) {
        if (order == null) throw new IllegalArgumentException("Order cannot be null");
        if (!involvedIn.contains(order)) {
            involvedIn.add(order);
        }
    }

    private void addResponsibleOrder(Order order) {
        if (order == null) throw new IllegalArgumentException("Order cannot be null");
        if (!responsibleFor.contains(order)) {
            responsibleFor.add(order);
        }
    }

    public void makeChefResponsibleForOrder(Order order) {
        if (order == null)
            throw new IllegalArgumentException("Order cannot be null");

        if (!involvedIn.contains(order))
            throw new IllegalArgumentException("Chef must be involved in order to be responsible for it");

        if (order.getStatus() != OrderStatus.CREATED)
            throw new IllegalArgumentException("Order must be in CREATED state");

        order.setChef(this);
        order.setStatus(OrderStatus.InPREPARATION);

        addResponsibleOrder(order);
    }

    public void markOrderAsPrepared(int choice) {
        if (choice < 0 || choice >= responsibleFor.size())
            throw new IllegalArgumentException("invalid index");

        Order order = responsibleFor.get(choice);

        if (order.getStatus() != OrderStatus.InPREPARATION)
            throw new IllegalArgumentException("Order must be in IN PREPARATION state");

        order.setStatus(OrderStatus.PREPARED);

    }

    public void addChefInvolvementFromOrder(Order order) {
        addInvolvedOrder(order);
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
