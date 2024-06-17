package org.nailsandscrews;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.WindowMatchers;

import java.nio.file.Files;
import java.nio.file.Paths;


import java.util.Objects;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class StockControllerTest extends ApplicationTest {


    @Start
    public void start(javafx.stage.Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testDataConnection() throws Exception {
        clickOn("#usernameField").write("1");
        clickOn("#passwordField").write("1");

        clickOn("#loginButton");
        DatabaseConnection.openDBSession();
        DatabaseConnection.closeDBSession();
    }

    @Test
    public void testContactAdminButton() throws Exception {
        clickOn("#usernameField").write("1");
        clickOn("#passwordField").write("1");

        clickOn("#loginButton");
        clickOn("#contactAdmin");
        FxAssert.verifyThat(window("Contact Admin"), WindowMatchers.isShowing());
    }

    @Test
    public void testLogoutButton() throws Exception {
        clickOn("#usernameField").write("1");
        clickOn("#passwordField").write("1");

        clickOn("#loginButton");
        clickOn("#logoutButton");
        FxAssert.verifyThat(window("Login Screen"), WindowMatchers.isShowing());
    }

    @Test
    public void testSearchField() throws Exception {
        clickOn("#usernameField").write("1");
        clickOn("#passwordField").write("1");

        clickOn("#loginButton");
        clickOn("#searchField").write("Nail");
    }

    @Test
    public void testTreeViewNavigation() {


        clickOn("#usernameField").write("1");
        clickOn("#passwordField").write("1");

        clickOn("#loginButton");
        // Get the TreeView

        //Get the TreeView
        TreeView<?> treeView = lookup("#treeView").query();

        // Expand the root node
        Platform.runLater(() -> treeView.getRoot().setExpanded(true));
        sleep(1000); // wait for the UI to update

        // Click on the root node
        clickOn("#treeView .tree-cell:contains('Screw')");

        // Add a delay to ensure that the child nodes are available when the test is run
        sleep(1000);

        // Click on the child node
        moveTo("#treeView .tree-cell:contains('Phillips Head Screw')");

        clickOn("#treeView .tree-cell:contains('Phillips Head Screw')");
    }

    @Test
    public void testAddStockButton() throws Exception {
        clickOn("#usernameField").write("1");
        clickOn("#passwordField").write("1");

        clickOn("#loginButton");

        // Get the rightScreen Node
        Node rightScreen = lookup("#rightScreen").query();

        // Make the rightScreen visible
        Platform.runLater(() -> rightScreen.setVisible(true));

        // Add a delay to ensure that the rightScreen is visible when the test is run
        sleep(1000);
        clickOn("#buyStockAmount").write("1");
        clickOn("#buyStockAmountButton");
        Assertions.assertEquals("1", "#buyStockAmount");
    }

    @Test
    public void testSellStockButton() throws Exception {
        clickOn("#usernameField").write("1");
        clickOn("#passwordField").write("1");

        clickOn("#loginButton");

        // Get the rightScreen Node
        Node rightScreen = lookup("#rightScreen").query();

        // Make the rightScreen visible
        Platform.runLater(() -> rightScreen.setVisible(true));

        // Add a delay to ensure that the rightScreen is visible when the test is run
        sleep(1000);
        clickOn("#sellStockAmount").write("1");

        clickOn("#sellStockAmountButton");
        Assertions.assertEquals("1", "#sellStockAmount");
    }

    @Test
    public void testPrintAll() throws Exception {
        clickOn("#usernameField").write("1");
        clickOn("#passwordField").write("1");

        clickOn("#loginButton");

        // Add a delay to ensure that the rightScreen is visible when the test is run
        sleep(1000);
        clickOn("#printAll");
        // check if txt file AllStock output.txt exists
        boolean fileExists = Files.exists(Paths.get("AllStock output.txt"));
        Assertions.assertTrue(fileExists);
        Files.deleteIfExists(Paths.get("AllStock output.txt"));
    }

    @Test
    public void testPrintSelectedItem() throws Exception {
        clickOn("#usernameField").write("1");
        clickOn("#passwordField").write("1");

        clickOn("#loginButton");

        // Get the rightScreen Node
        Node rightScreen = lookup("#rightScreen").query();

        // Make the rightScreen visible
        Platform.runLater(() -> rightScreen.setVisible(true));

        // Add a delay to ensure that the rightScreen is visible when the test is run
        sleep(1000);
        clickOn("#PrintSpec");
        // check if txt file SelectedStock output.txt exists
        boolean fileExists = Files.exists(Paths.get("SpecificStock output.txt"));
        Assertions.assertTrue(fileExists);
        Files.deleteIfExists(Paths.get("SpecificStock output.txt"));
    }
}