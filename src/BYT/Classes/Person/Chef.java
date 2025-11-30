package BYT.Classes.Person;

import BYT.Classes.Order.Order;
import BYT.Classes.Order.OrderStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Chef extends Employee implements Serializable {
    private static final List<Chef> extent = new ArrayList<>();
    private ArrayList<Chef> supervisedChefs=new ArrayList<Chef>();

    public ArrayList<Chef> getSupervisedChefs() {
        return supervisedChefs;
    }
    public ArrayList<Order> responsibleFor=new ArrayList<Order>();
    public void addOrderToChef(Order order)throws Exception{
        if(order.getStatus()==OrderStatus.CREATED){
            order.setStatus(OrderStatus.InPREPARATION);
            this.responsibleFor.add(order);
        }else{
            throw new IllegalArgumentException("Order status must be created : ");
        }

    }
    public void markOrderAsPrepared(){
        Scanner input=new Scanner(System.in);
        System.out.println("Enter Order ID: ");
        int choice=input.nextInt();
        for(int i=0;i<responsibleFor.size();i++){
            System.out.println(i+" "+responsibleFor.get(i));
        }
        if(responsibleFor.get(choice).getStatus()==OrderStatus.InPREPARATION){
            responsibleFor.get(choice).setStatus(OrderStatus.PREPARED);
        }else{
            throw new IllegalArgumentException("Order status must be Inpraparation : ");
        }

    }

    public void setSupervisedChefs(ArrayList<Chef> supervisedChefs) {
        for (Chef chef : supervisedChefs) {
            if (chef == this) {
                throw new IllegalArgumentException("A Chef cannot supervise himself");
            }
        }
        this.supervisedChefs = supervisedChefs;
    }
    public void addSupervisedChef(Chef chef) {
        if (chef == null) {
            throw new IllegalArgumentException("Chef cannot be null");
        }

        if (chef == this) {
            throw new IllegalArgumentException("A Chef cannot supervise himself");
        }

        if (!supervisedChefs.contains(chef)) {
            supervisedChefs.add(chef);
        }
    }

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
