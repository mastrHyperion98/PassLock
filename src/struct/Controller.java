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
import java.util.Scanner;
import java.io.FileWriter;   // Import the FileWriter class

public class Controller {
    private String databse;
    private boolean isAuth;
    private Session session;
    public Controller(){
        isAuth = false;
        databse = "src/data/sqlite.db";
        session = new Session(databse);
        if(!session.exist())
            session.createTable("1234565678");
    }


}
