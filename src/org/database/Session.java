package org.database;
import Encoder.AES;
import struct.Password;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Hashtable;

public class Session {
    private String database;
    private Connection  connection = null;
    public Session(String _database){
        database = _database;
        connect();
    }
    private void connect(){
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:"+database);
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean exist() {
        String query = "SELECT * FROM sqlite_master WHERE type='table' and name='Password'";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet results = statement.executeQuery(query);
            return results.next();
        } catch (SQLException throwables) {
            return false;
        }
    }

    public boolean createTable(String masterPassword){
        String domain = "__master__";
        String password = masterPassword;
        boolean isCreated;

        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE \"Password\" (\n" +
                    "\t\"id\"\tINTEGER,\n" +
                    "\t\"Email\"\tTEXT NOT NULL,\n" +
                    "\t\"Username\"\tTEXT NOT NULL,\n" +
                    "\t\"Domain\"\tTEXT NOT NULL UNIQUE,\n" +
                    "\t\"Password\"\tTEXT NOT NULL,\n" +
                    "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                    ")";
            statement.execute(query);
            isCreated = true;
            writeMasterEntry(domain,password);
        } catch (SQLException throwables) {
           isCreated = false;
           throwables.printStackTrace();
        }

        return isCreated;
    }
    public boolean isOpen() throws SQLException {
        if (connection != null)
            return connection.isClosed();
        return false;
    }

    public void writeEntry(String domain, String email, String username, String password) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO Password (domain, email, username, password)"+
                " VALUES (?,?,?,?)");
        statement.setString(1, domain);
        statement.setString(2, email);
        statement.setString(3, username);
        statement.setString(4, password);
        statement.executeUpdate();
    }

    public void writeEntry(String domain, String email, String password) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO Password (domain, email, password)"+
                " VALUES (?,?,?,?)");
        statement.setString(1, domain);
        statement.setString(2, email);
        statement.setString(3, "NA");
        statement.setString(4, password);
        statement.executeUpdate();
    }

    private void writeMasterEntry(String domain,String password) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO Password (domain, email, username, password)"+
                " VALUES (?,?,?,?)");
        statement.setString(1, domain);
        statement.setString(2, "NA");
        statement.setString(3, "NA");
        statement.setString(4, password);
        statement.executeUpdate();
    }

    public List<Password> fetchEntries() throws SQLException {
        List<Password> list = new LinkedList<>();

        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery("SELECT id, domain, email, username, password FROM Password where domain!=\"__master__\"");

        // loop and add to dictionary
        while (results.next()) {
            int id = results.getInt(1);
            String domain = results.getString(2);
            String email = results.getString(3);
            String username = results.getString(4);
            String password = results.getString(5);
            Password entry = new Password(id,domain,username,email,password);
            list.add(entry);
        }

        return list;
    }

    public Password fetchEntry(String domain) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery("SELECT id, domain, email, username, password FROM Password where domain=\""+domain+"\"");

        // loop and add to dictionary
        int id = results.getInt(1);
        String _domain = results.getString(2);
        String email = results.getString(3);
        String username = results.getString(4);
        String password = results.getString(5);
        Password entry = new Password(id,_domain,username,email,password);

        return entry;
    }

    public boolean isAuth(String password) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery("SELECT domain, password FROM Password where domain=\"__master__\"");

        // loop and add to dictionary
       if (results.next())
           return results.getString(2).equals(AES.encrypt(password));
       return false;
    }
}
