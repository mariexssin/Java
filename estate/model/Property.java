package ua.estate.model;

import ua.estate.util.Utils;
import java.util.Objects;

public class Property {

    private String address;
    private PropertyType type;
    private double price;
    private double area;

    public Property(String address, PropertyType type, double price, double area) {
        setAddress(address);
        setType(type);
        setPrice(price);
        setArea(area);
    }

    public static Property createApartment(String address, double price, double area) {
        return new Property(address, PropertyType.APARTMENT, price, area);
    }

    public static Property createOffice(String address, double price, double area) {
        return new Property(address, PropertyType.OFFICE, price, area);
    }

    public String getAddress() { return address; }
    public PropertyType getType() { return type; }
    public double getPrice() { return price; }
    public double getArea() { return area; }

    public void setAddress(String address) {
        Utils.validateString(address, "Адреса");
        this.address = address;
    }
    public void setType(PropertyType type) {
        Objects.requireNonNull(type, "Тип нерухомості не може бути null");
        this.type = type;
    }
    public void setPrice(double price) {
        Utils.validatePositive(price, "Ціна");
        this.price = price;
    }
    public void setArea(double area) {
        Utils.validatePositive(area, "Площа");
        this.area = area;
    }

    public String toString() {
        return "Property{" +
               "address='" + address + '\'' +
               ", type=" + type +
               ", price=" + Utils.formatCurrency(price) + 
               ", area=" + area + " sq.m" +
               '}';
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return Double.compare(property.price, price) == 0 &&
               Double.compare(property.area, area) == 0 &&
               Objects.equals(address, property.address) &&
               type == property.type;
    }

    public int hashCode() {
        return Objects.hash(address, type, price, area);
    }
}
