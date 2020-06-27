package org.database;
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
        ResultSet results = statement.executeQuery("SELECT domain, password FROM Password");

        // loop and add to dictionary
        while (results.next()) {
            dictionary.put(results.getString(1), results.getString(2));
        }

        return dictionary;
    }
}
