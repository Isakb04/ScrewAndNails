package org.nailsandscrews;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginController {

    @FXML
    public Button contactAdminButton;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    Alert error = new Alert(Alert.AlertType.ERROR);

    public void initialize() {
        // prepare the database connection for quicker login
        DatabaseConnection.openDBSession();
        usernameField.setOnAction(e -> passwordField.requestFocus());
        passwordField.setOnAction(e -> loginButton.fire());
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String userType = DatabaseConnection.authenticateUser(username, password);

            if (userType != null) {
                // Perform login action (e.g., show main application window)
                System.out.println("Login successful! User type: " + userType);
                Parent root = null;
                try {
                    if (userType.equals("Admin")) {
                        SceneController sceneController = new SceneController();
                        sceneController.AdminScreen(e);
                    } else {
                        SceneController sceneController = new SceneController();
                        sceneController.StockScreen(e);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                // set wrong username or password alert
                error.setContentText("Invalid username or password!\nPlease try again or contact an Administrator.");                // show the dialog
                error.show();
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
