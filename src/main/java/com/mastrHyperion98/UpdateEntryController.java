package com.mastrHyperion98;

/*
Created by: Steven Smith
Created for: PasswordManager project @ https://github.com/mastrHyperion98/PasswordManager

Project under the GPL3 license.
Controls Update Entry view.
 */

import com.mastrHyperion98.struct.Controller;
import com.mastrHyperion98.struct.Data;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateEntryController   {
    @FXML
    private TextField tfDomain;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfUsername;
    @FXML
    private TextField tfPassword;

    private Controller myController;
    private final int PASSWORD_GEN_LENGTH=32;
    private Data data_entry;

    @FXML
    void btnUpdateClicked(ActionEvent event) {
        String domain = tfDomain.getText();
        int id = data_entry.getId();
        String username = tfUsername.getText();
        String email = tfEmail.getText();
        String password = tfPassword.getText();

        boolean success = myController.getSession().editEntry(id,domain,email,username,password);
        if(success) {
            data_entry.setUsername(username);
            data_entry.setEmail(email);
            data_entry.setPassword(password);
            closeStage(event);
        }
    }

    @FXML
    void btnGeneratePassowrd(ActionEvent event) {
        String password= getAlphaNumericString(PASSWORD_GEN_LENGTH);
        tfPassword.setText(password);
    }

    private String getAlphaNumericString(int n){
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz"
                + "#$%&=+-!%";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    public void setController(Controller _controller){

        myController = _controller;
    }

    public void setPassword(Data data){
        data_entry = data;
        tfDomain.setText(data.getDomain());
        tfEmail.setText(data.getEmail());
        tfUsername.setText(data.getUsername());
        tfPassword.setText(data.getPassword());
        // disable editing on the domain
        tfDomain.setEditable(false);
    }
    private void closeStage(ActionEvent event) {
        Node  source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

}