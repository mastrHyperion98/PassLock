package com.mastrHyperion98.struct;

import com.mastrHyperion98.Encoder.AES;
import com.mastrHyperion98.org.database.Session;

import java.io.File;
import java.sql.SQLException;

public class Controller {
    private String databse;
    private final Session session;
    private String home;
    private File data_directory;
    private File config_directory;
    public Controller(){
        String home = System.getProperty("user.home");
        File data_directory = new File(home + "/PasswordManager/data");
        File config_directory = new File(home + "/PasswordManager/config");
        if(!data_directory.exists())
            data_directory.mkdir();

        if(!config_directory.exists())
            config_directory.mkdir();

        databse = data_directory.getPath()+"\\db.db";
        session = new Session(databse);
    }
    public boolean CreateDatabase(String password){
        if(!Validate())
            return false;
       return session.createTable();
    }

    public final Session getSession(){
        return session;
    }

    public boolean Validate(){
        return session.Validate();
    }


}
