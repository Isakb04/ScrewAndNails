package org.nailsandscrews;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StockController implements Initializable {

    public AreaChart sellData;

    public AreaChart buyData;
    public ImageView stockProductImage;
    public TextField buyStockAmount;
    public TextField sellStockAmount;
    public Button buyStockAmountButton;
    public Button sellStockAmountButton;
    public AnchorPane rightScreen;

    @FXML
    private ProgressBar quantityBar;

    @FXML
    private TreeView treeView;

    @FXML
    private TextField searchField;

    @FXML
    public MenuItem logoutButton;

    @FXML
    private MenuItem infoButton;

    @FXML
    private Button sellOrBuyStockButton;

    @FXML
    private ImageView imageView;

    @FXML
    private Label type;

    @FXML
    private Label productType;

    @FXML
    private Label material;

    @FXML
    private Label quantity;

    @FXML
    private Label length;

    @FXML
    private Label buyingPrice;

    @FXML
    private Label sellingPrice;

    @FXML
    private Label supplier;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        quantityBar.setVisible(false);


        TreeItem<String> rootItem = new TreeItem<>("Stock");
        treeView.setShowRoot(false);
        treeView.setRoot(rootItem);

        DatabaseConnection.openDBSession();
        DatabaseConnection.databaseSession.beginTransaction();
        List<Stock> stocks = DatabaseConnection.databaseSession.createQuery("from Stock").getResultList();
        DatabaseConnection.databaseSession.getTransaction().commit();
        DatabaseConnection.closeDBSession();

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

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                collapseProductTypes(treeView.getRoot());
                return;
            }
            if (newValue.length() > 1) {
                filterTree(newValue);
            }
        });

        //only take ints as input and when buyStockAmountButton is pressed add the amount to the quality in the stock database of the selected stock item
        buyStockAmount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                buyStockAmount.setText(newValue.replaceAll("[^\\d]", ""));
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

        rightScreen.setVisible(false);
        sellOrBuyStockButton.setOnAction(e -> {
            if (rightScreen.isVisible()) {
                rightScreen.setVisible(false);
            } else {
                rightScreen.setVisible(true);
            }
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
        infoOfItem(item);

    }

    private void infoOfItem(TreeItem<String> item) {
    // Get the selected item's value
    String selectedItemValue = item.getValue();

    // Get all stocks from the database
    List<Stock> stocks = DatabaseConnection.getAllStocks();

    // Find the stock that matches the selected item
    for (Stock stock : stocks) {
        // Check the level of the selected item in the TreeView
        int level = treeView.getTreeItemLevel(item);

        switch (level) {
            case 1: // Type level
                if (stock.getType().equals(selectedItemValue)) {
                    type.setText("Stock: " + stock.getType());
                }
                quantityBar.setVisible(false);
                break;
            case 2: // Product type level
                if (stock.getProduct_type().equals(selectedItemValue)) {
                    productType.setText("Product Type: " + stock.getProduct_type());
                }
                quantityBar.setVisible(false);
                break;
            case 3: // Material level
                if (stock.getMaterial().equals(selectedItemValue)) {
                    material.setText("Material: " + stock.getMaterial());
                }
                quantityBar.setVisible(false);
                break;
            case 4: // Length level
                if (stock.getLength().equals(selectedItemValue)) {
                    length.setText("Length: " + stock.getLength());
                    quantity.setText("Quantity: " + stock.getQuantity());

                    quantityBar.setProgress((double) stock.getQuantity() / 500);
                    if (stock.getQuantity() < 165) {
                        quantityBar.setStyle("-fx-accent: red;");
                    } else if (stock.getQuantity() < 330) {
                        quantityBar.setStyle("-fx-accent: yellow;");
                    } else {
                        quantityBar.setStyle("-fx-accent: green;");
                    }

                    buyingPrice.setText("Buying Price: " + stock.getBuying_price());
                    sellingPrice.setText("Selling Price: " + stock.getSelling_price());
                    supplier.setText("Supplier: " + stock.getSupplier());

                    // Make the quantityBar visible
                    quantityBar.setVisible(true);
                }
                break;
            default:
                // Make the quantityBar invisible when a non-length item is selected
                quantityBar.setVisible(false);
                break;
        }

        // Print debug information
        System.out.println("Selected item: " + selectedItemValue);
        System.out.println("Matching stock found: " + stock.getType());
        System.out.println("Labels updated");

        // Stop the loop once the matching stock is found
        if (stock.getType().equals(selectedItemValue) ||
                stock.getProduct_type().equals(selectedItemValue) ||
                stock.getMaterial().equals(selectedItemValue) ||
                stock.getLength().equals(selectedItemValue)) {
            break;
        }
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

    public void searchStock() {
        System.out.println("Search stock");
    }

    //set test data into buyData and sellData
    public void setTestData() {
        //set test data into buyData
        sellData.getData().add(new AreaChart.Series("Sell", FXCollections.observableArrayList(
                new AreaChart.Data(1, 10),
                new AreaChart.Data(2, 20),
                new AreaChart.Data(3, 30),
                new AreaChart.Data(4, 40),
                new AreaChart.Data(5, 50),
                new AreaChart.Data(6, 60),
                new AreaChart.Data(7, 70),
                new AreaChart.Data(8, 80),
                new AreaChart.Data(9, 90),
                new AreaChart.Data(10, 100)
        )));
        //set test data into sellData
        buyData.getData().add(new AreaChart.Series("Buy", FXCollections.observableArrayList(
                new AreaChart.Data(1, 100),
                new AreaChart.Data(2, 90),
                new AreaChart.Data(3, 80),
                new AreaChart.Data(4, 70),
                new AreaChart.Data(5, 60),
                new AreaChart.Data(6, 50),
                new AreaChart.Data(7, 40),
                new AreaChart.Data(8, 30),
                new AreaChart.Data(9, 20),
                new AreaChart.Data(10, 10)
        )));
    }
}