import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.java.dao.ParticipantDAO;
import main.java.entity.Participant;

import java.util.List;

public class ParticipantDAOTest {

    private ParticipantDAO participantDAO;

    @BeforeEach
    public void setUp() {
        participantDAO = new ParticipantDAO();
    }

    @Test
    public void testCreateParticipant() {
        Participant participant = new Participant("John Doe");
        participantDAO.create(participant);
        assertNotNull(participant.getId());
    }

    @Test
    public void testReadParticipant() {
        Participant participant = new Participant("Jane Doe");
        participantDAO.create(participant);
        Participant retrievedParticipant = participantDAO.read(participant.getId());
        assertEquals(participant.getName(), retrievedParticipant.getName());
    }

    @Test
    public void testUpdateParticipant() {
        Participant participant = new Participant("John Smith");
        participantDAO.create(participant);
        participant.setName("John Doe");
        participantDAO.update(participant);
        Participant updatedParticipant = participantDAO.read(participant.getId());
        assertEquals("John Doe", updatedParticipant.getName());
    }

    @Test
    public void testDeleteParticipant() {
        Participant participant = new Participant("Jane Smith");
        participantDAO.create(participant);
        participantDAO.delete(participant.getId());
        Participant deletedParticipant = participantDAO.read(participant.getId());
        assertNull(deletedParticipant);
    }

    @Test
    public void testGetAllParticipants() {
        participantDAO.create(new Participant("Alice"));
        participantDAO.create(new Participant("Bob"));
        List<Participant> participants = participantDAO.getAll();
        assertEquals(2, participants.size());
    }
}