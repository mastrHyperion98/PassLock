package struct;/*
struct.Controller will contain the logic and commands executed by the JavaFX application.
 */

import Encoder.AES;
import org.database.Session;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;   // Import the FileWriter class

public class Controller {
    private String databse;
    private String sample_master = "123456789";
    private boolean isAuth;
    private Session session;
    private boolean exist;
    public Controller(){
        isAuth = false;
        String home = System.getProperty("user.home");
        File directory = new File(home + "/Signum");
        if(!directory.exists())
            directory.mkdir();

        databse = directory.getPath()+"\\sqlite.db";
        session = new Session(databse);
        exist = session.exist();
        try {
            List<Password> list = session.fetchEntries();

            Iterator<Password>  iter = list.iterator();
            while(iter.hasNext()){
                Password next = iter.next();
                System.out.println("id: " + next.getId() + "\nDomain: "+ next.getDomain()+
                        "\nEmail: " + next.getEmail() + "\nUsername: " + next.getUsername()+
                        "\nPassword: " + AES.decrypt(next.getPassword()));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public boolean GenerateDatabase(String masterpassword){
        String password = AES.encrypt(masterpassword);
        return session.createTable(password);
    }
    public boolean auth(String password){
        try {
            isAuth= session.isAuth(password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  isAuth;
    }

    public boolean Exist(){
        return exist;
    }

}
