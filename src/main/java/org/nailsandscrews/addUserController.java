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
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField typeField;

    @FXML
    private Button addUser;

    @FXML
    private Button userSearch;

    @FXML
    private Button deleteUser;

    @FXML
    private Button updateUser;

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
    private void updateUser() {

    }
    public void initialize() {
        // display all users in the table
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

        // add user to the sql database
        addUser.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String type = typeField.getText();
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setType(type);
            user.setCreate_time(java.time.LocalDate.now().toString());
            DatabaseConnection.openDBSession();
            DatabaseConnection.databaseSession.beginTransaction();
            DatabaseConnection.databaseSession.save(user);
            DatabaseConnection.databaseSession.getTransaction().commit();
            DatabaseConnection.closeDBSession();
            UserTable.getItems().add(user);
            usernameField.clear();
            passwordField.clear();
            typeField.clear();
        });

        // search button to search database by either username or type or both and display the result in the tableview
        userSearch.setOnAction(e -> {
            String username = searchByUsernameField.getText();
            String type = searchByTypeField.getText();
            DatabaseConnection.openDBSession();
            DatabaseConnection.databaseSession.beginTransaction();
            List resultList = new ArrayList<>();
            if (username.isEmpty() && type.isEmpty()) {
                resultList = DatabaseConnection.databaseSession.createQuery("from User").getResultList();
            } else if (username.isEmpty()) {
                resultList = DatabaseConnection.databaseSession.createQuery("from User where type = :type")
                        .setParameter("type", type)
                        .getResultList();
            } else if (type.isEmpty()) {
                resultList = DatabaseConnection.databaseSession.createQuery("from User where username = :username")
                        .setParameter("username", username)
                        .getResultList();
            } else {
                resultList = DatabaseConnection.databaseSession.createQuery("from User where username = :username and type = :type")
                        .setParameter("username", username)
                        .setParameter("type", type)
                        .getResultList();
            }
            DatabaseConnection.databaseSession.getTransaction().commit();
            DatabaseConnection.closeDBSession();
            UserTable.setItems(FXCollections.observableArrayList(resultList));
            searchByUsernameField.clear();
            searchByTypeField.clear();
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

        // delete the selected user from the tableview and the database
        deleteUser.setOnAction(e -> {
            User user = UserTable.getSelectionModel().getSelectedItem();
            if (user == null) {
                return;
            }
            DatabaseConnection.openDBSession();
            DatabaseConnection.databaseSession.beginTransaction();
            DatabaseConnection.databaseSession.delete(user);
            DatabaseConnection.databaseSession.getTransaction().commit();
            DatabaseConnection.closeDBSession();
            UserTable.getItems().remove(user);
        });
    }


    public void searchUsers(ActionEvent event) {

    }

    public void addUser(ActionEvent event) {

    }

    public void deleteUser(ActionEvent event) {

    }

    public void updateUser(ActionEvent event) {

    }

}