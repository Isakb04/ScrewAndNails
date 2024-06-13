package org.nailsandscrews;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class contactAdminController {
    @FXML
    private Button sendMessage;

    @FXML
    private Button backButton;

    @FXML
    private TextArea messageBox;

    @FXML
    private TextField userNameField;

    @FXML
    private TextField message;

    @FXML
    private Label timer;

    public void initialize() {
        messageBox.setEditable(false);
        messageBox.setText("[Messages will appear here]");

        SceneController sceneController = SceneController.getInstance();
        String previousFunction = sceneController.thisFunctionName();
        System.out.println("the previous function is: " + sceneController.previousSceneName());

        backButton.setOnAction(e -> {
            try {
                String previousSceneName = sceneController.previousSceneName();
                if (previousSceneName != null) {
                    switch (previousSceneName) {
                        case "Login Screen":
                            sceneController.LoginScreen(e);
                            break;
                        case "Admin Screen":
                            sceneController.AdminScreen(e);
                            break;
                        case "Stock Screen":
                            sceneController.StockScreen(e);
                            break;
                        default:
                            System.out.println("Function not found");
                            break;
                    }
                } else {
                    System.out.println("No previous function");
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        sendMessage.setOnAction(e -> {
            JOptionPane.showMessageDialog(null, "Message sent successfully");
        });

        sendMessage.setOnAction(e -> {
        });

        userNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 0) {
                sendMessage.setDisable(false);
            } else {
                sendMessage.setDisable(true);
            }
        });

        message.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 0) {
                sendMessage.setDisable(false);
            } else {
                sendMessage.setDisable(true);
            }
        });
    }
}
