package ua.estate.model;

import ua.estate.exception.InvalidDataException;
import ua.estate.util.Utils;
import java.util.logging.Logger;

public record Client(String firstName, String lastName, String email) {
    private static final Logger logger = Logger.getLogger(Client.class.getName());
    public Client {
        Utils.validateString(firstName, "Ім'я клієнта");
        Utils.validateString(lastName, "Прізвище клієнта");
        Utils.validateEmail(email); // Специфічна валідація
        logger.info("Створено клієнта: " + firstName + " " + lastName);
    }
}
