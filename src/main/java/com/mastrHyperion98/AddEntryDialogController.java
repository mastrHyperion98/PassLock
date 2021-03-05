package com.mastrHyperion98;

/*
Created by: Steven Smith
Created for: PasswordManager project @ https://github.com/mastrHyperion98/PasswordManager

Project under the GPL3 license.
Controller for AddEntry FXML. Controls the actions of each element of the scene.
 */

import com.mastrHyperion98.Encoder.AES;
import com.mastrHyperion98.struct.Data;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddEntryDialogController   {
    @FXML
    private TextField tfDomain;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfUsername;
    @FXML
    private TextField tfPassword;

    private ObservableList<Data> appMainObservableList;
    private com.mastrHyperion98.struct.Controller myController;
    private final int PASSWORD_GEN_LENGTH=32;

    @FXML
    void btnAddEntryClicked(ActionEvent event) {
        String domain = tfDomain.getText().trim().toLowerCase();
        String email = tfEmail.getText().trim();
        String username = tfUsername.getText().trim();
        String password = tfPassword.getText().trim();


        try {
            myController.getSession().writeEntry(domain,email,username,AES.encrypt(password));
            Data data = myController.getSession().fetchEntry(domain);
            appMainObservableList.add(data);
        } catch (SQLException throwables) {
            System.out.println("ERROR--DUPLICATE");
        }
        closeStage(event);
    }

    @FXML
    void btnGeneratePassowrd(ActionEvent event) {
        String password= getAlphaNumericString(PASSWORD_GEN_LENGTH);
        tfPassword.setText(password);
    }

    /** A psudo-random String generator for passwords.
     *
     * @param n number of characters
     * @return the generated psudo-random String
     */
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

    /** Setter method for appMainObservableList
     *
     * @param observableList list of Passwords in the database/table
     */
    public void setAppMainObservableList(ObservableList<Data> observableList) {
       appMainObservableList = observableList;
    }

    /** Setter method for myController.
     *
     * @param _controller Reference to a Controller object
     */
    public void setController(com.mastrHyperion98.struct.Controller _controller){

        myController = _controller;
    }

    /** A function the closes the stage.
     *
     * @param event An Event that triggers the stage to close.
     */
    private void closeStage(ActionEvent event) {
        Node  source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
