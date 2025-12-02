package ua.estate.util;

import java.text.NumberFormat;
import java.util.Locale;

public class Utils {
    public static String formatCurrency(double price) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(price);
    }
    
    public static void validateString(String input, String fieldName) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " не може бути порожнім.");
        }
    }
}
