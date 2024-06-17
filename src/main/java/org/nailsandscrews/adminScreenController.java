package org.nailsandscrews;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class adminScreenController {

    @FXML
    private Button addUserButton;

    @FXML
    private Button messagesButton;

    @FXML
    Button stockScreenButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button addStockButton;

    public void initialize() {
        SceneController sceneController = new SceneController();

        addUserButton.setOnAction(e -> {
            try {
                sceneController.addUser(e);
            } catch (Exception ex) {
                // Log the exception
            }
        });

        messagesButton.setOnAction(e -> {
            try {
                sceneController.contactAdmin(e);
            } catch (Exception ex) {
                // Log the exception
            }
        });

        stockScreenButton.setOnAction(e -> {
            try {
                sceneController.StockScreen(e);
            } catch (Exception ex) {
                // Log the exception
            }
        });

        logoutButton.setOnAction(e -> {
            try {
                sceneController.LoginScreen(e);
            } catch (Exception ex) {
                // Log the exception
            }
        });

        addStockButton.setOnAction(e -> {
            try {
                sceneController.addStock(e);
            } catch (Exception ex) {
                // Log the exception
            }
        });
    }
}