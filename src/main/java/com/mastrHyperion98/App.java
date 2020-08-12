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
        System.out.println(isDatabaseValid+"\n"+isSecretKeyLoaded);
        // check if the database is valid
        if(isDatabaseValid && isSecretKeyLoaded){
            root = FXMLLoader.load(getClass().getResource("login.fxml"));
        }
        else{
            // first time setup required
            if(!isDatabaseValid && !isSecretKeyLoaded){
                root = FXMLLoader.load(getClass().getResource("FirstTimeSetup.fxml"));
            }
            // Missing secret key file
            else if(isDatabaseValid && !isDatabaseValid){
                root = FXMLLoader.load(getClass().getResource("FirstTimeSetup.fxml"));
            }
            // database is missing but secret key is valid.
            else{
                root = FXMLLoader.load(getClass().getResource("FirstTimeSetup.fxml"));
            }
        }

        LoginController.setController(_controller);
        LoginController.setStage(stage);
        scene = new Scene(root, 800, 500);
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
