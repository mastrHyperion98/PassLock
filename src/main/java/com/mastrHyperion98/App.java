package com.mastrHyperion98;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.mastrHyperion98.struct.Controller;

import java.util.Objects;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception{

        Controller _controller = new Controller();
        Scene scene;
        String title;

            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            LoginController.setController(_controller);
            LoginController.setStage(stage);
            scene = new Scene(root, 600, 500);
            title="Signum";

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
