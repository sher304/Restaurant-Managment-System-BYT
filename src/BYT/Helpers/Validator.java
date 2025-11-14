package BYT.Helpers;

import java.time.DateTimeException;
import java.time.LocalDate;

public class Validator {

    public static void validateDate(LocalDate releaseDate, LocalDate endDate) throws IllegalArgumentException { // I think IllegalArgumentException fits better. DateTimeException is for errors when manipulating date objects
        if (releaseDate == null || endDate == null) {
            throw new DateTimeException("Dates must not be null");
        }

        LocalDate today = LocalDate.now();

        if (today.isAfter(releaseDate))
            throw new IllegalArgumentException("The releaseDate cannot be *before* today. It must be at the earliest equal to today.");

        if (today.isAfter(endDate))
            throw new IllegalArgumentException("The endDate cannot be *before* today. It must be at the earliest equal to today.");

        if (endDate.isBefore(releaseDate))
            throw new IllegalArgumentException("The endDate cannot be before the releaseDate. The endDate must be equal (1-day menu) or after the releaseDate.");
    }

    public static double negativeNumberEntered(double value) throws IllegalArgumentException {
        if (value < 0) {
            throw new IllegalArgumentException("Value can not be negative!");
        }
        return value;
    }

    public static int negativeNumberEntered(int value) throws IllegalArgumentException {
        if (value < 0) {
            throw new IllegalArgumentException("Value can not be negative!");
        }
        return value;
    }

    public static long negativeNumberEntered(long value) throws IllegalArgumentException {
        if (value < 0) {
            throw new IllegalArgumentException("Value can not be negative!");
        }
        return value;
    }

    public static long validateSalary(long salary, long baseSalary) throws IllegalArgumentException {
        if (salary < baseSalary) {
            throw new IllegalArgumentException("Salary must be greater or equal to base salary");
        }
        return salary;
    }

    public static long validateBaseSalary(long baseSalary) throws IllegalArgumentException {
        if (baseSalary <= 0) {
            throw new IllegalArgumentException("Salary must be positive");
        }
        return baseSalary;
    }

    public static long validateNonZeroPhysicalAttribute(long amount) throws IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Physical attributes of items (volumes, weights) must be positive");
        }
        return amount;
    }

    public static String validateOptionalEmail(String email) throws IllegalArgumentException {
        if (email == null) return null;

        String trimmedEmail = email.trim();
        if (trimmedEmail.isEmpty())
            throw new IllegalArgumentException("If present, Email cannot be empty. Null should be used to mark no Email");

        // Email validation regex from RFC5322
        // (https://www.rfc-editor.org/rfc/pdfrfc/rfc5322.txt.pdf)
        String emailRegexPattern = "^[a-zA-Z0-9_!#$%&'*+/=?``{|}~^.-]+@[a-zA-Z0-9.-]+$";

        if (email.matches(emailRegexPattern)) {
            return trimmedEmail;
        } else {
            throw new IllegalArgumentException("Invalid email format");
        }
    }


    public static String validateAttributes(String value) throws IllegalArgumentException {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("A value, should not be empty!");
        }
        return value.trim();
    }
}
