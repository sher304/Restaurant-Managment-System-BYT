package BYT.Tests;

import BYT.Classes.Person.Chef;
import BYT.Classes.Person.Waiter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import BYT.Classes.Person.Employee;

public class EmployeeTest extends TestBase<Employee> {

    protected EmployeeTest() {
        super(Employee.class);
    }

    // no persistence test - abstract class

    @Test
    void emailCannotBeEmptyTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Employee emp = new Chef("John", "Doe", "+49 12 4567 8901", "", 8000);
        });
    }

    @Test
    void salaryCannotBeLowerThanBaseSalaryTest() {
        Employee.setBaseSalary(6000);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Employee waiter = new Waiter("John", "Doe", "+33 1 23 45 67 89",
                    "example@gmail.com", 4000);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Employee waiter = new Waiter("John", "Doe", "+33 1 23 45 67 89",
                    "example@gmail.com", 0);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Employee waiter = new Waiter("John", "Doe", "+33 1 23 45 67 89",
                    "example@gmail.com", -1000);
        });
    }

    @Test
    void cannotSetSalaryLowerThanBaseSalaryTest() {
        Employee emp = new Waiter("John", "Doe", "+48 555 555 555",
                "example@gmail.com", 8000);
        Employee.setBaseSalary(6000);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            emp.setSalary(5000);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            emp.setSalary(0);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            emp.setSalary(-1000);
        });
    }

    @Test
    void cannotSetBaseSalaryNegativeTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Employee.setBaseSalary(-1000);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Employee.setBaseSalary(-3000);
        });
    }

    @Test
    void cannotSetBaseSalaryZeroTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Employee.setBaseSalary(0);
        });
    }
}
