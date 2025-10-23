package ua.estate;

import ua.estate.model.Agent;
import ua.estate.model.Client;
import ua.estate.model.Property;
import ua.estate.model.PropertyType;
import ua.estate.util.Utils;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Лабораторна робота 1: Демонстрація ---");

        System.out.println("\n## 1. Створення об'єктів (Успішні сценарії) ##");

        Agent agent1 = new Agent("Іван", "Петренко", "+380501234567");
        Client client1 = new Client("Марія", "Сидоренко", "maria@example.com");

        Property prop1 = Property.createApartment("вул. Хрещатик, 1", 150000, 75.5);
        Property prop2 = Property.createOffice("вул. Велика Васильківська, 5", 500000, 200.0);

        System.out.println("Створено агента: " + agent1);
        System.out.println("Створено клієнта: " + client1);
        System.out.println("Створено нерухомість (1): " + prop1);
        System.out.println("Створено нерухомість (2): " + prop2);

        System.out.println("\n## 2. Робота валідації (Неуспішні сценарії) ##");

        try {
            Property propFail = new Property("Адреса", PropertyType.HOUSE, -100, 100);
        } catch (IllegalArgumentException e) {
            System.out.println("ПОМИЛКА (Ціна): " + e.getMessage());
        }

        try {
            Agent agentFail = new Agent("Ім'я", null, "123");
        } catch (IllegalArgumentException e) {
            System.out.println("ПОМИЛКА (Прізвище): " + e.getMessage());
        }

        try {
            client1.setEmail("invalid-email");
        } catch (IllegalArgumentException e) {
            System.out.println("ПОМИЛКА (Email): " + e.getMessage());
        }

        System.out.println("\n## 3. Демонстрація OOP та модифікаторів доступу ##");

        System.out.println("\n--- 3.1 Інкапсуляція ---");
        System.out.println("Доступ через public getter (Agent): " + agent1.getFirstName());

        System.out.println("\n--- 3.2 package-private vs protected ---");
        
        System.out.println("Використання public Utils: " + Utils.formatCurrency(99.99));
        System.out.println("Прямий доступ до 'ua.estate.util.ValidationHelper' з 'ua.estate' неможливий.");

        System.out.println("Доступ до 'protected' полів можливий через public геттери.");

        System.out.println("\n--- 3.3 Поліморфізм (equals/hashCode) ---");
        Agent agent2 = new Agent("Іван", "Петренко", "+380501234567");
        
        System.out.println("Агент 1: " + agent1);
        System.out.println("Агент 2: " + agent2);
        System.out.println("agent1.equals(agent2)? -> " + agent1.equals(agent2));
        System.out.println("HashCode agent1: " + agent1.hashCode());
        System.out.println("HashCode agent2: " + agent2.hashCode());
    }
}
