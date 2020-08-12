package com.mastrHyperion98.org.database;
import com.mastrHyperion98.Encoder.AES;
import com.mastrHyperion98.struct.Password;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class Session {
    private String database;
    private Connection  connection = null;
    private String password;
    private boolean isAuthenticated;
    public Session(String _database){
        database = _database;
        password = "";
        isAuthenticated = Authenticate();
    }
    public Session(String _database, String _password){
        database = _database;
        password = _password;
        isAuthenticated = Authenticate();
    }
    private void connect(){
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:"+database);
            System.out.println("Connection Opened");
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void Access() throws IllegalAccessException {
        if(!Authenticate())
            throw new IllegalAccessException("Database validation not cleared. Incorrect or empty password");
        connect();
    }

    private void disconnect(){
        try {
            if(connection != null && !connection.isClosed()){
                connection.close();
                connection = null;
                System.out.println("Connection Closed");
            }
        } catch (SQLException throwables) {
            System.out.println("ERROR: Connection cannot be closed.");
            throwables.printStackTrace();
        }
    }

    public boolean createTable(){
        boolean isCreated;
        try {
            connect();
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS\"Password\" (\n" +
                    "\t\"id\"\tINTEGER,\n" +
                    "\t\"Email\"\tTEXT NOT NULL,\n" +
                    "\t\"Username\"\tTEXT NOT NULL,\n" +
                    "\t\"Domain\"\tTEXT NOT NULL UNIQUE,\n" +
                    "\t\"Password\"\tTEXT NOT NULL,\n" +
                    "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                    ")";
            statement.execute(query);
            disconnect();
            isCreated = writeMasterEntry();
        } catch (SQLException throwables) {
           disconnect();
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
        try {
            Access();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        PreparedStatement statement = connection.prepareStatement("INSERT INTO Password (domain, email, username, password)"+
                " VALUES (?,?,?,?)");
        statement.setString(1, domain);
        statement.setString(2, email);
        statement.setString(3, username);
        statement.setString(4, password);
        statement.executeUpdate();
        disconnect();
    }

    /**
     * @return true or false if the operation worked.
     */
    private boolean writeMasterEntry(){
        // make sure the master password is not empty
        if(password.equals(""))
            return false;

        boolean isSuccessful = false;
        String domain = "__master__";
        connect();
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Password (domain, email, username, password)" +
                    " VALUES (?,?,?,?)");
            statement.setString(1, domain);
            statement.setString(2, "NA");
            statement.setString(3, "NA");
            statement.setString(4, password);
            statement.executeUpdate();
            disconnect();
            isSuccessful = true;
        }catch (SQLException exception){
            disconnect();
        }

        return isSuccessful;
    }
    /**deleteEntry deletes a database entry with the given integer id.
     *
     * @param id the id of the password entry to remove
     * @return remove true if the operation is successful, false otherwise.
     */

    public boolean deleteEntry(int id){
        // id == 1 is always the master password. Master password cannot be removed.
        if(id==1)
            return false;
        try {
            Access();
            Statement statement = connection.createStatement();
            String query = "DELETE from PASSWORD where id="+id;
            statement.executeUpdate(query);
            disconnect();
            return true;
        } catch (SQLException throwables) {
            disconnect();

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return false;
    }

    /** editEntry takes a id, domain, email, username and password as inputs and updates the database field accordingly
     *
     * @param id is the id of the password entry to remove.
     * @param domain the domain name of the service.
     * @param email the email used for registration.
     * @param username  the username, if applicable, used for the service.
     * @param password  the unencrypted password used for the service.
     * @return
     */
    public boolean editEntry(int id, String domain, String email, String username, String password){
        // this method cannot edit master password
        if(id==1)
            return false;
        password=AES.encrypt(password);
        try {
            Access();
            PreparedStatement statement = connection.prepareStatement("UPDATE Password SET username=?, domain=?,email=?, password=? WHERE id=?");
            statement.setString(1, username);
            statement.setString(2, domain);
            statement.setString(3,email);
            statement.setString(4, password);
            statement.setInt(5, id);
            statement.executeUpdate();
            disconnect();
            return true;
        } catch (SQLException throwables) {
            disconnect();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return false;
    }
    public List<Password> fetchEntries() throws SQLException {
        try {
            Access();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
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
        disconnect();
        return list;
    }

    public Password fetchEntry(String domain) throws SQLException {
        if(domain == "__master__")
            return null;

        try {
            Access();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery("SELECT id, domain, email, username, password FROM Password where domain=\""+domain+"\"");

        // loop and add to dictionary
        int id = results.getInt(1);
        String _domain = results.getString(2);
        String email = results.getString(3);
        String username = results.getString(4);
        String password = results.getString(5);
        Password entry = new Password(id,_domain,username,email,password);
        disconnect();
        return entry;
    }

    private boolean Authenticate(){
        boolean isMatch = false;
        try {
            connect();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT domain, password FROM Password where domain=\"__master__\"");
            // loop and add to dictionary
            if (results.next())
                isMatch = results.getString(2).equals(password);
            disconnect();
            return isMatch;
        }catch(SQLException exception){
            disconnect();
            return  isMatch;
        }
    }

    public void SetPassword(String _password){
        password = _password;
    }

    public boolean Validate(){
        boolean isValid = false;

        try{
            connect();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT id, domain, email, username, password FROM Password where domain=\"__master__\"");
            isValid = results.next();
            disconnect();
        } catch (SQLException ex){
            disconnect();
            isValid = false;
        }

        return isValid;
    }
}
