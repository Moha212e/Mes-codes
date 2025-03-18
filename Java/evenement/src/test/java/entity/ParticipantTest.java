import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ParticipantTest {
    private Participant participant;

    @BeforeEach
    void setUp() {
        participant = new Participant("John Doe", 1);
    }

    @Test
    void testGetName() {
        assertEquals("John Doe", participant.getName());
    }

    @Test
    void testSetName() {
        participant.setName("Jane Doe");
        assertEquals("Jane Doe", participant.getName());
    }

    @Test
    void testGetId() {
        assertEquals(1, participant.getId());
    }

    @Test
    void testSetId() {
        participant.setId(2);
        assertEquals(2, participant.getId());
    }
}