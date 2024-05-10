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
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("addUser.fxml"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Add User");
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    });

    messagesButton.setOnAction(e -> {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("contactAdmin.fxml"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Messages");
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    });

    stockScreenButton.setOnAction(e -> {
        Parent root = null;
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
    });

    logoutButton.setOnAction(e -> {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("login.fxml"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Login");
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    });

    addStockButton.setOnAction(e -> {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("addStock.fxml"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Add Stock");
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    });
    }
}
