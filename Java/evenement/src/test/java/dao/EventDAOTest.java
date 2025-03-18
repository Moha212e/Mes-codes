import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.java.dao.EventDAO;
import main.java.entity.Event;

import java.util.List;

public class EventDAOTest {
    private EventDAO eventDAO;

    @BeforeEach
    public void setUp() {
        eventDAO = new EventDAO();
    }

    @Test
    public void testCreateEvent() {
        Event event = new Event("Test Event", "2023-10-01", "This is a test event.");
        eventDAO.create(event);
        Event retrievedEvent = eventDAO.read(event.getId());
        assertEquals(event.getName(), retrievedEvent.getName());
    }

    @Test
    public void testReadEvent() {
        Event event = new Event("Read Event", "2023-10-02", "This event will be read.");
        eventDAO.create(event);
        Event retrievedEvent = eventDAO.read(event.getId());
        assertNotNull(retrievedEvent);
        assertEquals(event.getName(), retrievedEvent.getName());
    }

    @Test
    public void testUpdateEvent() {
        Event event = new Event("Update Event", "2023-10-03", "This event will be updated.");
        eventDAO.create(event);
        event.setName("Updated Event");
        eventDAO.update(event);
        Event updatedEvent = eventDAO.read(event.getId());
        assertEquals("Updated Event", updatedEvent.getName());
    }

    @Test
    public void testDeleteEvent() {
        Event event = new Event("Delete Event", "2023-10-04", "This event will be deleted.");
        eventDAO.create(event);
        eventDAO.delete(event.getId());
        Event deletedEvent = eventDAO.read(event.getId());
        assertNull(deletedEvent);
    }

    @Test
    public void testGetAllEvents() {
        Event event1 = new Event("Event 1", "2023-10-05", "First event.");
        Event event2 = new Event("Event 2", "2023-10-06", "Second event.");
        eventDAO.create(event1);
        eventDAO.create(event2);
        List<Event> events = eventDAO.getAll();
        assertTrue(events.size() >= 2);
    }
}