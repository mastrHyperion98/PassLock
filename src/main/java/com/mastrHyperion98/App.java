package com.mastrHyperion98;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import com.mastrHyperion98.struct.Controller;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception{

            Controller _controller = new Controller();
            Scene scene;
            String title;
            Pane root;
            boolean isDatabaseValid = _controller.ValidateDatabase();
            boolean isSecretKeyLoaded = _controller.LoadSecretKey();
            // check if the database is valid
            if (isDatabaseValid && isSecretKeyLoaded) {

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
                root = fxmlLoader.load();
                LoginController scene_controller = fxmlLoader.<LoginController>getController();
                scene_controller.setStage(stage);
                scene_controller.setController(_controller);
                scene = new Scene(root, 500, 300);
            } else {
                // first time setup required
                if (!isDatabaseValid && !isSecretKeyLoaded) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FirstTimeSetup.fxml"));
                    root = fxmlLoader.load();
                    FirstTimeController scene_controller = fxmlLoader.<FirstTimeController>getController();
                    scene_controller.setStage(stage);
                    scene_controller.setController(_controller);
                    scene = new Scene(root, 800, 500);
                }
                // Missing secret key file
                else if (isDatabaseValid && !isDatabaseValid) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FirstTimeSetup.fxml"));
                    root = fxmlLoader.load();
                    FirstTimeController scene_controller = fxmlLoader.<FirstTimeController>getController();
                    scene_controller.setStage(stage);
                    scene_controller.setController(_controller);
                    scene = new Scene(root, 800, 500);
                    System.exit(1);
                }
                // database is missing but secret key is valid.
                else {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FirstTimeSetup.fxml"));
                    root = fxmlLoader.load();
                    FirstTimeController scene_controller = fxmlLoader.<FirstTimeController>getController();
                    scene_controller.setStage(stage);
                    scene_controller.setController(_controller);
                    scene = new Scene(root, 800, 500);
                    System.exit(1);
                }
            }

            title = "PasswordManager";
            Launch(stage, scene, title);

    }
    public static void main(String[] args) {
        launch(args);
    }

    private static void Launch(Stage stage, Scene scene, String title){
        stage.getIcons().add(new Image(App.class.getResourceAsStream("icon/icons8-lock-64.png")));
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }
}
