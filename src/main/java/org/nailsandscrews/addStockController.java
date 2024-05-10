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
    private TableColumn<Stock, Integer> IdColumn;

    @FXML
    private TableColumn<Stock, String> typeColumn;

    @FXML
    private TableColumn<Stock, String> productTypeColumn;

    @FXML
    private TableColumn<Stock, String> materialColumn;

    @FXML
    private TableColumn<Stock, Integer> lengthColumn;

    @FXML
    private TableColumn<Stock, String> supplierColumn;

    @FXML
    private TableColumn<Stock, Double> buyingPriceColumn;

    @FXML
    private TableColumn<Stock, Double> sellingPriceColumn;

    @FXML
    private TableColumn<Stock, Integer> QuantityColumn;

    @FXML
    private TableColumn<Stock, String> dateAddedColumn;

    @FXML
    private TableColumn<Stock, String> lastUpdatedColumn;

    @FXML
    private TableView<Stock> StockTable;


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
                stock.setType(addTypeField.getText());
                stock.setProduct_type(addProductTypeField.getText());
                stock.setMaterial(addMaterialField.getText());
                stock.setLength(addLengthField.getText());
                stock.setSupplier(addSupplierField.getText());
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
        });

        openAdminPage.setOnAction(e -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("adminScreen.fxml"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin Screen");
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.show();
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
