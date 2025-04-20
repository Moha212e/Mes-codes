package org.example.model.entity;

import com.google.gson.annotations.Expose;

/**
 * Classe abstraite servant de base pour toutes les entités du système
 * Implémente les fonctionnalités communes à toutes les entités
 */
public abstract class AbstractEntity {
    
    /**
     * Retourne l'identifiant unique de l'entité
     * @return l'identifiant de l'entité
     */
    public abstract Object getId();
    
    /**
     * Retourne une représentation textuelle de l'entité
     * @return une chaîne de caractères représentant l'entité
     */
    @Override
    public abstract String toString();
    
    /**
     * Compare cette entité avec un autre objet
     * @param obj l'objet à comparer
     * @return true si les objets sont égaux, false sinon
     */
    @Override
    public abstract boolean equals(Object obj);
    
    /**
     * Calcule le code de hachage de l'entité
     *
     * @return le code de hachage
     */
    @Override
    public abstract int hashCode();
}
