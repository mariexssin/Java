package ua.estate.model;

import ua.estate.util.Utils;
import java.util.Objects;

public record Property(String address, PropertyType type, double price, double area, PropertyStatus status) {

    public Property {
        Utils.validateString(address, "Адреса");
        Objects.requireNonNull(type, "Тип нерухомості не може бути null");
        Utils.validatePositive(price, "Ціна");
        Utils.validatePositive(area, "Площа");
        Objects.requireNonNull(status, "Статус нерухомості не може бути null");
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
        return "Property{" +
                "address='" + address + '\'' +
                ", type=" + type +
                ", price=" + Utils.formatCurrency(price) +
                ", area=" + area + " sq.m" +
                ", status=" + status +
                '}';
    }
}
