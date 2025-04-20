package org.example.model.event;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite implémentant les fonctionnalités de base d'un objet observable
 * Implémente le principe de Open/Closed (O de SOLID)
 */
public abstract class AbstractObservable implements Observable {
    private final List<ModelListener> listeners = new ArrayList<>();
    
    @Override
    public void addModelListener(ModelListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }
    
    @Override
    public void removeModelListener(ModelListener listener) {
        listeners.remove(listener);
    }
    
    @Override
    public void notifyListeners(ModelEvent event) {
        // Créer une copie de la liste pour éviter les problèmes de concurrence
        List<ModelListener> listenersCopy = new ArrayList<>(listeners);
        
        // Notifier tous les écouteurs
        for (ModelListener listener : listenersCopy) {
            listener.onModelEvent(event);
        }
    }
    
    /**
     * Méthode utilitaire pour créer et envoyer un événement
     * @param eventType Type d'événement
     */
    protected void fireEvent(ModelEvent.EventType eventType) {
        notifyListeners(new ModelEvent(this, eventType));
    }
}
