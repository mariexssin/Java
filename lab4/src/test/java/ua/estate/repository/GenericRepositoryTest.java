package ua.estate.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.estate.exception.InvalidDataException;
import ua.estate.model.Agent; // Припускаємо, що Agent - це record

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GenericRepositoryTest {

    private GenericRepository<Agent> agentRepository;
    private Agent agent1;
    private Agent agent2;

    @BeforeEach
    void setUp() throws InvalidDataException {
        agentRepository = new GenericRepository<>(agent -> agent.contactInfo());
        
        agent1 = new Agent("Тест", "Агент1", "agent1@test.com");
        agent2 = new Agent("Тест", "Агент2", "agent2@test.com");
    }

    @Test
    void testAddAndFindByIdentity() {
        agentRepository.add(agent1);
        Agent found = agentRepository.findByIdentity("agent1@test.com");
        
        assertNotNull(found);
        assertEquals("Агент1", found.lastName());
        assertEquals(agent1, found); // 'record' мають коректний equals()
    }

    @Test
    void testFindByIdentityNotFound() {
        agentRepository.add(agent1);
        Agent found = agentRepository.findByIdentity("non-existent-id@test.com");
        assertNull(found);
    }

    @Test
    void testGetAll() {
        agentRepository.add(agent1);
        agentRepository.add(agent2);
        
        List<Agent> allAgents = agentRepository.getAll();
        assertEquals(2, allAgents.size());
        assertTrue(allAgents.contains(agent1));
        assertTrue(allAgents.contains(agent2));
    }

    @Test
    void testRemove() {
        agentRepository.add(agent1);
        agentRepository.add(agent2);
        assertEquals(2, agentRepository.getAll().size());

        agentRepository.remove(agent1);
        
        assertEquals(1, agentRepository.getAll().size());
        assertNull(agentRepository.findByIdentity("agent1@test.com"));
        assertNotNull(agentRepository.findByIdentity("agent2@test.com"));
    }

    @Test
    void testAddDuplicate() throws InvalidDataException {
        agentRepository.add(agent1);
        
        Agent duplicateAgent = new Agent("ІншеІм'я", "ІншеПрізвище", "agent1@test.com");
        
        agentRepository.add(duplicateAgent);
        
        assertEquals(1, agentRepository.getAll().size());
        
        Agent found = agentRepository.findByIdentity("agent1@test.com");
        assertEquals("Агент1", found.lastName());
    }
}
