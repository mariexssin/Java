package ua.estate.model;

import ua.estate.exception.InvalidDataException;
import ua.estate.util.Utils;
import java.util.Comparator;
import java.util.logging.Logger;

public record Property(String address, PropertyType type, double price, double area) implements Comparable<Property> {
    
    private static final Logger logger = Logger.getLogger(Property.class.getName());

    public static final Comparator<Property> BY_AREA = Comparator.comparingDouble(Property::area);

    public static final Comparator<Property> BY_TYPE = Comparator.comparing(Property::type);

    public Property throws InvalidDataException {
        Utils.validateString(address, "Адреса нерухомості");
        Utils.validatePositive(price, "Ціна");
        Utils.validatePositive(area, "Площа");

        logger.info("Створено нерухомість: " + address);
    }

    @Override
    public int compareTo(Property other) {
        return Double.compare(this.price, other.price);
    }
}
