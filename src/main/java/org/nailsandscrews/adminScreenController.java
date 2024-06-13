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


public class adminScreenController {

    @FXML
    private Button addUserButton;

    @FXML
    private Button messagesButton;

    @FXML
    private Button stockScreenButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button addStockButton;

    public void initialize() {
        addUserButton.setOnAction(e -> {
            try {
                SceneController sceneController = new SceneController();
                sceneController.addUser(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        messagesButton.setOnAction(e -> {
            try {
                SceneController sceneController = new SceneController();
                sceneController.contactAdmin(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        stockScreenButton.setOnAction(e -> {
            try {
                SceneController sceneController = new SceneController();
                sceneController.StockScreen(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        logoutButton.setOnAction(e -> {
            try {
                SceneController sceneController = new SceneController();
                sceneController.LoginScreen(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        addStockButton.setOnAction(e -> {
            try {
                SceneController sceneController = new SceneController();
                sceneController.addStock(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}
