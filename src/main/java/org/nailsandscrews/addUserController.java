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

public class addUserController {

    public TextField addTypeField;
    public TextField addUsernameField;
    public TextField addPasswordField;
    public Button addUserButton;
    public Button searchUserButton;
    public Button deleteUserButton;
    public Button updateUserButton;
    public TextField searchByUsernameField;
    public TextField searchByTypeField;
    public TextArea resultArea;


    @FXML
    TextField usernameField;

    @FXML
    TextField passwordField;

    @FXML
    TextField typeField;

    @FXML
    Button addUser;

    @FXML
    private Button userSearch;

    @FXML
    Button deleteUser;

    @FXML
    private Button openAdminPage;

    @FXML
    private TableColumn<User, Integer> IdColumn;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> passwordColumn;

    @FXML
    private TableColumn<User, String> typeColumn;

    @FXML
    private TableColumn<User, String> createColumn;

    @FXML
    private TableView<User> UserTable;

    @FXML
    private void insertUser() {

    }

    @FXML
    private void searchUser() {

    }

    @FXML
    private void deleteUser() {

    }

    @FXML
    private void addUsernameField() {

    }

    public void initialize() {
        // display all users columns in the table
        DatabaseConnection.openDBSession();
        DatabaseConnection.databaseSession.beginTransaction();
        List<User> users = DatabaseConnection.databaseSession.createQuery("from User").getResultList();
        DatabaseConnection.databaseSession.getTransaction().commit();
        DatabaseConnection.closeDBSession();
        UserTable.setItems(FXCollections.observableArrayList(users));

        IdColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("ID"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<User, String>("type"));
        createColumn.setCellValueFactory(new PropertyValueFactory<User, String>("create_time"));

        // add user to the sql database and the tableview when the add user button is clicked from the add fields
        addUser.setOnAction(e -> {
            String username = addUsernameField.getText();
            username = username.substring(0, 1).toUpperCase() + username.substring(1);

            String password = addPasswordField.getText();
            password = password.substring(0, 1).toUpperCase() + password.substring(1);

            String type = addTypeField.getText();
            type = type.substring(0, 1).toUpperCase() + type.substring(1);

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setType(type);
            user.setCreate_time(LocalDateTime.now().toString());

            DatabaseConnection.openDBSession();
            DatabaseConnection.databaseSession.beginTransaction();
            DatabaseConnection.databaseSession.save(user);
            DatabaseConnection.databaseSession.getTransaction().commit();
            DatabaseConnection.closeDBSession();

            UserTable.getItems().add(user);

            addUsernameField.clear();
            addPasswordField.clear();
            addTypeField.clear();
            //display the command to add users to the database in the resultArea
            resultArea.setText("INSERT INTO User (username, password, type, create_time) VALUES ('" + username + "', '" + password + "', '" + type + "', '" + LocalDateTime.now().toString() + "');");

            SceneController sceneController = new SceneController();
            try {
                sceneController.addUser(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });


        // search button to highlight the user in the tableview that matches the username and type fields


        openAdminPage.setOnAction(e -> {
            try {
                SceneController sceneController = new SceneController();
                sceneController.AdminScreen(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        // delete the selected user from the tableview and the database
        deleteUser.setOnAction(e -> {
            User user = UserTable.getSelectionModel().getSelectedItem();
            UserTable.getItems().remove(user);

            DatabaseConnection.openDBSession();
            DatabaseConnection.databaseSession.beginTransaction();
            DatabaseConnection.databaseSession.delete(user);
            DatabaseConnection.databaseSession.getTransaction().commit();
            DatabaseConnection.closeDBSession();

            //display the command to delete users from the database in the resultArea
            resultArea.setText("DELETE FROM User WHERE ID = " + user.getID() + ";");
            SceneController sceneController = new SceneController();
            try {
                sceneController.addUser(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // update the selected user from the tableview and the database
    }


    public void searchUsers(ActionEvent event) {

    }

    public void addUser(ActionEvent event) {

    }

    public void deleteUser(ActionEvent event) {

    }
}