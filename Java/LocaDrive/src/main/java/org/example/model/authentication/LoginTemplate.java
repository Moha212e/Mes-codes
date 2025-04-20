package org.example.model.authentication;

public abstract class LoginTemplate {

    // Méthode template finale : fixe le déroulement
    public final boolean login(String username, String password) {
        String encryptedPassword = encryptPassword(password);
        return authenticate(username, encryptedPassword);
    }

    // Méthodes à spécialiser
    protected abstract String encryptPassword(String password);
    protected abstract boolean authenticate(String username, String encryptedPassword);
}