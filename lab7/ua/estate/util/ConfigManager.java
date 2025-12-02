package ua.estate.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Вибачте, не вдалося знайти config.properties");
            } else {
                PROPERTIES.load(input);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }
    
    public static int getInt(String key) {
        return Integer.parseInt(PROPERTIES.getProperty(key, "0"));
    }
}
