package org.nailsandscrews;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.WindowMatchers;

import java.util.Objects;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginControllerTest extends ApplicationTest {


    @Start
    public void start(javafx.stage.Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testDatabaseConnection() {
        DatabaseConnection.openDBSession();
        DatabaseConnection.closeDBSession();
    }

    @Test
    public void testSuccessfulAdminLogin() {
        clickOn("#usernameField").write("0");

        clickOn("#passwordField").write("0");

        clickOn("#loginButton");

        FxAssert.verifyThat(window("Admin Screen"), WindowMatchers.isShowing());
    }

    @Test
    public void testSuccessfulEmployeeLogin() {
        clickOn("#usernameField").write("1");

        clickOn("#passwordField").write("1");

        clickOn("#loginButton");

        FxAssert.verifyThat(window("Stock Screen"), WindowMatchers.isShowing());
    }

    @Test
    public void testWrongPasswordLogin() {
        // Click on the username field and write a username
        clickOn("#usernameField").write("1");

        // Click on the password field and write a password
        clickOn("#passwordField").write("0");

        // Click on the login button
        clickOn("#loginButton");

        // Verify that an error dialog is shown
        FxAssert.verifyThat("Error", NodeMatchers.isVisible());
    }

    @Test
    public void testEmptyFieldsLogin() {
        // Click on the login button
        clickOn("#loginButton");

        // Verify that an error dialog is shown
        FxAssert.verifyThat("Error", NodeMatchers.isVisible());
    }

    @Test
    public void testEmptyUsernameLogin() {
        // Click on the password field and write a password
        clickOn("#passwordField").write("1");

        // Click on the login button
        clickOn("#loginButton");

        // Verify that an error dialog is shown
        FxAssert.verifyThat("Error", NodeMatchers.isVisible());
    }

    @Test
    public void testEmptyPasswordLogin() {
        // Click on the username field and write a username
        clickOn("#usernameField").write("1");

        // Click on the login button
        clickOn("#loginButton");

        // Verify that an error dialog is shown
        FxAssert.verifyThat("Error", NodeMatchers.isVisible());
    }

    @Test
    public void testLoginWithEnterKey() {
        // Click on the username field and write a username
        clickOn("#usernameField").write("1");

        // Click on the password field and write a password
        clickOn("#passwordField").write("1");

        // Press the enter key
        push(KeyCode.ENTER);

        // Verify that the Stock screen is shown
        FxAssert.verifyThat(window("Stock Screen"), WindowMatchers.isShowing());
    }

    @Test
    public void testContactAdminButton() {
        // Click on the contact admin button
        clickOn("#contactAdminButton");

        // Verify that the contact admin dialog is shown
        FxAssert.verifyThat(window("Contact Admin"), WindowMatchers.isShowing());
    }
}