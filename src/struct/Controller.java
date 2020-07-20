package struct;

import Encoder.AES;
import org.database.Session;

import java.io.File;
import java.sql.SQLException;

public class Controller {
    private String databse;
    private boolean isAuth;
    private final Session session;
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
    }
    public boolean GenerateDatabase(String masterpassword){
        String password = AES.encrypt(masterpassword);

        exist = session.createTable(password);
        return exist;
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

    public final Session getSession(){
        return session;
    }
}
