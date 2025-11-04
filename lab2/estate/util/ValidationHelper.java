package ua.estate.util;

class ValidationHelper {
    static boolean isStringInvalid(String str) {
        return str == null || str.trim().isEmpty();
    }
    static boolean isNegativeOrZero(double value) {
        return value <= 0;
    }
    static boolean isValidEmail(String email) {
        if (isStringInvalid(email)) return false;
        // Дуже проста перевірка для демонстрації
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }
}
