package controller;

import model.EventManagerModel;
import entity.Event;
import entity.Participant;

import java.util.List;

public class EventController {
    private EventManagerModel model;

    public EventController(EventManagerModel model) {
        this.model = model;
    }

    public void addEvent(Event event) {
        model.addEvent(event);
    }

    public void removeEvent(Event event) {
        model.removeEvent(event);
    }

    public List<Event> getAllEvents() {
        return model.getAllEvents();
    }

    public void addParticipantToEvent(Event event, Participant participant) {
        model.addParticipantToEvent(event, participant);
    }

    public void removeParticipantFromEvent(Event event, Participant participant) {
        model.removeParticipantFromEvent(event, participant);
    }
}