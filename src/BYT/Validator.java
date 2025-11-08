package BYT;

public class Validator {
    
    public static String validateOptionalEmail(String email) {
        if (email == null) return null;

        String trimmedEmail = email.trim();
        if (trimmedEmail.isEmpty()) return null;
        if (!trimmedEmail.contains("@")) {
            throw new IllegalArgumentException("Email address is invalid, include'@ symbol.");
        }
        return trimmedEmail;
    }


    public static String validateAttributes(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("A value, should not be empty!");
        }
        return value.trim();
    }
}
