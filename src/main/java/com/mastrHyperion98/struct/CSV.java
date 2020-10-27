package com.mastrHyperion98.struct;

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
}
