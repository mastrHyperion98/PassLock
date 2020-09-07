package com.mastrHyperion98;

import com.mastrHyperion98.Encoder.AES;
import com.mastrHyperion98.struct.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.Base64;
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
        try {
            // generate a secret key for the database
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = new SecureRandom();
            int keyBitSize = 256;
            keyGenerator.init(keyBitSize, secureRandom);
            SecretKey secretKey = keyGenerator.generateKey();
            // Write secretKey to file (Use new thread)
            Thread secret_key_writer = new Thread(() -> {
                String path = database_controller.getConfigPath()+"/keys.p12";
                try {
                    char[] entryPassword = database_controller.getKeyStorePassword();
                    KeyStore.PasswordProtection entryProtection = new KeyStore.PasswordProtection(entryPassword);
                    KeyStore keyStore = KeyStore.getInstance("PKCS12");
                    keyStore.load(null, entryPassword );
                    KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(secretKey);
                    keyStore.setEntry("secretKey", secretKeyEntry, entryProtection);
                    try (FileOutputStream keyStoreOutputStream = new FileOutputStream(path)) {
                        keyStore.store(keyStoreOutputStream, entryPassword);
                    }
                    System.out.println("KeyStore stored successfully");
                } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
                    e.printStackTrace();
                    File file = new File(path);
                    if(file.exists())
                        file.delete();
                }
            });
            secret_key_writer.start();
            AES.setSecretKey(secretKey);
            String password = passwordField.getText();
            boolean success = database_controller.CreateDatabase(password);
            if(success)
                main_stage.close();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
