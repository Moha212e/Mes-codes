import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoginManagerTest {

    private LoginManager loginManager;

    @BeforeEach
    public void setUp() {
        loginManager = new LoginManager();
    }

    @Test
    public void testAuthenticateValidUser() {
        String username = "validUser";
        String password = "validPassword";
        assertTrue(loginManager.authenticate(username, password));
    }

    @Test
    public void testAuthenticateInvalidUser() {
        String username = "invalidUser";
        String password = "invalidPassword";
        assertFalse(loginManager.authenticate(username, password));
    }

    @Test
    public void testAuthenticateEmptyUsername() {
        String username = "";
        String password = "somePassword";
        assertFalse(loginManager.authenticate(username, password));
    }

    @Test
    public void testAuthenticateEmptyPassword() {
        String username = "someUser";
        String password = "";
        assertFalse(loginManager.authenticate(username, password));
    }

    @Test
    public void testAuthenticateNullUsername() {
        String username = null;
        String password = "somePassword";
        assertFalse(loginManager.authenticate(username, password));
    }

    @Test
    public void testAuthenticateNullPassword() {
        String username = "someUser";
        String password = null;
        assertFalse(loginManager.authenticate(username, password));
    }
}