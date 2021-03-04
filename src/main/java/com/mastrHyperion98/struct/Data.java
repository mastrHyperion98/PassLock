package com.mastrHyperion98.struct;
/*
Created by: Steven Smith
Created for: PasswordManager project @ https://github.com/mastrHyperion98/PasswordManager

Project under the GPL3 license.
Data is a object that holds the information necessary to display in the table view element.
 */
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class Data{
    private SimpleIntegerProperty _id;
    private SimpleStringProperty _domain;
    private SimpleStringProperty _username;
    private SimpleStringProperty _email;
    private SimpleStringProperty _password;


    public Data(int id, String domain, String username, String email, String password){
       _id = new SimpleIntegerProperty(id);
       _domain=new SimpleStringProperty(domain);
       _email=new SimpleStringProperty(email);
       _username = new SimpleStringProperty(username);
       _password = new SimpleStringProperty(password);
    }
    public Data(int id, String domain, String email, String password){
        _id = new SimpleIntegerProperty(id);
        _domain=new SimpleStringProperty(domain);
        _email=new SimpleStringProperty(email);
        _username = new SimpleStringProperty("NA");
        _password = new SimpleStringProperty(password);
    }

    public Data(SerializableData data){
        _id = new SimpleIntegerProperty(data.getId());
        _domain=new SimpleStringProperty(data.getDomain());
        _email=new SimpleStringProperty(data.getEmail());
        _username = new SimpleStringProperty(data.getUsername());
        _password = new SimpleStringProperty(data.getPassword());
    }

    // list of setters and getters for every field
    public int getId(){
        return _id.get();
    }

    public void setId(int id){
        _id.set(id);
    }

    public String getEmail(){
        return _email.get();
    }

    public void setEmail(String email){
        _email.set(email);
    }

    public String getDomain(){
        return _domain.get();
    }

    public void setDomain(String domain){
        _domain.set(domain);
    }

    public String getUsername(){
        return _username.get();
    }

    public void setUsername(String username){
        _username.set(username);
    }

    public String getPassword(){
        return _password.get();
    }

    public void setPassword(String password){
        _password.set(password);
    }
}
