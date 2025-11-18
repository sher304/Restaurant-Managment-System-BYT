package BYT.Classes.Person;

import BYT.Helpers.Validator;

import java.io.Serializable;
import java.util.Objects;

public abstract class Employee extends Person implements Serializable {
    // no extent - abstract class
    private long salary;
    private static long baseSalary = 6000;

    public Employee(String firstName, String lastName, String phoneNumber, String email, long salary) {
        super(firstName, lastName, phoneNumber, email);
        this.salary = Validator.validateSalary(salary, baseSalary);
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = Validator.validateSalary(salary, baseSalary);
    }

    public static long getBaseSalary() {
        return baseSalary;
    }

    public static void setBaseSalary(long baseSalary) throws IllegalArgumentException {
        Employee.baseSalary = Validator.validateBaseSalary(baseSalary);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "salary=" + salary +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Employee employee = (Employee) o;
        return salary == employee.salary;
    }
}
