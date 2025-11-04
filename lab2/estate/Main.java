package ua.estate;

import ua.estate.model.Agent;
import ua.estate.model.Client;
import ua.estate.model.Property;
import ua.estate.model.PropertyStatus;
import ua.estate.model.PropertyType;
import ua.estate.util.Utils;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Демонстрація: Records, Enums, Switch Expressions ---");

        System.out.println("\n## 1. Створення об'єктів Record та Enum ##");

        PropertyStatus statusForSale = PropertyStatus.FOR_SALE;

        Agent agent = new Agent("Віктор", "Ткаченко", "+380995554433");
        Client client = new Client("Олена", "Ковальчук", "olena.k@gmail.com");

        Property property = new Property(
                "вул. Прорізна, 15",
                PropertyType.APARTMENT,
                120000,
                55.0,
                statusForSale
        );

        System.out.println("Створено агента: " + agent);
        System.out.println("Створено клієнта: " + client);
        System.out.println("Створено нерухомість: " + property);
        System.out.println("Статус нерухомості: " + property.status().getDisplayName());

        System.out.println("\n## 2. Робота зі структурованими даними (Record) ##");

        System.out.println("Ім'я агента: " + agent.firstName());
        System.out.println("Email клієнта: " + client.email());
        System.out.println("Ціна нерухомості: " + Utils.formatCurrency(property.price()));

        Agent agentCopy = new Agent("Віктор", "Ткаченко", "+380995554433");
        System.out.println("agent.equals(agentCopy)? -> " + agent.equals(agentCopy));

        System.out.println("\n## 3. Демонстрація Switch ##");

        System.out.println("--- 3.1. Switch Expression (Java 14+) ---");

        double commission = property.calculateCommission();
        System.out.println("Комісія (з Property.calculateCommission): " + Utils.formatCurrency(commission));

        PropertyStatus statusSold = PropertyStatus.SOLD;
        String statusDescription = switch (statusSold) {
            case FOR_SALE, FOR_RENT -> "Об'єкт доступний на ринку.";
            case PENDING -> "Об'єкт в процесі угоди.";
            case SOLD, RENTED -> "Об'єкт більше не доступний.";
        };
        System.out.println("Опис статусу 'SOLD': " + statusDescription);


        System.out.println("\n--- 3.2. Традиційний Switch-Case ---");

        demoTraditionalSwitch(PropertyType.OFFICE);
        demoTraditionalSwitch(property.type());

        System.out.println("\n## 4. Валідація в Record (через компактний конструктор) ##");

        try {
            Agent agentFail = new Agent("Ім'я", "", "123");
        } catch (IllegalArgumentException e) {
            System.out.println("ПОМИЛКА (Валідація Record): " + e.getMessage());
        }

        try {
            Property propFail = new Property("Адреса", PropertyType.LAND, -100, 100, PropertyStatus.PENDING);
        } catch (IllegalArgumentException e) {
            System.out.println("ПОМИЛКА (Валідація Record): " + e.getMessage());
        }
    }

    public static void demoTraditionalSwitch(PropertyType type) {
        System.out.print("Перевірка (traditional switch) для " + type + ": ");
        switch (type) {
            case APARTMENT:
                System.out.println("Це житлова нерухомість.");
                break;
            case HOUSE:
                System.out.println("Це житлова нерухомість.");
                break;
            case OFFICE:
                System.out.println("Це комерційна нерухомість.");
                break;
            case LAND:
                System.out.println("Це земельна ділянка.");
                break;
            default:
                System.out.println("Невідомий тип.");
                break;
        }
    }
}
