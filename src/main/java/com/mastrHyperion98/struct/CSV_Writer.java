package com.mastrHyperion98.struct;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSV_Writer {
    public static void Write(File file, CSV document) throws IOException {
        FileWriter fw = new FileWriter(file);
        fw.write(document.toString() + "\n");
        fw.flush();
        fw.close();
    }
}
