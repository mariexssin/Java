package ua.estate.model;

import ua.estate.util.Utils;

public record Agent(String firstName, String lastName, String contactInfo) {

    public Agent {
        Utils.validateString(firstName, "Ім'я");
        Utils.validateString(lastName, "Прізвище");
        Utils.validateString(contactInfo, "Контактна інформація");
    }
}
