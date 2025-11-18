package ua.estate.model;

import ua.estate.exception.InvalidDataException;
import ua.estate.util.Utils;
import java.util.Comparator;
import java.util.logging.Logger;

public record Agent(String firstName, String lastName, String contactInfo) implements Comparable<Agent> {

    private static final Logger logger = Logger.getLogger(Agent.class.getName());

    public static final Comparator<Agent> BY_FIRST_NAME = Comparator.comparing(Agent::firstName);

    public static final Comparator<Agent> BY_CONTACT_INFO = (a1, a2) -> a1.contactInfo().compareTo(a2.contactInfo());

    public Agent {
        Utils.validateString(firstName, "Ім'я агента");
        Utils.validateString(lastName, "Прізвище агента");
        Utils.validateString(contactInfo, "Контакти агента");
        logger.info("Створено агента: " + firstName + " " + lastName);
    }

    @Override
    public int compareTo(Agent other) {
        return this.lastName.compareTo(other.lastName);
    }
}
