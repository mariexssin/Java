package ua.estate;

import ua.estate.exception.InvalidDataException;
import ua.estate.model.*;
import ua.estate.repository.GenericRepository;

import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        logger.info("--- Запуск демонстрації репозиторію ---");

        GenericRepository<Agent> agentRepo = new GenericRepository<>(agent -> agent.contactInfo());
        
        GenericRepository<Client> clientRepo = new GenericRepository<>(client -> client.email());

        GenericRepository<Property> propertyRepo = new GenericRepository<>(prop -> prop.getAddress());

        try {
            Agent agent1 = new Agent("Іван", "Петренко", "+380501112233");
            Client client1 = new Client("Марія", "Сидоренко", "maria@example.com");
            Property prop1 = new Property("вул. Хрещатик, 1", PropertyType.APARTMENT, 150000, 75.5);

            agentRepo.add(agent1);
            clientRepo.add(client1);
            propertyRepo.add(prop1);

        } catch (InvalidDataException e) {
            logger.severe("Помилка валідації при створенні: " + e.getMessage());
        }

        System.out.println("\n--- Вміст репозиторіїв ---");
        System.out.println("Агенти: " + agentRepo.getAll().size());
        System.out.println("Клієнти: " + clientRepo.getAll().size());
        System.out.println("Нерухомість: " + propertyRepo.getAll().size());

        System.out.println("\n--- Демонстрація пошуку ---");
        String agentId = "+380501112233";
        Agent foundAgent = agentRepo.findByIdentity(agentId);
        if (foundAgent != null) {
            System.out.println("Знайдено агента за ID (" + agentId + "): " + foundAgent.firstName());
        } else {
            System.out.println("Агента за ID (" + agentId + ") не знайдено.");
        }

        String clientId = "non-existent@example.com";
        Client foundClient = clientRepo.findByIdentity(clientId);
        System.out.println("Пошук неіснуючого клієнта (" + clientId + "): " + foundClient);

        System.out.println("\n--- Демонстрація дублікатів ---");
        try {
            Agent agentDuplicate = new Agent("Петро", "Іванов", "+380501112233");
            
            System.out.println("Спроба додати дублікат агента...");
            agentRepo.add(agentDuplicate); // Цей об'єкт не буде додано
            
            System.out.println("Загальна кількість агентів: " + agentRepo.getAll().size());

        } catch (InvalidDataException e) {
            logger.severe("Помилка валідації: " + e.getMessage());
        }
        
        logger.info("--- Демонстрація завершена ---");
    }
}
