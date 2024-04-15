package org.nailsandscrews;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class Login {
    public void load(Stage primaryStage) {
        primaryStage.setTitle("System Login");

        // Create a VBox layout
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(25));

        // input fields
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameField.setMaxWidth(200);
        usernameField.setTranslateY(290);
        usernameField.setScaleX(0.75);
        usernameField.setScaleY(0.75);
        Label usernameLabel = new Label("Username:");
        usernameLabel.setTranslateY(300);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setMaxWidth(200);
        passwordField.setTranslateY(270);
        passwordField.setScaleX(0.75);
        passwordField.setScaleY(0.75);
        Label passwordLabel = new Label("Password:");
        passwordLabel.setTranslateY(280);

        Button loginButton = getButton(usernameField, passwordField);
        loginButton.setTranslateY(275);

        Button contactAdmin = new Button("Request Admin");
        contactAdmin.setTranslateY(270);
        contactAdmin.setScaleY(0.75);
        contactAdmin.setScaleX(0.75);
        contactAdmin.setOnAction(e -> {
            // Contact admin functionality
            System.out.println("Contacting admin...");
        });

        // add image
        Image image = new Image("org/resources/images/Designer.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(243);
        imageView.setFitWidth(243);
        imageView.setTranslateY(-225);


        // Add controls to the VBox
        vbox.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, loginButton, contactAdmin, imageView);

        Scene scene = new Scene(vbox, 350, 550);
        scene.getStylesheets().add("org/resources/design/LoginStyle.css");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button getButton(TextField usernameField, PasswordField passwordField) {
        Button loginButton = new Button("Login");
        loginButton.setPrefWidth(100);
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            boolean isAuthenticated = DatabaseConnection.authenticateUser(username, password);
            if (isAuthenticated) {
                // Perform login action (e.g., show main application window)
                System.out.println("Login successful!");
                StockScreen screen = new StockScreen();
            } else {
                // Show error message (e.g., invalid credentials)
                System.out.println("Invalid username or password!");
            }
        });
        return loginButton;
    }
}
