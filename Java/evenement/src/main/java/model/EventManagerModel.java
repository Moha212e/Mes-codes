public class EventManagerModel {
    private static EventManagerModel instance;
    private List<Event> events;
    private List<Participant> participants;

    private EventManagerModel() {
        events = new ArrayList<>();
        participants = new ArrayList<>();
    }

    public static EventManagerModel getInstance() {
        if (instance == null) {
            instance = new EventManagerModel();
        }
        return instance;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void addParticipant(Participant participant) {
        participants.add(participant);
    }
}