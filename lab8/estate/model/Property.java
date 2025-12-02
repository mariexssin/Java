package ua.estate.model;

import ua.estate.util.EntityValidator;
import ua.estate.util.Utils;

public record Property(String address, PropertyType type, double price, double area, PropertyStatus status) {

    public Property {
        EntityValidator validator = new EntityValidator("Property");

        validator.checkString(address, "address");
        validator.checkNotNull(type, "type");
        validator.checkPositive(price, "price");
        validator.checkPositive(area, "area");
        validator.checkNotNull(status, "status");

        validator.throwIfInvalid();
    }

    public double calculateCommission() {
        double commissionRate = switch (type) {
            case APARTMENT, HOUSE -> 0.05;
            case OFFICE -> 0.08;
            case LAND -> 0.03;
        };
        return price * commissionRate;
    }

    @Override
    public String toString() {
        String priceStr = (price > 0) ? String.format("%.2f", price) : "0.00";
        try {
             priceStr = Utils.formatCurrency(price);
        } catch (Exception e) {
        }

        return "Property{" +
                "address='" + address + '\'' +
                ", type=" + type +
                ", price=" + priceStr +
                ", area=" + area +
                ", status=" + status +
                '}';
    }
}
