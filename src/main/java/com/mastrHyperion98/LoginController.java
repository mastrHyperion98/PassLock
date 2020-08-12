package com.mastrHyperion98;

import com.mastrHyperion98.Encoder.AES;
import com.mastrHyperion98.struct.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

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
    @FXML private TextField encryptionField;

    @FXML protected void handleSubmitButtonAction(ActionEvent event) throws IOException {

        if (!encryptionField.getText().equals("")){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TableView.fxml"));
            Parent root = fxmlLoader.load();
            TableViewController tableViewController = fxmlLoader.<TableViewController>getController();
            tableViewController.setController(myController);
            AES.setSecretKey(encryptionField.getText());
            Scene scene = new Scene(root, 400, 300);
            main_stage.setScene(scene);
            main_stage.setMinWidth(1000);
            main_stage.setMinHeight(400);
            main_stage.setResizable(true);
        }
        else{
            actiontarget.setFill(Color.RED);
            actiontarget.setText("ERROR: Invalid Input");
        }
    }

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
    protected void saveEncryptionButtonAction(ActionEvent event){
        //create and saves a file that is encrypted and contains our secret key either to an ini file
        // or to a .key file. (Content should obviously be encrypted) -- encryption key
        if(encryptionField.getText().equals(""))
            return;

        String home = System.getProperty("user.home");
        File directory = new File(home + "/PasswordManager/config");
        if(!directory.exists())
            directory.mkdir();
        File file = new File(directory.getPath()+"/secret_key.key");
        try {
            FileWriter writer = new FileWriter(file);
            AES.setSecretKey("Non-Release placeholder");
            writer.write(AES.encrypt(encryptionField.getText()));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setController(Controller _controller){
        myController = _controller;
    }

    public void setStage(Stage stage){
        main_stage = stage;
    }
}