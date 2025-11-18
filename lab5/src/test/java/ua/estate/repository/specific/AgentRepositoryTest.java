package ua.estate.repository.specific;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.estate.exception.InvalidDataException;
import ua.estate.model.Agent;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AgentRepositoryTest {

    private AgentRepository agentRepository;
    private Agent a1, a2, a3, a4;

    @BeforeEach
    void setUp() throws InvalidDataException {
        agentRepository = new AgentRepository();
        
        a1 = new Agent("Антон", "Зозуля", "+380509000000");
        a2 = new Agent("Богдан", "Іванов", "+380501111111");
        a3 = new Agent("Віктор", "Петренко", "+380502222222");
        a4 = new Agent("Сергій", "Коваленко", "+380503333333");
        
        agentRepository.add(a1);
        agentRepository.add(a2);
        agentRepository.add(a3);
        agentRepository.add(a4);
    }

    @Test
    void testSortByLastNameAsc() {
        agentRepository.sortByLastName("ASC");
        List<Agent> sorted = agentRepository.getAll();
        assertEquals(a2, sorted.get(0)); 
        assertEquals(a4, sorted.get(1)); 
        assertEquals(a3, sorted.get(2)); 
        assertEquals(a1, sorted.get(3)); 
    }

    @Test
    void testSortByFirstNameDesc() {
        agentRepository.sortByFirstName("DESC");
        List<Agent> sorted = agentRepository.getAll();
        assertEquals(a4, sorted.get(0)); 
        assertEquals(a3, sorted.get(1)); 
        assertEquals(a2, sorted.get(2)); 
        assertEquals(a1, sorted.get(3)); 
    }
}
