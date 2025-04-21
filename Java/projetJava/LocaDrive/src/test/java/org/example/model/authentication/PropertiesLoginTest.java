package org.example.model.authentication;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class PropertiesLoginTest {

    private static final String TEST_PROPERTIES_FILE = "src/test/resources/user.properties";
    private PropertiesLogin propertiesLogin;
    
    @BeforeEach
    public void setUp() {
        // Créer le répertoire de test s'il n'existe pas
        File directory = new File("src/test/resources");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        // Supprimer le fichier de test s'il existe déjà
        File file = new File(TEST_PROPERTIES_FILE);
        if (file.exists()) {
            file.delete();
        }
        
        // Créer une nouvelle instance de PropertiesLogin avec le fichier de test
        propertiesLogin = new PropertiesLogin(TEST_PROPERTIES_FILE);
        
        // Ajouter des utilisateurs de test manuellement pour s'assurer qu'ils existent
        propertiesLogin.addUser("admin", "admin", "admin");
        propertiesLogin.addUser("mohammed", "1234", "user");
        propertiesLogin.addUser("zafina", "azerty", "user");
    }
    
    @AfterEach
    public void tearDown() {
        // Supprimer le fichier de test après chaque test
        File file = new File(TEST_PROPERTIES_FILE);
        if (file.exists()) {
            file.delete();
        }
    }
    
    @Test
    public void testValidLogin() {
        // Les utilisateurs par défaut devraient être créés lors de l'initialisation
        assertTrue(propertiesLogin.login("admin", "admin"));
        assertTrue(propertiesLogin.login("mohammed", "1234"));
        assertTrue(propertiesLogin.login("zafina", "azerty"));
    }
    
    @Test
    public void testInvalidPassword() {
        assertFalse(propertiesLogin.login("admin", "wrongpassword"));
        assertFalse(propertiesLogin.login("mohammed", "wrongpassword"));
    }
    
    @Test
    public void testNonExistentUser() {
        assertFalse(propertiesLogin.login("nonexistent", "password"));
    }
    
    @Test
    public void testNullOrEmptyCredentials() {
        assertFalse(propertiesLogin.login(null, "password"));
        assertFalse(propertiesLogin.login("admin", null));
        assertFalse(propertiesLogin.login("", "password"));
        assertFalse(propertiesLogin.login("admin", ""));
    }
    
    @Test
    public void testAddUser() {
        // Ajouter un nouvel utilisateur
        assertTrue(propertiesLogin.addUser("newuser", "newpassword", "user"));
        
        // Vérifier que l'utilisateur peut se connecter
        assertTrue(propertiesLogin.login("newuser", "newpassword"));
        
        // Vérifier que le rôle est correct
        assertEquals("user", propertiesLogin.getUserRole("newuser"));
    }
    
    @Test
    public void testAddExistingUser() {
        // Essayer d'ajouter un utilisateur qui existe déjà
        assertFalse(propertiesLogin.addUser("admin", "newpassword", "user"));
        
        // Vérifier que le mot de passe original fonctionne toujours
        assertTrue(propertiesLogin.login("admin", "admin"));
    }
    
    @Test
    public void testRemoveUser() {
        // Supprimer un utilisateur existant
        assertTrue(propertiesLogin.removeUser("mohammed"));
        
        // Vérifier que l'utilisateur ne peut plus se connecter
        assertFalse(propertiesLogin.login("mohammed", "1234"));
    }
    
    @Test
    public void testRemoveNonExistentUser() {
        // Essayer de supprimer un utilisateur qui n'existe pas
        assertFalse(propertiesLogin.removeUser("nonexistent"));
    }
    
    @Test
    public void testGetUserRole() {
        // Vérifier les rôles des utilisateurs par défaut
        assertEquals("admin", propertiesLogin.getUserRole("admin"));
        assertEquals("user", propertiesLogin.getUserRole("mohammed"));
        assertEquals("user", propertiesLogin.getUserRole("zafina"));
        
        // Vérifier le rôle d'un utilisateur qui n'existe pas
        assertNull(propertiesLogin.getUserRole("nonexistent"));
    }
    
    @Test
    public void testFileCreation() {
        // Vérifier que le fichier properties a été créé
        File file = new File(TEST_PROPERTIES_FILE);
        assertTrue(file.exists());
    }
}
