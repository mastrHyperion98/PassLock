package com.mastrHyperion98.struct;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSV_Writer {
    public static void Write(File file, CSV document) throws IOException {
        // TODO: Create a new message with a progress bar for the import.
        // TODO: Convert the content below to a task
        FileWriter fw = new FileWriter(file);
        fw.write(document.toString() + "\n");
        fw.flush();
        fw.close();
    }
}
