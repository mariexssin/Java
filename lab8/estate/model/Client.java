package ua.estate.model;

import ua.estate.util.EntityValidator;

public record Client(String firstName, String lastName, String email) {

    public Client {
        EntityValidator validator = new EntityValidator("Client");

        validator.checkString(firstName, "firstName");
        validator.checkString(lastName, "lastName");
        
        validator.checkString(email, "email");
        if (email != null && !email.contains("@")) {
            validator.check(false, "email", "некоректний формат (відсутній @)");
        }

        validator.throwIfInvalid();
    }
}
