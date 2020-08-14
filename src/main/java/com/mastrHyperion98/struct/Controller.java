package com.mastrHyperion98.struct;

import com.mastrHyperion98.Encoder.AES;
import com.mastrHyperion98.org.database.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.*;
import java.sql.SQLException;
import java.util.Scanner;

public class Controller {
    private String databse;
    private Session session;
    private File data_directory;
    private File config_directory;
    private File app_directory;
    private boolean isSecretKeyLoaded;
    private final String KEY = "PLACEHOLDER";

    public Controller(){
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
    public boolean CreateDatabase(String password){
        session.SetPassword(password);
       return session.createTable();
    }

    public final Session getSession(){
        return session;
    }

    public boolean ValidateDatabase(){
        return session.Validate();
    }

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

    public boolean ValidateLogin(String password){
        session.SetPassword(password);
        return session.Authenticate();
    }

}
