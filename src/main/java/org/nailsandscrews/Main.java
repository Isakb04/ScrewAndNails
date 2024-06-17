package org.nailsandscrews;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {

 @Override
 public void start(Stage stage) {
  try {
   Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
   Scene scene = new Scene(root);
   stage.setTitle("Login Screen");
   stage.setResizable(false);
   stage.setScene(scene);
   stage.centerOnScreen();
   stage.show();
  } catch(Exception e) {
   e.printStackTrace();
  }
 }
    public class Launcher {
        public static void main(String[] args) {
            Main.launch(Main.class, args);
        }
    }

 public static void main(String[] args) {
  launch(args);
 }
}
