package org.nailsandscrews;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {

  private Stage stage;
  private Scene scene;
  private Parent root;

  public void switchToScene1(ActionEvent event) throws IOException {
   root = FXMLLoader.load(getClass().getResource("Login.fxml"));
   stage = (Stage)((Node)event.getSource()).getScene().getWindow();
   scene = new Scene(root);
   stage.setScene(scene);
   stage.show();
  }

  public void switchToScene2(ActionEvent event) throws IOException {
   Parent root = FXMLLoader.load(getClass().getResource("StockScreen.fxml"));
   stage = (Stage)((Node)event.getSource()).getScene().getWindow();
   scene = new Scene(root);
   stage.setScene(scene);
   stage.show();
  }

  public void switchToScene3(ActionEvent event) throws IOException {
   Parent root = FXMLLoader.load(getClass().getResource("contactAdmin.fxml"));
   stage = (Stage)((Node)event.getSource()).getScene().getWindow();
   scene = new Scene(root);
   stage.setScene(scene);
   stage.show();
  }

  public void switchToScene4(ActionEvent event) throws IOException {
   Parent root = FXMLLoader.load(getClass().getResource("addUser.fxml"));
   stage = (Stage)((Node)event.getSource()).getScene().getWindow();
   scene = new Scene(root);
   stage.setScene(scene);
   stage.show();
  }

    public void switchToScene5(ActionEvent event) throws IOException {
     Parent root = FXMLLoader.load(getClass().getResource("addStock.fxml"));
     stage = (Stage)((Node)event.getSource()).getScene().getWindow();
     scene = new Scene(root);
     stage.setScene(scene);
     stage.show();
    }

    public void switchToScene6(ActionEvent event) throws IOException {
     Parent root = FXMLLoader.load(getClass().getResource("adminStockScreen.fxml"));
     stage = (Stage)((Node)event.getSource()).getScene().getWindow();
     scene = new Scene(root);
     stage.setScene(scene);
     stage.show();
    }
}