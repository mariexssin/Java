package ua.estate;

import ua.estate.exception.InvalidDataException;
import ua.estate.model.Property;
import ua.estate.model.PropertyType;
import ua.estate.repository.specific.PropertyRepository;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        logger.info("--- ЛР 6: Демонстрація Stream API ---");

        PropertyRepository propRepo = new PropertyRepository();
        
        try {
            propRepo.add(new Property("вул. Шкільна, 1", PropertyType.HOUSE, 200_000, 100.0));
            propRepo.add(new Property("пр. Свободи, 5", PropertyType.APARTMENT, 120_000, 60.0));
            propRepo.add(new Property("пл. Ринок, 10", PropertyType.OFFICE, 450_000, 150.0));
            propRepo.add(new Property("вул. Героїв, 20", PropertyType.APARTMENT, 95_000, 45.0));
            propRepo.add(new Property("Ліс, 1", PropertyType.LAND, 50_000, 1000.0));

        } catch (InvalidDataException e) {
            logger.severe("Помилка при створенні об'єктів: " + e.getMessage());
            return;
        }

        System.out.println("\n--- 2. Демонстрація пошуку та forEach (collect) ---");
        
        List<Property> affordableProps = propRepo.findByPriceRange(90_000, 210_000);
        
        System.out.println("Знайдено об'єктів у діапазоні (90k-210k): " + affordableProps.size());
        
        System.out.print("Адреси: ");
        affordableProps.stream().map(Property::address).forEach(address -> System.out.print(address + "; "));
        System.out.println();
        
        System.out.println("\n--- 3. Демонстрація Reduce ---");
        
        double totalApartmentArea = propRepo.getAll().stream()
                .filter(p -> p.type() == PropertyType.APARTMENT)
                .map(Property::area)
                .reduce(0.0, (subtotal, element) -> subtotal + element); // reduce
                
        System.out.printf("Загальна площа усіх квартир: %.2f кв.м.%n", totalApartmentArea);

        System.out.println("\n--- 4. Порівняння продуктивності (Benchmark) ---");
        
        List<Double> largeList = propRepo.getAll().stream()
                .map(Property::price)
                .collect(Collectors.toList());
        for (int i = 0; i < 100000; i++) {
            largeList.add(99.99); 
        }
        
        long startTime = System.nanoTime();
        double sumSequential = largeList.stream().mapToDouble(d -> d * 1.01).sum();
        long timeSequential = System.nanoTime() - startTime;

        startTime = System.nanoTime();
        double sumParallel = largeList.parallelStream().mapToDouble(d -> d * 1.01).sum();
        long timeParallel = System.nanoTime() - startTime;

        logger.info(String.format("Sequential Stream (послідовний): %d мс", timeSequential / 1_000_000));
        logger.info(String.format("Parallel Stream (паралельний):   %d мс", timeParallel / 1_000_000));
        System.out.println("Примітка: Паралельний стрім не завжди швидший, особливо на малих даних.");
        
        logger.info("--- Завдання ЛР 6 завершено ---");
    }
}
