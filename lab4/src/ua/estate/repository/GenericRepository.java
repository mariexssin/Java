package ua.estate.repository;

import ua.estate.repository.functional.IdentityExtractor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class GenericRepository<T> {

    private static final Logger logger = Logger.getLogger(GenericRepository.class.getName());

    private final List<T> items = new ArrayList<>();

    private final IdentityExtractor<T> identityExtractor;

    public GenericRepository(IdentityExtractor<T> identityExtractor) {
        this.identityExtractor = Objects.requireNonNull(identityExtractor, 
            "Identity extractor не може бути null");
    }

    public void add(T item) {
        String identity = identityExtractor.getIdentity(item);
        
        if (findByIdentity(identity) != null) {
            logger.warning("Спроба додати дублікат. Об'єкт не додано: " + identity);
            return;
        }
        
        items.add(item);
        logger.info("Додано новий об'єкт: " + identity);
    }

    public void remove(T item) {
        if (items.remove(item)) {
            logger.info("Об'єкт видалено: " + identityExtractor.getIdentity(item));
        } else {
            logger.warning("Спроба видалити неіснуючий об'єкт.");
        }
    }

    public List<T> getAll() {
        return Collections.unmodifiableList(items);
    }

    public T findByIdentity(String identity) {
        return items.stream()
                .filter(item -> identityExtractor.getIdentity(item).equals(identity))
                .findFirst()
                .orElse(null);
    }
}
