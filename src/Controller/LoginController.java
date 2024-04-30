package Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;
import Database.MySQLDatabaseConnection;
import java.io.IOException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class LoginController  {

    public static String validateLogin(String name, String password) {
        try {
            Connection conn = MySQLDatabaseConnection.getConnection();

            String sql = "SELECT password, role FROM Users WHERE email=?"; // Updated SQL query
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name); // Assuming 'username' is the email address

            // Execute the query and check if the result set is not empty
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("password"); // Retrieve the password from the ResultSet
                String role = rs.getString("role"); // Retrieve the role from the ResultSet

                // Compare the provided password with the stored password
                if (password.equals(storedPassword)) {
                    return role;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
