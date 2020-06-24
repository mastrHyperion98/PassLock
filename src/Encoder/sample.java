package Encoder;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Iterator;

import org.database.Connect;
public class sample {
    public static void main(String[] args) throws IOException {
        String message = "Jello is good!";
        String encrypted = AES.encrypt(message);
        String decrypted = AES.decrypt(encrypted);

        System.out.println(message);
        System.out.println(encrypted);
        System.out.println(decrypted);
        String file = "src/data/sqlite.db";
        Connect connect = new Connect(file);
        try {
                //connect.writeEntry("www.Facebook.com", "Panther98@123");
            Dictionary<String, String> results = connect.fetchEntries();
            Iterator<String> it = results.keys().asIterator();

            while (it.hasNext()){
                String key = it.next();
                System.out.println("Domain: " + key + "\nPassword: "+results.get(key));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
