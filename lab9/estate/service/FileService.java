package ua.estate.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
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
        this.jsonMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        this.yamlMapper = new ObjectMapper(new YAMLFactory());
        this.yamlMapper.findAndRegisterModules();
    }

    public void saveToJson(List<T> data, String filePath) {
        save(data, filePath, jsonMapper);
    }
    
    public void saveToYaml(List<T> data, String filePath) {
        save(data, filePath, yamlMapper);
    }

    private void save(List<T> data, String filePath, ObjectMapper mapper) {
        try {
            File file = new File(filePath);
            if (file.getParentFile() != null) file.getParentFile().mkdirs();
            mapper.writeValue(file, data);
        } catch (IOException e) {
            throw new DataSerializationException("Save error: " + filePath, e);
        }
    }

    public List<T> loadFromJson(String filePath) {
        return load(filePath, jsonMapper);
    }

    public List<T> loadFromYaml(String filePath) {
        return load(filePath, yamlMapper);
    }

    private List<T> load(String filePath, ObjectMapper mapper) {
        try {
            File file = new File(filePath);
            if (!file.exists()) return List.of();
            return mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, type));
        } catch (IOException e) {
            throw new DataSerializationException("Load error: " + filePath, e);
        }
    }
}
