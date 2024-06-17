package org.nailsandscrews;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import org.junit.jupiter.api.*;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.WindowMatchers;


import java.util.Objects;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ContactAdminControllerTest extends ApplicationTest {


    @Start
    public void start(javafx.stage.Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("contactAdmin.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        LoginController.savedUsername = null;
        LoginController.savedType = null;
    }

    @Test
    @Order(1)
    public void testBackButton() {
        // Click on the back button
        clickOn("#backButton");

        // Verify that the admin screen is shown
        FxAssert.verifyThat(window("Login Screen"), WindowMatchers.isShowing());
    }

    @Test
    @Order(2)
    public void testSendMessage() {
        LoginController.savedUsername = null;
        LoginController.savedType = null;
        clickOn("#userNameField").write("Test");
        clickOn("#message").write("Hello World!");

        // Click on the send button
        clickOn("#sendMessage");
        TextArea messageBox = lookup("#messageBox").queryAs(TextArea.class);

        // Add a delay to ensure that the TextArea is available when the test is run
        sleep(1000);
        String text = messageBox.getText();
        Assertions.assertEquals("""
                [Messages]
                                
                Test: Hello World!
                """, text);
    }


    @Test
    @Order(3)
    public void testSendMessageLoggedInEmployee() {
        clickOn("#backButton");

        clickOn("#usernameField").write("1");

        clickOn("#passwordField").write("1");

        clickOn("#loginButton");

        clickOn("#contactAdmin");


        clickOn("#message").write("Hello World!");

        // Click on the send button
        clickOn("#sendMessage");
        sleep(1000);
        TextArea messageBox = lookup("#messageBox").queryAs(TextArea.class);

        // Add a delay to ensure that the TextArea is available when the test is run
        sleep(1000);
        String text = messageBox.getText();
        Assertions.assertEquals("""
                [Messages]
                                
                Employee 1: Hello World!
                """, text);
    }

    @Test
    @Order(4)
    public void testSendMessageLoggedInAdmin() {
        clickOn("#backButton");

        clickOn("#usernameField").write("0");

        clickOn("#passwordField").write("0");

        clickOn("#loginButton");

        clickOn("#messagesButton");

        // Click on the message field and write a message
        clickOn("#message").write("Hello World!");

        // Click on the send button
        clickOn("#sendMessage");
        sleep(1000);
        TextArea messageBox = lookup("#messageBox").queryAs(TextArea.class);

        // Add a delay to ensure that the TextArea is available when the test is run
        sleep(1000);
        String text = messageBox.getText();
        Assertions.assertEquals("""
                [Messages]
                                
                Admin 0: Hello World!
                """, text);
    }

    @Test
    @Order(5)
    public void testSaveMessage() {

        // Click on the message field and write a message
        clickOn("#userNameField").write("Test");
        clickOn("#message").write("Hello World!");
        clickOn("#sendMessage");
        clickOn("#backButton");
        clickOn("#contactAdminButton");
        sleep(1000);
        TextArea messageBox = lookup("#messageBox").queryAs(TextArea.class);

        // Add a delay to ensure that the TextArea is available when the test is run
        sleep(1000);
        String text = messageBox.getText();
        Assertions.assertEquals("""
                [Messages]
                                
                Test: Hello World!
                """, text);
    }
}