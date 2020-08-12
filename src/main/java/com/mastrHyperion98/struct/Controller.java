package com.mastrHyperion98.struct;

import com.mastrHyperion98.Encoder.AES;
import com.mastrHyperion98.org.database.Session;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

public class Controller {
    private String databse;
    private final Session session;
    private String home;
    private File data_directory;
    private File config_directory;
    private final String KEY = "THIS_IS_A_PLACEHOLDER_KEY_NOT_USED_IN_THE_RELEASE_BUILDS";

    public Controller(){
        String home = System.getProperty("user.home");
        data_directory = new File(home + "/PasswordManager/data");
        config_directory = new File(home + "/PasswordManager/config");
        if(!data_directory.exists())
            data_directory.mkdir();

        if(!config_directory.exists())
            config_directory.mkdir();

        databse = data_directory.getPath()+"\\db.db";
        session = new Session(databse);
    }
    public boolean CreateDatabase(String password){
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
        try {
            Scanner file_reader = new Scanner(new FileInputStream(file));
            if(!file_reader.hasNextLine())
                return false;
            AES.setSecretKey("Non-Release placeholder");
            String secretKey = AES.decrypt(file_reader.nextLine());
            AES.setSecretKey(secretKey);
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }

    }


}
