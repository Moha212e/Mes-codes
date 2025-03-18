import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

class EventTest {
    private Event event;

    @BeforeEach
    void setUp() {
        event = new Event("Test Event", LocalDateTime.now(), "This is a test event.");
    }

    @Test
    void testGetName() {
        assertEquals("Test Event", event.getName());
    }

    @Test
    void testSetName() {
        event.setName("Updated Event");
        assertEquals("Updated Event", event.getName());
    }

    @Test
    void testGetDate() {
        LocalDateTime date = event.getDate();
        assertNotNull(date);
    }

    @Test
    void testSetDate() {
        LocalDateTime newDate = LocalDateTime.now().plusDays(1);
        event.setDate(newDate);
        assertEquals(newDate, event.getDate());
    }

    @Test
    void testGetDescription() {
        assertEquals("This is a test event.", event.getDescription());
    }

    @Test
    void testSetDescription() {
        event.setDescription("Updated description.");
        assertEquals("Updated description.", event.getDescription());
    }
}