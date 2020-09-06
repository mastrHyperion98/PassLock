package com.mastrHyperion98;

import com.mastrHyperion98.struct.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateDabaseController implements Initializable {
    @FXML
    private PasswordField passwordField;
    private Stage main_stage;
    private Controller database_controller;

    public void setDatabaseController(Controller object){
        database_controller = object;
    }

    public void setStage(Stage stage){
        main_stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML protected void handleCreateButtonAction(ActionEvent event){

    }
}
