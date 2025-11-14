package ua.estate.service;
import ua.estate.exception.InvalidDataException;
import ua.estate.model.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileService {

    private static final Logger logger = Logger.getLogger(FileService.class.getName());
    public List<Object> loadData(String filePath) throws IOException {
        List<Object> results = new ArrayList<>();
        String line = "";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); 
            
            logger.info("Початок зчитування файлу: " + filePath);

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 0) continue; 

                String type = data[0];
                
                try {
                    switch (type) {
                        case "AGENT":
                            results.add(new Agent(data[1], data[2], data[3]));
                            break;
                        case "CLIENT":
                            results.add(new Client(data[1], data[2], data[3]));
                            break;
                        case "PROPERTY":
                            PropertyType pType = PropertyType.valueOf(data[2]);
                            double price = Double.parseDouble(data[3]); 
                            double area = Double.parseDouble(data[4]);
                            results.add(new Property(data[1], pType, price, area));
                            break;
                        default:
                            logger.warning("Невідомий тип об'єкта в рядку: " + line);
                    }
                } 
                catch (InvalidDataException | IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                    logger.log(Level.WARNING, "Помилка парсингу рядка, рядок пропущено: " + line, e);
                }
            }
        } 
        
        logger.info("Успішно завантажено " + results.size() + " об'єктів.");
        return results;
    }
}
