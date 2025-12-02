package ua.estate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.estate.exception.InvalidDataException;
import ua.estate.model.Agent;
import ua.estate.model.Property;
import ua.estate.model.PropertyStatus;
import ua.estate.model.PropertyType;

class ValidationTest {

    @Test
    void testValidObjectCreation() {
        Assertions.assertDoesNotThrow(() -> {
            new Agent("Test", "User", "long_contact_info");
        });
    }

    @Test
    void testAgentValidationErrors() {
        InvalidDataException exception = Assertions.assertThrows(InvalidDataException.class, () -> {
            new Agent("", "ValidName", "12"); 
        });

        String msg = exception.getMessage();
        Assertions.assertTrue(msg.contains("firstName"));
        Assertions.assertTrue(msg.contains("contactInfo"));
    }

    @Test
    void testPropertyValidationErrors() {
        InvalidDataException exception = Assertions.assertThrows(InvalidDataException.class, () -> {
            new Property(null, null, -500, 0, null);
        });

        String msg = exception.getMessage();
        System.out.println("Test Logs: " + msg);
        
        Assertions.assertTrue(msg.contains("address"));
        Assertions.assertTrue(msg.contains("type"));
        Assertions.assertTrue(msg.contains("price"));
        Assertions.assertTrue(msg.contains("status"));
    }
}
