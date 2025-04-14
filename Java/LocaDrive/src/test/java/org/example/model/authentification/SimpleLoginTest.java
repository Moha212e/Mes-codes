package org.example.model.authentification;

import org.example.model.authentication.SimpleLogin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleLoginTest {

    private SimpleLogin simpleLogin;

    @BeforeEach
    public void setUp() {
        Map<String, String> users = new HashMap<>();
        users.put("mohammed", "1234");
        users.put("zafina", "azerty");
        users.put("admin", "admin");

        simpleLogin = new SimpleLogin(users);
    }

    @Test
    public void testValidLogin() {
        assertTrue(simpleLogin.login("mohammed", "1234"));
        assertTrue(simpleLogin.login("zafina", "azerty"));
        assertTrue(simpleLogin.login("admin", "admin"));
    }

    @Test
    public void testInvalidPassword() {
        assertFalse(simpleLogin.login("mohammed", "wrong"));
        assertFalse(simpleLogin.login("zafina", "1234"));
    }

    @Test
    public void testUnknownUser() {
        assertFalse(simpleLogin.login("unknown", "pass"));
    }

    @Test
    public void testNullInputs() {
        assertFalse(simpleLogin.login(null, "1234"));
        assertFalse(simpleLogin.login("mohammed", null));
        assertFalse(simpleLogin.login(null, null));
    }
}
