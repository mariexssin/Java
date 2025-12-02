package ua.estate.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.estate.exception.DataSerializationException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileService<T> {

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);
    private final ObjectMapper jsonMapper;
    private final ObjectMapper yamlMapper;
    private final Class<T> type; 

    public FileService(Class<T> type) {
        this.type = type;
        this.jsonMapper = new ObjectMapper();
        this.jsonMapper.enable(SerializationFeature.INDENT_OUTPUT); 
        this.yamlMapper = new ObjectMapper(new YAMLFactory());
        this.yamlMapper.findAndRegisterModules(); 
    }

    public void saveToJson(List<T> data, String filePath) {
        save(data, filePath, jsonMapper, "JSON");
    }

    public void saveToYaml(List<T> data, String filePath) {
        save(data, filePath, yamlMapper, "YAML");
    }

    private void save(List<T> data, String filePath, ObjectMapper mapper, String formatName) {
        try {
            File file = new File(filePath);
            // Створюємо папку, якщо її немає
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }
            
            mapper.writeValue(file, data);
            logger.info("Успішно збережено {} об'єктів у {} файл: {}", data.size(), formatName, filePath);
        } catch (IOException e) {
            logger.error("Помилка при збереженні у {}: {}", formatName, e.getMessage());
            throw new DataSerializationException("Не вдалося зберегти дані у " + formatName, e);
        }
    }

    public List<T> loadFromJson(String filePath) {
        return load(filePath, jsonMapper, "JSON");
    }

    public List<T> loadFromYaml(String filePath) {
        return load(filePath, yamlMapper, "YAML");
    }

    private List<T> load(String filePath, ObjectMapper mapper, String formatName) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                logger.warn("Файл {} не знайдено: {}", formatName, filePath);
                return List.of();
            }

            List<T> data = mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, type));
            
            logger.info("Успішно завантажено {} об'єктів з {} файлу: {}", data.size(), formatName, filePath);
            return data;
        } catch (IOException e) {
            logger.error("Помилка при зчитуванні з {}: {}", formatName, e.getMessage());
            throw new DataSerializationException("Не вдалося завантажити дані з " + formatName, e);
        }
    }
}
