package org.database;
import Encoder.AES;

import java.sql.*;
import java.util.Dictionary;
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
                    "\t\"Domain\"\tTEXT NOT NULL UNIQUE,\n" +
                    "\t\"Password\"\tTEXT NOT NULL,\n" +
                    "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                    ")";
            statement.execute(query);
            isCreated = true;
            writeEntry(domain,password);
        } catch (SQLException throwables) {
           isCreated = false;
        }

        return isCreated;
    }
    public boolean isOpen() throws SQLException {
        if (connection != null)
            return connection.isClosed();
        return false;
    }

    public void writeEntry(String domain, String password) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO Password (domain, password)"+
                " VALUES (?,?)");
        statement.setString(1, domain);
        statement.setString(2, password);
        statement.executeUpdate();
    }

    public Dictionary<String, String> fetchEntries() throws SQLException {
        Dictionary<String, String> dictionary = new Hashtable();

        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery("SELECT domain, password FROM Password where domain!=\"__master__\"");

        // loop and add to dictionary
        while (results.next()) {
            dictionary.put(results.getString(1), results.getString(2));
        }

        return dictionary;
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

/*

CREATE TABLE "Password" (
	"id"	INTEGER,
	"Domain"	TEXT NOT NULL,
	"Password"	TEXT NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT)
);
 */