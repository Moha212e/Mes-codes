package org.example.controller;

import java.util.EventObject;

/**
 * Classe représentant un événement du contrôleur
 * Implémente le principe de Single Responsibility (S de SOLID)
 */
public class ControllerEvent extends EventObject {
    private final EventType eventType;

    /**
     * Types d'événements possibles
     */
    public enum EventType {
        DATA_ADDED,
        DATA_MODIFIED,
        DATA_DELETED,
        DATA_LOADED,
        UNKNOWN
    }

    /**
     * Constructeur de l'événement
     * @param source Source de l'événement
     * @param eventType Type d'événement
     */
    public ControllerEvent(Object source, EventType eventType) {
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
