package com.mastrHyperion98;
/*
Created by: Steven Smith
Created for: PasswordManager project @ https://github.com/mastrHyperion98/PasswordManager

Project under the GPL3 license.
Controller for the Login logic of the Login FXML view.
 */


import com.mastrHyperion98.struct.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    private Controller myController;
    private Stage main_stage;
   // @FXML
    //private Text actiontarget;
    @FXML private PasswordField passwordField;

    @FXML protected void handleSubmitButtonAction(ActionEvent event) throws IOException {

        if(passwordField.getText().equals("")) {
           // actiontarget.setFill(Color.RED);
           // actiontarget.setText("ERROR: Invalid Input. Password cannot be empty.");
            return;
        }
        // validate if password is valid.
        if(myController.ValidateLogin(passwordField.getText())){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dataview.fxml"));
            Parent root = fxmlLoader.load();
            TableViewController tableViewController = fxmlLoader.<TableViewController>getController();
            tableViewController.setController(myController);
            Scene scene = new Scene(root, 900, 550);
            main_stage.setScene(scene);
            main_stage.setMinWidth(900);
            main_stage.setMinHeight(550);
        }else{
            //actiontarget.setFill(Color.RED);
            System.out.println("ERROR: Invalid Input. Password is incorrect.");
        }
    }
    public void setController(Controller _controller){
        myController = _controller;
    }

    public void setStage(Stage stage){
        main_stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        passwordField.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER)
            {
                try {
                    handleSubmitButtonAction(new ActionEvent());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}