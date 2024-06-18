package org.nailsandscrews;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.util.Random;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.net.URL;
import java.util.concurrent.atomic.AtomicReference;

public class StockController<stock> implements Initializable {

    public LineChart changeInPrice;
    public ImageView stockProductImage;
    public TextField buyStockAmount;
    public TextField sellStockAmount;
    public Button buyStockAmountButton;
    public Button sellStockAmountButton;
    public AnchorPane rightScreen;

    @FXML
    ProgressBar quantityBar;

    @FXML
    TreeView<String> treeView;

    @FXML
    TextField searchField;

    @FXML
    Button logoutButton;

    @FXML
    Button contactAdmin;

    @FXML
    Button sellOrBuyStockButton;

    @FXML
    Button PrintSpec;

    @FXML
    Button printAll;

    @FXML
    Button adminHub;

    @FXML
    ImageView imageView;

    @FXML
    Label type;

    @FXML
    Label productType;

    @FXML
    Label material;

    @FXML
    Label quantity;

    @FXML
    Label length;

    @FXML
    Label buyingPrice;

    @FXML
    Label sellingPrice;

    @FXML
    Label supplier;

    @FXML
    Text userNameInfoPage;

    @FXML
    Text typeInfoPage;

    @FXML
    Text systemInfoPage;

    Map<String, Stock> stockMap = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        quantityBar.setVisible(false);
        sellOrBuyStockButton.setVisible(false);
        adminHub.setVisible(false);
        showUserInfo();

        TreeItem<String> rootItem = new TreeItem<>("Stock");
        treeView.setShowRoot(false);
        treeView.setRoot(rootItem);

        DatabaseConnection.openDBSession();
        DatabaseConnection.databaseSession.beginTransaction();
        AtomicReference<List<Stock>> stocks = new AtomicReference<>(DatabaseConnection.databaseSession.createQuery("from Stock").getResultList());
        DatabaseConnection.databaseSession.getTransaction().commit();
        DatabaseConnection.closeDBSession();

        for (Stock stock : stocks.get()) {
            String uniqueKey = stock.getType() + stock.getProduct_type() + stock.getMaterial() + stock.getLength();
            stockMap.put(uniqueKey, stock);
        }

        // Create a Map to store the TreeItems for each type
        Map<String, TreeItem<String>> typeItems = new HashMap<>();

        // Create a Map to store the TreeItems for each product type
        Map<String, TreeItem<String>> productTypeItems = new HashMap<>();

        // Create a Map to store the TreeItems for each material
        Map<String, TreeItem<String>> materialItems = new HashMap<>();

        // Create a Map to store the TreeItems for each length
        Map<String, TreeItem<String>> lengthItems = new HashMap<>();

