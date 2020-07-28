package scenes;

import Encoder.AES;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import struct.Password;
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

    private ObservableList<Password> appMainObservableList;
    private struct.Controller myController;
    private final int PASSWORD_GEN_LENGTH=32;

    @FXML
    void btnAddEntryClicked(ActionEvent event) {
        System.out.println("btnAddPersonClicked");
        String domain = tfDomain.getText().trim().toLowerCase();
        String email = tfEmail.getText().trim();
        String username = tfUsername.getText().trim();
        String password = tfPassword.getText().trim();


        try {
            myController.getSession().writeEntry(domain,email,username,AES.encrypt(password));
            Password data = myController.getSession().fetchEntry(domain);
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
    public void setAppMainObservableList(ObservableList<Password> observableList) {
        this.appMainObservableList = observableList;
    }

    public void setController(struct.Controller _controller){

        myController = _controller;
    }

    private void closeStage(ActionEvent event) {
        Node  source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
