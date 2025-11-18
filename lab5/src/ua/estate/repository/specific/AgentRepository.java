package ua.estate.repository.specific;

import ua.estate.model.Agent;
import ua.estate.repository.GenericRepository;
import ua.estate.repository.functional.IdentityExtractor;

import java.util.logging.Logger;

public class AgentRepository extends GenericRepository<Agent> {

    private static final Logger logger = Logger.getLogger(AgentRepository.class.getName());
    
    private static final IdentityExtractor<Agent> AGENT_IDENTITY_EXTRACTOR = Agent::contactInfo;

    public AgentRepository() {
        super(AGENT_IDENTITY_EXTRACTOR);
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
