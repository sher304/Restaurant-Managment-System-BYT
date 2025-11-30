package BYT.Helpers;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Validator {

    public static Object validateNullObjects(Object object) throws IllegalArgumentException{
        if (object == null) throw new IllegalArgumentException("Object can not be null!");
        return object;
    }

    public static void validateMenuDate(LocalDate releaseDate, LocalDate endDate) throws IllegalArgumentException {
        validateDate(releaseDate, endDate, "releaseDate", "endDate");
    }

    public static void validateReservationDate(LocalDateTime startAt, LocalDateTime endAt) throws IllegalArgumentException {

    }

    private static void validateDate(LocalDate releaseDate, LocalDate endDate, String releaseDateName, String endDateName) throws IllegalArgumentException {
        if (releaseDate == null || endDate == null)
            throw new DateTimeException("Dates must not be null");

        LocalDate today = LocalDate.now();

        if (today.isAfter(releaseDate))
            throw new IllegalArgumentException("The releaseDate cannot be *before* today. It must be at the earliest equal to today.");

        if (today.isAfter(endDate))
            throw new IllegalArgumentException("The endDate cannot be *before* today. It must be at the earliest equal to today.");

        if (endDate.isBefore(releaseDate))
            throw new IllegalArgumentException("The endDate cannot be before the releaseDate. The endDate must be equal (1-day menu) or after the releaseDate.");
    }

    public static int validateNumberOfPeople(int people, int max) throws IllegalArgumentException {
        if(!(people > 0)){
            throw new IllegalArgumentException("Number of people must be greater than zero");
        }

        if(people > max){
            throw new IllegalArgumentException("Number of people cannot be greater than the maximum number of people for a table");
        }

        return people;
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

    public static long validatePrice(long price) throws IllegalArgumentException {
        if(price < 0) {
            throw new IllegalArgumentException("Price can not be negative");
        }
        if(price == 0){
            throw new IllegalArgumentException("Price can not be zero");
        }
        return price;
    }

    public static long validateBaseSalary(long baseSalary) throws IllegalArgumentException {
        if (baseSalary <= 0) {
            throw new IllegalArgumentException("Salary must be positive");
        }
        return baseSalary;
    }

    public static int validateNonZeroPhysicalAttribute(int amount) throws IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Physical attributes of items (volumes, weights) must be positive");
        }
        return amount;
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

    public static String validatePhoneNumber(String phoneNumber) throws IllegalArgumentException {
        if (phoneNumber == null) throw new IllegalArgumentException("Phone number can not be null");

        // delete spaces or - for validation and storage
        String trimmedPhoneNumber = phoneNumber.replaceAll("\\s+", "").replaceAll("-+", "");
        if(trimmedPhoneNumber.isEmpty())
            throw new IllegalArgumentException("Phone number cannot be empty");

        /*
        ^ – start of string
        \\+ – literal +
        [1-9] – country code first digit can’t start with 0
        \\d{3,49} – 3 to 49 more digits (so total 4–50 digits)
        $ – end of string
         */
        String phoneRegexPattern = "^\\+[1-9]\\d{3,49}$";

        if (trimmedPhoneNumber.matches(phoneRegexPattern)) {
            return trimmedPhoneNumber;
        }else{
            throw new IllegalArgumentException("Invalid phone number");
        }
    }

    public static String validateAttributes(String value) throws IllegalArgumentException {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("A value, should not be empty!");
        }
        return value.trim();
    }

    public static String validateOptionalAttributes(String value) throws IllegalArgumentException {
        if(value == null){
            return null;
        }
        if (value.trim().isEmpty()) {
            throw new IllegalArgumentException("If present, a optional string attribute cannot be empty.");
        }
        return value.trim();
    }
}
