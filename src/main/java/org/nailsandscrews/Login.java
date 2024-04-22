package org.nailsandscrews;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class Login {


    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static void launch(String[] args) {
    }

    public void load(Stage stage) {
    }
}