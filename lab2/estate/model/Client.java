package ua.estate.model;

import ua.estate.util.Utils;

public record Client(String firstName, String lastName, String email) {

    public Client {
        Utils.validateString(firstName, "Ім'я");
        Utils.validateString(lastName, "Прізвище");
        Utils.validateEmail(email);
    }
}
