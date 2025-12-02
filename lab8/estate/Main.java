package ua.estate;

import ua.estate.exception.InvalidDataException;
import ua.estate.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final List<Object> repository = new ArrayList<>();

    public static void main(String[] args) {
        logger.info("--- ЛР 8: Запуск демонстрації валідації ---");
        System.out.println("\n=== 1. Успішне створення об'єктів ===");
        try {
            Agent validAgent = new Agent("Джеймс", "Бонд", "007-MI6-Agent");
            Property validProp = new Property("Лондон, Бейкер стріт", PropertyType.APARTMENT, 500000, 85, PropertyStatus.SOLD);
            
            addToRepo(validAgent);
            addToRepo(validProp);
        } catch (InvalidDataException e) {
            logger.error("Несподівана помилка: " + e.getMessage());
        }

        System.out.println("\n=== 2. Спроба створити невалідного агента ===");
        try {
            new Agent("", null, "123");
        } catch (InvalidDataException e) {
            System.err.println(">> ПЕРЕХОПЛЕНО: " + e.getMessage());
        }

        System.out.println("\n=== 3. Спроба створити невалідну нерухомість ===");
        try {
            new Property("   ", null, -100.0, 0.0, PropertyStatus.FOR_SALE);
        } catch (InvalidDataException e) {
            System.err.println(">> ПЕРЕХОПЛЕНО: " + e.getMessage());
        }

        System.out.println("\n=== Кінець демонстрації ===");
    }

    private static void addToRepo(Object obj) {
        repository.add(obj);
        System.out.println("Збережено в базу: " + obj);
    }
}
