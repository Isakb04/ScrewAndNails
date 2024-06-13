package org.nailsandscrews;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest extends ApplicationTest {

    private TextField usernameField;
    private PasswordField passwordField;
    private LoginController loginController;

    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Login Screen");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    void setUp() {
        usernameField = new TextField();
        passwordField = new PasswordField();
        Button loginButton = new Button();

        // Use TestFX's interact method to ensure JavaFX components are created on the JavaFX Application Thread
        interact(() -> {
            loginController = new LoginController();
            loginController.usernameField = usernameField;
            loginController.passwordField = passwordField;
            loginController.loginButton = loginButton;
        });

        // Set up in-memory database
        DatabaseConnection.openDBSession();
    }

    @Test
    void loadLoginScreen() {
        assertNotNull(usernameField);
        assertNotNull(passwordField);
        assertNotNull(loginController);
    }

    @Test
    void testLoginSuccess() {
        // Set the username and password fields
        interact(() -> {
            usernameField.setText("0");
            passwordField.setText("0");
        });

        // Fire the login button
        interact(() -> loginController.loginButton.fire());

        // Check if the login was successful
        assertEquals("Admin", loginController.userType);
    }
}