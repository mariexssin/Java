package ua.estate.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.estate.exception.InvalidDataException;
import java.util.ArrayList;
import java.util.List;

public class EntityValidator {
    private static final Logger logger = LoggerFactory.getLogger(EntityValidator.class);
    private final String entityName;
    private final List<String> errors = new ArrayList<>();

    public EntityValidator(String entityName) {
        this.entityName = entityName;
    }

    public void checkString(String value, String field) {
        if (value == null || value.trim().isEmpty()) errors.add(field + ": порожнє");
    }

    public void checkPositive(double value, String field) {
        if (value <= 0) errors.add(field + ": має бути > 0");
    }

    public void checkNotNull(Object value, String field) {
        if (value == null) errors.add(field + ": null");
    }

    public void throwIfInvalid() {
        if (!errors.isEmpty()) {
            String allErrors = String.join("; ", errors);
            logger.error("Валідація не пройшла {}: {}", entityName, allErrors);
            throw new InvalidDataException(allErrors);
        }
    }
}
