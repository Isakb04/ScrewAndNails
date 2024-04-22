package org.nailsandscrews;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    public void initialize() {
        usernameField.setOnAction(e -> passwordField.requestFocus());
        passwordField.setOnAction(e -> loginButton.fire());
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            boolean isAuthenticated = DatabaseConnection.authenticateUser(username, password);
            if (isAuthenticated) {
                // Perform login action (e.g., show main application window)
                System.out.println("Login successful!");
                // move to stock screen
                Parent root = null;
                if (type.equals("Admin")) {
                    try {
                        root = FXMLLoader.load(getClass().getResource("adminScreen.fxml"));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Admin Screen");
                    stage.centerOnScreen();
                    stage.setResizable(false);
                    stage.show();
                }
                else
                    try {
                        root = FXMLLoader.load(getClass().getResource("StockScreen.fxml"));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Stock Screen");
                    stage.centerOnScreen();
                    stage.setResizable(false);
                    stage.show();
                } else {
                    // Show error message (e.g., invalid credentials)
                    System.out.println("Invalid username or password!");
                }
            });

        contactAdminButton.setOnAction(e -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("contactAdmin.fxml"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Register");
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.show();
        });
    }
}
