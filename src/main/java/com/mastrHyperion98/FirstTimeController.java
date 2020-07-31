package com.mastrHyperion98;

import com.mastrHyperion98.struct.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class FirstTimeController {
    private static Controller myController;
    private static Stage main_stage;
    @FXML
    private Text actiontarget;
    @FXML private PasswordField passwordField;

    @FXML protected void handleSubmitButtonAction(ActionEvent event) throws IOException {
        String password = passwordField.getText();
        // validate password
        boolean valid = myController.GenerateDatabase(password);
        if (valid){
            Parent root = FXMLLoader.load(getClass().getResource("/scenes/login.fxml"));
            LoginController.setController(myController);
            LoginController.setStage(main_stage);
            Scene scene = new Scene(root, 400, 300);
            main_stage.setScene(scene);
        }
        else{
            actiontarget.setFill(Color.RED);
            actiontarget.setText("ERROR: Password is invalid!");
        }
    }
    public static void setController(Controller _controller){
        myController = _controller;
    }

    public static void setStage(Stage stage){
        main_stage = stage;
    }
}
