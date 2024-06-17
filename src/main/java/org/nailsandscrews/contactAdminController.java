package org.nailsandscrews;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

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

    public void initialize() {
        messageBox.setEditable(false);
        messageBox.setText("[Messages]" + "\n\n");

        if (Files.exists(Paths.get("messages.txt"))) {
            try {
                String messages = new String(Files.readAllBytes(Paths.get("messages.txt")));
                messageBox.setText(messages);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                Files.deleteIfExists(Paths.get("messages.txt"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));

        if (LoginController.savedUsername != null) {
            userNameField.setText(LoginController.savedType + " " + LoginController.savedUsername);
            userNameField.setDisable(true);
            sendMessage.setDisable(false);
        }

        if (userNameField.getText().isEmpty() || message.getText().isEmpty()) {
            sendMessage.setDisable(true);
        }

        SceneController sceneController = SceneController.getInstance();

        backButton.setOnAction(e -> {
            try {
                Files.write(Paths.get("messages.txt"), messageBox.getText().getBytes(), StandardOpenOption.CREATE);
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
                            System.out.println("Function not found, returning to login screen");
                            sceneController.LoginScreen(e);
                            break;
                    }
                } else {
                    System.out.println("No previous function, returning to login screen");
                    sceneController.LoginScreen(e);
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        sendMessage.setOnAction(e -> {
            String userName = userNameField.getText();
            String messageText = message.getText();
            String currentMessages = messageBox.getText();
            String newMessage = userName + ": " + messageText + "\n";
            messageBox.setText(currentMessages + newMessage);
            message.clear();
        });

        userNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            sendMessage.setDisable(newValue.trim().isEmpty());
        });

        message.textProperty().addListener((observable, oldValue, newValue) -> {
            sendMessage.setDisable(newValue.trim().isEmpty());
        });
    }
}