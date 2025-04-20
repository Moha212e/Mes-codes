package org.example.model.persistence;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite implémentant le pattern Template Method pour la persistance des données
 * Implémente les principes Open/Closed et Template Method
 * @param <T> Type d'entité à persister
 */
public abstract class AbstractPersistence<T> implements DataPersistence<T> {
    protected final String dataDir;
    protected final String filePath;
    
    /**
     * Constructeur
     * @param dataDir Répertoire de données
     * @param fileName Nom du fichier
     */
    public AbstractPersistence(String dataDir, String fileName) {
        this.dataDir = dataDir;
        this.filePath = dataDir + "/" + fileName;
        initDataDirectory();
    }
    
    /**
     * Initialise le répertoire de données s'il n'existe pas
     */
    protected void initDataDirectory() {
        File dataDir = new File(this.dataDir);
        if (!dataDir.exists()) {
            boolean created = dataDir.mkdirs();
            if (created) {
                System.out.println("✅ Répertoire de données créé avec succès: " + this.dataDir);
            } else {
                System.err.println("❌ Impossible de créer le répertoire de données: " + this.dataDir);
            }
        }
    }
    
    /**
     * Template Method pour sauvegarder les données
     * Définit le squelette de l'algorithme de sauvegarde
     * @param data Liste d'entités à sauvegarder
     * @return true si la sauvegarde a réussi, false sinon
     */
    @Override
    public final boolean save(List<T> data) {
        try {
            // 1. Préparation des données
            List<T> preparedData = prepareDataForSave(data);
            
            // 2. Sauvegarde effective des données
            boolean result = saveData(preparedData);
            
            // 3. Actions post-sauvegarde
            if (result) {
                onSaveSuccess(preparedData);
            } else {
                onSaveFailure(preparedData);
            }
            
            return result;
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la sauvegarde des données: " + e.getMessage());
            onSaveFailure(data);
            return false;
        }
    }
    
    /**
     * Template Method pour charger les données
     * Définit le squelette de l'algorithme de chargement
     * @return Liste d'entités chargées
     */
    @Override
    public final List<T> load() {
        try {
            // 1. Vérification de l'existence du fichier
            if (!fileExists()) {
                System.out.println("ℹ️ Le fichier " + filePath + " n'existe pas encore");
                return new ArrayList<>();
            }
            
            // 2. Chargement effectif des données
            List<T> loadedData = loadData();
            
            // 3. Post-traitement des données
            List<T> processedData = processLoadedData(loadedData);
            
            // 4. Actions post-chargement
            onLoadSuccess(processedData);
            
            return processedData;
        } catch (Exception e) {
            System.err.println("❌ Erreur lors du chargement des données: " + e.getMessage());
            onLoadFailure();
            return new ArrayList<>();
        }
    }
    
    /**
     * Vérifie si le fichier de données existe
     * @return true si le fichier existe, false sinon
     */
    protected boolean fileExists() {
        return new File(filePath).exists();
    }
    
    /**
     * Méthode à implémenter pour préparer les données avant sauvegarde
     * @param data Données à préparer
     * @return Données préparées
     */
    protected List<T> prepareDataForSave(List<T> data) {
        // Par défaut, ne fait rien de spécial
        return new ArrayList<>(data);
    }
    
    /**
     * Méthode à implémenter pour effectuer la sauvegarde des données
     * @param data Données à sauvegarder
     * @return true si la sauvegarde a réussi, false sinon
     */
    protected abstract boolean saveData(List<T> data);
    
    /**
     * Méthode à implémenter pour effectuer le chargement des données
     * @return Données chargées
     */
    protected abstract List<T> loadData();
    
    /**
     * Méthode à implémenter pour traiter les données après chargement
     * @param data Données chargées
     * @return Données traitées
     */
    protected List<T> processLoadedData(List<T> data) {
        // Par défaut, ne fait rien de spécial
        return data != null ? data : new ArrayList<>();
    }
    
    /**
     * Méthode appelée après une sauvegarde réussie
     * @param data Données sauvegardées
     */
    protected void onSaveSuccess(List<T> data) {
        System.out.println("✅ Données sauvegardées avec succès dans " + filePath);
    }
    
    /**
     * Méthode appelée après un échec de sauvegarde
     * @param data Données qui n'ont pas pu être sauvegardées
     */
    protected void onSaveFailure(List<T> data) {
        // Par défaut, ne fait rien de spécial
    }
    
    /**
     * Méthode appelée après un chargement réussi
     * @param data Données chargées
     */
    protected void onLoadSuccess(List<T> data) {
        System.out.println("✅ Données chargées avec succès depuis " + filePath);
    }
    
    /**
     * Méthode appelée après un échec de chargement
     */
    protected void onLoadFailure() {
        // Par défaut, ne fait rien de spécial
    }
}
