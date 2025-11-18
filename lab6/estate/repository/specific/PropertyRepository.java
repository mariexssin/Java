package ua.estate.repository.specific;

import ua.estate.model.Property;
import ua.estate.model.PropertyType;
import ua.estate.repository.GenericRepository;
import ua.estate.repository.functional.IdentityExtractor;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class PropertyRepository extends GenericRepository<Property> {

    private static final Logger logger = Logger.getLogger(PropertyRepository.class.getName());
    
    private static final IdentityExtractor<Property> PROPERTY_IDENTITY_EXTRACTOR = Property::address;

    public PropertyRepository() {
        super(PROPERTY_IDENTITY_EXTRACTOR);
    }

    public List<Property> findByPriceRange(double minPrice, double maxPrice) {
        logger.info(String.format("Пошук нерухомості в діапазоні цін: [%.2f - %.2f]", minPrice, maxPrice));
        
        return getAll().stream() // Створення послідовного стріму
                .filter(p -> p.price() >= minPrice && p.price() <= maxPrice) // filter
                .collect(Collectors.toList()); // collect (термінальна операція)
    }

    public double getAverageAreaByType(PropertyType type) {
        logger.info("Обчислення середньої площі для типу: " + type);
        
        return getAll().stream()
                .filter(p -> p.type() == type)
                .mapToDouble(Property::area) // mapToDouble
                .average() // термінальна операція (повертає OptionalDouble)
                .orElse(0.0);
    }
}
