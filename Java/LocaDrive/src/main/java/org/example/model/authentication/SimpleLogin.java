package org.example.model.authentication;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

public class SimpleLogin extends LoginTemplate {
    private Map<String, String> userStore;
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );
    private static final String PROPERTIES_FILE = "users.properties";


    public SimpleLogin(Map<String, String> userStore) {
        this.userStore = userStore;
        // Charger les utilisateurs existants depuis le fichier properties
        loadUsersFromProperties();
    }

    /**
     * Charge les utilisateurs depuis le fichier properties
     */
    private void loadUsersFromProperties() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(PROPERTIES_FILE)) {
            properties.load(fis);
            for (String username : properties.stringPropertyNames()) {
                userStore.put(username, properties.getProperty(username));
            }
        } catch (IOException e) {
            // Le fichier n'existe peut-être pas encore, ce n'est pas une erreur
            System.out.println("Fichier properties non trouvé, il sera créé lors de la première inscription");
        }
    }

    /**
     * Sauvegarde les utilisateurs dans le fichier properties
     */
    private void saveUsersToProperties() {
        Properties properties = new Properties();
        for (Map.Entry<String, String> entry : userStore.entrySet()) {
            properties.setProperty(entry.getKey(), entry.getValue());
        }
        
        try (FileOutputStream fos = new FileOutputStream(PROPERTIES_FILE)) {
            properties.store(fos, "LocaDrive Users");
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des utilisateurs: " + e.getMessage());
        }
    }

    @Override
    protected String encryptPassword(String password) {
        if(password == null) {
            return null;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erreur de chiffrement", e);
        }
    }

    @Override
    protected boolean authenticate(String username, String encryptedPassword) {
        if (!isValidEmail(username) || !userStore.containsKey(username)) {
            return false;
        }
        return userStore.get(username).equals(encryptedPassword);
    }

    public boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public boolean register(String username, String password) {
        if (!isValidEmail(username) || password == null || password.length() < 8) {
            return false;
        }
        if (userStore.containsKey(username)) {
            return false;
        }
        userStore.put(username, encryptPassword(password));
        // Sauvegarder les utilisateurs dans le fichier properties après chaque inscription
        saveUsersToProperties();
        return true;
    }
}
