package ua.estate.repository.specific;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.estate.exception.InvalidDataException;
import ua.estate.model.Property;
import ua.estate.model.PropertyType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PropertyRepositoryTest {

    private PropertyRepository repository;

    @BeforeEach
    void setUp() throws InvalidDataException {
        repository = new PropertyRepository();
        repository.add(new Property("A-101", PropertyType.APARTMENT, 100_000, 50.0));
        repository.add(new Property("H-202", PropertyType.HOUSE, 250_000, 150.0));
        repository.add(new Property("O-303", PropertyType.OFFICE, 150_000, 80.0));
        repository.add(new Property("A-102", PropertyType.APARTMENT, 120_000, 60.0));
        repository.add(new Property("L-404", PropertyType.LAND, 50_000, 500.0));
    }

    @Test
    void testFindByPriceRange_Success() {
        List<Property> results = repository.findByPriceRange(90_000, 150_000);
        
        assertEquals(3, results.size(), "Повинно бути 3 об'єкти (100k, 150k, 120k).");
        assertTrue(results.stream().anyMatch(p -> p.address().equals("O-303")));
    }

    @Test
    void testFindByPriceRange_Empty() {
        List<Property> results = repository.findByPriceRange(500_000, 1_000_000);
        assertTrue(results.isEmpty());
    }
    
    @Test
    void testGetAverageAreaByType_Apartment() {
        double avg = repository.getAverageAreaByType(PropertyType.APARTMENT);
        assertEquals(55.0, avg, 0.01);
    }
    
    @Test
    void testGetAverageAreaByType_NonExistent() {
        double avg = repository.getAverageAreaByType(PropertyType.OFFICE);
        assertEquals(80.0, avg, 0.01); // Офіс O-303 має площу 80.0
    }
}
