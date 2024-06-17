package org.nailsandscrews;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import org.junit.jupiter.api.*;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.WindowMatchers;


import java.util.Objects;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddStockControllerTest extends ApplicationTest {

    private TableView<Stock> tableView;

    @Start
    public void start(javafx.stage.Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addStock.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    public void setUp() {
        // Get the TableView
        tableView = lookup("#StockTable").queryAs(TableView.class);
        // Ensure the TableView is scrolled to the top before each test
        Platform.runLater(() -> tableView.scrollTo(0));
    }

    @Test
    @Order(1)
    public void returnToAdminHub() {
        // Click on the back button
        clickOn("#openAdminPage");

        // Verify that the admin screen is shown
        FxAssert.verifyThat(window("Admin Screen"), WindowMatchers.isShowing());
    }

    @Test
    @Order(3)
    public void testDeleteStock() {
        // Get the items from the TableView
        ObservableList<Stock> items = tableView.getItems();

        // Define the stock to search for
        String searchStock = "Test";

        // Iterate over the items
        for (int i = 0; i < items.size(); i++) {
            Stock item = items.get(i);
            // Check if the stock is found
            if (item.getType().equals(searchStock) || item.getProduct_type().equals(searchStock) || item.getMaterial().equals(searchStock) || item.getSupplier().equals(searchStock)) {
                // Scroll to the item
                moveTo(tableView);
                final int index = i;
                Platform.runLater(() -> tableView.scrollTo(index));
                // Click on the item
                sleep(1000);
                clickOn("Test");
                clickOn("#deleteStock");
                break;
            }
        }
    }

    @Test
    @Order(2)
    public void testAddStock() {
        clickOn("#addTypeField").write("Test");
        clickOn("#addProductTypeField").write("Test");
        clickOn("#addMaterialField").write("Test");
        clickOn("#addLengthField").write("1-inch");
        clickOn("#addSupplierField").write("Test");
        clickOn("#addStockPriceField").write("0.1");
        clickOn("#addSellPriceField").write("0.1");

        clickOn("#addStock");

        sleep(1000);

        // search TableColumn for the added stock
        TableView tableView = lookup("#StockTable").queryAs(TableView.class);
        ObservableList<Stock> items = tableView.getItems();

        boolean isFound = false;
        for (Object item : items) {
            Stock stock = (Stock) item;
            if (stock.getType().equals("Test") || stock.getProduct_type().equals("Test") || stock.getMaterial().equals("Test") || stock.getSupplier().equals("Test")) {
                isFound = true;
                break;
            }
        }

        Assertions.assertTrue(isFound);
    }
}