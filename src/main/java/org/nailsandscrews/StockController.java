package org.nailsandscrews;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StockController implements Initializable {

    public ImageView imageView;

    @FXML
    private TreeView treeView;

    @FXML
    private TextField searchField;

    @FXML
    public MenuItem logoutButton;

    @FXML
    private MenuItem infoButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TreeItem<String> rootItem = new TreeItem<>("Stock");
        treeView.setShowRoot(false);
        treeView.setRoot(rootItem);

        List<Stock> stocks = DatabaseConnection.getAllStocks();

        // Create a Map to store the TreeItems for each type
        Map<String, TreeItem<String>> typeItems = new HashMap<>();

        // Create a Map to store the TreeItems for each product type
        Map<String, TreeItem<String>> productTypeItems = new HashMap<>();

        // Create a Map to store the TreeItems for each material
        Map<String, TreeItem<String>> materialItems = new HashMap<>();

        // Create a Map to store the TreeItems for each length
        Map<String, TreeItem<String>> lengthItems = new HashMap<>();

        for (Stock stock : stocks) {
            // Get or create the TreeItem for the type
            TreeItem<String> typeItem = typeItems.computeIfAbsent(stock.getType(), k -> {
                ImageView typeIcon = getImage(stock.getType());
                TreeItem<String> item = new TreeItem<>(k, typeIcon);
                rootItem.getChildren().add(item);
                return item;
            });

            // Get or create the TreeItem for the product type
            String productTypeKey = stock.getType() + stock.getProduct_type();
            TreeItem<String> productTypeItem = productTypeItems.computeIfAbsent(productTypeKey, k -> {
                ImageView productTypeIcon = getImage("productType");
                TreeItem<String> item = new TreeItem<>(stock.getProduct_type(), productTypeIcon);
                typeItem.getChildren().add(item);
                return item;
            });

            // Get or create the TreeItem for the material
            String materialKey = productTypeKey + stock.getMaterial();
            TreeItem<String> materialItem = materialItems.computeIfAbsent(materialKey, k -> {
                ImageView materialIcon = getImage("material");
                TreeItem<String> item = new TreeItem<>(stock.getMaterial(), materialIcon);
                productTypeItem.getChildren().add(item);
                return item;
            });

            // Get or create the TreeItem for the length
            String lengthKey = materialKey + stock.getLength();
            TreeItem<String> lengthItem = lengthItems.computeIfAbsent(lengthKey, k -> {
                ImageView lengthIcon = getImage("length");
                TreeItem<String> item = new TreeItem<>(stock.getLength(), lengthIcon);
                materialItem.getChildren().add(item);
                return item;
            });
        }
        searchField.textProperty().addListener(new ChangeListener<String>() {

        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.isEmpty()) {
                    collapseProductTypes(treeView.getRoot());
                    return;
                }
                if (newValue.length() > 1) {
                    filterTree(newValue);
                }
            }
        });

        logoutButton.setOnAction(e -> {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        Stage stage = (Stage) treeView.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Login");
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
        });

        infoButton.setOnAction(e -> {
            System.out.println("Info button clicked");
        });
    }


    private ImageView getImage(String name) {
        String imagePath = "/org/resources/images/" + name.toLowerCase() + ".png";
        URL url = getClass().getResource(imagePath);
        if (url == null) {
            // If the image file does not exist, use the "unknown.png" image
            imagePath = "/org/resources/images/unknown.png";
        }
        return new ImageView(new Image(imagePath));
    }

    public void selectItem() {
        TreeItem<String> item = (TreeItem<String>) treeView.getSelectionModel().getSelectedItem();
        if (item != null) {
            System.out.println(item.getValue());
        }
    }

    private void filterTree(String filter) {
        // Get the root item
        TreeItem<String> root = treeView.getRoot();

        // Iterate over the children of the root (which are the type items)
        for (TreeItem<String> typeItem : root.getChildren()) {
            // Iterate over the children of the type item (which are the product type items)
            for (TreeItem<String> productTypeItem : typeItem.getChildren()) {
                // Call the filter method on the product type item
                filter(productTypeItem, filter);
            }
        }
    }

   private void filter(TreeItem<String> item, String filter) {
    if (item.getValue().toLowerCase().contains(filter.toLowerCase())) {
        // If the item matches the filter, make sure it is visible and selected
        TreeItem<String> parent = item.getParent();
        while (parent != null) {
            parent.setExpanded(true);
            parent = parent.getParent();
        }
        treeView.getSelectionModel().select(item);
    } else {
        // If the item does not match the filter, make sure it is not visible
        item.getParent().setExpanded(false);
        }
    }

    private void collapseProductTypes(TreeItem<String> item) {
        for (TreeItem<String> child : item.getChildren()) {
            child.setExpanded(false);
            if (child.getChildren() != null) {
                collapseProductTypes(child);
            }
        }
    }

}