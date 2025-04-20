package org.example.model.authentification;

import org.example.model.authentication.SimpleLogin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleLoginTest {

    private SimpleLogin simpleLogin;
    private Map<String, String> users;

    @BeforeEach
    public void setUp() {
        users = new HashMap<>();
        simpleLogin = new SimpleLogin(users);
        
        simpleLogin.register("mohammed@test.com", "12345678");
        simpleLogin.register("zafina@test.com", "azertyui");
        simpleLogin.register("admin@test.com", "adminpass");
    }

    @Test
    public void testValidLogin() {
        assertTrue(simpleLogin.login("mohammed@test.com", "12345678"));
        assertTrue(simpleLogin.login("zafina@test.com", "azertyui"));
        assertTrue(simpleLogin.login("admin@test.com", "adminpass"));
    }

    @Test
    public void testInvalidPassword() {
        assertFalse(simpleLogin.login("mohammed@test.com", "wrong"));
        assertFalse(simpleLogin.login("zafina@test.com", "12345678"));
    }

    @Test
    public void testUnknownUser() {
        assertFalse(simpleLogin.login("unknown@test.com", "pass"));
    }

    @Test
    public void testNullInputs() {
        assertFalse(simpleLogin.login(null, "12345678"));
        assertFalse(simpleLogin.login("mohammed@test.com", null));
        assertFalse(simpleLogin.login(null, null));
    }

    @Test
    public void testValidEmailFormat() {
        assertTrue(simpleLogin.isValidEmail("test@example.com"));
        assertTrue(simpleLogin.isValidEmail("user.name@domain.com"));
        assertTrue(simpleLogin.isValidEmail("user+tag@domain.co.uk"));
        assertTrue(simpleLogin.isValidEmail("123@domain.com"));
    }

    @Test
    public void testInvalidEmailFormat() {
        assertFalse(simpleLogin.isValidEmail(""));
        assertFalse(simpleLogin.isValidEmail("test"));
        assertFalse(simpleLogin.isValidEmail("test@"));
        assertFalse(simpleLogin.isValidEmail("@domain.com"));
        assertFalse(simpleLogin.isValidEmail("test@domain"));
        assertFalse(simpleLogin.isValidEmail("test domain.com"));
        assertFalse(simpleLogin.isValidEmail(null));
    }

    @Test
    public void testSuccessfulRegistration() {
        String email = "newuser@test.com";
        String password = "password123";
        
        assertTrue(simpleLogin.register(email, password));
        assertTrue(simpleLogin.login(email, password));
    }

    @Test
    public void testRegistrationWithExistingEmail() {
        assertFalse(simpleLogin.register("mohammed@test.com", "newpassword"));
    }

    @Test
    public void testRegistrationWithInvalidEmail() {
        assertFalse(simpleLogin.register("notanemail", "password123"));
    }

    @Test
    public void testRegistrationWithShortPassword() {
        assertFalse(simpleLogin.register("valid@test.com", "short"));
    }

    @Test
    public void testRegistrationWithInvalidInputs() {
        assertFalse(simpleLogin.register(null, "password123"));
        assertFalse(simpleLogin.register("valid@test.com", null));
        assertFalse(simpleLogin.register(null, null));
        assertFalse(simpleLogin.register("", "password123"));
        assertFalse(simpleLogin.register("valid@test.com", ""));
    }

    @Test
    public void testMultipleRegistrationsAndLogins() {
        // Premier utilisateur
        assertTrue(simpleLogin.register("user1@test.com", "password123"));
        assertTrue(simpleLogin.login("user1@test.com", "password123"));

        // Deuxième utilisateur
        assertTrue(simpleLogin.register("user2@test.com", "diffpassword"));
        assertTrue(simpleLogin.login("user2@test.com", "diffpassword"));

        // Vérifier que le premier utilisateur peut toujours se connecter
        assertTrue(simpleLogin.login("user1@test.com", "password123"));
    }
}
