package ua.estate;

import ua.estate.exception.InvalidDataException;
import ua.estate.model.Agent;
import ua.estate.repository.GenericRepository;
import ua.estate.repository.specific.AgentRepository;

import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static <T extends Agent> void printCollection(String title, GenericRepository<T> repo) {
        System.out.println("\n--- " + title + " ---");
        repo.getAll().forEach(agent -> System.out.println(agent.lastName() + " " + agent.firstName()));
    }

    public static void main(String[] args) {
        logger.info("--- Запуск демонстрації сортування ---");

        AgentRepository agentRepo = new AgentRepository();

        try {
            agentRepo.add(new Agent("Антон", "Зозуля", "+380509000000"));
            agentRepo.add(new Agent("Богдан", "Іванов", "+380501111111"));
            agentRepo.add(new Agent("Віктор", "Петренко", "+380502222222"));
            agentRepo.add(new Agent("Сергій", "Коваленко", "+380503333333"));
            
        } catch (InvalidDataException e) {
            logger.severe("Помилка валідації при створенні: " + e.getMessage());
            return;
        }

        agentRepo.sortByLastName("ASC");
        printCollection("Сортування за ПРІЗВИЩЕМ (ASC)", agentRepo);

        agentRepo.sortByLastName("DESC");
        printCollection("Сортування за ПРІЗВИЩЕМ (DESC)", agentRepo);
        
        agentRepo.sortByFirstName("ASC");
        printCollection("Сортування за ІМЕНЕМ (ASC)", agentRepo);
        
        logger.info("--- Демонстрація сортування завершена ---");
    }
}
