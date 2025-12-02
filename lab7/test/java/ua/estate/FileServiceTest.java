package ua.estate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ua.estate.exception.DataSerializationException;
import ua.estate.model.Agent;
import ua.estate.service.FileService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class FileServiceTest {

    @Test
    void testJsonSerialization(@TempDir Path tempDir) {
        Path filePath = tempDir.resolve("test_agents.json");
        FileService<Agent> service = new FileService<>(Agent.class);
        
        List<Agent> originalList = List.of(
                new Agent("Json", "Bourne", "json@bourne.com"),
                new Agent("James", "Bond", "007@mi6.uk")
        );

        service.saveToJson(originalList, filePath.toString());
        List<Agent> loadedList = service.loadFromJson(filePath.toString());
        Assertions.assertEquals(originalList.size(), loadedList.size());
        Assertions.assertEquals(originalList, loadedList);
    }
    
    @Test
    void testYamlSerialization(@TempDir Path tempDir) {
        Path filePath = tempDir.resolve("test_agents.yaml");
        FileService<Agent> service = new FileService<>(Agent.class);

        List<Agent> originalList = List.of(
                new Agent("Yaml", "Format", "yaml@test.com"),
                new Agent("Another", "User", "user@test.com")
        );
        service.saveToYaml(originalList, filePath.toString());
        List<Agent> loadedList = service.loadFromYaml(filePath.toString());

        Assertions.assertEquals(originalList.size(), loadedList.size());
        Assertions.assertEquals(originalList, loadedList);
    }

    @Test
    void testLoadInvalidData(@TempDir Path tempDir) throws IOException {

        Path filePath = tempDir.resolve("bad_file.json");
        Files.writeString(filePath, "Це не JSON формат, це просто текст");
        FileService<Agent> service = new FileService<>(Agent.class);
        Assertions.assertThrows(DataSerializationException.class, () -> {
            service.loadFromJson(filePath.toString());
        });
    }
}
