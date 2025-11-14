import org.junit.jupiter.api.Test;
import ua.estate.exception.InvalidDataException;
import ua.estate.model.Agent;
import ua.estate.model.Property;
import ua.estate.model.PropertyType;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    @Test
    void testAgentCreationSuccess() {
        assertDoesNotThrow(() -> {
            new Agent("Ім'я", "Прізвище", "12345");
        });
    }

    @Test
    void testAgentCreationFail_InvalidData() {

        Exception exception = assertThrows(InvalidDataException.class, () -> {
            new Agent("Ім'я", null, "12345");
        });

        String expectedMessage = "Прізвище агента не може бути порожнім.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testPropertyPriceLogic() {
        assertThrows(InvalidDataException.class, () -> {
            Property prop = new Property("Адреса", PropertyType.LAND, 100, 100);
            prop.setPrice(-50.0); 
        });
    }
}
