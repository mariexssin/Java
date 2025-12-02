package ua.estate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.estate.model.*;
import ua.estate.service.AsyncService;
import ua.estate.service.FileService;
import ua.estate.util.ConfigManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final List<Agent> agentRepo = Collections.synchronizedList(new ArrayList<>());
    private static final List<Property> propertyRepo = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {
        logger.info("--- ЛР 9: Багатопоточність та Concurrency ---");

        FileService<Agent> agentFileService = new FileService<>(Agent.class);
        FileService<Property> propFileService = new FileService<>(Property.class);
        AsyncService asyncService = new AsyncService();
        prepareTestData(agentFileService, propFileService);

        String jsonPath = ConfigManager.get("data.path.json");
        String yamlPath = ConfigManager.get("data.path.yaml");

        try {
            logger.info(">>> Початок паралельного завантаження...");
            long startLoad = System.currentTimeMillis();

            CompletableFuture<List<Agent>> agentsFuture = asyncService.runAsync(() -> 
                agentFileService.loadFromJson(jsonPath)
            ).exceptionally(ex -> {
                logger.error("Не вдалося завантажити агентів: " + ex.getMessage());
                return List.of(); 
            });

            CompletableFuture<List<Property>> propsFuture = asyncService.runAsync(() -> 
                propFileService.loadFromYaml(yamlPath)
            );

            CompletableFuture.allOf(agentsFuture, propsFuture).join();
            agentRepo.addAll(agentsFuture.get());
            propertyRepo.addAll(propsFuture.get());
            long endLoad = System.currentTimeMillis();
            logger.info(">>> Завантаження завершено за {} мс. Агентів: {}, Об'єктів: {}", 
                    (endLoad - startLoad), agentRepo.size(), propertyRepo.size());

            double minPrice = 150000;
            logger.info("\n>>> Test A: Parallel Stream");
            long startStream = System.currentTimeMillis();
            List<Property> expensivePropsStream = propertyRepo.parallelStream()
                    .filter(p -> {
                        try { Thread.sleep(1); } catch (InterruptedException e) {}
                        return p.price() > minPrice;
                    })
                    .toList();
                    
            long endStream = System.currentTimeMillis();
            logger.info("ParallelStream знайшов {} об'єктів за {} мс", expensivePropsStream.size(), (endStream - startStream));
            logger.info("\n>>> Test B: CompletableFuture & Executor");
            long startAsync = System.currentTimeMillis();
            CompletableFuture<List<Property>> filterTask = CompletableFuture.supplyAsync(() -> propertyRepo, new java.util.concurrent.ForkJoinPool()) // або asyncService executor
                    .thenApplyAsync(list -> {
                        logger.debug("Фільтрація в потоці: " + Thread.currentThread().getName());
                         return list.stream()
                                .filter(p -> {
                                    try { Thread.sleep(1); } catch (InterruptedException e) {}
                                    return p.price() > minPrice;
                                })
                                .collect(Collectors.toList());
                    })
                    .handle((result, ex) -> {
                        if (ex != null) {
                            logger.error("Помилка під час фільтрації: " + ex.getMessage());
                            return new ArrayList<Property>();
                        }
                        return result;
                    });

            List<Property> expensivePropsAsync = filterTask.join();
            long endAsync = System.currentTimeMillis();
            logger.info("CompletableFuture знайшов {} об'єктів за {} мс", expensivePropsAsync.size(), (endAsync - startAsync));

        } catch (Exception e) {
            logger.error("Критична помилка в Main", e);
        } finally {
            asyncService.shutdown();
        }
    }

    private static void prepareTestData(FileService<Agent> as, FileService<Property> ps) {
        List<Property> properties = IntStream.range(0, 1000)
                .mapToObj(i -> new Property(
                        "Address " + i, 
                        (i % 2 == 0) ? PropertyType.APARTMENT : PropertyType.HOUSE, 
                        100000 + (i * 100), 
                        50 + i, 
                        PropertyStatus.FOR_SALE))
                .collect(Collectors.toList());
        
        List<Agent> agents = List.of(new Agent("Test", "Agent", "00000"));

        ps.saveToYaml(properties, ConfigManager.get("data.path.yaml"));
        as.saveToJson(agents, ConfigManager.get("data.path.json"));
    }
}
