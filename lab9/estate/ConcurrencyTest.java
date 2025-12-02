package ua.estate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.estate.model.Property;
import ua.estate.model.PropertyStatus;
import ua.estate.model.PropertyType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

class ConcurrencyTest {

    @Test
    void testThreadSafeCollectionFill() {
        List<Property> synchronizedList = Collections.synchronizedList(new ArrayList<>());
        ExecutorService executor = Executors.newFixedThreadPool(10);

        int numberOfTasks = 1000;
        
        List<CompletableFuture<Void>> futures = IntStream.range(0, numberOfTasks)
                .mapToObj(i -> CompletableFuture.runAsync(() -> {
                    synchronizedList.add(new Property("Addr", PropertyType.LAND, 100, 10, PropertyStatus.SOLD));
                }, executor))
                .toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        Assertions.assertEquals(numberOfTasks, synchronizedList.size());
        executor.shutdown();
    }

    @Test
    void testParallelStreamCorrectness() {
        List<Integer> numbers = IntStream.range(0, 1000).boxed().toList();

        long countEven = numbers.parallelStream()
                .filter(n -> n % 2 == 0)
                .count();

        Assertions.assertEquals(500, countEven);
    }
}
