package org.example.model.event;

import java.util.EventListener;

/**
 * Interface pour les écouteurs d'événements du modèle
 * Implémente le principe d'Interface Segregation (I de SOLID)
 */
public interface ModelListener extends EventListener {
    /**
     * Méthode appelée lorsqu'un événement du modèle se produit
     * @param event Événement du modèle
     */
    void onModelEvent(ModelEvent event);
}
