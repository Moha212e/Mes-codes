package org.example.model.event;

import java.util.EventObject;

/**
 * Classe de base pour tous les événements du modèle
 * Implémente le principe de Single Responsibility (S de SOLID)
 */
public class ModelEvent extends EventObject {
    private final EventType eventType;

    /**
     * Types d'événements possibles
     */
    public enum EventType {
        ENTITY_ADDED,
        ENTITY_MODIFIED,
        ENTITY_DELETED,
        DATA_LOADED
    }

    /**
     * Constructeur de l'événement
     * @param source Source de l'événement
     * @param eventType Type d'événement
     */
    public ModelEvent(Object source, EventType eventType) {
        super(source);
        this.eventType = eventType;
    }

    /**
     * Récupère le type d'événement
     * @return Type d'événement
     */
    public EventType getEventType() {
        return eventType;
    }
}
