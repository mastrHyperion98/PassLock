package com.mastrHyperion98.struct;

import com.mastrHyperion98.Encoder.AES;
import com.mastrHyperion98.org.database.Session;

import java.io.File;
import java.sql.SQLException;

public class Controller {
    private String databse;
    private final Session session;
    private boolean exist;
    public Controller(){
        String home = System.getProperty("user.home");
        File directory = new File(home + "/Signum");
        if(!directory.exists())
            directory.mkdir();

        databse = directory.getPath()+"\\sqlite.db";
        session = new Session(databse);
        exist = session.exist();
    }
    public boolean GenerateDatabase(){
        exist = session.createTable();
        return exist;
    }
    public boolean Exist(){
        return exist;
    }
    public final Session getSession(){
        return session;
    }
}
