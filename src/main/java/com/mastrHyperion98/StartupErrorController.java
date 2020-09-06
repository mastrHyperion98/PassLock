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

public class StartupErrorController implements Initializable {
    // member variable
    @FXML
    private Text message;
    private Stage current_stage;
    private Controller database_controller;
    public void setErrorMessage(String errorMessage){
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

    /*
    If the user clicks the "Yes" button it will load the proper CreateDatabase view.
     */
    @FXML protected void handleYesButton(ActionEvent event){
        // change the content of the stage
        //current_stage.hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateDatabase.fxml"));
        try {
            Parent parent = fxmlLoader.load();
            CreateDabaseController viewController = fxmlLoader.<CreateDabaseController>getController();
            viewController.setDatabaseController(database_controller);
            viewController.setStage(current_stage);
            Scene scene = new Scene(parent, 800, 500);
            current_stage.setScene(scene);
            current_stage.setTitle("PassLock: Create Database");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    Closes the stage upon clicking "No"
     */
    @FXML protected void handleNoButton(ActionEvent event){
        current_stage.close();
    }
}
