package com.mastrHyperion98.struct;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CSV_Reader {
    public static void Write(File file) throws IOException {
        FileReader fr = new FileReader(file);
      /*  FileWriter fw = new FileWriter(file);
        String[][] body = document.getBody();
        String headers = String.join(",", document.getHeaders());
        fw.write(headers + "\n");
        for(int line = 0; line < document.getLinesCount()-1; line++){
            String content = String.join(",", body[line]);
            fw.write(content + "\n");
        }
        fw.flush();
        fw.close();*/
    }
}
