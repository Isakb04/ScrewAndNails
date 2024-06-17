package org.nailsandscrews;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController extends Parent {

    public static String savedUsername;
    public static String savedType;

    @FXML
    Button contactAdminButton;

    @FXML
    TextField usernameField;

    @FXML
    PasswordField passwordField;

    @FXML
    Button loginButton;

    String userType;
    private SceneController sceneController;

    public String getUsername() {
        return this.savedUsername;
    }

    public String getType() {
        return this.savedType;
    }

    public void initialize() {
        DatabaseConnection.openDBSession();
        usernameField.setOnAction(e -> passwordField.requestFocus());
        passwordField.setOnAction(e -> loginButton.fire());
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String userType = DatabaseConnection.authenticateUser(username, password);

            if (userType != null) {
                System.out.println("Login successful! User type: " + userType);
                savedUsername = username;
                savedType = userType;
                System.out.println("Saved username: " + savedUsername);
                System.out.println("Saved type: " + savedType);

                SceneController sceneController = new SceneController();
                try {
                    if ("Admin".equals(userType)) {
                        sceneController.AdminScreen(e);
                    } else {
                        sceneController.StockScreen(e);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                javafx.application.Platform.runLater(() -> {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setContentText("Invalid username or password!\nPlease try again or contact an Administrator.");
                    error.show();
                });
            }
        });

        contactAdminButton.setOnAction(e -> {
            try {
                SceneController sceneController = new SceneController();
                sceneController.contactAdmin(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}
