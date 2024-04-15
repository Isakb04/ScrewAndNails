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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StockScreen {
    public void load(Stage primaryStage) {
        // Create UI components
        Label nameLabel = new Label("Product Name:");
        TextField nameField = new TextField();

        Label quantityLabel = new Label("Quantity:");
        TextField quantityField = new TextField();

        Button addButton = new Button("Add Stock");
        Button removeButton = new Button("Remove Stock");


        // Create layout
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(nameField, quantityLabel, quantityField, addButton, removeButton);
        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(vbox);

        // Add functionality to buttons
        addButton.setOnAction(e -> {
            // Add stock functionality
            String productName = nameField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            // Implement add stock functionality here
        });

        removeButton.setOnAction(e -> {
            // Remove stock functionality
            String productName = nameField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            // Implement remove stock functionality here
        });

        // Create scene
        Scene scene = new Scene(hbox, 1280, 720);


        // Set the stage
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Stock Management System");
        primaryStage.show();
    }
}
