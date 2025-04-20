package org.example.model.entity;

/**
 * Interface définissant les méthodes pour les entités identifiables
 * Toute classe qui implémente cette interface doit fournir un moyen d'obtenir un identifiant unique
 */
public interface Identifiable {
    
    /**
     * Retourne l'identifiant unique de l'entité
     * @return l'identifiant de l'entité
     */
    Object getId();
}
