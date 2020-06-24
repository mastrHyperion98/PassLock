package org.database;
import java.sql.*;
import java.util.Dictionary;

public class Connect {
    private String database;
    private Connection  connection = null;
    public Connect(String _database){
        try {
        database = _database;
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

    public void FetchEntries(){
        Dictionary
    }
}
