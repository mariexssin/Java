package ua.estate;

import ua.estate.model.*;
import ua.estate.service.FileService;
import ua.estate.util.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("--- ЛР 7: Початок роботи ---");
        String jsonPath = ConfigManager.get("data.path.json");
        String yamlPath = ConfigManager.get("data.path.yaml");
        int genCount = ConfigManager.getInt("generation.count");
        
        logger.info("Налаштування завантажено. JSON шлях: {}", jsonPath);
        List<Agent> agents = new ArrayList<>();
        agents.add(new Agent("Бонд", "Джеймс", "007@mi6.gov.uk"));
        agents.add(new Agent("Том", "Круз", "mission@impossible.com"));
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("Baker Street 221b", PropertyType.APARTMENT, 500000, 80.0, PropertyStatus.SOLD));
        properties.add(new Property("Fifth Avenue, NY", PropertyType.OFFICE, 1200000, 150.0, PropertyStatus.FOR_SALE));
        FileService<Agent> agentService = new FileService<>(Agent.class);
        FileService<Property> propertyService = new FileService<>(Property.class);

        try {
            System.out.println("\n--- Збереження ---");
            agentService.saveToJson(agents, jsonPath);
            propertyService.saveToYaml(properties, yamlPath);
            System.out.println("\n--- Завантаження ---");
            List<Agent> loadedAgents = agentService.loadFromJson(jsonPath);
            List<Property> loadedProperties = propertyService.loadFromYaml(yamlPath);
            System.out.println("\n--- Результати ---");
            System.out.println("Агенти рівні? " + agents.equals(loadedAgents));
            System.out.println("Нерухомість рівна? " + properties.equals(loadedProperties));

            System.out.println("\nЗавантажений агент: " + loadedAgents.get(0));
            System.out.println("Завантажена нерухомість: " + loadedProperties.get(1));

        } catch (Exception e) {
            logger.error("Критична помилка в Main", e);
        }
        
        logger.info("--- ЛР 7: Завершення роботи ---");
    }
}
