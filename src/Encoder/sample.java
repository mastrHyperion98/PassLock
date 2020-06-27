package Encoder;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Iterator;

import org.database.Session;
import struct.Controller;

public class sample {
    public static void main(String[] args) throws IOException {
      struct.Controller controller = new Controller();
      controller.createAuthFile("Pokemon");
      print(controller.isAuthExist());
      print(controller.auth("Pokemon"));
    }

    public static void print(Object x){
        System.out.println(x);
    }
}