        for (Stock stock : stocks.get()) {
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


        buyStockAmount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                buyStockAmount.setText(newValue.replaceAll("\\D", ""));
            }
        });

        sellStockAmount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                sellStockAmount.setText(newValue.replaceAll("\\D", ""));
            }
        });

        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectItem();
        });

        // add the amount from buyStockAmount to the quantity in the stock database of the selected stock item from treeview
        buyStockAmountButton.setOnAction(e -> {
            TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
            if (item != null) {
                String selectedItemValue = getSelectedItemValue(item);
                Stock stockItem = stockMap.get(selectedItemValue);
                if (stockItem != null) {
                    int amount = Integer.parseInt(buyStockAmount.getText());
                    stockItem.setQuantity(stockItem.getQuantity() + amount);
                    quantity.setText("Quantity: " + stockItem.getQuantity());
                    quantityBar.setProgress((double) stockItem.getQuantity() / 500);
                    quantityBar.setStyle(stockItem.getQuantity() < 165 ? "-fx-accent: red;" : stockItem.getQuantity() < 330 ? "-fx-accent: yellow;" : "-fx-accent: green;");
                    buyStockAmount.clear();

                    // Update the stock in the database
                    DatabaseConnection.openDBSession();
                    DatabaseConnection.databaseSession.beginTransaction();
                    DatabaseConnection.databaseSession.update(stockItem);
                    DatabaseConnection.databaseSession.getTransaction().commit();
                    DatabaseConnection.closeDBSession();
                }
            }
        });


        // subtract the amount from sellStockAmount to the quantity in the stock database of the selected stock item from treeview
        sellStockAmountButton.setOnAction(e -> {
            TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
            if (item != null) {
                String selectedItemValue = getSelectedItemValue(item);
                Stock stockItem = stockMap.get(selectedItemValue);
                if (stockItem != null) {
                    int amount = Integer.parseInt(sellStockAmount.getText());
                    stockItem.setQuantity(stockItem.getQuantity() - amount);
                    quantity.setText("Quantity: " + stockItem.getQuantity());
                    quantityBar.setProgress((double) stockItem.getQuantity() / 500);
                    quantityBar.setStyle(stockItem.getQuantity() < 165 ? "-fx-accent: red;" : stockItem.getQuantity() < 330 ? "-fx-accent: yellow;" : "-fx-accent: green;");
                    sellStockAmount.clear();

                    // Update the stock in the database
                    DatabaseConnection.openDBSession();
                    DatabaseConnection.databaseSession.beginTransaction();
                    DatabaseConnection.databaseSession.update(stockItem);
                    DatabaseConnection.databaseSession.getTransaction().commit();
                    DatabaseConnection.closeDBSession();
                }
            }
        });

        // logout button
        logoutButton.setOnAction(e -> {
            try {
                SceneController sceneController = new SceneController();
                sceneController.LoginScreen(e);
                LoginController.savedUsername = null;
                LoginController.savedType = null;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        // contact admin button
        contactAdmin.setOnAction(e -> {
            try {
                SceneController sceneController = new SceneController();
                sceneController.contactAdmin(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        if (LoginController.savedType.equals("Admin")) {
            adminHub.setVisible(true);
        }

        // admin hub button
        adminHub.setOnAction(e -> {
            try {
                if (LoginController.savedType.equals("Admin")) {
                    SceneController sceneController = new SceneController();
                    sceneController.AdminScreen(e);
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        // print specific information selected in the tree view to a TXT file when PrintSpec button is pressed
        PrintSpec.setOnAction(e ->
        {
            try {
                FileWriter writer = new FileWriter("SpecificStock output.txt", false); // Overwrite the file
                BufferedWriter buffer = new BufferedWriter(writer);

                TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
                if (item != null) {
                    String selectedItemValue = getSelectedItemValue(item);
                    Stock stock = stockMap.get(selectedItemValue);
                    if (stock != null) {
                        buffer.write("Stock type: " + stock.getType() + "\n");
                        buffer.write("Product type: " + stock.getProduct_type() + "\n");
                        buffer.write("Material: " + stock.getMaterial() + "\n");
                        buffer.write("Length: " + stock.getLength() + "\n");
                        buffer.write("Quantity: " + stock.getQuantity() + "\n");
                        buffer.write("Buying price: " + stock.getBuying_price() + "\n");
                        buffer.write("Selling price: " + stock.getSelling_price() + "\n");
                        buffer.write("Supplier: " + stock.getSupplier() + "\n");
                    }
                }

                buffer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        // print all information in the tree view to a TXT file when printAll button is pressed
        printAll.setOnAction(e ->

        {
            try {
                FileWriter writer = new FileWriter("AllStock output.txt", false); // Overwrite the file
                BufferedWriter buffer = new BufferedWriter(writer);

                List<Stock> stockq = DatabaseConnection.getAllStocks();
                for (Stock stock : stockq) {
                    buffer.write("Stock type: " + stock.getType() + "\n");
                    buffer.write("Product type: " + stock.getProduct_type() + "\n");
                    buffer.write("Material: " + stock.getMaterial() + "\n");
                    buffer.write("Length: " + stock.getLength() + "\n");
                    buffer.write("Quantity: " + stock.getQuantity() + "\n");
                    buffer.write("Buying price: " + stock.getBuying_price() + "\n");
                    buffer.write("Selling price: " + stock.getSelling_price() + "\n");
                    buffer.write("Supplier: " + stock.getSupplier() + "\n");
                    buffer.write("\n"); // Add a blank line between stocks
                }

                buffer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        rightScreen.setVisible(false);
        sellOrBuyStockButton.setOnAction(e ->

        {
            if (rightScreen.isVisible()) {
                rightScreen.setVisible(false);
            } else {
                rightScreen.setVisible(true);
            }
        });
    }

    // check what the selected item is
    private ImageView getImage(String name) {
        String imagePath = "/org/resources/images/" + name.toLowerCase() + ".png";
        URL url = getClass().getResource(imagePath);
        if (url == null) {
            // If the image file does not exist, use the "unknown.png" image
            imagePath = "/org/resources/images/unknown.png";
        }
        return new ImageView(new Image(imagePath));
    }

    // check what the selected item is
    public void selectItem() {
        TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
        if (item != null) {
            // Collapse all items
            collapseAll(treeView.getRoot());
            // Expand the selected item and its parents
            expandItemAndParents(item);
            // Display information of the selected item
            Stock stock = displayItemInfo(item);
            // Check if the selected item is a full stock row
            int level = treeView.getTreeItemLevel(item);
            if (level == 4 && stock != null) {
                System.out.println("Selected full stock row: " + stock);
                System.out.println("Selected full stock row: " + stock.getId());
            }
        }
    }

    // collapse all items in the tree
    private void collapseAll(TreeItem<String> item) {
        item.setExpanded(false);
        for (TreeItem<String> child : item.getChildren()) {
            collapseAll(child);
        }
    }

    // only expand the selected item type list
    private void expandItemAndParents(TreeItem<String> item) {
        TreeItem<String> parent = item.getParent();
        if (parent != null) {
            // Collapse all siblings
            for (TreeItem<String> sibling : parent.getChildren()) {
                if (sibling != item) {
                    collapseAll(sibling);
                    rightScreen.setVisible(false);
                    sellOrBuyStockButton.setVisible(false);
                    refreshShowData();

                }
            }
            // Expand the selected item and its parent
            parent.setExpanded(true);
            item.setExpanded(true);
            expandItemAndParents(parent);
        }
    }

    // only expand the selected item type list
    private String getSelectedItemValue(TreeItem<String> item) {
        int level = treeView.getTreeItemLevel(item);
        return switch (level) {
            case 1 -> item.getValue();
            case 2 -> item.getParent().getValue() + item.getValue();
            case 3 -> item.getParent().getParent().getValue() + item.getParent().getValue() + item.getValue();
            case 4 ->
                    item.getParent().getParent().getParent().getValue() + item.getParent().getParent().getValue() + item.getParent().getValue() + item.getValue();
            default -> null;
        };
    }

    // display the information of the selected item
    public Stock displayItemInfo(TreeItem<String> item) {
        String selectedItemValue = getSelectedItemValue(item);
        Stock stock = stockMap.get(selectedItemValue);
        if (stock != null) {
            int level = treeView.getTreeItemLevel(item);
            switch (level) {
                case 1, 2, 3:
                    break;
                case 4:
                    type.setText("Type: " + stock.getType());
                    productType.setText("Product Type: " + stock.getProduct_type());
                    material.setText("Material: " + stock.getMaterial());
                    length.setText("Length: " + stock.getLength());
                    quantity.setText("Quantity: " + stock.getQuantity());
                    quantityBar.setProgress((double) stock.getQuantity() / 500);
                    quantityBar.setStyle(stock.getQuantity() < 165 ? "-fx-accent: red;" : stock.getQuantity() < 330 ? "-fx-accent: yellow;" : "-fx-accent: green;");
                    buyingPrice.setText("Buying Price: " + stock.getBuying_price());
                    sellingPrice.setText("Selling Price: " + stock.getSelling_price());
                    supplier.setText("Supplier: " + stock.getSupplier());
                    quantityBar.setVisible(true);
                    sellOrBuyStockButton.setVisible(true);
                    rightScreen.setVisible(true);
                    showData();
                    break;
                default:
                    quantityBar.setVisible(false);
                    sellOrBuyStockButton.setVisible(false);
                    break;
            }
            loadImage(stock);

            // Print the full product details to the terminal
            System.out.println("Stock ID: " + stock.getId());
            System.out.println("Stock Type: " + stock.getType());
            System.out.println("Product Type: " + stock.getProduct_type());
            System.out.println("Material: " + stock.getMaterial());
            System.out.println("Length: " + stock.getLength());
            System.out.println("Quantity: " + stock.getQuantity());
            System.out.println("Buying Price: " + stock.getBuying_price());
            System.out.println("Selling Price: " + stock.getSelling_price());
            System.out.println("Supplier: " + stock.getSupplier());

        }
        return stock;
    }

    // load the image of the selected stock item
    private void loadImage(Stock stock) {
        String imagePath = "/org/resources/images/" + stock.getType().toLowerCase() + " box " + stock.getLength().toLowerCase() + ".jpeg";
        URL url = getClass().getResource(imagePath);
        if (url == null) {
            imagePath = "/org/resources/images/unknown-image.jpg";
            url = getClass().getResource(imagePath);
            if (url == null) {
                System.err.println("Default image file not found");
                return;
            }
        }
        Image image = new Image(imagePath);
        stockProductImage.setImage(image);
    }

    // filter the tree view based on the search field
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


    // filter the tree view based on the search field
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

    // collapse all product types
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

    // show data about selected stock
    private void showData() {
        // Create a series for the change in price
        AreaChart.Series<String, Number> changeInPriceSeries = new AreaChart.Series<>();
        changeInPriceSeries.setName("Change in Price");

        // Create a Random object
        Random random = new Random();

        // Add data to the series
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        for (String month : months) {
            int stockPrice = 100 + random.nextInt(600); // Generate a random stock price between 100 and 700
            changeInPriceSeries.getData().add(new AreaChart.Data<>(month, stockPrice));
        }
        // Add the series to the chart
        changeInPrice.getData().add(changeInPriceSeries);

        changeInPrice.setCreateSymbols(false);
    }

    // refresh the show data
    public void refreshShowData() {
        changeInPrice.getData().clear();
        showData();
    }

    //show the user info
    public void showUserInfo() {
        userNameInfoPage.setText("[UserName]: " + LoginController.savedUsername);
        typeInfoPage.setText("[Type]: " + LoginController.savedType);
        //get the system info
        String systemInfo = System.getProperty("os.name") + " " + System.getProperty("os.version") + " " + System.getProperty("os.arch");
        systemInfoPage.setText("[System Info]: " + systemInfo);
    }
}