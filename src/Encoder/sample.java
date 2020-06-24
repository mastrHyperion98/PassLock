package Encoder;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

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
                connect.writeEntry("www.Facebook.com", "Panther98@123");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
