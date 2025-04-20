package org.example.model.event;

/**
 * Interface pour les objets observables
 * Implémente le principe d'Interface Segregation (I de SOLID)
 */
public interface Observable {
    /**
     * Ajoute un écouteur d'événements
     * @param listener Écouteur à ajouter
     */
    void addModelListener(ModelListener listener);
    
    /**
     * Supprime un écouteur d'événements
     * @param listener Écouteur à supprimer
     */
    void removeModelListener(ModelListener listener);
    
    /**
     * Notifie tous les écouteurs d'un événement
     * @param event Événement à notifier
     */
    void notifyListeners(ModelEvent event);
}
