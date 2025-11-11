package BYT;

// this inheritance is {Dynamic, Overlapping} so I think we need to use a different method
// we can't do extends, we have to "emulate" it somehow - not for this assignment
public abstract class Employee extends Person {
    private long salary;
    private static long baseSalary = 6000;

    public Employee(String firstName, String lastName, String phoneNumber, String email, long salary) {
        super(firstName, lastName, phoneNumber, email);
        this.salary = Validator.validateSalary(salary);
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = Validator.validateSalary(salary);
    }

    public static long getBaseSalary() {
        return baseSalary;
    }

    public static void setBaseSalary(long baseSalary) {
        Employee.baseSalary = Validator.validateSalary(baseSalary);
    }
}
