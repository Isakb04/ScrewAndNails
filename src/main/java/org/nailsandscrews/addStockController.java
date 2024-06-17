package org.nailsandscrews;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class addStockController {


    public TextField typeField;
    public Button stockSearch;
    public Button deleteStock;
    public Button addStock;
    public TextArea resultArea;
    public TextField searchBySupplierField;
    public TextField addTypeField;
    public TextField addProductTypeField;
    public TextField addMaterialField;
    public TextField addLengthField;
    public TextField addSupplierField;
    public TextField addStockPriceField;
    public TextField addSellPriceField;
    public Button searchStockButton;
    public Button openAdminPage;

    @FXML
    TableColumn<Stock, Integer> IdColumn;

    @FXML
    TableColumn<Stock, String> typeColumn;

    @FXML
    TableColumn<Stock, String> productTypeColumn;

    @FXML
    TableColumn<Stock, String> materialColumn;

    @FXML
    TableColumn<Stock, Integer> lengthColumn;

    @FXML
    TableColumn<Stock, String> supplierColumn;

    @FXML
    TableColumn<Stock, Double> buyingPriceColumn;

    @FXML
    TableColumn<Stock, Double> sellingPriceColumn;

    @FXML
    TableColumn<Stock, Integer> QuantityColumn;

    @FXML
    TableColumn<Stock, String> dateAddedColumn;

    @FXML
    TableColumn<Stock, String> lastUpdatedColumn;

    @FXML
    TableView<Stock> StockTable;


    @FXML
    private void initialize() {
        // display all users columns in the table
        DatabaseConnection.openDBSession();
        DatabaseConnection.databaseSession.beginTransaction();
        List<Stock> stocks = DatabaseConnection.databaseSession.createQuery("from Stock").getResultList();
        DatabaseConnection.databaseSession.getTransaction().commit();
        DatabaseConnection.closeDBSession();
        StockTable.setItems(FXCollections.observableArrayList(stocks));


        IdColumn.setCellValueFactory(new PropertyValueFactory<Stock, Integer>("id"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Stock, String>("type"));
        productTypeColumn.setCellValueFactory(new PropertyValueFactory<Stock, String>("product_type"));
        materialColumn.setCellValueFactory(new PropertyValueFactory<Stock, String>("material"));
        lengthColumn.setCellValueFactory(new PropertyValueFactory<Stock, Integer>("length"));
        supplierColumn.setCellValueFactory(new PropertyValueFactory<Stock, String>("supplier"));
        buyingPriceColumn.setCellValueFactory(new PropertyValueFactory<Stock, Double>("buying_price"));
        sellingPriceColumn.setCellValueFactory(new PropertyValueFactory<Stock, Double>("selling_price"));
        QuantityColumn.setCellValueFactory(new PropertyValueFactory<Stock, Integer>("quantity"));
        dateAddedColumn.setCellValueFactory(new PropertyValueFactory<Stock, String>("date_added"));
        lastUpdatedColumn.setCellValueFactory(new PropertyValueFactory<Stock, String>("last_updated"));

        addStock.setOnAction(e -> {
            DatabaseConnection.openDBSession();
            DatabaseConnection.databaseSession.beginTransaction();
            Stock stock = new Stock();
            String type = addTypeField.getText();
            type = type.substring(0, 1).toUpperCase() + type.substring(1);
            stock.setType(type);

            String productType = addProductTypeField.getText();
            productType = productType.substring(0, 1).toUpperCase() + productType.substring(1);
            stock.setProduct_type(productType);

            String material = addMaterialField.getText();
            material = material.substring(0, 1).toUpperCase() + material.substring(1);
            stock.setMaterial(material);

            String length = addLengthField.getText();
            length = length.substring(0, 1).toUpperCase() + length.substring(1);
            stock.setLength(length);

            String supplier = addSupplierField.getText();
            supplier = supplier.substring(0, 1).toUpperCase() + supplier.substring(1);
            stock.setSupplier(supplier);

            stock.setBuying_price(Float.parseFloat(addStockPriceField.getText()));
            stock.setSelling_price(Float.parseFloat(addSellPriceField.getText()));
            stock.setQuantity(0);
            stock.setDate_added(LocalDateTime.now().toString());
            stock.setLast_updated(LocalDateTime.now().toString());
            addTypeField.clear();
            addProductTypeField.clear();
            addMaterialField.clear();
            addLengthField.clear();
            addSupplierField.clear();
            addStockPriceField.clear();
            addSellPriceField.clear();
            StockTable.getItems().add(stock);
            resultArea.setText("INSERT INTO Stock (type, product_type, material, length, supplier, buying_price, selling_price, quantity, date_added, last_updated) VALUES ('" + stock.getType() + "', '" + stock.getProduct_type() + "', '" + stock.getMaterial() + "', '" + stock.getLength() + "', '" + stock.getSupplier() + "', " + stock.getBuying_price() + ", " + stock.getSelling_price() + ", " + stock.getQuantity() + ", '" + stock.getDate_added() + "', '" + stock.getLast_updated() + "');");

            DatabaseConnection.databaseSession.save(stock);
            DatabaseConnection.databaseSession.getTransaction().commit();
            DatabaseConnection.closeDBSession();
            DatabaseConnection.openDBSession();
            DatabaseConnection.closeDBSession();

            SceneController sceneController = new SceneController();
            try {
                sceneController.addStock(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        addStockPriceField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                addStockPriceField.setText(newValue.replaceAll("[^\\d.]", ""));
            }
        });

        addSellPriceField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                addSellPriceField.setText(newValue.replaceAll("[^\\d.]", ""));
            }
        });

        openAdminPage.setOnAction(e -> {
            try {
                SceneController sceneController = new SceneController();
                sceneController.AdminScreen(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        deleteStock.setOnAction(e ->
        {
            Stock stock = StockTable.getSelectionModel().getSelectedItem();
            DatabaseConnection.openDBSession();
            DatabaseConnection.databaseSession.beginTransaction();
            DatabaseConnection.databaseSession.delete(stock);
            DatabaseConnection.databaseSession.getTransaction().commit();
            DatabaseConnection.closeDBSession();
            StockTable.getItems().remove(stock);
            resultArea.setText("DELETE FROM Stock WHERE id = " + stock.getId() + ";");

            SceneController sceneController = new SceneController();
            try {
                sceneController.addStock(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public void searchStock(ActionEvent event) {
    }

    public void deleteStock(ActionEvent event) {
    }

    public void insertStock(ActionEvent event) {
    }

    public void openAdminPage(ActionEvent event) {
    }

    public void searchAllStock(ActionEvent event) {

    }
}
