package com.mastrHyperion98;

import com.mastrHyperion98.Encoder.AES;
import com.mastrHyperion98.struct.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;

public class LoginController {
    private Controller myController;
    private Stage main_stage;
    @FXML
    private Text actiontarget;
    @FXML private PasswordField passwordField;

    @FXML protected void handleSubmitButtonAction(ActionEvent event) throws IOException {

        if(passwordField.getText().equals("")) {
            actiontarget.setFill(Color.RED);
            actiontarget.setText("ERROR: Invalid Input. Password cannot be empty.");
            return;
        }
        // validate if password is valid.
        if(myController.ValidateLogin(passwordField.getText())){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TableView.fxml"));
            Parent root = fxmlLoader.load();
            TableViewController tableViewController = fxmlLoader.<TableViewController>getController();
            tableViewController.setController(myController);
            Scene scene = new Scene(root, 400, 300);
            main_stage.setScene(scene);
            main_stage.setMinWidth(1000);
            main_stage.setMinHeight(400);
            main_stage.setResizable(true);
        }else{
            actiontarget.setFill(Color.RED);
            actiontarget.setText("ERROR: Invalid Input. Password is incorrect.");
        }
    }
    public void setController(Controller _controller){
        myController = _controller;
    }

    public void setStage(Stage stage){
        main_stage = stage;
    }
}