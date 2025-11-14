package ua.estate.model;

import ua.estate.exception.InvalidDataException;
import ua.estate.util.Utils;
import java.util.logging.Logger;
public record Agent(String firstName, String lastName, String contactInfo) {
    private static final Logger logger = Logger.getLogger(Agent.class.getName());
    public Agent {
        Utils.validateString(firstName, "Ім'я агента");
        Utils.validateString(lastName, "Прізвище агента");
        Utils.validateString(contactInfo, "Контакти агента");
        logger.info("Створено агента: " + firstName + " " + lastName);
    }
}
