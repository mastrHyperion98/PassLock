package com.mastrHyperion98.struct;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class CSV_Reader {
    public static CSV Write(File file) throws IOException {
        Scanner fr = new Scanner(file);
        // get headers
        if(!fr.hasNextLine())
            return null;
        String[] headers = fr.nextLine().split(",");
        if(!validateFile(headers))
            return null;
        Queue<String[]> bodyLines = new LinkedList<>();

        while(fr.hasNextLine()){
            String[] line = fr.nextLine().split(",");
            if(line.length == headers.length)
                bodyLines.add(line);
        }

        String[][] body = new String[bodyLines.size()][headers.length];
        int row = 0;
        while(!bodyLines.isEmpty()){
            String[] line = bodyLines.poll();
            body[row] = line;
        }

        return new CSV(headers, body);
    }

    private static boolean validateFile(String[] headers){
        boolean isValid = false;

        if(headers.equals(new String[]{"domain", "username", "email", "password"})){
           isValid = true;
        }

        return isValid;
    }
}
