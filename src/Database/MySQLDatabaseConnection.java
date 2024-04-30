package Database;

import java.sql.*;
import java.util.*;
import java.io.*;

public class MySQLDatabaseConnection {

    // Attributes
    public static String mysql;
    public static String username;
    public static String password;

    // Connection
    public static Connection dbConn = null;

    public MySQLDatabaseConnection() {
        readMySQLProperties();
        // connect();
    }

    public static Connection getConnection() {
        if (dbConn != null)
            return dbConn;
        return getConnection(mysql, username, password);
    }

    /**
     * connect method
     * Connects to MySQL using the DriveManager
     * Username & url are retrieved from "dbConfig.txt" file - CHANGE THIS LATER
     * 
     * @return boolean
     */
    public static Connection getConnection(String mySQL, String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            dbConn = DriverManager.getConnection(mySQL, username, password);

            if (dbConn != null) {
                System.out.println("Connection has been established.");
                return dbConn;
            }

        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            return dbConn;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return dbConn;
        }
        return dbConn;
    }// connect method end

    /**
     * close method- closes the MySQL connection
     * 
     * @return boolean
     */
    public boolean close() {

        try {
            if (dbConn != null) {
                dbConn.close(); // Closing
                System.out.println("Connection has been closed.");
                return true;
            }
            return false;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        } catch (NullPointerException nex) {
            System.out.println(nex.getMessage());
            return false;
        }
    }// close method end

    /**
     * readMySQLProperties
     * Reads and then sets the necessary parameters for the connection from the
     * dbConfig.txt file located in the ./lib/ folder
     * Password is user inputted for security reasons; although, the steps to read
     * it are the same as the username & url
     * 
     * THIS NEEDS TO BE CHANGED LATER WITH FINAL RELEASE
     * 
     */
    public void readMySQLProperties() {
        try {
            Properties props = new Properties();
            FileInputStream in = new FileInputStream("FilmFinder/lib/dbConfig.txt");

            props.load(in);
            in.close();

            // Reading
            String url = props.getProperty("jdbc.url");
            String username = props.getProperty("jdbc.username");
            String password = props.getProperty("jdbc.password");

            // Set the properties
            this.setMySQL(url);
            this.setUsername(username);
            this.setPassword(password);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }// readMySQLProperties method end

    // ----------------- Getters/Accessors -------------------
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getMysql() {
        return mysql;
    }

    // public static Connection getConnection() {
    // return dbConn;
    // }

    // Setters/Mutators
    public void setUsername(String username) {
        MySQLDatabaseConnection.username = username;
    }

    public void setMySQL(String mysql) {
        MySQLDatabaseConnection.mysql = mysql;
    }

    public void setPassword(String password) {
        MySQLDatabaseConnection.password = password;
    }
}// MySQLDatabase class end
