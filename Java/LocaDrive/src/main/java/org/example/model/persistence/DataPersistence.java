package org.example.model.persistence;

import java.util.List;

/**
 * Interface générique pour la persistance des données
 * Implémente le principe d'Interface Segregation (I de SOLID)
 */
public interface DataPersistence<T> {
    /**
     * Sauvegarde une liste d'entités
     * @param data Liste d'entités à sauvegarder
     * @return true si la sauvegarde a réussi, false sinon
     */
    boolean save(List<T> data);
    
    /**
     * Charge une liste d'entités
     * @return Liste d'entités chargées
     */
    List<T> load();
}
