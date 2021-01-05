package com.mastrHyperion98;

import com.mastrHyperion98.struct.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NotificationController  implements Initializable {
    // member variable
    @FXML
    private Text message;
    private Stage current_stage;
    private Controller database_controller;
    public void setMessage(String errorMessage){
        message.setText(errorMessage);
    }

    public void setDatabaseController(Controller object){
        database_controller = object;
    }

    public void setCurrent_stage(Stage stage){
        current_stage = stage;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML protected void confirm(ActionEvent event){
        current_stage.close();
    }
}
