package ua.estate;

import ua.estate.service.FileService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {        
        logger.info("--- Запуск програми ---");
        
        FileService fileService = new FileService();
        List<Object> loadedData = null;
        
        try {
            String filename = "data.csv";
            loadedData = fileService.loadData(filename);

            logger.info("--- Результати зчитування ---");
            for (Object obj : loadedData) {
                System.out.println(obj);
            }

        } 
        catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "Файл не знайдено: " + e.getMessage(), e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Помилка читання файлу: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Неочікувана помилка в Main: " + e.getMessage(), e);
        } 

        finally {
            if (loadedData != null) {
                logger.info("Обробка завершена. Всього об'єктів у пам'яті: " + loadedData.size());
            } else {
                logger.warning("Обробка завершена, але дані не були завантажені.");
            }
            
            logger.info("--- Завершення роботи програми ---");
        }
    }
}
