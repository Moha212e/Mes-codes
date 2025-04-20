package org.example.controller;

import java.util.EventListener;

/**
 * Interface pour les écouteurs d'événements du contrôleur
 * Implémente le principe d'Interface Segregation (I de SOLID)
 */
public interface ControllerListener extends EventListener {
    /**
     * Méthode appelée lorsqu'un événement du contrôleur se produit
     * @param event Événement du contrôleur
     */
    void onControllerEvent(ControllerEvent event);
}
