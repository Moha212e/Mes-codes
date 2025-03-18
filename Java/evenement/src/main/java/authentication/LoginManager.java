package authentication;

public abstract class LoginManager {
    
    public final boolean login(String username, String password) {
        if (authenticate(username, password)) {
            onLoginSuccess();
            return true;
        } else {
            onLoginFailure();
            return false;
        }
    }

    protected abstract boolean authenticate(String username, String password);

    protected void onLoginSuccess() {
        System.out.println("Login successful!");
    }

    protected void onLoginFailure() {
        System.out.println("Login failed. Please check your credentials.");
    }
}