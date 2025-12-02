package ua.estate.model;

public enum PropertyStatus {
    FOR_SALE("Продається"),
    FOR_RENT("Орендується"),
    PENDING("Очікує"),
    SOLD("Продано"),
    RENTED("Орендовано");

    private final String displayName;

    PropertyStatus(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
