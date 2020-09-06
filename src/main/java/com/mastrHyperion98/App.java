package com.mastrHyperion98;
/*
Created by: Steven Smith
Created for: PasswordManager project @ https://github.com/mastrHyperion98/PasswordManager

Project under the GPL3 license.
Main application
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.mastrHyperion98.struct.Controller;

import java.io.IOException;

public class App extends Application {
    private static final String DATABASE_LAUNCH_ERROR="Database Instance is missing or incorrect! This may be the first time the application is launched.";
    private static final String SK_LAUNCH_ERROR="keys.p12 is either corrupted or missing! Security information cannot be validated.\n\nPlease replace keys.p12 with a proper key or generate a new database.";
    private static Controller _controller;
    @Override
    public void start(Stage stage) throws Exception{
            _controller = new Controller();
            Scene scene;
            String title;
            Pane root;
            boolean isDatabaseValid = _controller.ValidateDatabase();
            boolean isSecretKeyLoaded = _controller.LoadSecretKey();
            // check if the database is valid
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("_login.fxml"));
            root = fxmlLoader.load();
            LoginController scene_controller = fxmlLoader.<LoginController>getController();
            scene_controller.setStage(stage);
            scene_controller.setController(_controller);
            scene = new Scene(root, 800, 500);

            title = "PassLock";
            Launch(stage, scene, title);

            InvalidDatabase(isDatabaseValid);
            InvalidSecretKey(isSecretKeyLoaded);
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

    private static void InvalidDatabase(boolean isDatabaseValid){
        if(!isDatabaseValid){
            FXMLLoader _fxmlLoader = new FXMLLoader(App.class.getResource("StartupError.fxml"));
            Parent parent = null;
            Stage popup_stage = new Stage();
            Scene popup_scene;
            try {

                parent = _fxmlLoader.load();
                StartupErrorController viewController = _fxmlLoader.<StartupErrorController>getController();
                viewController.setDatabaseController(_controller);
                viewController.setErrorMessage(DATABASE_LAUNCH_ERROR);
                viewController.setCurrent_stage(popup_stage);
                popup_scene = new Scene(parent, 350, 220);
                popup_stage.setResizable(false);
                popup_stage.setTitle("PassLock: Error");
                popup_stage.initModality(Modality.APPLICATION_MODAL);
                popup_stage.setScene(popup_scene);
                popup_stage.getIcons().add(new Image(App.class.getResourceAsStream("icon/icons8-lock-64.png")));
                popup_stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void InvalidSecretKey(boolean isSecretKeyLoaded){
        if(!isSecretKeyLoaded){
            FXMLLoader _fxmlLoader = new FXMLLoader(App.class.getResource("StartupError.fxml"));
            Parent parent = null;
            Stage popup_stage = new Stage();
            Scene popup_scene;
            try {

                parent = _fxmlLoader.load();
                StartupErrorController viewController = _fxmlLoader.<StartupErrorController>getController();
                viewController.setDatabaseController(_controller);
                viewController.setErrorMessage(SK_LAUNCH_ERROR);
                viewController.setCurrent_stage(popup_stage);
                popup_scene = new Scene(parent, 350, 220);
                popup_stage.setResizable(false);
                popup_stage.setTitle("PassLock: Error");
                popup_stage.initModality(Modality.APPLICATION_MODAL);
                popup_stage.setScene(popup_scene);
                popup_stage.getIcons().add(new Image(App.class.getResourceAsStream("icon/icons8-lock-64.png")));
                popup_stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
