package org.nailsandscrews;

import java.io.IOException;
import java.util.Objects;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {
    private static SceneController instance;
    private String previousSceneName; // Field to keep track of the previous scene name
    private String previousFunctionName; // New field to keep track of the previous function name

    public SceneController() {
        instance = this;
    }

    public static SceneController getInstance() {
        if (instance == null) {
            instance = new SceneController();
        }
        return instance;
    }

    private void switchScene(ActionEvent event, String fxmlFile, String title, String functionName) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set previousScene, previousSceneName, and previousFunctionName to the current scene, its name, and function name before switching
        if (stage.getScene() != null) {
            // Field to keep track of the previous scene
            Scene previousScene = stage.getScene();
            previousSceneName = stage.getTitle();
            previousFunctionName = functionName;
        }

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlFile)));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        System.out.println("saved scene name: " + previousSceneName);
        if (title != null) {
            stage.setTitle(title);
        }
        stage.centerOnScreen();
        stage.toFront();
        stage.setResizable(false);
        stage.show();
    }

    public String thisFunctionName() {
        return previousFunctionName;
    }

    public String previousSceneName() {
        return previousSceneName;
    }

    public void LoginScreen(ActionEvent event) throws IOException {
        switchScene(event, "Login.fxml", "Login Screen", "Login Screen");
    }

    public void AdminScreen(ActionEvent event) throws IOException {
        switchScene(event, "AdminScreen.fxml", "Admin Screen", "Admin Screen");
    }

    public void StockScreen(ActionEvent event) throws IOException {
        switchScene(event, "stockScreen.fxml", "Stock Screen", "Stock Screen");
    }

    public void contactAdmin(ActionEvent event) throws IOException {
        switchScene(event, "contactAdmin.fxml", "Contact Admin", "contact Admin");
    }

    public void addUser(ActionEvent event) throws IOException {
        switchScene(event, "addUser.fxml", "Add User", "add User");
    }

    public void addStock(ActionEvent event) throws IOException {
        switchScene(event, "addStock.fxml", "Add Stock", "add Stock");
    }

}