package ua.estate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.estate.model.Property;

import java.util.List;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class AsyncService {
    private static final Logger logger = LoggerFactory.getLogger(AsyncService.class);
    
    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    public <T> CompletableFuture<T> runAsync(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(() -> {
            String threadName = Thread.currentThread().getName();
            logger.info("[{}] Початок виконання задачі...", threadName);
            try {
                // Штучна затримка для демонстрації паралельності
                Thread.sleep(500); 
                T result = supplier.get();
                logger.info("[{}] Задача завершена успішно.", threadName);
                return result;
            } catch (Exception e) {
                logger.error("[{}] Помилка в потоці: {}", threadName, e.getMessage());
                throw new RuntimeException(e);
            }
        }, executor);
    }

    public Future<List<Property>> filterPropertiesFuture(List<Property> properties, double minPrice) {
        return executor.submit(() -> {
            logger.info("ExecutorService: Початок фільтрації цін > {}", minPrice);
            Thread.sleep(200); // Імітація важкої роботи
            return properties.stream()
                    .filter(p -> p.price() > minPrice)
                    .collect(Collectors.toList());
        });
    }

    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}
