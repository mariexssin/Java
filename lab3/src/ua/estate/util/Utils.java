package ua.estate.util;

import ua.estate.exception.InvalidDataException;
import java.text.NumberFormat;
import java.util.Locale;

public class Utils {

    public static void validateString(String input, String fieldName) throws InvalidDataException {
        if (ValidationHelper.isStringInvalid(input)) {
            throw new InvalidDataException(fieldName + " не може бути порожнім.");
        }
    }

    public static void validatePositive(double value, String fieldName) throws InvalidDataException {
        if (ValidationHelper.isNegativeOrZero(value)) {
            throw new InvalidDataException(fieldName + " має бути додатним числом.");
        }
    }

    public static void validateEmail(String email) throws InvalidDataException {
        if (!ValidationHelper.isValidEmail(email)) {
            throw new InvalidDataException("Некоректний формат email: " + email);
        }
    }

    public static String formatCurrency(double price) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(price);
    }
}
