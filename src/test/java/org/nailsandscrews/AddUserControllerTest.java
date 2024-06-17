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

import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddUserControllerTest extends ApplicationTest {

    private TableView<User> tableView;


    @Start
    public void start(javafx.stage.Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addUser.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    public void setUp() {
        // Get the TableView
        tableView = lookup("#UserTable").queryAs(TableView.class);
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
    @Order(2)
    public void testAddUser() {

        clickOn("#addUsernameField").write("Test");
        clickOn("#addPasswordField").write("Test");
        clickOn("#addTypeField").write("Test");
        clickOn("#addUser");
        sleep(1000);

        // Verify that the user Test was created
        Assertions.assertTrue(lookup("Test").tryQuery().isPresent());
    }

    @Test
    @Order(3)
    public void testDeleteUser() {
        // Get the items from the TableView
        ObservableList<User> items = tableView.getItems();

        // Define the user to search for
        String searchUser = "Test";

        // Iterate over the items
        for (int i = 0; i < items.size(); i++) {
            User item = items.get(i);
            // Check if the user is found
            if (item.getUsername().equals(searchUser)) {
                // Scroll to the item
                moveTo(tableView);
                final int index = i;
                Platform.runLater(() -> tableView.scrollTo(index));
                // Click on the item
                sleep(1000);
                clickOn("Test");
                clickOn("#deleteUser");
                break;
            }
        }
    }
}