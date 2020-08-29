package com.mastrHyperion98;
/*
Created by: Steven Smith
Created for: PasswordManager project @ https://github.com/mastrHyperion98/PasswordManager

Project under the GPL3 license.
Controller for for the First Time fxml view. Logic control for the first time setup operations
 */
import com.mastrHyperion98.struct.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;

public class FirstTimeController {
    @FXML
    private TextField encryptionField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Text errorMessage;
    private Controller myController;
    private Stage main_stage;

    @FXML
    protected void generateEncryptionButtonAction(ActionEvent event){
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz"
                + "#$%&=+-!%";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(256);

        for (int i = 0; i < 256; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        encryptionField.setText(sb.toString());
    }

    @FXML
    protected void createInstance(ActionEvent event){
        if(passwordField.getText().equals("") || encryptionField.getText().equals("")) {
            errorMessage.setText("ERROR: Fields cannot be empty");
            return;
        }
        boolean isSuccessful = myController.WriteSecretKey(encryptionField.getText()) && myController.CreateDatabase(passwordField.getText()) ;
        if(isSuccessful){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TableView.fxml"));
            Parent root = null;
            try {
                root = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            TableViewController tableViewController = fxmlLoader.<TableViewController>getController();
            tableViewController.setController(myController);
            Scene scene = new Scene(root, 400, 300);
            main_stage.setScene(scene);
            main_stage.setMinWidth(1000);
            main_stage.setMinHeight(400);
            main_stage.setResizable(true);
        }
    }
    public void setController(Controller _controller){
        myController = _controller;
    }

    public void setStage(Stage stage){
        main_stage=stage;
    }
}
