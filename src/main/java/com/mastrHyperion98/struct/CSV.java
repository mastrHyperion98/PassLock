package com.mastrHyperion98.struct;

import java.util.Arrays;

public class CSV {
    private String[] header;
    private String[][] body;
    private int lines;
    public CSV(String[] header, String[][] body){
        this.header = header;
        this.body = body;
        lines= body.length;
    }

    public String[] getHeaders(){
        return header;
    }

    public String[][] getBody(){
        return body;
    }
    // get a specific row
    public String[] getRow(int row){
        if(row >= 0 && row < body.length){
            return body[row];
        }
        else{
            return new String[0];
        }
    }

    public int getLinesCount(){
        return lines;
    }

    @Override public String toString(){
        StringBuilder csv = new StringBuilder(String.join(",", header) + "\n");
        for(int line = 0; line <lines; line++){
            csv.append(String.join(",", body[line])).append("\n");
        }
        return csv.toString();
    }

    @Override public boolean equals(Object obj){
        if(obj instanceof CSV){
            if(Arrays.equals(header, ((CSV) obj).header)){
                return Arrays.deepEquals(body, ((CSV) obj).body);
            }
        }
        return false;
    }
}
