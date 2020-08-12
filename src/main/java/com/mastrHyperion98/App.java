package com.mastrHyperion98;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import com.mastrHyperion98.struct.Controller;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception{

        Controller _controller = new Controller();
        Scene scene;
        String title;
        Parent root;
        boolean isDatabaseValid = _controller.ValidateDatabase();
        boolean isSecretKeyLoaded =  _controller.LoadSecretKey();
        // check if the database is valid
        if(isDatabaseValid && isSecretKeyLoaded){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
            root = fxmlLoader.load();
            LoginController scene_controller = fxmlLoader.<LoginController >getController();
            scene_controller.setStage(stage);
            scene_controller.setController(_controller);
            scene = new Scene(root, 800, 500);
        }
        else{
            // first time setup required
            if(!isDatabaseValid && !isSecretKeyLoaded){
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FirstTimeSetup.fxml"));
                root = fxmlLoader.load();
                FirstTimeController scene_controller = fxmlLoader.<FirstTimeController>getController();
                scene_controller.setStage(stage);
                scene_controller.setController(_controller);
                scene = new Scene(root, 800, 500);
            }
            // Missing secret key file
            else if(isDatabaseValid && !isDatabaseValid){
                root = FXMLLoader.load(getClass().getResource("FirstTimeSetup.fxml"));
                scene = new Scene(root, 800, 500);
            }
            // database is missing but secret key is valid.
            else{
                root = FXMLLoader.load(getClass().getResource("FirstTimeSetup.fxml"));
                scene = new Scene(root, 800, 500);
            }
        }


        title="PasswordManager";
        Launch(stage, scene, title);
    }
    public static void main(String[] args) {
        launch(args);
    }

    private static void Launch(Stage stage, Scene scene, String title){
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }
}
