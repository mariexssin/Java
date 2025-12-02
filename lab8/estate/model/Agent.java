package ua.estate.model;

import ua.estate.util.EntityValidator;

public record Agent(String firstName, String lastName, String contactInfo) {

    public Agent {
        EntityValidator validator = new EntityValidator("Agent");

        validator.checkString(firstName, "firstName");
        validator.checkString(lastName, "lastName");
        validator.checkString(contactInfo, "contactInfo");

        if (contactInfo != null) {
            validator.check(contactInfo.length() >= 10, "contactInfo", "має містити щонайменше 10 символів");
        }

        validator.throwIfInvalid();
    }
}
