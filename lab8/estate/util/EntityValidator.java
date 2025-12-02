package ua.estate.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.estate.exception.InvalidDataException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class EntityValidator {
    private static final Logger logger = LoggerFactory.getLogger(EntityValidator.class);
    
    private final String entityName;
    private final List<String> errors = new ArrayList<>();

    public EntityValidator(String entityName) {
        this.entityName = entityName;
        logger.info("Початок створення/валідації об'єкта: {}", entityName);
    }

    public void check(boolean condition, String field, String errorMessage) {
        if (!condition) {
            errors.add(field + ": " + errorMessage);
        }
    }

    public void checkString(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            errors.add(field + ": не може бути порожнім");
        }
    }

    public void checkPositive(double value, String field) {
        if (value <= 0) {
            errors.add(field + ": має бути більше 0");
        }
    }

    public void checkNotNull(Object value, String field) {
        if (value == null) {
            errors.add(field + ": не може бути null");
        }
    }

    public void throwIfInvalid() {
        if (!errors.isEmpty()) {
            String allErrors = String.join("; ", errors);
            logger.error("Помилка валідації {}: [{}]", entityName, allErrors);
            throw new InvalidDataException("Помилки валідації для " + entityName + " -> " + allErrors);
        }
        logger.info("Успішно створено валідний об'єкт: {}", entityName);
    }
}
