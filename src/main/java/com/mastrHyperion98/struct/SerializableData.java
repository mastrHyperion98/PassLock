package com.mastrHyperion98.struct;

import java.io.Serializable;

public class SerializableData implements Serializable {
    private int id;
    private String domain;
    private String username;
    private String email;
    private String password;

    /*
    Constructor that creates a SerializableData from a non-SerializableData field
     */
    public SerializableData(Data data){
        id = data.getId();
        domain = data.getDomain();
        username = data.getUsername();
        email = data.getEmail();
        this.password = data.getPassword();
    }

    public int getId(){
        return id;
    }

    public String getDomain(){
        return domain;
    }

    public String getUsername(){
        return username;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }
}
