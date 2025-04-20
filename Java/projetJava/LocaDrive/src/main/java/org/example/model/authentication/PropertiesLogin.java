package org.example.model.authentication;

import java.io.*;
import java.util.Properties;

/**
 * Implémentation du LoginTemplate qui utilise un fichier properties pour stocker
 * les informations d'authentification des utilisateurs.
 */
public class PropertiesLogin extends LoginTemplate {
    private Properties userProperties;
    private final String propertiesFilePath;
    
    /**
     * Constructeur qui initialise le chemin du fichier properties.
     * @param propertiesFilePath Chemin du fichier properties
     */
    public PropertiesLogin(String propertiesFilePath) {
        this.propertiesFilePath = propertiesFilePath;
        this.userProperties = new Properties();
        loadProperties();
    }
    
    /**
     * Charge les propriétés depuis le fichier.
     */
    private void loadProperties() {
        try (InputStream input = new FileInputStream(propertiesFilePath)) {
            userProperties.load(input);
            System.out.println("Fichier properties chargé avec succès: " + propertiesFilePath);
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du fichier properties: " + e.getMessage());
            // Si le fichier n'existe pas, on le crée
            File file = new File(propertiesFilePath);
            if (!file.exists()) {
                try {
                    file.getParentFile().mkdirs(); // Crée les répertoires parents si nécessaire
                    file.createNewFile();
                    
                    // Ajouter des utilisateurs par défaut
                    userProperties.setProperty("admin", "admin:admin");
                    userProperties.setProperty("mohammed", "1234:user");
                    userProperties.setProperty("zafina", "azerty:user");
                    
                    saveProperties(); // Sauvegarde le fichier properties avec les utilisateurs par défaut
                    System.out.println(STR."Fichier properties créé avec des utilisateurs par défaut: \{propertiesFilePath}");
                } catch (IOException ex) {
                    System.err.println(STR."Erreur lors de la création du fichier properties: \{ex.getMessage()}");
                }
            }
        }
    }
    
    /**
     * Sauvegarde les propriétés dans le fichier.
     */
    private void saveProperties() {
        try (OutputStream output = new FileOutputStream(propertiesFilePath)) {
            userProperties.store(output, "LocaDrive User Properties");
            System.out.println(STR."Fichier properties sauvegardé avec succès: \{propertiesFilePath}");
        } catch (IOException e) {
            System.err.println(STR."Erreur lors de la sauvegarde du fichier properties: \{e.getMessage()}");
        }
    }
    
    @Override
    protected boolean checkCredentials(String username, String password) {
        if (!userProperties.containsKey(username)) {
            return false;
        }
        
        String storedValue = userProperties.getProperty(username);
        String[] parts = storedValue.split(":");
        
        if (parts.length < 1) {
            return false;
        }
        
        String storedPassword = parts[0];
        return storedPassword.equals(password);
    }
    
    @Override
    protected String encryptPassword(String password) {
        // Pas de chiffrement dans cette implémentation simple
        return password;
    }
    
    @Override
    protected boolean authenticate(String username, String encryptedPassword) {
        if (!userProperties.containsKey(username)) {
            return false;
        }
        
        String storedValue = userProperties.getProperty(username);
        String[] parts = storedValue.split(":");
        
        if (parts.length < 1) {
            return false;
        }
        
        String storedPassword = parts[0];
        return storedPassword.equals(encryptedPassword);
    }
    
    /**
     * Ajoute un nouvel utilisateur.
     * @param username Nom d'utilisateur
     * @param password Mot de passe
     * @param role Rôle de l'utilisateur (admin, user, etc.)
     * @return true si l'utilisateur a été ajouté avec succès, false sinon
     */
    public boolean addUser(String username, String password, String role) {
        if (username == null || password == null || role == null || 
            username.isEmpty() || password.isEmpty() || role.isEmpty()) {
            return false;
        }
        
        if (userProperties.containsKey(username)) {
            return false; // L'utilisateur existe déjà
        }
        
        userProperties.setProperty(username, password + ":" + role);
        saveProperties();
        return true;
    }
    
    /**
     * Supprime un utilisateur.
     * @param username Nom d'utilisateur
     * @return true si l'utilisateur a été supprimé avec succès, false sinon
     */
    public boolean removeUser(String username) {
        if (!userProperties.containsKey(username)) {
            return false;
        }
        
        userProperties.remove(username);
        saveProperties();
        return true;
    }
    
    /**
     * Récupère le rôle d'un utilisateur.
     * @param username Nom d'utilisateur
     * @return Le rôle de l'utilisateur ou null si l'utilisateur n'existe pas
     */
    public String getUserRole(String username) {
        if (!userProperties.containsKey(username)) {
            return null;
        }
        
        String storedValue = userProperties.getProperty(username);
        String[] parts = storedValue.split(":");
        
        if (parts.length < 2) {
            return "user"; // Rôle par défaut
        }
        
        return parts[1];
    }
}
