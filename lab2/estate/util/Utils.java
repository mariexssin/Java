package ua.estate.util;



import java.text.NumberFormat;

import java.util.Locale;

public class Utils {

    public static void validateString(String input, String fieldName) {

        if (ValidationHelper.isStringInvalid(input)) {

            throw new IllegalArgumentException(fieldName + " не може бути порожнім.");

        }

    }



    public static void validatePositive(double value, String fieldName) {

        if (ValidationHelper.isNegativeOrZero(value)) {

            // ВАЛІДАЦІЯ

            throw new IllegalArgumentException(fieldName + " має бути додатним числом.");

        }

    }

    public static void validateEmail(String email) {

        if (!ValidationHelper.isValidEmail(email)) {

            throw new IllegalArgumentException("Некоректний формат email: " + email);

        }

    }



    public static String formatCurrency(double price) {

        return NumberFormat.getCurrencyInstance(Locale.US).format(price);

    }

}
