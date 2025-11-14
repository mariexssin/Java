package ua.estate.model;

import ua.estate.exception.InvalidDataException;
import ua.estate.util.Utils;
import java.util.Objects;
import java.util.logging.Logger;

public class Property {

    private static final Logger logger = Logger.getLogger(Property.class.getName());

    private String address;
    private PropertyType type;
    private double price;
    private double area;
    public Property(String address, PropertyType type, double price, double area) throws InvalidDataException {
        // ВАЛІДАЦІЯ у конструкторі через сеттери
        setAddress(address);
        setType(type);
        setPrice(price);
        setArea(area);
        logger.info("Створено нерухомість: " + address);
    }
    
    public String getAddress() { return address; }
    public PropertyType getType() { return type; }
    public double getPrice() { return price; }
    public double getArea() { return area; }

    public void setAddress(String address) throws InvalidDataException {
        Utils.validateString(address, "Адреса");
        this.address = address;
    }
    public void setType(PropertyType type) {
        Objects.requireNonNull(type, "Тип нерухомості не може бути null");
        this.type = type;
    }
    public void setPrice(double price) throws InvalidDataException {
        Utils.validatePositive(price, "Ціна");
        this.price = price;
    }
    public void setArea(double area) throws InvalidDataException {
        Utils.validatePositive(area, "Площа");
        this.area = area;
    }
    
    @Override
    public String toString() {
        return "Property{" +
               "address='" + address + '\'' +
               ", type=" + type +
               ", price=" + Utils.formatCurrency(price) + 
               ", area=" + area + " sq.m" +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return Double.compare(property.price, price) == 0 &&
               Double.compare(property.area, area) == 0 &&
               Objects.equals(address, property.address) &&
               type == property.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, type, price, area);
    }
}
