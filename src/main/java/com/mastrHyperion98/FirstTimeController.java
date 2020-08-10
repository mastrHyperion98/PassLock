package com.mastrHyperion98;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class FirstTimeController {
    @FXML
    private TextField encryptionField;

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

    }
}
