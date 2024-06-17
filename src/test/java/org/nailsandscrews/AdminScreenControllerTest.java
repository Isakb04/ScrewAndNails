package org.nailsandscrews;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.WindowMatchers;

import java.util.Objects;

import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdminScreenControllerTest extends ApplicationTest {


    @Start
    public void start(javafx.stage.Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminScreen.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testAddStockButton() throws Exception {
        clickOn("#addStockButton");
        FxAssert.verifyThat(window("Add Stock"), WindowMatchers.isShowing());
    }

    @Test
    public void testAddUserButton() throws Exception {
        clickOn("#addUserButton");
        FxAssert.verifyThat(window("Add User"), WindowMatchers.isShowing());
    }

    @Test
    public void testMessagesButton() throws Exception {
        clickOn("#messagesButton");
        FxAssert.verifyThat(window("Contact Admin"), WindowMatchers.isShowing());
    }

    @Test
    public void testStockButton() throws Exception {
        clickOn("#stockScreenButton");
        sleep(5000);
        FxAssert.verifyThat(window("Stock Screen"), WindowMatchers.isShowing());
    }

    @Test
    public void testLogoutButton() throws Exception {
        clickOn("#logoutButton");
        FxAssert.verifyThat(window("Login Screen"), WindowMatchers.isShowing());
    }
}