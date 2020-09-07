package com.mastrHyperion98.struct;
/*
Created by: Steven Smith
Created for: PasswordManager project @ https://github.com/mastrHyperion98/PasswordManager

Project under the GPL3 license.
Controller contains all the actions that is available to the user from the user interface.
 */

import com.mastrHyperion98.Encoder.AES;
import com.mastrHyperion98.org.database.Session;

import javax.crypto.SecretKey;
import java.io.*;
import java.security.KeyStore;
import java.security.KeyStore.SecretKeyEntry;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.Scanner;


public class Controller {
    private String databse;
    private Session session;
    private final File data_directory;
    private final File config_directory;
    private final File app_directory;
    private final String FOLDER_NAME="PassLock";
    private boolean isSecretKeyLoaded;
    private char[] key_store_password = "PLACEHOLDER".toCharArray();

    /** Default Constructor
     *
     */
    public Controller(){
        // acquire files path directory
        String home = System.getProperty("user.home");
        app_directory = new File(home+"/"+FOLDER_NAME);
        if(!app_directory.exists())
            app_directory.mkdir();

        data_directory = new File(app_directory.getPath() + "/data");
        config_directory = new File(app_directory.getPath() + "/config");
        if(!data_directory.exists())
            data_directory.mkdir();

        if(!config_directory.exists())
            config_directory.mkdir();

        databse = data_directory.getPath()+"/db.db";
        session = new Session(databse);
        isSecretKeyLoaded = false;
    }

    public char[] getKeyStorePassword(){
        return key_store_password;
    }

    /** A function that creates and setups the database to fit the requirements of the application.
     *
     * @param password a String denoting the master password to be used by the user
     * @return true if the database is successfully created
     */
    public boolean CreateDatabase(String password){
        session.SetPassword(password);
       return session.createTable();
    }

    /** Getter function for session.
     *
     * @return the instance of Session
     */
    public final Session getSession(){
        return session;
    }

    /** A function that validates the database.
     *
     * @return true if the database if valide
     */
    public boolean ValidateDatabase(){
        return session.Validate();
    }

    /** A function that reads, decrypt the secret key from its file.
     *
     * @return true if the secret key is successfully read
     */
    public boolean LoadSecretKey(){
        String path = (config_directory.getPath()+"/keys.p12");
        boolean success = false;
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            try(InputStream keyStoreData = new FileInputStream(path)){
                keyStore.load(keyStoreData, key_store_password);
            } catch (IOException e) {
                e.printStackTrace();
            }
            KeyStore.PasswordProtection entryProtection = new KeyStore.PasswordProtection(key_store_password);
            SecretKeyEntry secret_key_entry = (SecretKeyEntry) keyStore.getEntry("secretKey",entryProtection);
            SecretKey secret_key = secret_key_entry.getSecretKey();
            AES.setSecretKey(secret_key);
            success = true;
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | UnrecoverableEntryException e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     *
     * @return a String of the path to the config directory
     */
    public String getConfigPath(){
        return config_directory.getPath();
    }

    /** A function that validates the users password and authenticates the login request.
     *
     * @param password a String that denotes the users entered password.
     * @return true if the password is valid, false otherwise.
     */
    public boolean ValidateLogin(String password){
        session.SetPassword(password);
        return session.Authenticate();
    }

    public void DeleteFiles(){
        File config = new File(config_directory.getPath()+"keys.p12");
        File database = new File(data_directory+"/db.db");

        if(config.exists())
            config.delete();
        if(database.exists())
            database.delete();
    }
}
