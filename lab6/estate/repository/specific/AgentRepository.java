package ua.estate.repository.specific;

import ua.estate.model.Agent;
import ua.estate.repository.GenericRepository;
import ua.estate.repository.functional.IdentityExtractor;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors; 

public class AgentRepository extends GenericRepository<Agent> {

    private static final Logger logger = Logger.getLogger(AgentRepository.class.getName());
    
    private static final IdentityExtractor<Agent> AGENT_IDENTITY_EXTRACTOR = Agent::contactInfo;

    public AgentRepository() {
        super(AGENT_IDENTITY_EXTRACTOR);
    }

    public List<Agent> findByLastNamePrefix(String prefix) {
        logger.info("Пошук агентів за префіксом прізвища: " + prefix);
        
        return getAll().stream() 
                .filter(a -> a.lastName().startsWith(prefix))
                .collect(Collectors.toList());
    }
    
    public void sortByLastName(String order) {
        logger.info("Сортування агентів за прізвищем (Comparable).");
        sortByComparable(order);
    }

    public void sortByFirstName(String order) {
        logger.info("Сортування агентів за ім'ям.");
        sortByComparator(Agent.BY_FIRST_NAME, order);
    }
}
