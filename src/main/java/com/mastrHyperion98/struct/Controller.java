package com.mastrHyperion98.struct;
/*
Created by: Steven Smith
Created for: PasswordManager project @ https://github.com/mastrHyperion98/PasswordManager

Project under the GPL3 license.
Controller contains all the actions that is available to the user from the user interface.
 */

import com.mastrHyperion98.Encoder.AES;
import com.mastrHyperion98.org.database.Session;
import java.io.*;
import java.util.Scanner;


public class Controller {
    private String databse;
    private Session session;
    private final File data_directory;
    private final File config_directory;
    private final File app_directory;
    private boolean isSecretKeyLoaded;
    private final String KEY = "PLACEHOLDER";

    /** Default Constructor
     *
     */
    public Controller(){
        // acquire files path directory
        String home = System.getProperty("user.home");
        app_directory = new File(home+"/PasswordManager");
        if(!app_directory.exists())
            app_directory.mkdir();

        data_directory = new File(app_directory.getPath() + "/data");
        config_directory = new File(app_directory.getPath() + "/config");
        if(!data_directory.exists())
            data_directory.mkdir();

        if(!config_directory.exists())
            config_directory.mkdir();

        databse = data_directory.getPath()+"\\db.db";
        session = new Session(databse);
        isSecretKeyLoaded = false;
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
        File file = new File(config_directory.getPath()+"/secret_key.key");
        if(!file.exists())
            return false;
        if(isSecretKeyLoaded)
            return isSecretKeyLoaded;
        try {
            Scanner file_reader = new Scanner(new FileInputStream(file));
            if(!file_reader.hasNextLine())
                return false;
            AES.setSecretKey(KEY);
            String secretKey = AES.decrypt(file_reader.nextLine());
            AES.setSecretKey(secretKey);
            isSecretKeyLoaded = true;
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    /** A function that encrypts and writes the secretKey to its appropriate location.
     *
     * @param secretKey a String denoting the secretKey to be stored.
     * @return true if the operation is successful, false otherwise.
     */
    public boolean WriteSecretKey(String secretKey){
        if(isSecretKeyLoaded)
            return !isSecretKeyLoaded;

        File file = new File(config_directory.getPath()+"/secret_key.key");
        try {
            FileWriter writer = new FileWriter(file);
            AES.setSecretKey(KEY);
            writer.write(AES.encrypt(secretKey));
            AES.setSecretKey(secretKey);
            writer.flush();
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            file.delete();
            return false;
        }
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

}
